package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;
import static scanner.TokenKind.*;

class Block extends PascalSyntax {
	ConstDeclPart cdp;
	VarDeclPart vdp;
	StatmList stmList;
	ArrayList<ProcDecl> procList;
	
	public Block(int lNum) {
		super(lNum);
		procList = new ArrayList<ProcDecl>();
	}
	
	@Override
	public String identify() {
		return "<block> on line " + lineNum;
	}
	
	public static Block parse(Scanner s) {
		enterParser("block");
		Block b = new Block(s.curLineNum());
		
		if(s.curToken.kind == constToken) {
			b.cdp = ConstDeclPart.parse(s);
		}
		if(s.curToken.kind == varToken) {
			b.vdp = VarDeclPart.parse(s);
		}
		
		while(true) {
			if(s.curToken.kind == procedureToken) {
				b.procList.add(ProcDecl.parse(s));
			} else if(s.curToken.kind == functionToken) {
				b.procList.add(FuncDecl.parse(s));
			} else break;
		}
		
		s.skip(beginToken);
		b.stmList = StatmList.parse(s);
		s.skip(endToken);
		
		leaveParser("block");
		return b;
	}
	
	@Override
	public void prettyPrint() {
		if(cdp != null) {
			cdp.prettyPrint();
			Main.log.prettyPrintLn(); Main.log.prettyPrintLn();
		}
		if(vdp != null) {
			vdp.prettyPrint();
			Main.log.prettyPrintLn(); Main.log.prettyPrintLn();
		}
		for(ProcDecl dec : procList) {
			dec.prettyPrint();
			Main.log.prettyPrintLn(); Main.log.prettyPrintLn();
		}
		Main.log.prettyPrintLn("begin"); Main.log.prettyIndent();
		stmList.prettyPrint(); Main.log.prettyPrintLn();
		Main.log.prettyOutdent(); Main.log.prettyPrint("end");
	}
}