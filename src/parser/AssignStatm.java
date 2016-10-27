package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class AssignStatm extends Statement {
	Variable var;
	Expression expr;
	
	public AssignStatm(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<assign statm> on line " + lineNum;
	}
	
	public static AssignStatm parse(Scanner s) {
		enterParser("assign statm");
		AssignStatm assignment = new AssignStatm(s.curLineNum());
		
		assignment.var = Variable.parse(s);
		s.skip(assignToken);
		assignment.expr = Expression.parse(s);
		
		leaveParser("assign statm");
		return assignment;
	}
	
	@Override
	public void prettyPrint() {
		var.prettyPrint();
		Main.log.prettyPrint(" := ");
		expr.prettyPrint();
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		var.check(curScope, lib);
		expr.check(curScope, lib);
		
		// Check if the variable is assignable
		var.declRef.checkWhetherAssignable(this);
		
		// Check if the assignment is for an entire array
		if(var.type instanceof types.ArrayType)
			error("Assignment of arrays is not allowed.");
		
		// Check if the assignment to function happens outside the function
		if(var.declRef instanceof FuncDecl && 
				curScope.decls.containsValue(var.declRef)) {
			error("Assignment to '" + var.name 
					+ "' is only allowed inside its body!");
		}
		// Check if both sides of assignment are of the same type
		var.type.checkType(expr.type, ":=", this, 
				"Different types in assignment!");
	}
}