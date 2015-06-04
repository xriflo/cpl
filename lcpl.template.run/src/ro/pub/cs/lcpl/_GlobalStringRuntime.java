package ro.pub.cs.lcpl;

import java.util.LinkedHashMap;

public class _GlobalStringRuntime {
	private int globalIndex;
	private LinkedHashMap<StringConstant, Integer> literals;
	private String stringCode = "TString SC_empty={ &RString, 0, \"\" };\n";
	
	
	public void searchProgramForStrings(Program p) {
		for(LCPLClass c : p.getClasses()) {
			for(Feature f : c.getFeatures()) {
				if (f instanceof Attribute) {
					Attribute a = (Attribute)f;
					searchForStrings(a.getInit(), this.literals);
				}
			}
		}
		for(LCPLClass c : p.getClasses()) {
			for(Feature f : c.getFeatures()) {
				if(f instanceof Method) {
					Method m = (Method)f;
					searchForStrings(m.getBody(),this.literals);
					
				}
			}
		}
	}
	/* search every corner for a string and put them in literals */
	public void searchForStrings(Expression e, LinkedHashMap<StringConstant, Integer> literals) {
		/* if it's a string increment the global index */
		if(e instanceof StringConstant) {
			StringConstant sc = (StringConstant)e;
			literals.put(sc, this.globalIndex);
			
			/* generate the C code for strings */
			stringCode +=
					String.format("struct TString SC%d={ &RString, %d, \"%s\" };\n",
							this.globalIndex,sc.getValue().length(), _FuncLibrary.convertSpecialCh(sc.getValue()));
			this.globalIndex = this.globalIndex + 1;
		}
		else if(e instanceof Block) {
			Block block = (Block)e;
			for(Expression expr : block.getExpressions()) {
				this.searchForStrings(expr, literals);
			}
			
		}
		else if(e instanceof WhileStatement) {
			WhileStatement ws = (WhileStatement)e;
			this.searchForStrings(ws.getCondition(), literals);
			this.searchForStrings(ws.getLoopBody(), literals);
		}
		else if(e instanceof IfStatement) {
			IfStatement ifs = (IfStatement)e;
			this.searchForStrings(ifs.getCondition(), literals);
			this.searchForStrings(ifs.getIfExpr(), literals);
			this.searchForStrings(ifs.getThenExpr(), literals);
		}
		else if(e instanceof SubString) {
			SubString subs = (SubString)e;
			this.searchForStrings(subs.getStringExpr(), literals);
		}
		else if(e instanceof Cast) {
			Cast cast = (Cast)e;
			this.searchForStrings(cast.getE1(), literals);
		}
		else if(e instanceof LocalDefinition) {
			LocalDefinition ld = (LocalDefinition)e;
			this.searchForStrings(ld.getInit(), literals);
			this.searchForStrings(ld.getScope(), literals);
		}
		else if(e instanceof Assignment) {
			Assignment a = (Assignment)e;
			this.searchForStrings(a.getE1(), literals);
		}
		else if(e instanceof UnaryOp) {
			if(e instanceof UnaryMinus) {
				UnaryMinus um = (UnaryMinus)e;
				this.searchForStrings(um.getE1(), literals);
			}
			else if(e instanceof LogicalNegation) {
				LogicalNegation lg = (LogicalNegation)e;
				this.searchForStrings(lg.getE1(), literals);
			}
		}
		else if(e instanceof BinaryOp) {
			if(e instanceof Division) {
				Division d = (Division)e;
				this.searchForStrings(d.getE1(), literals);
				this.searchForStrings(d.getE2(), literals);
			}
			else if(e instanceof EqualComparison) {
				EqualComparison d = (EqualComparison)e;
				this.searchForStrings(d.getE1(), literals);
				this.searchForStrings(d.getE2(), literals);
			}
			else if(e instanceof LessThan) {
				LessThan d = (LessThan)e;
				this.searchForStrings(d.getE1(), literals);
				this.searchForStrings(d.getE2(), literals);
			}
			else if(e instanceof LessThanEqual) {
				LessThanEqual d = (LessThanEqual)e;
				this.searchForStrings(d.getE1(), literals);
				this.searchForStrings(d.getE2(), literals);
			}
			else if(e instanceof Multiplication) {
				Multiplication d = (Multiplication)e;
				this.searchForStrings(d.getE1(), literals);
				this.searchForStrings(d.getE2(), literals);
			}
			else if(e instanceof Subtraction) {
				Subtraction d = (Subtraction)e;
				this.searchForStrings(d.getE1(), literals);
				this.searchForStrings(d.getE2(), literals);
			}
			else if(e instanceof Addition) {
				Addition d = (Addition)e;
				this.searchForStrings(d.getE1(), literals);
				this.searchForStrings(d.getE2(), literals);
			}
			
		}
		else if(e instanceof BaseDispatch) {
			if(e instanceof Dispatch) {
				Dispatch dispatch = (Dispatch)e;
				this.searchForStrings(dispatch.getObject(), literals);
				for(Expression expr : dispatch.getArguments()) {
					this.searchForStrings(expr, literals);
				}
				
			}
			else if(e instanceof StaticDispatch) {
				StaticDispatch sd = (StaticDispatch)e;
				this.searchForStrings(sd.getObject(), literals);
				for(Expression expr : sd.getArguments()) {
					this.searchForStrings(expr, literals);
				}
			}
		}
		
	}
	
	/* constructor, getters and setters */
	
	public String getStringCode() {
		return stringCode;
	}
	public void setStringCode(String stringCode) {
		this.stringCode = stringCode;
	}
	
	public _GlobalStringRuntime() {
		this.globalIndex = 1;
		this.literals = new LinkedHashMap<StringConstant, Integer>();
	}
	
	public int getGlobalIndex() {
		return globalIndex;
	}
	public void setGlobalIndex(int globalIndex) {
		this.globalIndex = globalIndex;
	}
	public LinkedHashMap<StringConstant, Integer> getLiterals() {
		return literals;
	}
	public void setLiterals(LinkedHashMap<StringConstant, Integer> literals) {
		this.literals = literals;
	}
	
}
