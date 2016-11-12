package parser;
import main.*;
import scanner.*;
import java.util.ArrayList;
import static scanner.TokenKind.*;

public class Term extends PascalSyntax {
	ArrayList<Factor> facList = new ArrayList<Factor>();
	ArrayList<FactorOperator> facOprList = new ArrayList<FactorOperator>();
	types.Type type;
	
	public Term(int lNum) {
		super(lNum);
	}
	
	@Override
	public String identify() {
		return "<term> on line " + lineNum;
	}	
	
	public static Term parse(Scanner s) {
		enterParser("term");		
		Term term = new Term(s.curLineNum());
	
		while(true) {
			term.facList.add(Factor.parse(s));
			if(! s.curToken.kind.isFactorOpr()) break;
			term.facOprList.add(FactorOperator.parse(s));
		}
		leaveParser("term");
		return term;
	}
	
	@Override
	public void prettyPrint() {
		for(int i = 0; i < facList.size(); i++) {
			facList.get(i).prettyPrint();
			if(i < facOprList.size()) {
				Main.log.prettyPrint(" ");
				facOprList.get(i).prettyPrint();
				Main.log.prettyPrint(" ");
			}
		}
	}
	
	@Override
	public void check(Block curScope, Library lib) {
		Factor fact0 = facList.get(0); // the first factor always exists
		fact0.check(curScope, lib);
		type = fact0.type;
		
		for(int i = 0; i < facOprList.size(); i++) {
			facList.get(i+1).check(curScope, lib);
			TokenKind oprType = facOprList.get(i).oprType;
			types.Type lTermType = facList.get(i).type;   // type of left factor
			types.Type rTermType = facList.get(i+1).type; // type of right factor
			
			if(oprType == multiplyToken 
					|| oprType == divToken 
					|| oprType == modToken) {
				lTermType.checkType(lib.integerType, 
					"left " + oprType + " operand", this, 
					"Left operand to " + oprType + " is not a number!");
				rTermType.checkType(lib.integerType, 
					"right " + oprType + " operand", this, 
					"Right operand to " + oprType + " is not a number!");
			}
			else if(oprType == andToken) {
				lTermType.checkType(lib.boolType, 
					"left 'and' operand", this, 
					"Left operand to " + oprType + " is not a Boolean!");
				rTermType.checkType(lib.boolType, 
					"right 'and' operand", this, 
					"Right operand to " + oprType + " is not a Boolean!");
			}
		}
	}

	@Override
	public void genCode(CodeFile f) {
		facList.get(0).genCode(f);
		for(int i = 0; i < facOprList.size(); i++) {
			f.genInstr("", "pushl", "%eax", "");
			facList.get(i+1).genCode(f);
			f.genInstr("", "movl", "%eax,%ecx", "");
			f.genInstr("", "popl", "%eax", "");
			
			switch(facOprList.get(i).oprType) {
			case multiplyToken:
				f.genInstr("", "imull", "%ecx,%eax", "  *");
				break;
			case divToken:
				f.genInstr("", "cdq", "", "");
				f.genInstr("", "idivl", "%ecx", "  /");
				break;
			case modToken:
				f.genInstr("", "cdq", "", "");
				f.genInstr("", "idivl", "%ecx", "");
				f.genInstr("", "movl", "%edx,%eax", "  mod");
				break;
			case andToken:
				f.genInstr("", "andl", "%ecx,%eax", "  and");
				break;
			default: // will never execute
				break;
			}
		}
	}
}