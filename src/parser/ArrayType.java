package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class ArrayType extends Type {
	Constant firstInd;
	Constant lastInd;
	parser.Type typeFromParser; 
	types.Type indexType;
	types.Type elemType;

	public ArrayType(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<array-type> on line " + lineNum;
	}
	
	public static ArrayType parse(Scanner s) {
		enterParser("array-type");		
		ArrayType array = new ArrayType(s.curLineNum());
		
		s.skip(arrayToken); s.skip(leftBracketToken);
		array.firstInd = Constant.parse(s);
		s.skip(rangeToken);
		array.lastInd = Constant.parse(s);
		s.skip(rightBracketToken); s.skip(ofToken);
		array.typeFromParser = parser.Type.parse(s);
		
		leaveParser("array-type");
		return array;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint("array [");
		firstInd.prettyPrint();
		Main.log.prettyPrint("..");
		lastInd.prettyPrint();
		Main.log.prettyPrint("] of ");
		typeFromParser.prettyPrint();
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		firstInd.check(curScope, lib);
		lastInd.check(curScope, lib);
		typeFromParser.check(curScope, lib);
		
		// Check if the limits are of the same type
		firstInd.type.checkType(lastInd.type, "array limits",
				this, "Array limits must be of the same type.");
		indexType = firstInd.type;
		
		// Check the array size
		if(lastInd.constVal - firstInd.constVal < 0) {
			this.error("Arrays cannot have negative size!");
		}
		// Assign the element type
		elemType = typeFromParser.type;
		type = lib.arrayType;
	}
}