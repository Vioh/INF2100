package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;
	
public class WhileStatm extends Statement {
	Expression condition;
	Statement body;
	
	public WhileStatm(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<while-statm> on line " + lineNum;
	}
	
	public static WhileStatm parse(Scanner s) {
		enterParser("while-statm");
		WhileStatm ws = new WhileStatm(s.curLineNum());
		
		s.skip(whileToken);
		ws.condition = Expression.parse(s);
		s.skip(doToken);
		ws.body = Statement.parse(s);
		
		leaveParser("while-statm");
		return ws;
	}
	
	@Override 
	public void prettyPrint() {
		Main.log.prettyPrint("while "); condition.prettyPrint();
		Main.log.prettyPrintLn(" do"); Main.log.prettyIndent();
		body.prettyPrint(); Main.log.prettyOutdent();
	}

	@Override
	public void check(Block curScope, Library lib) {
		condition.check(curScope, lib);
		condition.type.checkType(lib.boolType, "while-test", this,
				"While-test is not Boolean.");
		body.check(curScope, lib);
	}

	@Override
	public void genCode(CodeFile f) {
		String testLabel = f.getLocalLabel();
		String endLabel  = f.getLocalLabel();
		
		f.genInstr(testLabel, "", "", "Start while-statement");
		condition.genCode(f);
		f.genInstr("", "cmpl", "$0,%eax", "");
		f.genInstr("", "je", endLabel, "");
		body.genCode(f);
		f.genInstr("", "jmp", testLabel, "");
		f.genInstr(endLabel, "", "", "End while-statement");
		
	}
}