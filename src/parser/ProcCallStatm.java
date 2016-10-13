package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;
import static scanner.TokenKind.*;

class ProcCallStatm extends Statement {
	String name;
	ArrayList<Expression> expressions;

	public ProcCallStatm(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<proccall-statm> on line " + lineNum;
	}
	
	public static ProcCallStatm parse(Scanner s) {
		enterParser("proccall-statm");
		
		ProcCallStatm pcs = new ProcCallStatm(s.curLineNum());
		pcs.name = s.curToken.id;
		s.readNextToken();
		
		if(s.curToken.kind == leftParToken) {
			s.readNextToken(); //skipping the left parenthesis 
			pcs.expressions = new ArrayList<Expression>();
			do {
				pcs.expressions.add(Expression.parse(s));
			}while(s.curToken.kind == commaToken);
			s.skip(rightParToken);			
		} else {
			pcs.expressions = null;
		}
		leaveParser("proccall-statm");
		return pcs;
	}
}