package ro.pub.cs.lcpl;

import java.util.LinkedHashMap;

public class _ClassName {
	private String classNameCode;
	
	public void generateClassNames(Program p) {
		for(LCPLClass c : p.getClasses()) 
			if(!c.getName().matches("Object|String|IO")) {
				String cName = c.getName();
				this.classNameCode +=
						String.format("struct TString N%s={ &RString, %d, \"%s\" };\n", cName, 
								cName.length(), cName);
			}
	}
	
	public _ClassName() {
		this.classNameCode = "";
	}

	public String getClassNameCode() {
		return classNameCode;
	}

	public void setClassNameCode(String classNameCode) {
		this.classNameCode = classNameCode;
	}
	

}
