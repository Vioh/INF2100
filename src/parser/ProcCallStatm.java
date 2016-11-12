package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;
import static scanner.TokenKind.*;

public class ProcCallStatm extends Statement {
	String name;
	ProcDecl declRef; // reference to the procedure declaration
	ArrayList<Expression> exprList = new ArrayList<Expression>();

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
		// Find the declaration and check if it's an actual procedure name
		PascalDecl pd = curScope.findDecl(name, this);
		pd.checkWhetherProcedure(this);
		declRef = (ProcDecl) pd;
		
		// Special case if this is the in-built "write" procedure
		if(pd == lib.writeProc) {
			for(Expression expr : exprList) {
				expr.check(curScope, lib);
				if(expr.type instanceof types.ArrayType) 
					error("You may not print arrays.");
			}
			return;
		}
		// Make an alias for the parameter declarations array-list (if exists)
		ArrayList<ParamDecl> pdl_alias = null;
		if(declRef.pdl != null) 
			pdl_alias = declRef.pdl.pdList;
		
		// Check if this procedure has no formal parameters
		if(pdl_alias == null) {
			if(!exprList.isEmpty()) 
				error("Too many parameters in call on " + name + "!");
			return;
		}
		// Check if the types of formal and actual parameters match
		if(exprList.size() > pdl_alias.size())
			error("Too many parameters in call on " + name + "!"); 
		else if(exprList.size() < pdl_alias.size())
			error("Too few parameters in call on " + name + "!");
		else {
			for(int i = 0; i < exprList.size(); i++) {
				exprList.get(i).check(curScope, lib);
				pdl_alias.get(i).type.checkType(exprList.get(i).type, 
						"param #" + (i+1), this,
						"Illegal type of parameter #" + (i+1) + "!");
			}
		}
	}

	@Override
	public void genCode(CodeFile f) {
		if(declRef.name.equals("write")) {
			for(Expression expr : exprList) {
				expr.genCode(f);
				f.genInstr("", "pushl", "%eax", "Push next param.");
				if(expr.type instanceof types.IntType) 
					f.genInstr("", "call", "write_int", "");
				else if(expr.type instanceof types.BoolType)
					f.genInstr("", "call", "write_bool", "");
				else if(expr.type instanceof types.CharType)
					f.genInstr("", "call", "write_char", "");
				f.genInstr("", "addl", "$4,%esp", "Pop param.");
			}
			return;
		}
		for(int i = exprList.size()-1; i >= 0; i--) {
			exprList.get(i).genCode(f);
			f.genInstr("", "pushl", "%eax", "Push param #"+(i+1)+".");
		}
		f.genInstr("", "call", declRef.progProcFuncLabel, "");
		int nbytes = 4 * exprList.size();
		if(nbytes > 0) 
			f.genInstr("", "addl", "$"+nbytes+",%esp", "Pop params.");
	}
}