package parser;
import main.CodeFile;
import scanner.*;

public class EmptyStatm extends Statement {
	public EmptyStatm(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<empty-statm> on line " + lineNum;
	}
	
	public static EmptyStatm parse(Scanner s) {
		enterParser("empty statm");
		leaveParser("empty statm");
		return new EmptyStatm(s.curLineNum());
	}
	
	@Override
	public void prettyPrint() {
		// Print nothing for empty statement
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		// No need to check an empty statement
	}

	@Override
	public void genCode(CodeFile f) {
		// No code needs to be generated for an empty statement
	}
}