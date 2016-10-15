package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class Variable extends Factor {
	String name;
	Expression expr; //optional
	
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
}