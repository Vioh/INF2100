package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;
import static scanner.TokenKind.*;

public class ProcCallStatm extends Statement {
	String name;
	ArrayList<Expression> expressions = new ArrayList<Expression>();

	public ProcCallStatm(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<proc call> on line " + lineNum;
	}
	
	public static ProcCallStatm parse(Scanner s) {
		enterParser("proc call");
		ProcCallStatm pcs = new ProcCallStatm(s.curLineNum());
		
		s.test(nameToken); pcs.name = s.curToken.id; s.skip(nameToken);
		if(s.curToken.kind == leftParToken) {
			s.skip(leftParToken);
			while(true) {
				pcs.expressions.add(Expression.parse(s));
				if(s.curToken.kind != commaToken) break;
				s.skip(commaToken);		
			}
			s.skip(rightParToken);			
		} 
		leaveParser("proc call");
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