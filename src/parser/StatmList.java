package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;
import static scanner.TokenKind.*;


class StatmList extends PascalSyntax {
	ArrayList<Statement> stlist = new ArrayList<Statement>();
	
	public StatmList(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<statm-list> on line " + lineNum;
	}
	
	public static StatmList parse(Scanner s) {
		enterParser("statm-list");		
		StatmList stl = new StatmList(s.curLineNum());	
		do {
			stl.stlist.add(Statement.parse(s));
		}while(s.curToken.kind == semicolonToken);
		leaveParser("statm-list");
		return stl;
	}
	
}