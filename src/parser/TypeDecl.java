package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class TypeDecl extends PascalDecl {
	public TypeDecl(String name, int lNum) {
		super(name, lNum);
	}
	
	@Override
	public String identify() {
		return "<Type> on line " + lineNum;
	}
	
	//TODO
	public static TypeDecl parse(Scanner s) {
//		enterParser("type-decl");
//		TypeDecl tdecl = null;
//		switch(s.curToken.kind) {
//		case arrayToken:
//			tdecl = ArrayType.parse(s); break;
//		default:
//			tdecl = TypeName.parse(s); break;
//		}
//		leaveParser("type-decl");
//		return tdecl;
	}
	
	@Override
	public void prettyPrint() {
		
	}
}