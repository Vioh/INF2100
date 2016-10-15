package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class ParamDecl extends PascalDecl {
	TypeName type;
	
	public ParamDecl(String name, int lNum) {
		super(name, lNum);
	}
	
	@Override
	public String identify() {
		return "<param decl> " + name + " on line " + lineNum;
	}
	
	public static ParamDecl parse(Scanner s) {
		enterParser("param decl");
		s.test(nameToken);
		ParamDecl pd = new ParamDecl(s.curToken.id, s.curLineNum());
		
		s.skip(nameToken); s.skip(colonToken);
		pd.type = TypeName.parse(s);
		
		leaveParser("param decl");
		return pd;
	}

	@Override
	public void prettyPrint() {
		Main.log.prettyPrint(this.name + ": ");
		type.prettyPrint();
	}
}