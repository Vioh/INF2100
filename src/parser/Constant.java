package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class Constant extends PascalSyntax {
	PrefixOperator prefix;
	UnsignedConstant uconstant;
	
	public Constant(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<constant> on line " + lineNum;
	}
	
	public static Constant parse(Scanner s) {
		enterParser("constant");
		Constant c = new Constant(s.curLineNum());
		
		if(s.curToken.kind == addToken
				|| s.curToken.kind == subtractToken) {
			c.prefix = PrefixOperator.parse(s);
		}
		c.uconstant = UnsignedConstant.parse(s);
		
		leaveParser("constant");
		return c;
	}
}