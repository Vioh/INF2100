package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class UnsignedConstant extends Factor {
	String name;    //name
	int number;     //numeric literal
	char character; //char literal
	ConstantType type;
	
	/** A tri-state boolean to distinguish between different constant types */
	enum ConstantType {
		NAME, NUMB, CHAR
	}
	
	public UnsignedConstant(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<unsigned constant> on line " + lineNum;
	}
	
	public static UnsignedConstant parse(Scanner s) {
		enterParser("unsigned constant");
		UnsignedConstant uc = new UnsignedConstant(s.curLineNum());
		
		switch(s.curToken.kind) {
		case intValToken:
			enterParser("number literal");
			uc.number = s.curToken.intVal;
			uc.type = ConstantType.NUMB;
			s.skip(intValToken);
			leaveParser("number literal");
			break;
		case charValToken: 
			enterParser("char literal");
			uc.character = s.curToken.charVal;
			uc.type = ConstantType.CHAR;
			s.skip(charValToken);
			leaveParser("char literal");
			break;
		case nameToken:
			enterParser("name constant");
			uc.name = s.curToken.id;
			uc.type = ConstantType.NAME;
			s.skip(nameToken);
			leaveParser("name constant");
			break;
		default:
			s.testError("unsigned constant"); // expect an unsigned const here
		}
		leaveParser("unsigned constant");
		return uc;
	}
	
	@Override
	public void prettyPrint() {
		if(type == ConstantType.NUMB) 
			Main.log.prettyPrint("" + number);
		if(type == ConstantType.CHAR) 
			Main.log.prettyPrint("'" + character + "'");
		if(type == ConstantType.NAME) 
			Main.log.prettyPrint(name);
	}
}