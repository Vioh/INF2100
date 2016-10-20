package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class Negation extends Factor {
	Factor fact;
	
	public Negation(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<negation> on line " + lineNum;
	}
	
	public static Negation parse(Scanner s) {
		enterParser("negation");
		Negation neg = new Negation(s.curLineNum());
		
		s.skip(notToken);
		neg.fact = Factor.parse(s);
		
		leaveParser("negation");
		return neg;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint("not ");
		fact.prettyPrint();
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		fact.check(curScope, lib);
	}
}