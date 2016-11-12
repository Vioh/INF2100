package parser;
import main.*;
import scanner.*;

public class PrefixOperator extends PascalSyntax {
	TokenKind oprType;
	
	public PrefixOperator(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<prefix opr> on line " + lineNum;
	}
	
	public static PrefixOperator parse(Scanner s) {
		enterParser("prefix opr");
		PrefixOperator popr = new PrefixOperator(s.curLineNum());
		
		popr.oprType = s.curToken.kind;
		s.readNextToken();
		
		leaveParser("prefix opr");
		return popr;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint(oprType.toString());
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		// No need to check for a prefix operator
	}
	
	@Override
	public void genCode(CodeFile f) {
		// No code needs to be generated for an operator
	}
}