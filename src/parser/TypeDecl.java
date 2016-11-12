package parser;

import main.CodeFile;

/** 
 * NOTE: TypeDecl is the only class in the "parser" package that does not
 * appear anywhere on the grammar-diagram (jernbanediagram). This class
 * is added purely for the purpose of declaring primitive datatypes in the 
 * Pascal standard library (see parser.Library class).
 */
public class TypeDecl extends PascalDecl {
	public TypeDecl(String id, int lNum, types.Type type) {
		super(id, lNum);
		this.type = type;
	}
	
	@Override
	public String identify() {
		return "<type decl> " + name + " in the library";
	}
	
	@Override 
	public void checkWhetherAssignable(PascalSyntax where) {
		where.error("You cannot assign to a type name.");
	}

	@Override public void checkWhetherFunction(PascalSyntax where) {
		where.error(name + " is a type name, not a function.");
	}

	@Override
	public void checkWhetherProcedure(PascalSyntax where) {
		where.error(name + " is a type name, not a procedure.");
	}

	@Override
	public void checkWhetherValue(PascalSyntax where) {
		where.error(name + " is a type name, not a value.");
	}

	@Override public void prettyPrint() {}
	@Override public void check(Block curScope, Library lib) {}

	@Override
	public void genCode(CodeFile f) {
		// No code needs to be generated for a type declaration
	}
}