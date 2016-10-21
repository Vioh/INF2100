package parser;
import scanner.*;

public abstract class Type extends PascalSyntax {
	types.Type type;
	
	public Type(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<type> on line " + lineNum;
	}
	
	public static Type parse(Scanner s) {
		enterParser("type");
		Type type = null;
		
		switch(s.curToken.kind) {
		case arrayToken:
			type = ArrayType.parse(s); break;
		case nameToken:
			type = TypeName.parse(s); break;
		default:
			s.testError("type"); // we expect a type here
		}
		leaveParser("type");
		return type;
	}
}