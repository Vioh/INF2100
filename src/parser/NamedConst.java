package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class NamedConst extends UnsignedConstant {
	String name;
	
	public NamedConst(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<name-constant> on line " + lineNum;
	}
	
	public static NamedConst parse(Scanner s) {
		enterParser("name-constant");
		NamedConst nc= new NamedConst(s.curLineNum());
		nc.name = s.curToken.id;
		leaveParser("name-constant");
		return nc;
	}
}