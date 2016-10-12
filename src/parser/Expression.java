package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class Expression extends PascalSyntax {
	SimpleExpr se1;
	SimpleExpr se2;
	RelOperator ro;
	
	public Expression(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<expression> on line " + lineNum;
	}
	
	public static Expression parse(Scanner s) {
		enterParser("expression");
		
		Expression e = new Expression(s.curLineNum());
		
		
		e.se1 = SimpleExpr.parse(s);
		s.readNextToken();
		if(s.test(equalToken)) {
			e.ro = RelOperator.parse(equalToken); // MÅ VI SENDE INN SELVE TOKEN ELLER e.ro.opr = equalTOken ??? CHECK
		
		
		leaveParser("expression");
		return ws;
	}
}