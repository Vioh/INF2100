package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;
import static scanner.TokenKind.*;

class ConstDeclPart extends PascalSyntax {
	ArrayList<ConstDecl> decList = new ArrayList<ConstDecl>();
	
	public ConstDeclPart(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<const-decl-part> on line " + lineNum;
	}
	
	public static ConstDeclPart parse(Scanner s) {
		enterParser("const-decl-part");
		ConstDeclPart cdp = new ConstDeclPart(s.curLineNum());
		
		do {
			cdp.decList.add(ConstDecl.parse(s));
		} while(s.curToken.kind == nameToken);
		
		leaveParser("const-decl-part");
		return cdp;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint("const"); Main.log.prettyIndent();
		for(ConstDecl dec : decList) {
			Main.log.prettyPrintLn();
			dec.prettyPrint();
			Main.log.prettyPrint(";");
		}
	}
}