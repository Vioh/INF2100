package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;
import static scanner.TokenKind.*;

public class StatmList extends PascalSyntax {
	ArrayList<Statement> stmtList = new ArrayList<Statement>();
	
	public StatmList(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<statm list> on line " + lineNum;
	}
	
	public static StatmList parse(Scanner s) {
		enterParser("statm list");		
		StatmList stl = new StatmList(s.curLineNum());
		
		while(true) {
			stl.stmtList.add(Statement.parse(s));
			if(s.curToken.kind != semicolonToken) break;
			s.skip(semicolonToken);
		}		
		leaveParser("statm list");
		return stl;
	}
	
	@Override
	public void prettyPrint() {
		for(int i = 0; i < stmtList.size(); i++) {
			if(i != 0) Main.log.prettyPrintLn(";");
			stmtList.get(i).prettyPrint();
		}
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		for(Statement stmt : stmtList) stmt.check(curScope, lib);
	}

	@Override
	public void genCode(CodeFile f) {
		for(Statement stmt : stmtList) stmt.genCode(f);
	}
}