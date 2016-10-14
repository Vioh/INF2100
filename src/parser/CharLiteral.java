package parser;
import main.*;
import scanner.*;

class CharLiteral extends UnsignedConstant {
	char literal;
	
	public CharLiteral(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<char-literal> on line " + lineNum;
	}
	
	public static CharLiteral parse(Scanner s) {
		enterParser("char-literal");
		CharLiteral cl = new CharLiteral(s.curLineNum());
		cl.literal = s.curToken.charVal;
		leaveParser("char-literal");
		return cl;
	}
	
	@Override
	public void prettyPrint() {
		String str = "'" + literal + "'";
		if(literal == '\'') str += "'";
		Main.log.prettyPrint(str);
	}	
} 