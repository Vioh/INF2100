package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;
import static scanner.TokenKind.*;

class ProcCallStatm extends Statement {
	String name;
	ArrayList<Expression> expressions; // can be NULL

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
		s.skip(nameToken);
		
		if(s.curToken.kind == leftParToken) {
			s.skip(leftParToken); 
			pcs.expressions = new ArrayList<Expression>();
			while(true) {
				pcs.expressions.add(Expression.parse(s));
				if(s.curToken.kind != commaToken) break;
				s.skip(commaToken);		
			}
			s.skip(rightParToken);			
		} 
		leaveParser("proccall-statm");
		return pcs;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint(name);
		if(expressions.isEmpty()) return;
		
		Main.log.prettyPrint("(");
		for(int i = 0; i < expressions.size(); i++) {
			if(i != 0) Main.log.prettyPrint(", ");
			expressions.get(i).prettyPrint();
		}
		Main.log.prettyPrint(")");
	}
}