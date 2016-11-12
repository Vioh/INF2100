package parser;
import main.*;
import scanner.*;

public class Expression extends PascalSyntax {
	SimpleExpr exprStart; // 1st expression
	SimpleExpr exprEnd;   // 2nd expression (optional)
	RelOperator opr;      // optional (like exprEnd)
	types.Type type;
	
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
	
	@Override
	public void check(Block curScope, Library lib) {
		exprStart.check(curScope, lib);
		type = exprStart.type;
		
		if(exprEnd != null) {
			exprEnd.check(curScope, lib);
			TokenKind oprType = opr.oprType;
			exprStart.type.checkType(exprEnd.type, oprType + " operands", this,
					"Operands to " + oprType + " are of different types!");
			type = lib.boolType;
		}
	}

	@Override
	public void genCode(CodeFile f) {
		exprStart.genCode(f);
		if(opr == null) return;
		f.genInstr("", "pushl", "%eax", "");
		exprEnd.genCode(f);
		f.genInstr("", "popl", "%ecx", "");
		f.genInstr("", "cmpl", "%eax,%ecx", "");
		f.genInstr("", "movl", "$0,%eax", "");
		
		String instr = null;
		switch(opr.oprType) {
		case equalToken:
			instr = "sete"; break;
		case notEqualToken:
			instr = "setne"; break;
		case lessToken:
			instr = "setl"; break;
		case lessEqualToken:
			instr = "setle"; break;
		case greaterToken:
			instr = "setg"; break;
		case greaterEqualToken:
			instr = "setge"; break;
		default:
			// will never execute
			break;
		}
		f.genInstr("", instr, "%al", "Test "+opr.oprType);
	}
}