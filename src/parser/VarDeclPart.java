package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;
import static scanner.TokenKind.*;

public class VarDeclPart extends PascalSyntax {
	ArrayList<VarDecl> vdList = new ArrayList<VarDecl>();
	
	public VarDeclPart(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<var decl part> on line " + lineNum;
	}
	
	public static VarDeclPart parse(Scanner s) {
		enterParser("var decl part");
		VarDeclPart vdp = new VarDeclPart(s.curLineNum());
		
		s.skip(varToken);
		do {
			vdp.vdList.add(VarDecl.parse(s));
		} while(s.curToken.kind == nameToken);
	
		leaveParser("var decl part");
		return vdp;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint("var"); Main.log.prettyIndent();
		for(VarDecl vd : vdList) {
			Main.log.prettyPrintLn();
			vd.prettyPrint();
		}
		Main.log.prettyOutdent();
	}

	@Override
	public void check(Block curScope, Library lib) {
		for(VarDecl vd: vdList) vd.check(curScope, lib);
	}
}