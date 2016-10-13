package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class ArrayType extends TypeDecl {
	Constant constFirst;
	Constant constSecond;
	TypeDecl type;
	
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
		s.skip(leftBracketToken);
		s.skip(ofToken);
		arrt.type = TypeDecl.parse(s);
		
		leaveParser("array-type");
		return arrt;
	}
}