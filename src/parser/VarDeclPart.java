package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;
import static scanner.TokenKind.*;

class VarDeclPart extends PascalSyntax {
	ArrayList<VarDecl> vdlist = new ArrayList<VarDecl>();
	
	public VarDeclPart(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<var-decl-part> on line " + lineNum;
	}
	//MIGZ
	public static VarDeclPart parse(Scanner s) {
		enterParser("var-decl-part");
		
		VarDeclPart vdp = new VarDeclPart(s.curLineNum());
		
		while(True) {
			vdp.vdlist.add(VarDecl.parse(s));
			if(s.test("\n")) { //MÅ FORANDRE FOR Å SJEKKE RIKTIG OM DET ER EN TIL VAR DECL
				s.readNextToken();
				continue;
			} else break;
		}
		
		leaveParser("var-decl-part");
		return vdp;
	}
}