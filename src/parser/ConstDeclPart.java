package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class ConstDeclPart extends PascalSyntax {
	ArrayList<ConstDecl> cdlist = new ArrayList<ConstDecl>();
	
	public ConstDeclPart(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<const-decl-part> on line " + lineNum;
	}
	//MIGZ
	public static ConstDeclPart parse(Scanner s) {
		enterParser("const-decl-part");
		
		ConstDeclPart cdp = new ConstDeclPart(s.curLineNum());
		
		while(True) {
			cdp.cdlist.add(ConstDecl.parse(s));
			if(s.test("\n")) { //MÅ FORANDRE FOR Å SJEKKE RIKTIG OM DET ER EN TIL CONST DECL
				s.readNextToken();
				continue;
			} else break;
		}
		
		leaveParser("const-decl-part");
		return cdp;
	}
}