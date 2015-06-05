package ro.pub.cs.lcpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class _RTTI_Layout {
	private String layoutCode;
	private LinkedList<LCPLClass> sortedClassesList;
	public HashMap<LCPLClass, HashMap<Method, Integer>> vt = new HashMap<LCPLClass, HashMap<Method, Integer>>();
	
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
		
		this.sortedClassesList = (LinkedList<LCPLClass>)sortedClasses;
		
		for(LCPLClass c : sortedClasses)
			if(!c.getName().matches("Object|String|IO")) {
				HashMap<Method, Integer> vt_c = new HashMap<Method, Integer>();
				int pos = 0;
				this.layoutCode += 
						String.format("struct __lcpl_rtti R%s = { &N%s, sizeof(struct T%s), &R%s, ",
								c.getName(),
								c.getName(),
								c.getName(),
								c.getParentData().getName());
				
				this.layoutCode += String.format("{ %s_init, ", c.getName());
				pos++;
				LinkedHashMap<String, Method>  methods = new LinkedHashMap<String, Method>();
				this.createMethods(p, c, methods);
				
				List<String> keyList = new ArrayList<String>(methods.keySet());
				
				for(Integer i=keyList.size()-1; i>=0; i--) {
					this.layoutCode +=
							String.format("M%d_%s_%s, ",
									methods.get(keyList.get(i)).getParent().getName().length(),
									methods.get(keyList.get(i)).getParent().getName(),
									keyList.get(i));
					vt_c.put(methods.get(keyList.get(i)), pos);
					pos++;
				}
				
				this.layoutCode = this.layoutCode.substring(0, this.layoutCode.length()-2);
				this.layoutCode += "} };\n";
				this.vt.put(c, vt_c);
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
		this.sortedClassesList = new LinkedList<LCPLClass>();
	}

	public String getLayoutCode() {
		return layoutCode;
	}

	public void setLayoutCode(String layoutCode) {
		this.layoutCode = layoutCode;
	}

	public LinkedList<LCPLClass> getSortedClassesList() {
		return sortedClassesList;
	}

	public void setSortedClassesList(LinkedList<LCPLClass> sortedClassesList) {
		this.sortedClassesList = sortedClassesList;
	}
	
}
