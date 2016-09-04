package scanner;

import main.Main;
import static scanner.TokenKind.*;

import java.io.*;

public class Scanner {
	public Token curToken = null, nextToken = null; 
	private LineNumberReader srcFile = null;
	private String srcFileName, srcLine = "";
	private int srcPos = 0;
	private final char[] SPECIAL_CHARS = ['=', '<', '>', '(', ')', '[', ']',
	                                      '+', '-', '*', '.', ',', ':', ';'];
	

	/* Parser tests:
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
	*/
	
	public Scanner(String fileName) {
		srcFileName = fileName;
		try {
			srcFile = new LineNumberReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			Main.error("Cannot read " + fileName + "!");
		}
		readNextToken(); readNextToken();
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

	private void error(String message) {
		Main.error("Scanner error on " +
		           (curLineNum()<0 ? "last line" : "line "+curLineNum()) + 
		           ": " + message);
	}

	
	public void readNextToken() {
		if(srcFile == null) return; //maybe give an error?
		int k;
		char x, c;
		curToken = nextToken; 
		nextToken = null;
		
		// SKIP OVER WHITE SPACES, HANDLE EOF-TOKEN IF NEEDED
		while(isSpace(srcLine.charAt(srcPos))) {
			if(++srcPos == srcLine.length()) {
				readNextLine();
				if(srcFile == null) {
					nextToken = new Token(TokenKind.eofToken,0);
					Main.log.noteToken(nextToken); return;
				}
			}
		}
		// SKIP OVER COMMENTS
		boolean commentError = false;
		if(srcLine.charAt(srcPos) == '{') {
			commentError = handleComment(true);
		} else if(srcLine.substring(srcPos,srcPos+1).equals("/*") {
			commentError = handleComment(false);
		} 
		if(commentError == true) return;
		
		// MAIN CONDITIONAL CONTROL STRUCTURE FOR GETTING NEXT TOKEN
		if(isDigit(x)) {
			for(k = srcPos+1; k < srcLine.length(); k++) {
				c = srcLine.charAt(k);
				if(!isDigit(c)) break;
			}
			int num = Integer.parseInt(srcLine.substring(srcPos, k));
			nextToken = new Token(num, srcLine.getLineNumber());
		}
		else if(isLetterAZ(x)) {
			for(k = srcPos+1; k < srcLine.length(); k++) {
				c = srcLine.charAt(k);
				if(!isLetterAZ(c) && !isDigit(c)) break;
			}
			String str = srcLine.substring(srcPos, k);
			nextToken = new Token(str, srcLine.getLineNumber());
		}
		//-------------------------------------- CONTINUE HERE

		Main.log.noteToken(nextToken);
	}


	private void readNextLine() {
		if (srcFile != null) {
			try {
				srcLine = srcFile.readLine();
				if (srcLine == null) {
					srcFile.close(); srcFile = null;
					srcLine = "";
				} else {
					srcLine += " ";
				}
				srcPos = 0;
			} catch (IOException e) {
				Main.error("Scanner error: unspecified I/O error!");
			}
		}
		if (srcFile != null) 
			Main.log.noteSourceLine(getFileLineNum(), srcLine);
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
	
	private boolean isValidChar(char c) {
		
	}
	
	private boolean isCommentChar(char c) {
		return c == '/' || c == '{'
	}
	
	/**
	 * Skips over a comment.
	 * @param isBracketComment true for curly-bracket style, false for C-style.
	 * @return true if the comment is successfully skipped over,
	 *         false if scanner error occurs due to absence of closing symbols.
	 */
	private void handleComment(boolean isBracketComment) {
		int cln = curLineNum();
		String errorMessage = "No end for comment starting on line " + cln;
		
		// SKIP OVER OPENING SYMBOLS
		if(isBracketComment) srcPos++;
		else srcPos = srcPos + 2;
		
		// MAIN LOOP FOR SEARCHING CLOSING SYMBOLS
		while(true) {
			// If the end of this line has been reached:
			if(srcPos == srcLine.length() - 1) {
				readNextLine();
				if(srcFile == null) {
					error(errorMessage);
					return false;
				} else continue;
			}
			// Handle bracket comment:
			if(isBracketComment) {
				if(srcLine.charAt(srcPos++) == '}') return true;
			}
			// Handle C-style comment:
			else if(srcLine.charAt(srcPos++) == '*') {
				if(srcLine.charAt(srcPos) == '/') return true;
			}
		}
	}
}
