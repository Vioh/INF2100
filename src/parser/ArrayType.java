package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class ArrayType extends Type {
	Constant firstInd;
	Constant lastInd;
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
		ArrayType array = new ArrayType(s.curLineNum());
		
		s.skip(arrayToken); s.skip(leftBracketToken);
		array.firstInd = Constant.parse(s);
		s.skip(rangeToken);
		array.lastInd = Constant.parse(s);
		s.skip(rightBracketToken); s.skip(ofToken);
		array.type = Type.parse(s);
		
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
		type.prettyPrint();
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		firstInd.check(curScope, lib);
		lastInd.check(curScope, lib);
		type.check(curScope, lib);
	}
	
	
	
	
	
	
	
	
}