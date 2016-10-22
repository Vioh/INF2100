package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;
import static scanner.TokenKind.*;

public class ProcCallStatm extends Statement {
	String name;
	ArrayList<Expression> exprList = new ArrayList<Expression>();
	ProcDecl procRef;

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
				pcs.exprList.add(Expression.parse(s));
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
		if(exprList.isEmpty()) return;
		
		Main.log.prettyPrint("(");
		for(int i = 0; i < exprList.size(); i++) {
			if(i != 0) Main.log.prettyPrint(", ");
			exprList.get(i).prettyPrint();
		}
		Main.log.prettyPrint(")");
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		PascalDecl pd = curScope.findDecl(name, this);
		for(Expression expr : exprList) expr.check(curScope, lib);
		
		// Check if the name is a procedure name
		pd.checkWhetherProcedure(this);
		procRef = (ProcDecl) pd;		
		
		// Make an alias for the parameter declarations array-list (if exists)
		ArrayList<ParamDecl> pdList_alias = null;
		if(procRef.pdl != null) pdList_alias = procRef.pdl.pdList;
		
		// Check if this procedure has no formal parameters
		if(pdList_alias == null) {
			if(!exprList.isEmpty()) 
				error("Too many parameters in call on " + name);
			return;
		}
		// Check if the types of formal and actual parameters match
		if(exprList.size() > pdList_alias.size())
			error("Too many parameters in call on " + name); 
		else if(exprList.size() < pdList_alias.size())
			error("Too few parameters in call on " + name);
		else {
			for(int i = 0; i < exprList.size(); i++) {
				pdList_alias.get(i).type.checkType(exprList.get(i).type,
						"param #" + i, this, "Illegal type of parameter #" + i);
			}
		}
	}
}