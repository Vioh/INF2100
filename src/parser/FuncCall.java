package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;
import static scanner.TokenKind.*;

public class FuncCall extends Factor {
	String name;
	ArrayList<Expression> exprList = new ArrayList<Expression>();
	FuncDecl funcRef;
	
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
		
		// Check the types of formal and actual parameters
		ArrayList<ParamDecl> pdList = funcRef.pdl.pdList;
		for(int i = 0; i < pdList.size(); i++) {
			pdList.get(i).type.checkType(exprList.get(i).type, 
					"param #" + i, this, "Illegal type of parameter #" + i);
		}
		this.type = funcRef.type;
	}
}