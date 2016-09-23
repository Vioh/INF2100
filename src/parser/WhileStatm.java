package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;
	
class WhileStatm extends Statement {
	Expression expr;
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
		
		ws.expr = Expression.parse(s);
		s.skip(doToken);
		ws.body = Statement.parse(s);
		
		leaveParser("while-statm");
		return ws;
	}
}












