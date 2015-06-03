package ro.pub.cs.lcpl;

import java.util.LinkedHashMap;

public class _RTTI_Layout {
	private String layoutCode;

	
	public void generateLayoutCode(Program p) {
		for(LCPLClass c : p.getClasses())
			if(!c.getName().matches("Object|String|IO")) {
				this.layoutCode += "struct TShape { struct __lcpl_rtti *rtti;";
				
				LinkedHashMap<String, Attribute> attributes = new LinkedHashMap<String, Attribute>();
				attributes = _FuncLibrary.createAttributes(p, c, attributes);
				for(String key : attributes.keySet()) {
					Attribute a = attributes.get(key);
					this.layoutCode += 
							String.format("%s %s;", _FuncLibrary.convert(a.getType()), a.getName());
					
				}
				this.layoutCode += "};\n";
			}
	}
	
	
	
	public _RTTI_Layout() {
		this.layoutCode = "";
	}

	public String getLayoutCode() {
		return layoutCode;
	}

	public void setLayoutCode(String layoutCode) {
		this.layoutCode = layoutCode;
	}
}
