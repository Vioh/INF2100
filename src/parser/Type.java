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
		enterParser("type-decl");
		Type tdecl = null;
		switch(s.curToken.kind) {
		case arrayToken:
			tdecl = ArrayType.parse(s); break;
		default:
			tdecl = TypeName.parse(s); break;
		}
		leaveParser("type-decl");
		return tdecl;
	}
}