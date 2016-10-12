package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

/* 
<statement> ::= <assign statm> | <compound statm> | <empty statm> |
                <if statm> | <proc call> | <while-statm>
*/

abstract class Statement extends PascalSyntax {
	public Statement(int lNum) {
		super(lNum);
	}
	//LOOK AT THIS METHOD AGAIN!!!! 
	@Override
	public String identify() {
		return "<statement> on line " + lineNum;
	}
	
	public static Statement parse(Scanner s) {
		enterParser("statement");
		
		Statement st = null;
		switch (s.curToken.kind) {
		case beginToken:
			st = CompoundStatm.parse(s); break;
		case ifToken:
			st = IfStatm.parse(s); break;
		case whileToken:
			st = WhileStatm.parse(s); break;	
		case nameToken:
			switch(s.nextToken.kind) {
			case assignToken:
			case leftBracketToken:
				st = AssignStatm.parse(s); break;
			default:
				st = procCallStatm.parse(s); break;
			} break;
		default:
			st = EmptyStatm.parse(s); break;
		}
		
		leaveParser("statement");
		return st;
	}
}