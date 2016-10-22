package parser;
import scanner.*;
import static scanner.TokenKind.*;

public class Constant extends PascalSyntax {
	PrefixOperator prefix; // optional
	UnsignedConstant uconst;
	int constVal;
	types.Type type;
	
	public Constant(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<constant> on line " + lineNum;
	}
	
	public static Constant parse(Scanner s) {
		enterParser("constant");
		Constant constant = new Constant(s.curLineNum());
		
		if(s.curToken.kind == addToken
				|| s.curToken.kind == subtractToken) {
			constant.prefix = PrefixOperator.parse(s);
		}
		constant.uconst = UnsignedConstant.parse(s);		
		
		leaveParser("constant");
		return constant;
	}
	
	@Override
	public void prettyPrint() {
		if(prefix != null) prefix.prettyPrint();
		uconst.prettyPrint();
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		uconst.check(curScope, lib);
		constVal = uconst.constVal;
		type = uconst.type;
		
		if(prefix != null) {
			TokenKind oprType = prefix.oprType;
			uconst.type.checkType(lib.integerType, "Prefix " + oprType, this,
					"Prefix + or - may only be applied to Integers.");
			if(oprType == subtractToken) constVal = -constVal;
		}
	}
}