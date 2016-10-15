package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class VarDecl extends PascalDecl {
	Type type;
	
	public VarDecl(String name, int lNum) {
		super(name, lNum);
	}

	@Override
	public String identify() {
		return "<var decl> " + name + " on line " + lineNum;
	}
	
	public static VarDecl parse(Scanner s) {
		enterParser("var decl");
		s.test(nameToken);
		VarDecl vd = new VarDecl(s.curToken.id, s.curLineNum());
		
		s.skip(nameToken); s.skip(colonToken);
		vd.type = Type.parse(s);
		s.skip(semicolonToken);
		
		leaveParser("var decl");
		return vd;
	}
	
	@Override 
	public void prettyPrint() {
		Main.log.prettyPrint(this.name + ": ");
		type.prettyPrint();
		Main.log.prettyPrint(";");
	}
}