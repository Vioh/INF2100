package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class CompoundStatm extends Statement {
	StatmList stmtList;
	
	public CompoundStatm(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<compound-statm> on line " + lineNum;
	}
	
	public static CompoundStatm parse(Scanner s) {
		enterParser("compound statm");
		CompoundStatm cs = new CompoundStatm(s.curLineNum());
		
		s.skip(beginToken);
		cs.stmtList = StatmList.parse(s);
		s.skip(endToken);
		
		leaveParser("compound statm");
		return cs;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrintLn("begin"); Main.log.prettyIndent();
		stmtList.prettyPrint(); Main.log.prettyPrintLn();
		Main.log.prettyOutdent(); Main.log.prettyPrint("end");
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		stmtList.check(curScope, lib);
	}

	@Override
	public void genCode(CodeFile f) {
		stmtList.genCode(f);
	}
}