package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;
import java.util.HashMap;
import static scanner.TokenKind.*;

public class Block extends PascalSyntax {
	VarDeclPart vdp;   // optional
	ConstDeclPart cdp; // optional
	StatmList stmtList;
	ArrayList<ProcDecl> procAndFuncList = new ArrayList<ProcDecl>();
	HashMap<String,PascalDecl> decls = new HashMap<String,PascalDecl>();
	Block outerScope;
	
	public Block(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<block> on line " + lineNum;
	}
	
	public static Block parse(Scanner s) {
		enterParser("block");
		Block block = new Block(s.curLineNum());
		
		if(s.curToken.kind == constToken) {
			block.cdp = ConstDeclPart.parse(s);
		}
		if(s.curToken.kind == varToken) {
			block.vdp = VarDeclPart.parse(s);
		}	
		while(true) {
			if(s.curToken.kind == procedureToken) {
				block.procAndFuncList.add(ProcDecl.parse(s));
			} else if(s.curToken.kind == functionToken) {
				block.procAndFuncList.add(FuncDecl.parse(s));
			} else break;
		}
		s.skip(beginToken);
		block.stmtList = StatmList.parse(s);
		s.skip(endToken);
		
		leaveParser("block");
		return block;
	}
	
	@Override
	public void prettyPrint() {
		if(cdp != null) {
			cdp.prettyPrint();
			Main.log.prettyPrintLn();
		}
		if(vdp != null) {
			vdp.prettyPrint();
			Main.log.prettyPrintLn();
		}
		for(ProcDecl decl : procAndFuncList) {
			Main.log.prettyPrintLn();
			decl.prettyPrint();
			Main.log.prettyPrintLn(); 
		}
		if(!procAndFuncList.isEmpty()) Main.log.prettyPrintLn();
		Main.log.prettyPrintLn("begin"); Main.log.prettyIndent();
		stmtList.prettyPrint(); Main.log.prettyPrintLn();
		Main.log.prettyOutdent(); Main.log.prettyPrint("end");
	}
	
	public void addDecl(String id, PascalDecl pd) {
		if(decls.containsKey(id)) {
			pd.error(id + " declared twice in same block!");
		}
		decls.put(id, pd);
	}
	
	public PascalDecl findDecl(String id, PascalSyntax where) {
		PascalDecl pd = decls.get(id); // find in current scope
		if(pd != null) {
			Main.log.noteBinding(id, where, pd); 
			return pd;
		}
		if(outerScope != null) {
			return outerScope.findDecl(id, where);
		}
		where.error("Name " + id + " is unknown!");
		return null;
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		outerScope = curScope; // initialization of outer block
		if(cdp != null) cdp.check(this, lib);
		if(vdp != null) vdp.check(this, lib);
		for(ProcDecl proc : procAndFuncList) proc.check(this, lib);
		stmtList.check(this, lib);
	}
}