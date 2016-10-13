package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class NumberLiteral extends UnsignedConstant {
	int numliteral;
	
	public NumberLiteral(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<number-literal> on line " + lineNum;
	}
	
	public static NumberLiteral parse(Scanner s) {
		enterParser("number-literal");
		NumberLiteral nl = new NumberLiteral(s.curLineNum());
		nl.numliteral = s.curToken.intVal;
		leaveParser("number-literal");
		return nl;
	}
}