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
		_Object_Layout ol = new _Object_Layout();
		ol.generateObjectLayoutCode(p);
		objectLayouts =ol.getObjLayoutCode();
		
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
		
		/* definitions of methods in the program, including constructors */
		String constructors;
		//"void Main_init(struct TMain *self){ IO_init((struct TIO*)self); } \n";
		_ClassInit ci = new _ClassInit(rtti.vt, gsr.getLiterals());
		ci.generateMethodsCode(p, rtti.getSortedClassesList());
		constructors = ci.getInitMethodsCode();
		
		String startup=                 "void startup(void) { struct TMain *main=__lcpl_new(&RMain); M4_Main_main(main); } \n";
		System.out.println(headers+stringConstants+objectLayouts+classNames+constructorDeclarations+methodDeclarations+vtables+constructors+startup);
		//System.out.println(stringConstants);
		/*
		System.out.println("=======headers========");
		System.out.println(headers);
		System.out.println("=======stringConstanf========");
		System.out.println(stringConstants);
		System.out.println("=======objectLayou========");
		System.out.println(objectLayouts);
		System.out.println("=======classnames========");
		System.out.println(classNames);
		System.out.println("=======constructDecl========");
		System.out.println(constructorDeclarations);
		System.out.println("=======methodDecl========");
		System.out.println(methodDeclarations);
		System.out.println("=======vtables========");
		System.out.println(vtables);
		System.out.println("=======constructors========");
		System.out.println(constructors);
		System.out.println("=======startup========");
		System.out.println(startup);*/
		return headers+stringConstants+objectLayouts+classNames+constructorDeclarations+methodDeclarations+vtables+constructors+startup;
		
	}
}
