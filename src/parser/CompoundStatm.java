package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class CompoundStatm extends Statement {
	StatmList stml;
	
	public CompoundStatm(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<compound-statm> on line " + lineNum;
	}
	
	public static CompoundStatm parse(Scanner s) {
		enterParser("compound-statm");
		
		CompundStatm cs = new CompoundStatm(s.curLineNum());
		
		s.skip(beginToken);
		cs.stml = StatmList.parse(s);
		s.skip(endToken);
		
		leaveParser("compound-statm");
		return cs;
	}
}