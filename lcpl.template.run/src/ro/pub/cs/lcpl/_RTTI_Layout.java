package ro.pub.cs.lcpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class _RTTI_Layout {
	private String layoutCode;
	public void generateHierarchy() {
		
	}
	
	public void generateLayoutCode(Program p) {
		List<LCPLClass> allClasses = new LinkedList<LCPLClass>();
		List<LCPLClass> sortedClasses = new LinkedList<LCPLClass>();
		
		/* add all classes except for Object, IO, String */
		for(LCPLClass c : p.getClasses())
			if(!c.getName().matches("Object|String|IO"))
				allClasses.add(c);
		
		/* add all classes which have Object parent */
		for(LCPLClass c : p.getClasses())
			if(c.getName().matches("Object"))
				sortedClasses.add(c);
		
		/* add all classes which have String/IO parent */
		for(LCPLClass c : p.getClasses())
			if(c.getName().matches("String|IO"))
				sortedClasses.add(c);
		
		while(allClasses.size()!=0) {
			LinkedList<LCPLClass> newList = new LinkedList<LCPLClass>();
			for(LCPLClass c : sortedClasses)
				for(LCPLClass px : allClasses)
					if(px.getParentData().getName()==c.getName())
						newList.add(px);
			allClasses.removeAll(newList);
			sortedClasses.addAll(newList);
		}
		
		
		for(LCPLClass c : sortedClasses)
			if(!c.getName().matches("Object|String|IO")) {
				this.layoutCode += 
						String.format("struct __lcpl_rtti R%s = { &N%s, sizeof(struct T%s), &R%s, ",
								c.getName(),
								c.getName(),
								c.getName(),
								c.getParentData().getName());
				this.layoutCode += String.format("{ %s_init, ", c.getName());
				LinkedHashMap<String, Method>  methods = new LinkedHashMap<String, Method>();
				this.createMethods(p, c, methods);
				
				List<String> keyList = new ArrayList<String>(methods.keySet());
				for(Integer i=keyList.size()-1; i>=0; i--) {
					this.layoutCode +=
							String.format("M%d_%s_%s, ",
									methods.get(keyList.get(i)).getParent().getName().length(),
									methods.get(keyList.get(i)).getParent().getName(),
									keyList.get(i));
				}
				
				this.layoutCode = this.layoutCode.substring(0, this.layoutCode.length()-2);
				this.layoutCode += "} };\n";
			}
	}
	
	private LinkedHashMap<String, Method> createMethods(Program p, LCPLClass c, LinkedHashMap<String, Method> methods) {
		
		if(c==null) return methods;
		else {
			for(Integer i=c.getFeatures().size()-1; i>=0; i--) {
				if(c.getFeatures().get(i) instanceof Method) {
					Method m = (Method)c.getFeatures().get(i);
					if(methods.get(m.getName())==null) {
						methods.put(m.getName(), m);
					}
				}
			}
			return createMethods(p, c.getParentData(), methods);
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
