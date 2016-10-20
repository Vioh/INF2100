package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class ParamDecl extends PascalDecl {
	TypeName typename;
	
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
		pd.typename = TypeName.parse(s);
		
		leaveParser("param decl");
		return pd;
	}

	@Override
	public void prettyPrint() {
		Main.log.prettyPrint(this.name + ": ");
		typename.prettyPrint();
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		curScope.addDecl(this.name, this);
		typename.check(curScope, lib);
		this.type = typename.type;
	}
}