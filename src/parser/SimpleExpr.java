package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;
import static scanner.TokenKind.*;

class SimpleExpr extends PascalSyntax {
	PrefixOperator popr; //can be NULL
	ArrayList<TermOperator> toprlist = new ArrayList<TermOperator>();
	ArrayList<Term> tlist = new ArrayList<Term>();
	
	public SimpleExpr(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<simple-expr> on line " + lineNum;
	}
	
	private static boolean isPrefixOpr(Token tok) {
		TokenKind kind = tok.kind;
		return kind == addToken || kind ==subtractToken;
	}
	
	private static boolean isTermOpr(Token tok) {
		TokenKind kind = tok.kind;
		return isPrefixOpr(tok) || kind == orToken;
	}
	
	public static SimpleExpr parse(Scanner s) {
		enterParser("simple-expr");		
		SimpleExpr se = new SimpleExpr(s.curLineNum());
		
		if(isPrefixOpr(s.curToken)) {
			se.popr = PrefixOperator.parse(s);		
		} 
		while(true) {
			se.tlist.add(Term.parse(s));
			if(isTermOpr(s.curToken)) {
				se.toprlist.add(TermOperator.parse(s));
				continue;
			} else break;
		}
		leaveParser("simple-expr");
		return se;
	}
	
	@Override
	//TODO
	public void prettyPrint() {
		if(popr != null) popr.prettyPrint();
		
	}
	
	
	
	
	
	
}