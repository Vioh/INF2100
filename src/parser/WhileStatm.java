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
}