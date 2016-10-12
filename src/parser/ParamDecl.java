package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class ParamDecl extends PascalDecl {
	String name;
	//TYPENAME Class Variable
	//MIGZ
	
	public ParamDecl(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<param-decl> on line " + lineNum;
	}
	
	public static ParamDecl parse(Scanner s) {
		enterParser("param-decl");
		
		ParamDecl pd = new ParamDecl(s.curLineNum());
		this.name = s.curToken.id;
		
		
		//TYPE NAME CLASS MAYBE NEEDED
		
		leaveParser("param-decl");
		return pd;
	}
}
}