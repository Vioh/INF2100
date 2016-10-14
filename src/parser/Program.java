package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class Program extends PascalDecl {
	Block progBlock;
		
	Program(String id, int lNum) {
		super(id, lNum);
	}
	
	@Override
	public String identify() {
		return "<program> " + name + " on line " + lineNum;
	}
	
	public static Program parse(Scanner s) {
		enterParser("program");
		s.skip(programToken);
		s.test(nameToken);
		
		Program p = new Program(s.curToken.id, s.curLineNum());
		s.readNextToken();
		s.skip(semicolonToken);
		p.progBlock = Block.parse(s); 
		// p.progBlock.context = p;
		s.skip(dotToken);
		
		leaveParser("program");
		return p;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrintLn("program " + this.name + ";");
		progBlock.prettyPrint();
		Main.log.prettyPrintLn(".");
	}

//	@Override
//	void checkWhetherAssignable(PascalSyntax where) {
//		// TODO Auto-generated method stub
//	}
//
//	@Override
//	void checkWhetherFunction(PascalSyntax where) {
//		// TODO Auto-generated method stub
//	}
//
//	@Override
//	void checkWhetherProcedure(PascalSyntax where) {
//		// TODO Auto-generated method stub
//	}
//
//	@Override
//	void checkWhetherValue(PascalSyntax where) {
//		// TODO Auto-generated method stub
//	}
}