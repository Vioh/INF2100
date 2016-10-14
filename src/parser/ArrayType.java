package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class ArrayType extends Type {
	Constant constFirst;
	Constant constSecond;
	Type type;
	
	public ArrayType(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<array-type> on line " + lineNum;
	}
	
	public static ArrayType parse(Scanner s) {
		enterParser("array-type");		
		ArrayType arrt = new ArrayType(s.curLineNum());
		
		s.skip(arrayToken);
		s.skip(leftBracketToken);
		arrt.constFirst = Constant.parse(s);
		s.skip(rangeToken);
		arrt.constSecond = Constant.parse(s);
		s.skip(rightBracketToken);
		s.skip(ofToken);
		arrt.type = Type.parse(s);
		
		leaveParser("array-type");
		return arrt;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint("array [");
		constFirst.prettyPrint();
		Main.log.prettyPrint("..");
		constSecond.prettyPrint();
		Main.log.prettyPrint("] of ");
		type.prettyPrint();
	}
}