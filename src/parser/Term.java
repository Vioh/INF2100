package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

import java.util.ArrayList;

class Term extends PascalSyntax {
	ArrayList<Factor> facList = new ArrayList<Factor>();
	ArrayList<FactorOperator> foprList = new ArrayList<FactorOperator>();
	
	public Term(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<term> on line " + lineNum;
	}
	
	private static boolean isFactorOpr(Token tok) {
		TokenKind kind = tok.kind;
		return kind == multiplyToken || kind == divToken ||
			kind == modToken || kind == andToken;
	}
	
	public static Term parse(Scanner s) {
		enterParser("term");		
		Term t = new Term(s.curLineNum());
	
		while(true) {
			t.facList.add(Factor.parse(s));
			if(isFactorOpr(s.curToken)) {
				t.foprList.add(FactorOperator.parse(s));
				continue;
			} else break;
		}
		leaveParser("term");
		return t;
	}
	
	@Override
	public void prettyPrint() {
		for(int i = 0; i < facList.size(); i++) {
			facList.get(i).prettyPrint();
			
			if(i != foprList.size()) {
				Main.log.prettyPrint(" ");
				foprList.get(i).prettyPrint();
				Main.log.prettyPrint(" ");
			}
		}
	}
}