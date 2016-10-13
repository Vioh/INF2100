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
		this.name = s.curToken.id;
		s.readNextToken();
		
		if(s.test(leftParToken)) {
			s.readNextToken(); //skipping the left parenthesis 
			pcs.expressions = new ArrayList<Expression>();
			while(true) {
				pcs.expression.add(Expression.parse(s));
				if(s.test(commaToken)) {
					s.readNextToken(); //skipping the comma token
					continue;
				} else break;
			} 
			s.skip(rightParToken);			
		} else {
			pcs.expressions = null;
		}
		leaveParser("proccall-statm");
		return pcs;
	}
}