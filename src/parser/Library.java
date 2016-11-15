package parser;
import main.*;

public class Library extends Block {
	public ProcDecl writeProc;
	public ConstDecl eolConst;
	public ConstDecl trueConst;
	public ConstDecl falseConst;
	public types.IntType integerType;
	public types.BoolType boolType;
	public types.CharType charType;
	
	public Library() {
		super(-1); // library doesn't have a line number
		
		// Instantiate the primitive types
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
		decls.put("char", new TypeDecl("char", -1, charType));
		decls.put("boolean", new TypeDecl("boolean", -1, boolType));
		decls.put("integer", new TypeDecl("integer", -1, integerType));
	}
	
	@Override
	public void genCode(CodeFile f) {
		// No code needs to be generated for hte library
	}
}