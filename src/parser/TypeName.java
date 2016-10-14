package parser;
import main.*;
import scanner.*;

class TypeName extends Type {
	String name;
	
	public TypeName(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<type-name> on line " + lineNum;
	}
	
	public static TypeName parse(Scanner s) {
		enterParser("type-name");
		TypeName typName = new TypeName(s.curLineNum());
		typName.name = s.curToken.id;
		leaveParser("type-name");
		return typName;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint(name);
	}
}



