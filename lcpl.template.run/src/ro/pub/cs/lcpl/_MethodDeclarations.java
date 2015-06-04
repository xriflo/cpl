package ro.pub.cs.lcpl;

public class _MethodDeclarations {
	private String methodDeclCode;
	
	
	public void generateMethodDeclCode(Program p) {
		for(LCPLClass c : p.getClasses()) {
			if(!c.getName().matches("Object|String|IO")) {
				for(Feature f : c.getFeatures())
					if(f instanceof Method) {
						Method m = (Method)f;
						this.methodDeclCode +=
								String.format("typedef %s (*TF_M%d_%s_%s)(%s, ",
										_FuncLibrary.convert(m.getReturnType()),
										c.getName().length(),
										c.getName(),
										m.getName(),
										_FuncLibrary.convert(c.getName()));
						for(FormalParam fp : m.getParameters()) {
							this.methodDeclCode +=
									String.format("%s %s, ", _FuncLibrary.convert(fp.getType()),
											fp.getName());
						}
						this.methodDeclCode = this.methodDeclCode.substring(0, this.methodDeclCode.length()-2);
						this.methodDeclCode += ");\n";
					}
				for(Feature f : c.getFeatures())
					if(f instanceof Method) {
						Method m = (Method)f;
						//void M8_Vertex_print(struct TVertex *self, struct TIO* stream);
						this.methodDeclCode +=
								String.format("%s M%d_%s_%s(%s self, ",
										_FuncLibrary.convert(m.getReturnType()),
										c.getName().length(),
										c.getName(),
										m.getName(),
										_FuncLibrary.convert(c.getName()));
						for(FormalParam fp : m.getParameters()) {
							this.methodDeclCode +=
									String.format("%s %s, ", _FuncLibrary.convert(fp.getType()),
											fp.getName());
						}
						this.methodDeclCode = this.methodDeclCode.substring(0, this.methodDeclCode.length()-2);
						this.methodDeclCode += ");\n";
					}
				
			}
			else {
				for(Feature f : c.getFeatures())
					if(f instanceof Method) {
						Method m = (Method)f;
						this.methodDeclCode +=
								String.format("typedef %s (*TF_M%d_%s_%s)(%s, ",
										_FuncLibrary.convert(m.getReturnType()),
										c.getName().length(),
										c.getName(),
										m.getName(),
										_FuncLibrary.convert(c.getName()));
						for(FormalParam fp : m.getParameters()) {
							this.methodDeclCode +=
									String.format("%s %s, ", _FuncLibrary.convert(fp.getType()),
											fp.getName());
						}
						this.methodDeclCode = this.methodDeclCode.substring(0, this.methodDeclCode.length()-2);
						this.methodDeclCode += ");\n";
					}
			}
			this.methodDeclCode += "\n";
		}
	}
	
	public _MethodDeclarations() {
		this.methodDeclCode = "";
	}

	public String getMethodDeclCode() {
		return methodDeclCode;
	}

	public void setMethodDeclCode(String methodDeclCode) {
		this.methodDeclCode = methodDeclCode;
	}
	
}
