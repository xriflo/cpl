package ro.pub.cs.lcpl;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class _FuncLibrary {

	public static final String convertSpecialCh(String lcpl) {
		if(lcpl=="\\")
			return "\\\\";
		else if(lcpl=="\"")
			return lcpl;
		else if(lcpl.equals("\n")) {
			return "\\n";
		}
		else if(lcpl=="\r")
			return "\\r";
		else
			return lcpl;
	}
	
	public static final String convert (String lcpl) {
		if(lcpl.equals("String")) return "struct TString*";
		else if(lcpl.equals("Int")) return "int";
		else if(lcpl.equals("void")) return "void";
		else return String.format("struct T%s*", lcpl);
	}
	
	public static final LinkedHashMap<String, Attribute> createAttributes(Program p, LCPLClass c, LinkedHashMap<String, Attribute> attributes) {
		if(c.equals(p.getObjectType())) return attributes;
		else {
			for(Feature feature : c.getFeatures()) {
				if(feature instanceof Attribute) {
					
					//	System.out.print(((Attribute)feature).getName()+" ");
					
					Attribute attribute = (Attribute)feature;
					attributes.put(attribute.getName(), attribute);
				}
			}
			return createAttributes(p, c.getParentData(), attributes);
		}
		
	}
	
	public static final HashMap<String, Method> createMethods(Program p, LCPLClass c, HashMap<String, Method> methods) {
		
		if(c==null) return methods;
		else {
			for(Feature feature : c.getFeatures()) {
				if(feature instanceof Method) {
					Method method = (Method)feature;
					if(methods.get(method.getName())==null) {
						methods.put(method.getName(), method);
					}
				}
			}
			return createMethods(p, c.getParentData(), methods);
		}
	}
}
