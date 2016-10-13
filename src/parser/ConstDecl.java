package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class ConstDecl extends PascalDecl {
	String name;
	Constant constant;
	
	public ConstDecl(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<constant-decl> on line " + lineNum;
	}
	
	public static ConstDecl parse(Scanner s) {
		enterParser("constant-decl");
		
		ConstDecl cd = new ConstDecl(s.curLineNum());
		
		cd.name = s.curToken.id;
		s.skip(equalToken);
		cd.constant = Constant.parse(s);
		s.skip(semicolonToken);
		
		leaveParser("constant-decl");
		return cd;
	}
	
}