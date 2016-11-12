package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;
import static scanner.TokenKind.*;

public class ConstDeclPart extends PascalSyntax {
	ArrayList<ConstDecl> cdList = new ArrayList<ConstDecl>();
	
	public ConstDeclPart(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<const decl part> on line " + lineNum;
	}
	
	public static ConstDeclPart parse(Scanner s) {
		enterParser("const decl part");
		ConstDeclPart cdp = new ConstDeclPart(s.curLineNum());
		
		s.skip(constToken);
		do {
			cdp.cdList.add(ConstDecl.parse(s));
		} while(s.curToken.kind == nameToken);
		
		leaveParser("const decl part");
		return cdp;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint("const "); Main.log.prettyIndent();
		for(ConstDecl dec : cdList) {
			Main.log.prettyPrintLn();
			dec.prettyPrint();
		}
		Main.log.prettyOutdent();
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		for(ConstDecl dec : cdList) dec.check(curScope, lib);
	}

	@Override
	public void genCode(CodeFile f) {
		// No code needs to be generated for a list of contant declarations
	}
}