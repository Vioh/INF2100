package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;
import static scanner.TokenKind.*;

public class SimpleExpr extends PascalSyntax {
	PrefixOperator prefix; //optional
	ArrayList<Term> termList = new ArrayList<Term>();
	ArrayList<TermOperator> termOprList = new ArrayList<TermOperator>();
	types.Type type;
	
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
	
	@Override
	public void check(Block curScope, Library lib) {
		if(prefix != null) {
			String oprName = prefix.oprType.toString();	
			Term firstTerm = termList.get(0);
			firstTerm.type.checkType(lib.integerType, "Prefix " + oprName, this, 
					"Prefix + or - may only be applied to Integers.");
		}
		
		for(int i = 0; i < termOprList.size(); i++) {
			TokenKind oprType = termOprList.get(i).oprType;
			types.Type lTermType = termList.get(i).type;   // type of left term
			types.Type rTermType = termList.get(i+1).type; // type of right term
			
			if(oprType == addToken || oprType == subtractToken) {
				lTermType.checkType(lib.integerType, 
					"left " + oprType.toString() + " operands", this, 
					"Left operand to " + oprType.toString() + " is not a number!");
				rTermType.checkType(lib.integerType, 
					"right " + oprType.toString() + " operands", this, 
					"Right operand to " + oprType.toString() + " is not a number!");
			}
			else if(oprType == orToken) {
				lTermType.checkType(lib.boolType, 
					"left " + oprType.toString() + " operands", this, 
					"Left operand to " + oprType.toString() + " is not a Boolean!");
				rTermType.checkType(lib.boolType, 
					"right " + oprType.toString() + " operands", this, 
					"Right operand to " + oprType.toString() + " is not a Boolean!");
			}
		}
		
	}
}