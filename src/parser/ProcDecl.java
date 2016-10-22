package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class ProcDecl extends PascalDecl {	
	ParamDeclList pdl; // optional
	Block block;
	
	public ProcDecl(String name, int lNum) {
		super(name, lNum);
	}
	
	@Override
	public String identify() {
		String ret = "<func decl> " + name;
		if(this.isInLibrary()) return ret + " in the library";
		return ret + " on line " + lineNum;
	}
	
	public static ProcDecl parse(Scanner s) {
		enterParser("proc decl");
		s.skip(procedureToken); s.test(nameToken);
		ProcDecl proc = new ProcDecl(s.curToken.id, s.curLineNum());
		
		s.skip(nameToken);
		if(s.curToken.kind == leftParToken) {
			proc.pdl = ParamDeclList.parse(s);
		}
		s.skip(semicolonToken);
		proc.block = Block.parse(s);
		s.skip(semicolonToken);
		
		leaveParser("proc decl");
		return proc;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint("procedure " + this.name);
		if(pdl != null) {
			Main.log.prettyPrint(" ");
			pdl.prettyPrint();
		}
		Main.log.prettyPrintLn(";");
		block.prettyPrint();
		Main.log.prettyPrint("; {" + this.name + "}");
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		curScope.addDecl(name, this);
		if(pdl != null) {
			pdl.check(block, lib);
		}
		block.check(curScope, lib);
	}

	@Override
	public void checkWhetherAssignable(PascalSyntax where) {
		where.error("You cannot assign to a procedure.");
	}

	@Override
	public void checkWhetherFunction(PascalSyntax where) {
		where.error(name + " is a procedure, not a function.");
	}

	@Override
	public void checkWhetherProcedure(PascalSyntax where) {
		// This is a procedure. Do nothing!
	}

	@Override
	public void checkWhetherValue(PascalSyntax where) {
		where.error(name + " is a procedure name; "
				+ "it may not be used in an expression.");
	}
}