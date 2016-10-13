package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class VarDecl extends PascalDecl {
	TypeDecl td;
	String name;
	
	public VarDecl(int lNum) {
		super(lNum);
	}

	@Override
	public String identify() {
		return "<var-decl> on line " + lineNum;
	}
	
	public static VarDecl parse(Scanner s) {
		enterParser("var-decl");		
		VarDecl vd = new VarDecl(s.curLineNum());
		
		vd.name = s.curToken.id;
		s.skip(colonToken);
		vd.td = TypeDecl.parse(s);
		s.skip(semicolonToken);
	
		leaveParser("var-decl");
		return vd;
	}
	
}