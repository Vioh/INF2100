package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class ConstDecl extends PascalDecl {
	Constant constant;
	
	public ConstDecl(String name, int lNum) {
		super(name, lNum);
	}
	
	@Override
	public String identify() {
		return "<const decl> " + name + " on line " + lineNum;
	}
	
	public static ConstDecl parse(Scanner s) {
		enterParser("const decl");
		s.test(nameToken);
		ConstDecl cd = new ConstDecl(s.curToken.id, s.curLineNum());
		
		s.skip(nameToken); s.skip(equalToken);
		cd.constant = Constant.parse(s);
		s.skip(semicolonToken);
		
		leaveParser("const decl");
		return cd;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint(name + " = ");
		constant.prettyPrint();
		Main.log.prettyPrint(";");
	}
}