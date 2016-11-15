package parser;
import main.*;
import scanner.*;

public class RelOperator extends PascalSyntax {
	TokenKind oprType;
	
	public RelOperator(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<rel opr> on line " + lineNum;
	}
	
	public static RelOperator parse(Scanner s) {
		enterParser("rel opr");
		RelOperator ropr = new RelOperator(s.curLineNum());
		
		ropr.oprType = s.curToken.kind;
		s.readNextToken();
		
		leaveParser("rel opr");
		return ropr;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint(oprType.toString());
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		// No need to check an operator
	}
	
	@Override
	public void genCode(CodeFile f) {
		// No code needs to be generated for an operator
	}
}