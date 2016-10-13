package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class Expression extends PascalSyntax {
	SimpleExpr exprStart; // 1st simple expr (must not be NULL)
	SimpleExpr exprEnd;   // 2nd simple expr (might be NULL)
	RelOperator opr;      // might be NULL
	
	public Expression(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<expression> on line " + lineNum;
	}
	
	private static boolean isRelOpr(Token tok) {
		TokenKind kind = tok.kind;
		return kind == equalToken || kind == notEqualToken ||
				kind == lessToken || kind == lessEqualToken ||
				kind == greaterToken || kind == greaterEqualToken;
	}
	
	public static Expression parse(Scanner s) {
		enterParser("expression");
		Expression expr = new Expression(s.curLineNum());
		
		expr.exprStart = SimpleExpr.parse(s);
		if(isRelOpr(s.curToken)) {
			expr.opr = RelOperator.parse(s);
			expr.exprEnd = SimpleExpr.parse(s);
		}
		leaveParser("expression");
		return ws;
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