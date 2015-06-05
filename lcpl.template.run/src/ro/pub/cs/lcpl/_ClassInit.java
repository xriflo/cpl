package ro.pub.cs.lcpl;

import java.util.HashMap;
import java.util.LinkedList;

public class _ClassInit {
	private String initMethodsCode;
	private _Evaluator evaluator;
	public HashMap<LCPLClass, HashMap<Method, Integer>> vt;
	
	public void generateMethodsCode(Program p, LinkedList<LCPLClass> sortedList) {
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
	}
	
	
	public String getInitMethodsCode() {
		return initMethodsCode;
	}

	public void setInitMethodsCode(String initMethodsCode) {
		this.initMethodsCode = initMethodsCode;
	}

	public _ClassInit(HashMap<LCPLClass, HashMap<Method, Integer>> vt) {
		this.initMethodsCode = "";
		this.evaluator = new _Evaluator();
		this.vt = vt;
	}
	
}
