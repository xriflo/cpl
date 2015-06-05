package ro.pub.cs.lcpl;


import java.util.HashMap;


public class _Evaluator {
	public HashMap<Variable, String> variables;
	public String code = "";

	public void evaluate(Expression e) {
		if(e instanceof Block) {
			Block b = (Block)e;
			
			
		}
		else if(e instanceof IfStatement) {
			

		}
		else if(e instanceof WhileStatement) {
			WhileStatement whs = (WhileStatement)e;
			
		}
		else if(e instanceof Cast) {
			Cast c = (Cast)e;
			if(c.getE1().getType().equals("Int") && c.getType().equals("String")) {
				
			}
			else if(c.getE1().getType().equals("String") && c.getType().equals("Int")) {
				
			}
			else {
				this.code +=
						String.format("((%s)__lcpl_cast(",
								_FuncLibrary.convert(c.getTypeData().getName()));
				evaluate(c.getE1());
				this.code += String.format(", R%s", c.getType());
				this.code += "));";
			}
			
		}
		else if(e instanceof LocalDefinition) {
			LocalDefinition ld = (LocalDefinition)e;
			
		}
		else if(e instanceof NewObject) {
			NewObject no = (NewObject)e;
			this.code += String.format("__lcpl_new(&R%s)", no.getType());
			
			
		}
		else if(e instanceof Assignment) {
			
		}
		else if(e instanceof Symbol) {
			Symbol s = (Symbol)e;
			
			
			
			
		}
		else if(e instanceof SubString) {
			SubString ss = (SubString)e;
			
		}
		else if(e instanceof BaseDispatch){
			
			if(e instanceof Dispatch) {
				
			}
			else if(e instanceof StaticDispatch){
				StaticDispatch sd = (StaticDispatch)e;
				
			}
		}
		else if(e instanceof IntConstant) {
			
			IntConstant ic = (IntConstant)e;
		}
		else if(e instanceof StringConstant) {
			StringConstant sc = (StringConstant)e;
		}
		else if(e instanceof VoidConstant) {
			VoidConstant vc = (VoidConstant)e;
		}
		else if(e instanceof UnaryOp) {
			if(e instanceof UnaryMinus) {
				UnaryMinus um = (UnaryMinus)e;
			}
			else if(e instanceof LogicalNegation) {
				LogicalNegation ln = (LogicalNegation)e;
				
			}
		}
		else if(e instanceof BinaryOp) {
			if(e instanceof Addition) {
				Addition add = (Addition)e;
				
				
			}
			else if(e instanceof Subtraction) {
				Subtraction sub = (Subtraction)e;
			}
			else if(e instanceof Multiplication) {
				Multiplication mul = (Multiplication)e;
			}
			else if(e instanceof Division) {
				Division div = (Division)e;
			}
			else if(e instanceof EqualComparison) {
				EqualComparison ec = (EqualComparison)e;
				
			}
			else if(e instanceof LessThan) {
				LessThan lt = (LessThan)e;
			}
			else if(e instanceof LessThanEqual) {
				LessThanEqual lte = (LessThanEqual)e;
			}
		}
	}
}
