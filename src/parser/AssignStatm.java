package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class AssignStatm extends Statement {
	Variable var;
	Expression expr;
	
	public AssignStatm(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<assign-statm> on line " + lineNum;
	}
	
	public static AssignStatm parse(Scanner s) {
		enterParser("assign-statm");
		
		AssignStatm as = new AssignStatm(s.curLineNum());
		
		as.var = Variable.parse(s);
		s.skip(assignToken);
		as.expr = Expression.parse(s);
		
		leaveParser("assign-statm");
		return as;
	}
	
	
}