package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;
import static scanner.TokenKind.*;

public class ParamDeclList extends PascalSyntax {
	ArrayList<ParamDecl> pdList = new ArrayList<ParamDecl>();
	
	public ParamDeclList(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<param decl list> on line " + lineNum;
	}
	
	public static ParamDeclList parse(Scanner s) {
		enterParser("param decl list");	
		ParamDeclList pdl = new ParamDeclList(s.curLineNum());
		
		s.skip(leftParToken);
		while(true) {
			pdl.pdList.add(ParamDecl.parse(s));
			if(s.curToken.kind != semicolonToken) break;
			s.skip(semicolonToken);
		}
		s.skip(rightParToken);
		
		leaveParser("param decl list");
		return pdl;
	}
	
	@Override
	public void prettyPrint() {
		Main.log.prettyPrint("(");
		for(int i = 0; i < pdList.size(); i++) {
			if(i != 0) Main.log.prettyPrint("; ");
			pdList.get(i).prettyPrint();
		}
		Main.log.prettyPrint(")");
	}
}