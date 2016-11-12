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
	
	@Override
	public void check(Block curScope, Library lib) {
		progBlock.check(curScope, lib);
	}

	@Override public void checkWhetherAssignable(PascalSyntax where) {}
	@Override public void checkWhetherFunction(PascalSyntax where) {}
	@Override public void checkWhetherProcedure(PascalSyntax where) {}
	@Override public void checkWhetherValue(PascalSyntax where) {}

	@Override
	public void genCode(CodeFile f) {
		// Increment the block level
		declLevel = ++Block.level;
		
		// Create the label and point the block's context to this declaration
		progProcFuncLabel = f.getLabel("prog$" + name);
		progBlock.context = this;
		
		// Generate codes for `main` and the program's body
		f.genInstr("", ".globl", "main", "");
		f.genInstr("main", "","","");
		f.genInstr("", "call", progProcFuncLabel, "Start program");
		f.genInstr("", "movl", "$0,%eax", "Set status 0 and");
		f.genInstr("", "ret", "", "terminate the program");
		progBlock.genCode(f);
		
		// Decrement the block level
		Block.level--;
	}
}