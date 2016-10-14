package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class VarDecl extends PascalDecl {
	Type td;
	
	public VarDecl(String name, int lNum) {
		super(name, lNum);
	}

	@Override
	public String identify() {
		return "<var-decl> on line " + lineNum;
	}
	
	public static VarDecl parse(Scanner s) {
		enterParser("var-decl");
		VarDecl vd = new VarDecl(s.curToken.id, s.curLineNum());

		s.skip(colonToken);
		vd.td = Type.parse(s);
		s.skip(semicolonToken);
	
		leaveParser("var-decl");
		return vd;
	}
	
	@Override 
	public void prettyPrint() {
		Main.log.prettyPrint(this.name + ": ");
		//TODO
	}
}