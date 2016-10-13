package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class ParamDecl extends PascalDecl {
	//SOME METHODS MUST BE IMPLEMENTED
	String name;
	TypeName type;
	
	
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
		pd.name = s.curToken.id;		
		s.skip(colonToken);
		pd.type = TypeName.parse(s);
		leaveParser("param-decl");
		return pd;
	}

	@Override
	void checkWhetherAssignable(PascalSyntax where) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void checkWhetherFunction(PascalSyntax where) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void checkWhetherProcedure(PascalSyntax where) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void checkWhetherValue(PascalSyntax where) {
		// TODO Auto-generated method stub
		
	}
}
