package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class Variable extends Factor {
	String name;
	Expression expr; //optional
	VarDecl varRef;
	
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
		
		if(expr != null) {
			expr.check(curScope, lib);
			varRef.type.checkType(lib.arrayType, "array index", this, 
					"Cannot index " + name + "; it is no array!");
			parser.ArrayType aa = (parser.ArrayType) varRef.typeFromParser;
			aa.elemType.checkType(expr.type, op, this, mess);
			
//			expr.type.checkType(, op, where, mess);
		}


		
//		type = varRef.type;
	}
}