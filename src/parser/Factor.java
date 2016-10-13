package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

abstract class Factor extends PascalSyntax {
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
		case intValToken: charValToken:
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
			// will NOT execute because of the line preceding the switch
		}
		
		leaveParser("factor");
		return fact;
	}
}