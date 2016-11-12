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
		
	}
}