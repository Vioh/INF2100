package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class TermOperator extends Operator {
	TokenKind oprType;
	
	public TermOperator(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<term-opr> on line " + lineNum;
	}
	
	public static TermOperator parse(Scanner s) {
		enterParser("term-opr");
		TermOperator topr = new TermOperator(s.curLineNum());
		topr.oprType = s.curToken.kind;
		leaveParser("term-opr");
		return topr;
	}
}