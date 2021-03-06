package scanner;
import main.Main;
import java.io.*;

public class Scanner {
	public Token curToken = null, nextToken = null; 
	private LineNumberReader srcFile = null;
	private String srcFileName;
	private String originalLine = ""; // can have upper-case
	private String srcLine = "";      // no upper-case is allowed here
	private int srcPos = 0;
	
	public Scanner(String fileName) {
		srcFileName = fileName;
		try {
			srcFile = new LineNumberReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			Main.error("Cannot read " + fileName + "!");
		}
		readNextToken(); 
		readNextToken();
	}
	
	public String identify() {
		return "Scanner reading " + srcFileName;
	}

	public int curLineNum() {
		return curToken.lineNum;
	}
	
	private int getFileLineNum() {
		return (srcFile!=null ? srcFile.getLineNumber() : 0);
	}
	
	private boolean isLetterAZ(char c) {
		return 'A'<=c && c<='Z' || 'a'<=c && c<='z';
	}
	
	private boolean isDigit(char c) {
		return '0'<=c && c<='9';
	}
	
	private boolean isSpace(char c) {
		return c == ' ' || c == '\t';
	}
	
	private void error(String message) {
		Main.error("Scanner error" + 
		    (getFileLineNum()>0 ? " on line "+getFileLineNum() : "") +
             ": " + message);
	}
	
	public void test(TokenKind t) {
		if (curToken.kind != t)
			testError(t.toString());
	}

	public void testError(String message) {
		Main.error(curLineNum(), 
			   "Expected a " + message +
			   " but found a " + curToken.kind + "!");
	}

	public void skip(TokenKind t) {
		test(t);  
		readNextToken();
	}
	
	private void readNextLine() {
		if (srcFile != null) {
			try {
				originalLine = srcFile.readLine();
				if (originalLine == null) {
					srcFile.close(); srcFile = null;
					originalLine = "";
				} else {
					originalLine += " ";
				}
				srcPos = 0;
			} catch (IOException e) {
				Main.error("Scanner error: unspecified I/O error!");
			}
		}
		if (srcFile != null) 
			Main.log.noteSourceLine(getFileLineNum(), originalLine);
		srcLine = originalLine.toLowerCase();
	}
	
	
	/** Reads the next token. Assumes that EOF-token has not been reached yet */
	public void readNextToken() {
		curToken = nextToken; 
		nextToken = null;
		char c;
		int k;

		// HANDLE WHITE SPACES AND COMMENTS
		while(true) {
			// Skip over white spaces, and handle EOF-token if needed:
			while(true) {
				if(srcPos >= srcLine.length()) {
					readNextLine();
					if(srcFile == null) {
						nextToken = new Token(TokenKind.eofToken,-1);
						Main.log.noteToken(nextToken); 
						return;
					}
				} 
				if(! isSpace(srcLine.charAt(srcPos))) break;
				srcPos++;
			}
			// Skip over comment:
			if(srcLine.charAt(srcPos) == '{') {
				handleComment(true); //bracket-style comment
			} else if(srcLine.substring(srcPos,srcPos+2).equals("/*")) {
				handleComment(false); //C-style comment
			}
			// Stop the loop if there are no white spaces left: 
			if(! isSpace(srcLine.charAt(srcPos))) break;
		}

		// MAIN SWITCH TO GET NEXT TOKEN
		if(isDigit(srcLine.charAt(srcPos))) {
			for(k = srcPos+1; k < srcLine.length(); k++) {
				c = srcLine.charAt(k);
				if(!isDigit(c)) break;
			}
			int num = Integer.parseInt(srcLine.substring(srcPos, k));
			nextToken = new Token(num, srcFile.getLineNumber());
			srcPos = k;
		}
		else if(isLetterAZ(srcLine.charAt(srcPos))) {
			for(k = srcPos+1; k < srcLine.length(); k++) {
				c = srcLine.charAt(k);
				if(!isLetterAZ(c) && !isDigit(c)) break;
			}
			String str = srcLine.substring(srcPos, k);
			nextToken = new Token(str, srcFile.getLineNumber());
			srcPos = k;
		}
		else {
			handleSymbol();
		}
		Main.log.noteToken(nextToken);
	}

	
	/** Processes symbols and create a token if there is a symbol token. */
	private void handleSymbol() {
		char c1 = srcLine.charAt(srcPos);
		char c2 = srcLine.charAt(srcPos+1);
		int cln = srcFile.getLineNumber();
		boolean isTwoCharToken = false;
		
		// MAIN SWITCH TO DETERMINE THE TYPE OF TOKEN
		if(c1 == '\'') {
			handleQuote();
			return;
		} 
		else if(c1 == '.') {
			if(c2 == '.') {
				nextToken = new Token(TokenKind.rangeToken, cln);
				isTwoCharToken = true;
			} else nextToken = new Token(TokenKind.dotToken, cln);
		} else if(c1 == ':') {
			if(c2 == '=') {
				nextToken = new Token(TokenKind.assignToken, cln);
				isTwoCharToken = true;
			} else nextToken = new Token(TokenKind.colonToken, cln);
		} else if(c1 == '>') {
			if(c2 == '=') {
				nextToken = new Token(TokenKind.greaterEqualToken, cln);
				isTwoCharToken = true;
			} else nextToken = new Token(TokenKind.greaterToken, cln); 
		} else if(c1 == '<') {
			if(c2 == '=') {
				nextToken = new Token(TokenKind.lessEqualToken, cln);
				isTwoCharToken = true;
			} else if (c2 == '>') {
				nextToken = new Token(TokenKind.notEqualToken, cln);
				isTwoCharToken = true;
			} else nextToken = new Token(TokenKind.lessToken, cln);
		}
		else if(c1 == '+') nextToken = new Token(TokenKind.addToken, cln);
		else if(c1 == '-') nextToken = new Token(TokenKind.subtractToken, cln);
		else if(c1 == '*') nextToken = new Token(TokenKind.multiplyToken, cln);
		else if(c1 == '=') nextToken = new Token(TokenKind.equalToken, cln);
		else if(c1 == '[') nextToken = new Token(TokenKind.leftBracketToken, cln);
		else if(c1 == ']') nextToken = new Token(TokenKind.rightBracketToken, cln);
		else if(c1 == '(') nextToken = new Token(TokenKind.leftParToken, cln);
		else if(c1 == ')') nextToken = new Token(TokenKind.rightParToken, cln);
		else if(c1 == ',') nextToken = new Token(TokenKind.commaToken, cln);
		else if(c1 == ';') nextToken = new Token(TokenKind.semicolonToken, cln);
		else error("Illegal character: '" + c1 + "'!");
		
		// UPDATE srcPos TO CORRECT VALUE
		if(isTwoCharToken) srcPos = srcPos + 2;
		else srcPos++;
	}
	
	
	/** Processes a char literal. */
	private void handleQuote() {
		boolean isLegal = false;
		int cln = srcFile.getLineNumber();
		if(++srcPos + 2 < srcLine.length()) {
			if(srcLine.substring(srcPos,srcPos+3).equals("'''")) {
				nextToken = new Token('\'', cln);
				srcPos = srcPos + 3;
				isLegal = true;
			} 
			else if(srcLine.charAt(srcPos) != '\'') {
				if(srcLine.charAt(srcPos+1) == '\'') {
					nextToken = new Token(originalLine.charAt(srcPos), cln);
					srcPos = srcPos + 2;
					isLegal = true;
				}
			}
		}
		if(!isLegal) error("Illegal char literal!");
	}
	
	
	/**
	 * Skips over a comment.
	 * @param isBracketComment true for curly-bracket style, false for C-style.
	 */
	private void handleComment(boolean isBracketComment) {
		int cln = srcFile.getLineNumber(); //current line number
		String errorMessage = "No end for comment starting on line " + cln;
		
		// SKIP OVER OPENING SYMBOLS
		if(isBracketComment) srcPos++;
		else srcPos = srcPos + 2;
		
		// MAIN LOOP FOR SEARCHING CLOSING SYMBOLS
		while(true) {
			// If the end of this line has been reached:
			if(srcPos >= srcLine.length() - 1) {
				readNextLine();
				if(srcFile == null) error(errorMessage);
				else continue;
			}
			// Handle bracket comment:
			if(isBracketComment) {
				if(srcLine.charAt(srcPos++) == '}') return;
				else continue;
			}
			// Handle C-style comment:
			if(srcLine.charAt(srcPos++) == '*') {
				if(srcLine.charAt(srcPos) == '/') {
					srcPos++; return;
				} else continue;
			}
		}
	}
}