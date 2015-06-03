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
		String stringConstants=         "struct TString SC1={ &RString, 12, \"Hello world!\" }; \n";
		
		/* object layouts: pointer to RTTI followed by attributes */
		String objectLayouts=           "struct TMain { struct __lcpl_rtti *rtti; }; \n";
		
		/* static data : class names, as strings */
		String classNames=              "struct TString NMain={ &RString, 4, \"Main\" }; \n";
		
		/* declarations of methods in the program, including constructors */
		String constructorDeclarations= "void Main_init(struct TMain *self); \n";
		String methodDeclarations=      "void M4_Main_main(struct TMain *self); \n";
		
		/* runtime type information (RTTI) - class name, class size, parent class, vtable */
		String vtables=                 "struct __lcpl_rtti RMain = { &NMain, sizeof(struct TMain), &RIO, { Main_init, M6_Object_abort, M6_Object_typeName, M6_Object_copy, M2_IO_in, M2_IO_out, M4_Main_main } }; \n";
		
		/* definitions of methods in the program, including constructors */
		String constructors=            "void Main_init(struct TMain *self){ IO_init((struct TIO*)self); } \n";
		String methods=                 "void M4_Main_main(struct TMain *self) \n"
				                      + "{\n  typedef struct TIO* (*TF1)(struct TIO *, struct TString *); \n"
				                      + "  ((TF1)(self->rtti->vtable[5])) ((struct TIO *)self,&SC1); \n} \n";
									  /* [out "Hello world!"] is a dispatch to the method "out String -> IO" inherited from "IO" */
		
		/* create a new Main object and call its main method */
		String startup=                 "void startup(void) { struct TMain *main=__lcpl_new(&RMain); M4_Main_main(main); } \n";
		System.out.println("na");
		return headers+stringConstants+objectLayouts+classNames+constructorDeclarations+methodDeclarations+vtables+constructors+methods+startup;
	}
}
