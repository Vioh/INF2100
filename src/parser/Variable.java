package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class Variable extends Factor {
	String name;
	Expression expr; // optional
	PascalDecl declRef;
	
	public Variable(int lNum) { 
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<variable> on line " + lineNum;
	}
	
	public static Variable parse(Scanner s) {
		enterParser("variable");	
		Variable var = new Variable(s.curLineNum());
		
		s.test(nameToken); var.name = s.curToken.id; s.skip(nameToken);		
		if(s.curToken.kind == leftBracketToken) {
			s.skip(leftBracketToken);
			var.expr = Expression.parse(s);
			s.skip(rightBracketToken);			
		}	
		leaveParser("variable");
		return var;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint(name);
		if(expr != null) {
			Main.log.prettyPrint("[");
			expr.prettyPrint();
			Main.log.prettyPrint("]");
		}
	}

	@Override
	public void check(Block curScope, Library lib) {
		declRef = curScope.findDecl(name, this);
		declRef.checkWhetherValue(this);
		type = declRef.type;
		if(expr == null) return; // nothing left to do
		
		// Check if this variable is a valid array access
		expr.check(curScope, lib);
		if(type instanceof types.ArrayType) {
			types.ArrayType array = (types.ArrayType) type;
			expr.type.checkType(array.indexType, "array index", this, 
					"Index to " + name + " has wrong type!");
			type = array.elemType;
		} else {
			error("You cannot index " + name + "; it is no array!");
		}
	}
	
	public void genCode(CodeFile f) {
		// Handle an array access
		if(expr != null) {
			expr.genCode(f);
			types.ArrayType t = (types.ArrayType) declRef.type;
			f.genInstr("", "subl", "$"+t.loLim+",%eax", "");
			f.genInstr("", "movl", (-4*declRef.declLevel)+"(%ebp),%edx", "");
			f.genInstr("", "leal", declRef.declOffset+"(%edx),%edx", "");
			f.genInstr("", "movl", "0(%edx,%eax,4),%eax", "  "+name+"[...]");
			return;
		}
		// Handle normal variable or parameter
		if(declRef instanceof VarDecl || declRef instanceof ParamDecl) {
			f.genInstr("", "movl", (-4*declRef.declLevel)+"(%ebp),%edx", "");
			f.genInstr("", "movl", declRef.declOffset+"(%edx),%eax", "  "+name);
			return;
		}
		// Handle the case when this is a constant
		if(declRef instanceof ConstDecl) {
			int val = ((ConstDecl) declRef).constVal;
			f.genInstr("", "movl", "$"+val+",%eax", "  "+val);
			return;
		}
		// PANIC! Something wrong with the compiler! At stage 4 of the project,
		// a Variable object can't be anything else other than the cases above.
		Main.panic("genCode() method of Variable class in the compiler");
	}
}