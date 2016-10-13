package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class Variable extends Factor {
	String name;
	Expression expr;
	
	public Variable(int lNum) { 
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<variable> on line " + lineNum;
	}
	
	public static Variable parse(Scanner s) {
		enterParser("variable");	
		Variable v = new Variable(s.curLineNum());
		v.name = s.curToken.id;
		s.readNextToken();
		
		if(s.curToken.kind == leftBracketToken) {
			s.readNextToken(); //skipping the left bracket 
			v.expr = Expression.parse(s);
			s.skip(rightBracketToken);			
		}	
		leaveParser("variable");
		return v;
	}
}
