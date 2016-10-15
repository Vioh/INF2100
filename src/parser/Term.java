package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;

public class Term extends PascalSyntax {
	ArrayList<Factor> facList = new ArrayList<Factor>();
	ArrayList<FactorOperator> facOprList = new ArrayList<FactorOperator>();
	
	public Term(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<term> on line " + lineNum;
	}	
	
	public static Term parse(Scanner s) {
		enterParser("term");		
		Term term = new Term(s.curLineNum());
	
		while(true) {
			term.facList.add(Factor.parse(s));
			if(! s.curToken.kind.isFactorOpr()) break;
			term.facOprList.add(FactorOperator.parse(s));
		}
		leaveParser("term");
		return term;
	}
	
	@Override
	public void prettyPrint() {
		for(int i = 0; i < facList.size(); i++) {
			facList.get(i).prettyPrint();
			if(i < facOprList.size()) {
				Main.log.prettyPrint(" ");
				facOprList.get(i).prettyPrint();
				Main.log.prettyPrint(" ");
			}
		}
	}
}