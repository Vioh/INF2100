package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class VarDecl extends PascalDecl {
	parser.Type typeFromParser;
	int nbytes;
	
	public VarDecl(String name, int lNum) {
		super(name, lNum);
	}

	@Override
	public String identify() {
		return "<var decl> " + name + " on line " + lineNum;
	}
	
	public static VarDecl parse(Scanner s) {
		enterParser("var decl");
		s.test(nameToken);
		VarDecl vd = new VarDecl(s.curToken.id, s.curLineNum());
		
		s.skip(nameToken); s.skip(colonToken);
		vd.typeFromParser = parser.Type.parse(s);
		s.skip(semicolonToken);
		
		leaveParser("var decl");
		return vd;
	}
	
	@Override 
	public void prettyPrint() {
		Main.log.prettyPrint(this.name + ": ");
		typeFromParser.prettyPrint();
		Main.log.prettyPrint(";");
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		curScope.addDecl(name, this);
		typeFromParser.check(curScope, lib);
		type = typeFromParser.type;
	}
	
	@Override
	public void checkWhetherAssignable(PascalSyntax where) {
		// Variable is always assignable. Do nothing!
	}

	@Override
	public void checkWhetherFunction(PascalSyntax where) {
		where.error(name + " is a variable, not a function.");
	}

	@Override
	public void checkWhetherProcedure(PascalSyntax where) {
		where.error(name + " is a variable, not a procedure.");
	}

	@Override
	public void checkWhetherValue(PascalSyntax where) {
		// Variable can have a value. Do nothing!
	}

	@Override
	public void genCode(CodeFile f) {
		nbytes = 4;
		if(type instanceof types.ArrayType) {
			types.ArrayType t = (types.ArrayType) type;
			nbytes = 4 * (t.hiLim - t.loLim + 1);
		}
	}
}