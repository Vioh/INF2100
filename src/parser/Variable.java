package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class Variable extends Factor {
	String name;
	Expression expr; //optional
	VarDecl varRef;
	types.Type type;
	
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
		PascalDecl pd = curScope.findDecl(name, this);
		if(!(pd instanceof VarDecl)) error(name + "is no variable!");
		varRef = (VarDecl) pd;
		if(expr == null) {
			type = varRef.type;
			return;
		} 
		// Check if this variable is a valid array access
		expr.check(curScope, lib);
		if(varRef.type instanceof types.ArrayType) {
			types.ArrayType array = (types.ArrayType) varRef.type;
			expr.type.checkType(array.indexType, "array index", this, 
					"Index to " + name + " has wrong type!");
			type = array.elemType;
		} else {
			error("Cannot index " + name + "; it is no array!");
		}
	}
}