package parser;
import scanner.*;

public abstract class UnsignedConstant extends Factor {
	int constVal;
	types.Type type;
	
	public UnsignedConstant(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<unsigned constant> on line " + lineNum;
	}
	
	public static UnsignedConstant parse(Scanner s) {
		enterParser("unsigned constant");
		UnsignedConstant uc = null;
		
		switch(s.curToken.kind) {
		case intValToken:
			uc = NumberLiteral.parse(s); break;
		case charValToken:
			uc = CharLiteral.parse(s); break;
		case nameToken:
			uc = NamedConst.parse(s); break;
		default:
			s.testError("unsigned constant"); // expect an unsigned const here
		}
		s.readNextToken();
		leaveParser("unsigned constant");
		return uc;
	}
}