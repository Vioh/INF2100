package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class TypeName extends Type {
	String name;
	
	public TypeName(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<type name> on line " + lineNum;
	}
	
	public static TypeName parse(Scanner s) {
		enterParser("type name");
		TypeName tn = new TypeName(s.curLineNum());
		
		tn.name = s.curToken.id;
		s.skip(nameToken);
		
		leaveParser("type name");
		return tn;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint(name);
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		type = lib.findPrimitiveType(name, this);
	}
}