package parser;
import scanner.*;

public abstract class Factor extends PascalSyntax {
	types.Type type;
	
	public Factor(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<factor> on line " + lineNum;
	}
	
	public static Factor parse(Scanner s) {
		enterParser("factor");
		Factor fact = null;
		
		switch (s.curToken.kind) {
		case intValToken: case charValToken:
			fact = UnsignedConstant.parse(s); break;
		case notToken:
			fact = Negation.parse(s); break;
		case leftParToken:
			fact = InnerExpr.parse(s); break;
		case nameToken:
			switch(s.nextToken.kind) {
			case leftParToken:
				fact = FuncCall.parse(s); break;
			default:
				fact = Variable.parse(s); break;
			} break;
		default:
			s.testError("value"); // we expect a value here
		}
		leaveParser("factor");
		return fact;
	}
}