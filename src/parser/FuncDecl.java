package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class FuncDecl extends ProcDecl {
	String name;
	ParamDeclList pdl; // might be NULL
	TypeName type;
	Block block;
	
	public FuncDecl(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<func-decl> on line " + lineNum;
	}
	
	public static FuncDecl parse(Scanner s) {
		enterParser("func-decl");
		FuncDecl fd = new FuncDecl(s.curLineNum());
		
		s.skip(functionToken);
		fd.name = s.curToken.id;
		s.readNextToken();
		if(s.curToken.kind == leftParToken) {
			fd.pdl = ParamDeclList.parse(s);
		}
		s.skip(colonToken);
		fd.type = TypeName.parse(s);
		s.skip(semicolonToken);
		fd.block = Block.parse(s);
		s.skip(semicolonToken);
		
		leaveParser("func-decl");
		return fd;
	}
}