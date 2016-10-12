package parser;
import main.*;
import scanner.*;
import static scanner.TokenKind.*;

class ParamDeclList extends PascalSyntax {
	ArrayList<ParamDecl> pdlist = new ArrayList<ParamDecl>();
	
	public ParamDeclList(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<param-decl-list> on line " + lineNum;
	}
	//MIGZ
	public static ParamDeclList parse(Scanner s) {
		enterParser("param-decl-list");
		
		ParamDeclList pdl = new ParamDeclList(s.curLineNum());
		s.skip(leftParToken);
		
		while(True) {
			pdl.pdlist.add(ParamDecl.parse(s));
			if(s.test(semicolonToken)) {
				s.readNextToken(); //skip semicolon
				continue;
			} else break; 
		}
		s.skip(rightParToken);
		leaveParser("param-decl-list");
		return pdl;
	}
	
}