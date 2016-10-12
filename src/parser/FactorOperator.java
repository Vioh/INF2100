package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class FactorOperator extends Operator {
	String opr;
	
	public FactorOperator(int lNum) {
		super(lNum);
	}
	//MIGZ
	@Override
	public String identify() {
		return "<factor-opr> on line " + lineNum;
	}
	
	public static FactorOperator parse(Scanner s) {
		enterParser("factor-opr");
		
		FactorOperator fo = new FactorOperator(s.curLineNum());
		switch (s.curToken.kind) {
			case equalToken:
				opr = equalToken; break;
			case andToken:
				opr = andToken; break;
			case modToken:
				opr = modToken; break;
			case divToken:
				opr = divToken; break;
		default:
			opr = null; break;
		}
		
		leaveParser("factor-opr");
		return fo;
	}
}