package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class ProcDecl extends PascalDecl {	
	String name;
	ParamDecl parDecl;
	Block block;
	
	public ProcDecl(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<proc-decl> on line " + lineNum;
	}
	
	public static ProcDecl parse(Scanner s) {
		enterParser("proc-decl");
		ProcDecl procDecl = new ProcDecl(s.curLineNum());
		s.skip(procedureToken);
		procDecl.name = s.curToken.id;
		if(s.curToken.kind == leftParToken) {
			procDecl.parDecl = ParamDecl.parse(s);
		}
		s.skip(semicolonToken);
		procDecl.block = Block.parse(s);
		s.skip(semicolonToken);
		leaveParser("proc-decl");
		return procDecl;
	}
}