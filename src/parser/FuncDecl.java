package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

public class FuncDecl extends ProcDecl {
	TypeName typename;
	
	public FuncDecl(String name, int lNum) {
		super(name, lNum);
	}
	
	@Override
	public String identify() {
		String ret = "<func decl> " + name;
		if(this.isInLibrary()) return ret + " in the library";
		return ret + " on line " + lineNum;
	}
	
	public static FuncDecl parse(Scanner s) {
		enterParser("func decl");
		s.skip(functionToken); s.test(nameToken);
		FuncDecl fd = new FuncDecl(s.curToken.id, s.curLineNum());
		
		s.skip(nameToken);
		if(s.curToken.kind == leftParToken) {
			fd.pdl = ParamDeclList.parse(s);
		}
		s.skip(colonToken);
		fd.typename = TypeName.parse(s);
		s.skip(semicolonToken);
		fd.block = Block.parse(s);
		s.skip(semicolonToken);
		
		leaveParser("func decl");
		return fd;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint("function " + this.name);
		if(pdl != null) {
			Main.log.prettyPrint(" ");
			pdl.prettyPrint();
		}
		Main.log.prettyPrint(": ");
		typename.prettyPrint();
		Main.log.prettyPrintLn(";");
		block.prettyPrint();
		Main.log.prettyPrint("; {" + name + "}");
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		curScope.addDecl(name, this);
		typename.check(curScope, lib);
		type = typename.type; // function's return-type
		
		if(pdl != null) {
			block.outerScope = curScope;
			pdl.check(block, lib); // the params must be inside the proc's scope
		}
		block.check(curScope, lib);
		
		// This function declaration must also be stored inside its own scope
		// so that it's easy to make sure that it can only be assigned within
		// its own block (for the function return value)
		block.addDecl(name, this);
	}
	
	@Override
	public void checkWhetherAssignable(PascalSyntax where) {
		// A function is assignable (return value). Do nothing!
	}
	
	@Override
	public void checkWhetherFunction(PascalSyntax where) {
		// This is a function. Do nothing!
	}
	
	@Override
	public void checkWhetherProcedure(PascalSyntax where) {
		where.error(name + " is a function, not a procedure.");
	}
	
	@Override
	public void checkWhetherValue(PascalSyntax where) {
		// Function always returns a value. Do nothing!
	}
	
	@Override
	public void genCode(CodeFile f) {
		// Increment the block level
		declLevel = Block.level++;
		
		// Create the label and point the block's context to this declaration
		progProcFuncLabel = f.getLabel("func$" + name);
		block.context = this;
		
		// Generate codes for the procedure's parameters and body
		if(pdl != null) pdl.genCode(f);
		block.genCode(f);
		
		// Decrement the block level
		Block.level--;
	}
	
}