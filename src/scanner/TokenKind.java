package scanner;

public enum TokenKind {
	nameToken("name"),
	intValToken("number"),
	charValToken("char"),
	
	addToken("+"),
	assignToken(":="),
	colonToken(":"),
	commaToken(","),
	dotToken("."),
	equalToken("="),
	greaterToken(">"),
	greaterEqualToken(">="),
	leftBracketToken("["),
	leftParToken("("),
	lessToken("<"),
	lessEqualToken("<="),
	multiplyToken("*"),
	notEqualToken("<>"),
	rangeToken(".."),
	rightBracketToken("]"),
	rightParToken(")"),
	semicolonToken(";"),
	subtractToken("-"),

	andToken("and"), 
	arrayToken("array"),
	beginToken("begin"), 
	constToken("const"),
	divToken("div"), 
	doToken("do"), 
	elseToken("else"), 
	endToken("end"),
	functionToken("function"),
	ifToken("if"), 
	modToken("mod"),
	notToken("not"),
	ofToken("of"), 
	orToken("or"),
	procedureToken("procedure"), 
	programToken("program"),
	thenToken("then"), 
	varToken("var"),
	whileToken("while"),
	eofToken("e-o-f");

	private String image;

	TokenKind(String im) {
		image = im;
	}

	public String identify() {
		return image + " token";
	}

	@Override public String toString() {
		return image;
	}


	public boolean isFactorOpr() {
		return this==multiplyToken || this==divToken ||
			this==modToken || this==andToken;
	}

	public boolean isPrefixOpr() {
		return this==addToken || this==subtractToken;
	}

	public boolean isRelOpr() {
		return this==equalToken || this==notEqualToken ||
			this==lessToken || this==lessEqualToken ||
			this==greaterToken || this==greaterEqualToken;
	}

	public boolean isTermOpr() {
		return isPrefixOpr() || this==orToken;
	}
}