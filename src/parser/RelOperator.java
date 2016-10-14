package parser;
import main.*;
import scanner.*;

class RelOperator extends PascalSyntax {
	TokenKind oprType;
	
	public RelOperator(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<Rel-opr> on line " + lineNum;
	}
	
	public static RelOperator parse(Scanner s) {
		enterParser("Rel-opr");
		RelOperator ropr = new RelOperator(s.curLineNum());
		ropr.oprType = s.curToken.kind;
		leaveParser("Rel-opr");
		return ropr;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint(oprType.toString());
	}
}