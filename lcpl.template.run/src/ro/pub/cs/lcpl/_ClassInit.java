package ro.pub.cs.lcpl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class _ClassInit {
	private String initMethodsCode;
	private _Evaluator evaluator;
	public HashMap<LCPLClass, HashMap<Method, Integer>> vt;
	public LinkedHashMap<StringConstant, Integer> literals;
	
	public void generateMethodsCode(Program p, LinkedList<LCPLClass> sortedList) {
		/* attributes part */
		for(LCPLClass c : sortedList) {
			if(!c.getName().matches("Object|String|IO")) {
				this.initMethodsCode +=
						String.format("void %s_init(struct T%s *self){\n",
								c.getName(),
								c.getName());
				/* init the parrent class */
				this.initMethodsCode +=
						String.format("%s_init((struct T%s*)self);\n",
								c.getParentData().getName(),
								c.getParentData().getName());
								
				/* init attributes */
				for(Feature f : c.getFeatures()) {
					if(f instanceof Attribute) {
						Attribute a = (Attribute)f;
						if(a.getInit()!=null) {
							this.initMethodsCode += String.format("self->%s = ",
									a.getName());
							this.evaluator.code = "";
							this.evaluator.evaluate(a.getInit());
							this.initMethodsCode += this.evaluator.code;
							
						}
						
					}
				}
				this.initMethodsCode += "\n}\n";
			}
		}
		
		for(LCPLClass c : sortedList) {
			if(!c.getName().matches("Object|String|IO")) {
				for(Feature f : c.getFeatures()) {
					if(f instanceof Method) {
						Method m = (Method)f;
						this.initMethodsCode +=
								String.format("%s M%d_%s_%s(%s self, ",
										_FuncLibrary.convert(m.getReturnType()),
										c.getName().length(),
										c.getName(),
										m.getName(),
										_FuncLibrary.convert(c.getName()));
						for(FormalParam fp : m.getParameters()) {
							this.initMethodsCode +=
									String.format("%s %s, ", _FuncLibrary.convert(fp.getType()),
											fp.getName());
						}
						this.initMethodsCode = this.initMethodsCode.substring(0, this.initMethodsCode.length()-2);
						this.initMethodsCode += "){\n";
						if(!m.getReturnType().equals("void"))
							this.initMethodsCode += "return";
						this.initMethodsCode += "({\n";
						//---------------------------
						evaluator.code = "";
						evaluator.evaluate(m.getBody());
						this.initMethodsCode += evaluator.code;
						//----------------------------
						this.initMethodsCode += "});\n";
						this.initMethodsCode += "}\n";
					}
				}
			}
		}
	}
	
	
	public String getInitMethodsCode() {
		return initMethodsCode;
	}

	public void setInitMethodsCode(String initMethodsCode) {
		this.initMethodsCode = initMethodsCode;
	}

	public _ClassInit(HashMap<LCPLClass, HashMap<Method, Integer>> vt,
			LinkedHashMap<StringConstant, Integer> literals) {
		this.initMethodsCode = "";
		this.vt = vt;
		this.literals = literals;
		this.evaluator = new _Evaluator();
		this.evaluator.vt = vt;
		this.evaluator.literals = literals;
		
	}
	
}
