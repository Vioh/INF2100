package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class InnerExpr extends Factor {
	Expression expr;
	
	public InnerExpr(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<inner expr> on line " + lineNum;
	}
	
	public static InnerExpr parse(Scanner s) {
		enterParser("inner expr");
		InnerExpr ie = new InnerExpr(s.curLineNum());
		
		s.skip(leftParToken);
		ie.expr = Expression.parse(s);
		s.skip(rightParToken);
		
		leaveParser("inner expr");
		return ie;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint("(");
		expr.prettyPrint();
		Main.log.prettyPrint(")");
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		expr.check(curScope, lib);
		type = expr.type;
	}
	
	@Override
	public void genCode(CodeFile f) {
		expr.genCode(f);
	}
}