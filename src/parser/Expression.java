package parser;
import main.*;
import scanner.*;

public class Expression extends PascalSyntax {
	SimpleExpr exprStart; // 1st expression
	SimpleExpr exprEnd;   // 2nd expression (optional)
	RelOperator opr;      // optional (like exprEnd)
	
	public Expression(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<expression> on line " + lineNum;
	}
	
	public static Expression parse(Scanner s) {
		enterParser("expression");
		Expression expr = new Expression(s.curLineNum());
		
		expr.exprStart = SimpleExpr.parse(s);
		if(s.curToken.kind.isRelOpr()) {
			expr.opr = RelOperator.parse(s);
			expr.exprEnd = SimpleExpr.parse(s);
		}
		leaveParser("expression");
		return expr;
	}
	
	@Override
	public void prettyPrint() {
		exprStart.prettyPrint();
		if(opr != null) {
			Main.log.prettyPrint(" ");
			opr.prettyPrint();
			Main.log.prettyPrint(" ");
			exprEnd.prettyPrint();
		}
	}
}