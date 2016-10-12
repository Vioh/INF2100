package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class Negation extends Factor {
	Factor fac;
	
	public Negation(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<negation> on line " + lineNum;
	}
	
	public static Negation parse(Scanner s) {
		enterParser("negation");
		
		Negation ws = new Negation(s.curLineNum());
		
		s.skip(notToken);
		ws.fac = Factor.parse(s);
		leaveParser("negation");
		return ws;
	}
	
}