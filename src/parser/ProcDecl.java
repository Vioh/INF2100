package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class ProcDecl extends PascalDecl {	
	ParamDeclList pdList;
	Block block;
	
	public ProcDecl(String name, int lNum) {
		super(name, lNum);
	}
	
	@Override
	public String identify() {
		return "<proc-decl> on line " + lineNum;
	}
	
	public static ProcDecl parse(Scanner s) {
		enterParser("proc-decl");
		
		s.skip(procedureToken);
		ProcDecl proc = new ProcDecl(s.curToken.id, s.curLineNum());
		s.skip(nameToken);
		
		if(s.curToken.kind == leftParToken) {
			proc.pdList = ParamDeclList.parse(s);
		}
		s.skip(semicolonToken);
		proc.block = Block.parse(s);
		s.skip(semicolonToken);
		
		leaveParser("proc-decl");
		return proc;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint("procedure " + this.name);
		if(pdList != null) pdList.prettyPrint();
		Main.log.prettyPrintLn(";");
		block.prettyPrint();
		Main.log.prettyPrint("; {" + this.name + "}");
	}
}