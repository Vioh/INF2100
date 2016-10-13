package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

abstract class UnsignedConstant extends Factor {
	
	public UnsignedConstant(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<unsigned-const> on line " + lineNum;
	}
	
	public static UnsignedConstant parse(Scanner s) {
		enterParser("unsigned-const");
		UnsignedConstant uc = null;
		switch(s.curToken.kind) {
		case nameToken:
			uc = NamedConst.parse(s); break;
		case intValToken:
			uc = NumberLiteral.parse(s); break;
		default:
			uc = CharLiteral.parse(s); break;
		}
		leaveParser("unsigned-const");
		return uc;
	}
}