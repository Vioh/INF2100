package parser;
import scanner.*;

public abstract class Statement extends PascalSyntax {
	public Statement(int lNum) {
		super(lNum);
	}
	
	public static Statement parse(Scanner s) {
		enterParser("statement");
		Statement stmt = null;
		
		switch (s.curToken.kind) {
		case beginToken:
			stmt = CompoundStatm.parse(s); break;
		case ifToken:
			stmt = IfStatm.parse(s); break;
		case whileToken:
			stmt = WhileStatm.parse(s); break;	
		case nameToken:
			switch(s.nextToken.kind) {
			case assignToken:
			case leftBracketToken:
				stmt = AssignStatm.parse(s); break;
			default:
				stmt = ProcCallStatm.parse(s); break;
			} break;
		default:
			stmt = EmptyStatm.parse(s); break;
		}
		leaveParser("statement");
		return stmt;
	}
}