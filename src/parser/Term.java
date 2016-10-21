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
		for(Factor fact : facList) fact.check(curScope, lib);
		
		for(int i = 0; i < facOprList.size(); i++) {
			TokenKind oprType = facOprList.get(i).oprType;
			types.Type lTermType = facList.get(i).type;   // type of left factor
			types.Type rTermType = facList.get(i+1).type; // type of right factor
			
			if(oprType == multiplyToken 
					|| oprType == divToken 
					|| oprType == modToken) {
				lTermType.checkType(lib.integerType, 
					"left " + oprType + " operands", this, 
					"Left operand to " + oprType + " is not a number!");
				rTermType.checkType(lib.integerType, 
					"right " + oprType + " operands", this, 
					"Right operand to " + oprType + " is not a number!");
			}
			else if(oprType == andToken) {
				lTermType.checkType(lib.boolType, 
					"left " + oprType + " operands", this, 
					"Left operand to " + oprType + " is not a Boolean!");
				rTermType.checkType(lib.boolType, 
					"right " + oprType + " operands", this, 
					"Right operand to " + oprType + " is not a Boolean!");
			}
		}
	}
}