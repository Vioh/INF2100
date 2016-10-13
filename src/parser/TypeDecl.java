package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class TypeDecl extends PascalDecl {
	
	public TypeDecl(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<Type> on line " + lineNum;
	}
	
	public static TypeDecl parse(Scanner s) {
		enterParser("Type");
		TypeDecl tdecl = null;
		switch(s.curToken.kind) {
		case arrayToken:
			tdecl = ArrayType.parse(s); break;
		default:
			tdecl = TypeName.parse(s); break;
		}
		leaveParser("Type");
		return tdecl;
	}
}