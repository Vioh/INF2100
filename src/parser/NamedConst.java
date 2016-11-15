package parser;
import main.*;
import scanner.Scanner;
import static scanner.TokenKind.*;

public class NamedConst extends UnsignedConstant {
	String name;
	ConstDecl declRef; // reference to the constant declaration
	
	public NamedConst(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<named constant> on line " + lineNum;
	}
	
	public static NamedConst parse(Scanner s) {
		enterParser("named constant");		
		NamedConst nc = new NamedConst(s.curLineNum());
		
		nc.name = s.curToken.id;
		s.skip(nameToken);
		
		leaveParser("named constant");
		return nc;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint(name);
	}
	
	@Override 
	public void check(Block curScope, Library lib) {
		PascalDecl pd = curScope.findDecl(name, this);
		if(!(pd instanceof ConstDecl)) 
			error(name + "is no constant!");
		declRef = (ConstDecl) pd;
		constVal = declRef.constVal;
		type = declRef.type;
	}
	
	@Override
	public void genCode(CodeFile f) {
		f.genInstr("", "movl", "$"+constVal+",%eax", "  "+constVal);
	}
}