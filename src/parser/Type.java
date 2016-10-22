package parser;
import scanner.*;

public abstract class Type extends PascalSyntax {
	types.Type type;
	
	public Type(int lNum) {
		super(lNum);
	}
	
	public static parser.Type parse(Scanner s) {
		enterParser("type");
		parser.Type typeFromParser = null;
		
		switch(s.curToken.kind) {
		case arrayToken:
			typeFromParser = ArrayType.parse(s); break;
		case nameToken:
			typeFromParser = TypeName.parse(s); break;
		default:
			s.testError("type"); // we expect a type here
		}
		leaveParser("type");
		return typeFromParser;
	}
}