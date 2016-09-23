package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class EmptyStatm extends Statement {
	public EmptyStatm(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<empty-statm> on line " + lineNum;
	}
	
	public static EmptyStatm parse(Scanner s) {
		enterParser("empty-statm");
		leaveParser("empty-statm");
		return new EmptyStatm(s.curLineNum());
	}
}