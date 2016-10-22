package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class ConstDecl extends PascalDecl {
	Constant constant;
	int constVal;
	
	public ConstDecl(String name, int lNum) {
		super(name, lNum);
	}
	
	@Override
	public String identify() {
		String ret = "<const decl> " + name;
		if(this.isInLibrary()) return ret + " in the library";
		return ret + " on line " + lineNum;
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
	
	@Override
	public void check(Block curScope, Library lib) {
		curScope.addDecl(name, this);
		constant.check(curScope, lib);
		constVal = constant.constVal;
		type = constant.type;
	}

	@Override
	public void checkWhetherAssignable(PascalSyntax where) {
		where.error("You cannot assign to a constant.");
	}

	@Override
	public void checkWhetherFunction(PascalSyntax where) {
		where.error(name + " is a constant, not a function.");
	}

	@Override
	public void checkWhetherProcedure(PascalSyntax where) {
		where.error(name + " is a constant, not a procedure.");
	}

	@Override
	public void checkWhetherValue(PascalSyntax where) {
		// Constant always has a value. Do nothing!
	}
} 