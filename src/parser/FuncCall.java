package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;
import static scanner.TokenKind.*;

public class FuncCall extends Factor {
	String name;
	ArrayList<Expression> exprList = new ArrayList<Expression>();
	FuncDecl funcRef;
	types.Type type;
	
	public FuncCall(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<func call> on line " + lineNum;
	}
	
	public static FuncCall parse(Scanner s) {
		enterParser("func call");
		FuncCall fc = new FuncCall(s.curLineNum());
		
		s.test(nameToken); fc.name = s.curToken.id; s.skip(nameToken);
		if(s.curToken.kind == leftParToken) {
			s.skip(leftParToken);
			while(true) {
				fc.exprList.add(Expression.parse(s));
				if(s.curToken.kind != commaToken) break;
				s.skip(commaToken);	
			}
			s.skip(rightParToken);		
		}
		leaveParser("func call");
		return fc;
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
		
		// Check if the name a function name
		pd.checkWhetherFunction(this);
		funcRef = (FuncDecl) pd;
		this.type = funcRef.type;
		
		// Make an alias for the parameter declarations array-list
		ArrayList<ParamDecl> pdList_alias = null;
		if(funcRef.pdl != null) pdList_alias = funcRef.pdl.pdList;
		
		// Check if this function has no formal parameters
		if(pdList_alias == null) {
			if(!exprList.isEmpty())
				error("Too many parameters in call on " + name);
			return;
		}
		// Check if the types of formal and actual parameters match
		if(exprList.size() > pdList_alias.size()) 
			error("Too many paramters in call on " + name);
		else if(exprList.size() < pdList_alias.size())
			error("Too few parameters in call on " + name);
		else {
			for(int i = 0; i < exprList.size(); i++) {
				pdList_alias.get(i).type.checkType(exprList.get(i).type, 
						"param #" + i, this, "Illegal type of parameter #" + i);
			}
		}
		// Assign the type of this function call to the type in its declaration
		type = funcRef.type;
	}
}