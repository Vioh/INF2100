package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

import java.util.ArrayList;

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
} 