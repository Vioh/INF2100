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
		if(var.declRef instanceof FuncDecl) {
			FuncDecl func = (FuncDecl) var.declRef;
			if(func.block.decls.containsValue(func)) 
				error("Assignment to '" + var.name 
						+ "' is only allowed inside its body!");
		}
		// Check if both sides of assignment are of the same type
		var.type.checkType(expr.type, ":=", this, 
				"Different types in assignment!");
	}

	@Override
	public void genCode(CodeFile f) {
		// Create some aliases
		Expression e1 = var.expr;
		Expression e2 = this.expr;
		PascalDecl pd = var.declRef;
		int offset = pd.declOffset;
		int level = pd.declLevel;
		
		// Evaluate the RHS of the assignment
		e2.genCode(f);
		
		if(pd instanceof FuncDecl) {
			// Assigning to a function (as a return statement)
			f.genInstr("", "movl", (-4*(level+1))+"(%ebp),%edx", "");
			f.genInstr("", "movl", "%eax,-32(%edx)", pd.name+" :=");
			return;
		}
		if(e1 == null) {
			// Assigning to a normal variable (non-array type) 
			f.genInstr("", "movl", (-4*level)+"(%ebp),%edx", "");
			f.genInstr("", "movl", "%eax,"+ offset+"(%edx)", pd.name+" :=");
		} else { 
			// Assigning to a variable inside an array
			f.genInstr("", "pushl", "%eax", "");
			e1.genCode(f);
			int low = ((types.ArrayType) pd.type).loLim;
			if(low != 0) f.genInstr("", "subl", "$"+low, "");
			f.genInstr("", "movl", (-4*level)+"(%ebp),%edx", "");
			f.genInstr("", "leal", offset+"(%edx),%edx", "");
			f.genInstr("", "popl", "%ecx", "");
			f.genInstr("", "movl", "%ecx,0(%edx,%eax,4)", pd.name+"[x] :=");
		}
	}
}