import java.util.HashMap;

import ro.pub.cs.lcpl.*;


public class LCPLTranslatorC {
	public String translate(Program p)
	{
		
		/* TO DO - traverse the AST and fill in the following sections. This is an example based on hello.lcpl
		 *  
		 * class Main inherits IO
    	 *   main :
    	 *     [out "Hello world!"];
    	 *   end;
    	 * end;
         */
		
		/* Use the runtime library */
		String headers=                 "#include \"lcpl_runtime.h\" \n";
		
		/* static data : strings used in the program */
		String stringConstants;
		_GlobalStringRuntime gsr = new _GlobalStringRuntime();
		gsr.searchProgramForStrings(p);
		stringConstants = gsr.getStringCode();
		
		/* object layouts: pointer to RTTI followed by attributes */
		String objectLayouts;
		_RTTI_Layout rttiL = new _RTTI_Layout();
		rttiL.generateLayoutCode(p);
		objectLayouts = rttiL.getLayoutCode();
		
		/* static data : class names, as strings */
		String classNames;
		_ClassName cn = new _ClassName();
		cn.generateClassNames(p);
		classNames = cn.getClassNameCode();
		
		/* constructors */
		String constructorDeclarations;
		_ConstructDeclarations cd = new _ConstructDeclarations();
		cd.generateConstructDecl(p);
		constructorDeclarations = cd.getConstructDeclCode();
		
		/* declaration of methods */
		String methodDeclarations;
		_MethodDeclarations md = new _MethodDeclarations();
		md.generateMethodDeclCode(p);
		methodDeclarations = md.getMethodDeclCode();
		
		/* runtime type information (RTTI) - class name, class size, parent class, vtable */
		String vtables;
		_RTTI_Layout rtti = new _RTTI_Layout();
		rtti.generateLayoutCode(p);
		vtables = rtti.getLayoutCode();
		/*
		System.out.println("==========");
		for(LCPLClass c: rtti.vt.keySet()) {
			System.out.print(c.getName()+":{");
			HashMap<Method, Integer> vtc = rtti.vt.get(c);
			for(Method m : vtc.keySet()) {
				System.out.print("["+m.getName()+","+vtc.get(m)+"],");
			}
			System.out.println();
		}
		System.out.println("==========");
		*/
		/* definitions of methods in the program, including constructors */
		String constructors;
		//"void Main_init(struct TMain *self){ IO_init((struct TIO*)self); } \n";
		_ClassInit ci = new _ClassInit(rtti.vt);
		ci.generateMethodsCode(p, rtti.getSortedClassesList());
		constructors = ci.getInitMethodsCode();
		
		String methods=                 "void M4_Main_main(struct TMain *self) \n"
				                      + "{\n  typedef struct TIO* (*TF1)(struct TIO *, struct TString *); \n"
				                      + "  ((TF1)(self->rtti->vtable[5])) ((struct TIO *)self,&SC1); \n} \n";
									  /* [out "Hello world!"] is a dispatch to the method "out String -> IO" inherited from "IO" */
		
		/* create a new Main object and call its main method */
		String startup=                 "void startup(void) { struct TMain *main=__lcpl_new(&RMain); M4_Main_main(main); } \n";
		System.out.println(constructors);
		return headers+stringConstants+objectLayouts+classNames+constructorDeclarations+methodDeclarations+vtables+constructors+methods+startup;
		
		
	}
}
