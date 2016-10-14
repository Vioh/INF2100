package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;
import static scanner.TokenKind.*;

class FuncCall extends Factor {
	String name;
	ArrayList<Expression> exprList = new ArrayList<Expression>();
	
	public FuncCall(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<func-call> on line " + lineNum;
	}
	
	public static FuncCall parse(Scanner s) {
		enterParser("func-call");
		FuncCall fc = new FuncCall(s.curLineNum());
		fc.name = s.curToken.id;
		s.skip(nameToken);
		
		if(s.curToken.kind == leftParToken) {
			s.skip(leftParToken); 
			fc.exprList = new ArrayList<Expression>();
			while(true) {
				fc.exprList.add(Expression.parse(s));
				if(s.curToken.kind != commaToken) break;
				s.skip(commaToken);		
			}
			s.skip(rightParToken);			
		} 
		
		leaveParser("func-call");
		return fc;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint(name);
		if(exprList.isEmpty()) return;
		
		Main.log.prettyPrint("(");
		exprList.get(0).prettyPrint();
		for(int i = 1; i < exprList.size(); i++) {
			Main.log.prettyPrint(", ");
			exprList.get(1).prettyPrint();
		}
		Main.log.prettyPrint(")");
	}
}