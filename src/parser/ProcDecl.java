package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class ProcDecl extends PascalDecl {	
	ParamDeclList pdList; //optional
	Block block;
	
	public ProcDecl(String name, int lNum) {
		super(name, lNum);
	}
	
	@Override
	public String identify() {
		return "<proc decl> " + name + " on line " + lineNum;
	}
	
	public static ProcDecl parse(Scanner s) {
		enterParser("proc decl");
		s.skip(procedureToken); s.test(nameToken);
		ProcDecl proc = new ProcDecl(s.curToken.id, s.curLineNum());
		
		s.skip(nameToken);
		if(s.curToken.kind == leftParToken) {
			proc.pdList = ParamDeclList.parse(s);
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
		if(pdList != null) {
			Main.log.prettyPrint(" ");
			pdList.prettyPrint();
		}
		Main.log.prettyPrintLn(";");
		block.prettyPrint();
		Main.log.prettyPrint("; {" + this.name + "}");
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		curScope.addDecl(this.name, this);
		if(this.pdList != null) {
			this.pdList.check(curScope, lib);
		}
		this.block.check(curScope, lib);
	}
	
}