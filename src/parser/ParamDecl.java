package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class ParamDecl extends PascalDecl {
	TypeName type;
	
	public ParamDecl(String name, int lNum) {
		super(name, lNum);
	}
	
	@Override
	public String identify() {
		return "<param-decl> on line " + lineNum;
	}
	
	public static ParamDecl parse(Scanner s) {
		enterParser("param-decl");	
		ParamDecl pd = new ParamDecl(s.curToken.id, s.curLineNum());		
		s.skip(colonToken);
		pd.type = TypeName.parse(s);
		leaveParser("param-decl");
		return pd;
	}

	@Override
	public void prettyPrint() {
		Main.log.prettyPrint(name + ": ");
		type.prettyPrint();
	}
}
