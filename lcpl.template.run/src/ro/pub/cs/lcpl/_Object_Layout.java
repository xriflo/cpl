package ro.pub.cs.lcpl;

import java.util.LinkedHashMap;

public class _Object_Layout {
	private String objLayoutCode;

	public void generateObjectLayoutCode(Program p) {
		for(LCPLClass c : p.getClasses()) {
			if(!c.getName().matches("Object|String|IO")) {
				LinkedHashMap<String, Attribute> attributes = new LinkedHashMap<String, Attribute>();
				attributes = _FuncLibrary.createAttributes(p, c, attributes);
				this.objLayoutCode +=
						String.format("struct T%s { struct __lcpl_rtti *rtti; ",
								c.getName());
				for(String attr : attributes.keySet()) {
					this.objLayoutCode +=
							String.format("%s %s; ",
									_FuncLibrary.convert(attributes.get(attr).getType()),
									attributes.get(attr).getName());
				}
				this.objLayoutCode = this.objLayoutCode.substring(0, this.objLayoutCode.length()-1);
				this.objLayoutCode += "};\n";
			}
		}
	}
	
	
	public String getObjLayoutCode() {
		return objLayoutCode;
	}

	public void setObjLayoutCode(String objLayoutCode) {
		this.objLayoutCode = objLayoutCode;
	}

	public _Object_Layout() {
		this.objLayoutCode = "";
	}
	
	
}
