package parser;

public class Library extends Block {
	public ProcDecl writeProc;
	public ConstDecl eolConst, trueConst, falseConst;
	public types.IntType integerType;
	public types.ArrayType arrayType;
	public types.BoolType boolType;
	public types.CharType charType;
	
	public Library() {
		super(-1); // library doesn't have a line number
		
		eolConst = new ConstDecl("eol", -1);
		trueConst = new ConstDecl
		
		
		integerType = new types.IntType();
		
	}
}
