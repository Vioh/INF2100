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
		return "<constant decl> on line " + lineNum;
	}
	
	public static ConstDecl parse(Scanner s) {
		enterParser("constant decl");
		s.test(nameToken);
		ConstDecl cd = new ConstDecl(s.curToken.id, s.curLineNum());
		
		s.skip(nameToken); s.skip(equalToken);
		cd.constant = Constant.parse(s);
		s.skip(semicolonToken);
		
		leaveParser("constant decl");
		return cd;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint(name + " = ");
		constant.prettyPrint();
		Main.log.prettyPrint(";");
	}
}