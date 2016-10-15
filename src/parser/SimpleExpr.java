package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;

public class SimpleExpr extends PascalSyntax {
	PrefixOperator prefix; //optional
	ArrayList<Term> termList = new ArrayList<Term>();
	ArrayList<TermOperator> termOprList = new ArrayList<TermOperator>();
	
	public SimpleExpr(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<simple expr> on line " + lineNum;
	}
	
	public static SimpleExpr parse(Scanner s) {
		enterParser("simple expr");		
		SimpleExpr expr = new SimpleExpr(s.curLineNum());
		
		if(s.curToken.kind.isPrefixOpr()) {
			expr.prefix = PrefixOperator.parse(s);		
		} 
		while(true) {
			expr.termList.add(Term.parse(s));
			if(! s.curToken.kind.isTermOpr()) break;
			expr.termOprList.add(TermOperator.parse(s));
		}
		leaveParser("simple expr");
		return expr;
	}
	
	@Override
	public void prettyPrint() {
		if(prefix != null) prefix.prettyPrint();
		for(int i = 0; i < termList.size(); i++) {
			termList.get(i).prettyPrint();
			if(i < termOprList.size()) {
				Main.log.prettyPrint(" ");
				termOprList.get(i).prettyPrint();
				Main.log.prettyPrint(" ");
			}
		}
	}
}