package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

import java.util.ArrayList;

class Term extends PascalSyntax {
	ArrayList<FactorOperator> foprList = new ArrayList<FactorOperator>();
	ArrayList<Factor> facList = new ArrayList<Factor>();
	
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
}