package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;
import static scanner.TokenKind.*;

class FactorOperator extends PascalSyntax {
	TokenKind oprType;
	
	public FactorOperator(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<factor-opr> on line " + lineNum;
	}
	
	public static FactorOperator parse(Scanner s) {
		enterParser("factor-opr");
		FactorOperator fopr = new FactorOperator(s.curLineNum());
		fopr.oprType = s.curToken.kind;
		leaveParser("factor-opr");
		return fopr;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint(oprType.toString());
	}
}
