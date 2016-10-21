package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class CharLiteral extends UnsignedConstant {
	char character;
	int constVal;
	types.Type type;
	
	public CharLiteral(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<char literal> on line " + lineNum;
	}
	
	public static CharLiteral parse(Scanner s) {
		enterParser("char literal");		
		CharLiteral cl = new CharLiteral(s.curLineNum());
		
		cl.character = s.curToken.charVal;
		s.skip(charValToken);
		
		leaveParser("char literal");
		return cl;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint("'" + character + "'");
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		type = lib.charType;
		constVal = (int) character;
	}
}
