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
		
		Main.log.prettyPrintLn(" then "); Main.log.prettyIndent();
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
		thenStmt.check(curScope, lib);
		if(elseStmt != null) elseStmt.check(curScope, lib);
		
		// Check type of if-condition
		condition.type.checkType(lib.boolType, "if-test", 
				this, "If-test is not Boolean!");
	}
}