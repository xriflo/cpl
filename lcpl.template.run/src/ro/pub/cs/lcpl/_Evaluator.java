package ro.pub.cs.lcpl;


import java.util.HashMap;
import java.util.LinkedHashMap;


public class _Evaluator {
	public HashMap<Variable, String> variables;
	public String code = "";
	public HashMap<LCPLClass, HashMap<Method, Integer>> vt = new HashMap<LCPLClass, HashMap<Method, Integer>>();
	public LinkedHashMap<StringConstant, Integer> literals = new LinkedHashMap<StringConstant, Integer>();
	public HashMap<Expression, String> temp_var;
	public Integer temp_pos;
	
	public void evaluate(Expression e) {
		if(e instanceof Block) {
			Block b = (Block)e;
			/*
			//this.code += "Block\n";
			Block b = (Block)e;
			for(Expression expr : b.getExpressions()) {
				evaluate(expr);
				this.code += ";\n";
			
			}
			*/
			if(b.getExpressions()==null || (b.getExpressions().size()==0))
				return;
			
			
			if(b.getExpressions().size()==1) {
				evaluate(b.getExpressions().get(0));
			}
			else {
				this.code += "({\n";
				for(Expression expr : b.getExpressions()) {
					evaluate(expr);
					this.code += ";\n";
				}
				this.code += "\n})";
			}
			
			
			
			
		}
		else if(e instanceof IfStatement) {
			IfStatement ifs = (IfStatement)e;
			if(ifs.getThenExpr()==null) {
				this.code += "if(";
				evaluate(ifs.getCondition());
				this.code += ")";
				evaluate(ifs.getIfExpr());
			}
			else {
				this.code += "(";
				evaluate(ifs.getCondition());
				this.code += ") ? (";
				evaluate(ifs.getIfExpr());
				this.code += ") : (";
				evaluate(ifs.getThenExpr());
				this.code += ")";
			}
		}
		else if(e instanceof WhileStatement) {
			WhileStatement whs = (WhileStatement)e;
			
		}
		else if(e instanceof Cast) {
			//this.code += "Cast\n";
			Cast c = (Cast)e;
			/*
			if(c.getE1().getTypeData().getName().equals("Int") && 
					c.getTypeData().getName().equals("String")) {
				this.code += "__lcpl_intToString(";
				evaluate(c.getE1());
				this.code += ")";
			}
			else if(c.getE1().getTypeData().getName().equals("String") && 
					c.getTypeData().getName().equals("Int")) {
				
			}
			else {
				this.code +=
						String.format("((%s)__lcpl_cast(",
								_FuncLibrary.convert(c.getTypeData().getName()));
				evaluate(c.getE1());
				if(this.code.endsWith("\n"))
					this.code = this.code.substring(0, this.code.length()-1);
				if(this.code.endsWith(";"))
					this.code = this.code.substring(0, this.code.length()-1);
				this.code += String.format(", &R%s", c.getType());
				this.code += "))";
			}
			*/
			if(c.getTypeData().getName().equals("Object")) {
				evaluate(c.getE1());
			}
			else if(c.getE1().getTypeData().getName().equals("Int") &&
					c.getTypeData().getName().equals("String")) {
				
			}
			else if(c.getE1().getTypeData().getName().equals("String") &&
					c.getTypeData().getName().equals("Int")) {
				
			}
			else {
				this.code +=
						String.format("((%s)__lcpl_cast(",
								_FuncLibrary.convert(c.getTypeData().getName()));
				evaluate(c.getE1());
				this.code += String.format(", &R%s))", c.getTypeData().getName());
			}
		}
		else if(e instanceof LocalDefinition) {
			//this.code += "LocalDef\n";
			LocalDefinition ld = (LocalDefinition)e;
			/*
			this.code += "({\n";
			this.code += String.format("%s _l_%s", _FuncLibrary.convert(ld.getType()),
					ld.getName());
			if(ld.getInit()!=null) {
				this.code += " = ";
				evaluate(ld.getInit());
			}
			this.code += ";\n";
			this.code += "(";
			evaluate(ld.getScope());
			this.code += ");\n";
					
			this.code += "})";
			
			*/
			
			this.code += "({\n";
			this.code += String.format("%s _l_%s", _FuncLibrary.convert(ld.getType()),
					ld.getName());
			if(ld.getInit()!=null) {
				this.code += " = ";
				evaluate(ld.getInit());
			}
			this.code += ";\n";
			this.code += "(";
			evaluate(ld.getScope());
			this.code += ");\n";
			
			this.code += "})\n";
		}
		else if(e instanceof NewObject) {
			//this.code += "NewObj\n";
			NewObject no = (NewObject)e;
			this.code += String.format("__lcpl_new(&R%s)", no.getType());
			
			
		}
		else if(e instanceof Assignment) {
			//this.code += "Assignment\n";
			Assignment a = (Assignment)e;
			
			if(a.getSymbolData() instanceof Attribute) {
				Attribute attr = (Attribute)a.getSymbolData();
				this.code += String.format("self->%s", attr.getName());
			}
			else if(a.getSymbolData() instanceof LocalDefinition) {
				this.code += ("_l_" + a.getSymbolData().getName());
			}
			
			else if(a.getSymbolData() instanceof FormalParam) {
				this.code += a.getSymbolData().getName();
			}
			else {
				this.code += "whatchu doin' here?!";
			}
			this.code += " = ";
			evaluate(a.getE1());
			
		}
		else if(e instanceof Symbol) {
			Symbol s = (Symbol)e;
			/*
			if(s.getVariable() instanceof Attribute) {
				this.code += String.format("self->%s", s.getVariable().getName());
			}
			else if(s.getVariable() instanceof FormalParam) {
				this.code += s.getVariable().getName();
			}
			else if(s.getVariable() instanceof LocalDefinition) {
				this.code += ("_l_"+s.getVariable().getName());
			}
			
			*/
			if(s.getVariable() instanceof Attribute)
				this.code += String.format("self->%s", s.getVariable().getName());
			if(s.getVariable() instanceof FormalParam)
				this.code += s.getVariable().getName();
			if(s.getVariable() instanceof LocalDefinition)
				this.code += ("_l_"+s.getVariable().getName());
			
		}
		else if(e instanceof SubString) {
			SubString ss = (SubString)e;
			
		}
		else if(e instanceof BaseDispatch){
			
			if(e instanceof Dispatch) {
			
				Dispatch d = (Dispatch)e;
			/*	
				if(d.getObject() instanceof Symbol) {
					
					this.code += String.format("((TF_M%d_%s_%s)(",
							d.getMethod().getParent().getName().length(),
							d.getMethod().getParent().getName(),
							d.getMethod().getName());
					evaluate(d.getObject());
					this.code += String.format("->rtti->vtable[%d]))",
							this.vt.get(d.getMethod().getParent()).get(d.getMethod()));
							
					this.code += String.format("((%s)", _FuncLibrary.convert(d.getObject().getTypeData().getName()));
					evaluate(d.getObject());
					this.code += ", ";
					if(d.getArguments()!=null && d.getArguments().size()!=0) {
						this.code += " ";
						for(Expression arg : d.getArguments()) {
							evaluate(arg);
							this.code +=", ";
						}
					}
					this.code = this.code.substring(0, this.code.length()-2);
					this.code += ")";
				}
				
				if(! (d.getObject() instanceof Symbol)) {
					this.code += "({\n";
					this.code += String.format("%s _t%d = ",
							_FuncLibrary.convert(d.getObject().getType()),
							this.temp_pos);
					
					this.temp_var.put(d.getObject(), "_t"+this.temp_pos);
					this.temp_pos++;
					evaluate(d.getObject());
					this.code += ";\n";
					
					this.code += String.format("((TF_M%d_%s_%s)(%s->rtti->vtable[%d]))((%s)%s, ",
							d.getObject().getType().length(),
							d.getObject().getTypeData().getName(),
							d.getMethod().getName(),
							this.temp_var.get(d.getObject()),
							this.vt.get(d.getObject().getTypeData()).get(d.getMethod()),
							_FuncLibrary.convert(d.getObject().getTypeData().getName()),
							this.temp_var.get(d.getObject()));
					
					for(Expression arg : d.getArguments()) {
						evaluate(arg);
						this.code += ", ";
					}
					this.code = this.code.substring(0, this.code.length()-2);
					this.code += ");\n";
					this.code += "});\n";
				
					
				}
				*/
				
				if(d.getObject() instanceof Symbol) {
					this.code += String.format("((TF_M%d_%s_%s)(",
							d.getObject().getTypeData().getName().length(),
							d.getObject().getTypeData().getName(),
							d.getMethod().getName());
					evaluate(d.getObject());
					this.code += String.format("->rtti->vtable[%d]))",
							this.vt.get(d.getObject().getTypeData()).get(d.getMethod()));
					
					this.code += String.format("((%s)", _FuncLibrary.convert(d.getObject().getTypeData().getName()));
					evaluate(d.getObject());
					this.code += ", ";
					if(d.getArguments()!=null && d.getArguments().size()!=0) {
						for(Expression arg : d.getArguments()) {
							evaluate(arg);
							this.code +=", ";
						}
					}
					this.code = this.code.substring(0, this.code.length()-2);
					this.code += ")";
							
				}
				
			}
			else if(e instanceof StaticDispatch){
				StaticDispatch sd = (StaticDispatch)e;
				
			}
		}
		else if(e instanceof IntConstant) {
			
			IntConstant ic = (IntConstant)e;
			this.code += ic.getValue();
		}
		else if(e instanceof StringConstant) {
			StringConstant sc = (StringConstant)e;
			
			this.code += "&SC"+this.literals.get(sc);
		}
		else if(e instanceof VoidConstant) {
			VoidConstant vc = (VoidConstant)e;
			this.code += "NULL";
		}
		else if(e instanceof UnaryOp) {
			if(e instanceof UnaryMinus) {
				UnaryMinus um = (UnaryMinus)e;
				this.code += "-(";
				evaluate(um.getE1());
				this.code += ")";
			}
			else if(e instanceof LogicalNegation) {
				LogicalNegation ln = (LogicalNegation)e;
				this.code += "!(";
				evaluate(ln.getE1());
				this.code += ")";
				
			}
		}
		else if(e instanceof BinaryOp) {
			if(e instanceof Addition) {
				Addition add = (Addition)e;
				if(add.getE1().getTypeData().equals("Int") &&
						add.getE2().getTypeData().equals("Int")) {
					this.code += "(";
					evaluate(add.getE1());
					this.code += ")";
					this.code += "+";
					this.code += "(";
					evaluate(add.getE2());
					this.code += ")";
				}
				else if(add.getE1().getTypeData().equals("String") &&
						add.getE2().getTypeData().equals("String")) {
				
				}
				else if(add.getE1().getTypeData().equals("String") &&
						add.getE2().getTypeData().equals("Int")) {
					
				}
				else if(add.getE1().getTypeData().equals("Int") &&
						add.getE2().getTypeData().equals("String")) {
				}
				else {
					
				}
			}
			else if(e instanceof Subtraction) {
				Subtraction sub = (Subtraction)e;
				this.code += "(";
				evaluate(sub.getE1());
				this.code += ")";
				this.code += "-";
				this.code += "(";
				evaluate(sub.getE2());
				this.code += ")";
			}
			else if(e instanceof Multiplication) {
				Multiplication mul = (Multiplication)e;
				this.code += "(";
				evaluate(mul.getE1());
				this.code += ")";
				this.code += "*";
				this.code += "(";
				evaluate(mul.getE2());
				this.code += ")";
			}
			else if(e instanceof Division) {
				Division div = (Division)e;
				this.code += "(";
				evaluate(div.getE1());
				this.code += ")";
				this.code += "/";
				this.code += "(";
				evaluate(div.getE2());
				this.code += ")";
			}
			else if(e instanceof EqualComparison) {
				EqualComparison ec = (EqualComparison)e;
				this.code += "(";
				evaluate(ec.getE1());
				this.code += ")";
				this.code += "==";
				this.code += "(";
				evaluate(ec.getE2());
				this.code += ")";
			}
			else if(e instanceof LessThan) {
				LessThan lt = (LessThan)e;
				this.code += "(";
				evaluate(lt.getE1());
				this.code += ")";
				this.code += "<";
				this.code += "(";
				evaluate(lt.getE2());
				this.code += ")";
			}
			else if(e instanceof LessThanEqual) {
				LessThanEqual lte = (LessThanEqual)e;
				this.code += "(";
				evaluate(lte.getE1());
				this.code += ")";
				this.code += "<=";
				this.code += "(";
				evaluate(lte.getE2());
				this.code += ")";
			}
		}
	}
}
