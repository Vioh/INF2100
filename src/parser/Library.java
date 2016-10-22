package parser;

public class Library extends Block {
	ProcDecl writeProc;
	ConstDecl eolConst;
	ConstDecl trueConst;
	ConstDecl falseConst;
	types.IntType integerType;
	types.BoolType boolType;
	types.CharType charType;
	
	public Library() {
		super(-1); // library doesn't have a line number
		
		// Instantiate primitive types
		integerType = new types.IntType();
		boolType = new types.BoolType();
		charType = new types.CharType();
		
		// Instantiate "write" procedure and other predefined constants
		writeProc = new ProcDecl("write", -1);
		eolConst = new ConstDecl("eol", -1);
		trueConst = new ConstDecl("true", -1);
		falseConst = new ConstDecl("false", -1);
		eolConst.type = charType; eolConst.constVal = 10;
		trueConst.type = boolType; trueConst.constVal = 1;
		falseConst.type = boolType; falseConst.constVal = 0;

		// Put all Pascal declarations into the "decls" hash-map
		decls.put("write", writeProc);
		decls.put("eol", eolConst);
		decls.put("true", trueConst);
		decls.put("false", falseConst);
	}
	
	public types.Type findPrimitiveType(String name, PascalSyntax where) {
		if(name.equals("integer")) return integerType;
		if(name.equals("boolean")) return boolType;
		if(name.equals("char")) return charType;
		where.error("Name " + name + " is unknown!");
		return null;
	}
}