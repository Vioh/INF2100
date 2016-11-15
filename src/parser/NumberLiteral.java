package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class NumberLiteral extends UnsignedConstant {
	int number;
	
	public NumberLiteral(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<number literal> on line " + lineNum;
	}
	
	public static NumberLiteral parse(Scanner s) {
		enterParser("number literal");		
		NumberLiteral nl = new NumberLiteral(s.curLineNum());
		
		nl.number = s.curToken.intVal;
		s.skip(intValToken);
		
		leaveParser("number literal");
		return nl;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint("" + number);
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		constVal = number;
		type = lib.integerType;
	}
	
	@Override
	public void genCode(CodeFile f) {
		f.genInstr("", "movl", "$"+constVal+",%eax", "  "+constVal);
	}
}