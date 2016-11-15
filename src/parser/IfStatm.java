package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class IfStatm extends Statement {
	Expression condition;
	Statement thenStmt;
	Statement elseStmt; // optional
	
	public IfStatm(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<if-statm> on line " + lineNum;
	}
	
	public static IfStatm parse(Scanner s) {
		enterParser("if-statm");
		IfStatm ifst = new IfStatm(s.curLineNum());
		
		s.skip(ifToken);
		ifst.condition = Expression.parse(s);
		s.skip(thenToken);
		ifst.thenStmt = Statement.parse(s);
		
		if(s.curToken.kind == elseToken) {
			s.skip(elseToken);
			ifst.elseStmt = Statement.parse(s);
		}	
		leaveParser("if-statm");
		return ifst;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint("if ");
		condition.prettyPrint();
		
		Main.log.prettyPrintLn(" then"); Main.log.prettyIndent();
		thenStmt.prettyPrint(); Main.log.prettyOutdent();
		
		if(elseStmt != null) {
			Main.log.prettyPrintLn();
			Main.log.prettyPrintLn("else"); Main.log.prettyIndent();
			elseStmt.prettyPrint(); Main.log.prettyOutdent();
		}
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		condition.check(curScope, lib);
		condition.type.checkType(lib.boolType, "if-test", 
				this, "If-test is not Boolean!");
		thenStmt.check(curScope, lib);
		if(elseStmt != null) elseStmt.check(curScope, lib);
	}

	@Override
	void genCode(CodeFile f) {
		String elseLabel = null;
		String endLabel  = null;
		
		f.genInstr("", "", "", "Start if-statement");
		condition.genCode(f);
		f.genInstr("", "cmpl", "$0,%eax", "");
		
		if(elseStmt == null) {
			endLabel = f.getLocalLabel();
			f.genInstr("", "je", endLabel, "");
			thenStmt.genCode(f);
		} else{
			elseLabel = f.getLocalLabel();
			endLabel = f.getLocalLabel();
			f.genInstr("", "je", elseLabel, "");
			thenStmt.genCode(f);
			f.genInstr("", "jmp", endLabel, "");	
			f.genInstr(elseLabel, "", "", "");
			elseStmt.genCode(f);
		}
		f.genInstr(endLabel, "", "", "");
		f.genInstr("", "", "", "End if-statement");
	}
}