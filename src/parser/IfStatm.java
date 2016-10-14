package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class IfStatm extends Statement {
	Expression expr;     // not NULL
	Statement thenStatm; // not NULL
	Statement elseStatm;
	
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
		
		ifst.expr = Expression.parse(s);
		s.skip(thenToken);
		ifst.thenStatm = Statement.parse(s);
		
		if(s.curToken.kind == elseToken) {
			s.readNextToken();
			ifst.elseStatm = Statement.parse(s);
		} else {
			ifst.elseStatm = null;
		}		
		leaveParser("if-statm");
		return ifst;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint("if ");
		expr.prettyPrint();
		
		Main.log.prettyPrintLn(" then "); Main.log.prettyIndent();
		thenStatm.prettyPrint();
		Main.log.prettyOutdent();
		
		if(elseStatm != null) {
			Main.log.prettyPrintLn();
			Main.log.prettyPrintLn("else"); Main.log.prettyIndent();
			elseStatm.prettyPrint();
			Main.log.prettyOutdent();
		}
	}
}