package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

abstract class Type extends PascalSyntax {
	public Type(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<Type> on line " + lineNum;
	}
	
	//TODO
	public static Type parse(Scanner s) {
		enterParser("type");
		Type t = null;
		switch(s.curToken.kind) {
		case arrayToken:
			t = ArrayType.parse(s); break;
		default:
			t = TypeName.parse(s); break;
		}
		leaveParser("type");
		return t;
	}
}