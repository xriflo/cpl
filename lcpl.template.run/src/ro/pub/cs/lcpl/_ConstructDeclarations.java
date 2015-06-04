package ro.pub.cs.lcpl;

public class _ConstructDeclarations {
	private String constructDeclCode;

	
	public void generateConstructDecl(Program p) {
		for(LCPLClass c : p.getClasses())
			if(!c.getName().matches("Object|String|IO")) {
				String cName = c.getName();
				this.constructDeclCode += String.format("void %s_init(struct T%s *self);\n", 
						cName, cName);
			}
	}
	
	public _ConstructDeclarations() {
		this.constructDeclCode = "";
	}

	public String getConstructDeclCode() {
		return constructDeclCode;
	}

	public void setConstructDeclCode(String constructDeclCode) {
		this.constructDeclCode = constructDeclCode;
	}
	
	
}
