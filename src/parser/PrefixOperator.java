package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class PrefixOperator extends PascalSyntax {
	TokenKind oprType;
	
	public PrefixOperator(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<prefix-opr> on line " + lineNum;
	}
	
	public static PrefixOperator parse(Scanner s) {
		enterParser("prefix-opr");
		PrefixOperator popr = new PrefixOperator(s.curLineNum());
		popr.oprType = s.curToken.kind;
		leaveParser("prefix-opr");
		return popr;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint(oprType.toString());
	}
}