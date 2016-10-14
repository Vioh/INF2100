package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class UnsignedConstant extends Factor {
	String name;
	int number;
	char character;
	ConstantType type;
	
	enum ConstantType {
		Name, Number, Character
	}
	
	public UnsignedConstant(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<unsigned-const> on line " + lineNum;
	}
	
	public static UnsignedConstant parse(Scanner s) {
		enterParser("unsigned const");
		UnsignedConstant uc = new UnsignedConstant(s.curLineNum());
		switch(s.curToken.kind) {
		case intValToken:
			enterParser("number literal");
			uc.number = s.curToken.intVal;
			uc.type = ConstantType.Number;
			s.skip(intValToken);
			leaveParser("number literal");
			break;
		case charValToken: 
			enterParser("char literal");
			uc.character = s.curToken.charVal;
			uc.type = ConstantType.Character;
			s.skip(charValToken);
			leaveParser("char literal");
			break;
		case nameToken:
			enterParser("name");
			uc.name = s.curToken.id;
			uc.type = ConstantType.Name;
			s.skip(nameToken);
			leaveParser("name");
			break;
		}
		leaveParser("unsigned const");
		return uc;
	}
	
	public void prettyPrint() {
		if(type == ConstantType.Number) 
			Main.log.prettyPrint("" + number);
		if(type == ConstantType.Character) 
			Main.log.prettyPrint("'" + character + "'");
		if(type == ConstantType.Name) 
			Main.log.prettyPrint(name);
	}
}