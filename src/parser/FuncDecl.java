package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class FuncDecl extends ProcDecl {
	TypeName type;
	
	public FuncDecl(String name, int lNum) {
		super(name, lNum);
	}
	
	@Override
	public String identify() {
		return "<func decl> " + name + " on line " + lineNum;
	}
	
	public static FuncDecl parse(Scanner s) {
		enterParser("func decl");
		s.skip(functionToken); s.test(nameToken);
		FuncDecl fd = new FuncDecl(s.curToken.id, s.curLineNum());
		
		s.skip(nameToken);
		if(s.curToken.kind == leftParToken) {
			fd.pdList = ParamDeclList.parse(s);
		}
		s.skip(colonToken);
		fd.type = TypeName.parse(s);
		s.skip(semicolonToken);
		fd.block = Block.parse(s);
		s.skip(semicolonToken);
		
		leaveParser("func-decl");
		return fd;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint("function " + this.name + " ");
		if(pdList != null) {
			pdList.prettyPrint();
		}
		Main.log.prettyPrint(": ");
		type.prettyPrint();
		Main.log.prettyPrintLn(";");
		block.prettyPrint();
		Main.log.prettyPrint("; {" + name + "}");
	}
}