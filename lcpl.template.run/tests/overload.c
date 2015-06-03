#include "lcpl_runtime.h" 
TString SC_empty={ &RString, 0, "" };
struct TString SC1={ &RString, 1, "
" };
struct TShape { struct __lcpl_rtti *rtti;int i;int J;};
struct TShape { struct __lcpl_rtti *rtti;int J;};
struct TShape { struct __lcpl_rtti *rtti;struct Tj* i;};
struct TString Ni={ &RString, 1, "i" };
struct TString Nj={ &RString, 1, "j" };
struct TString NMain={ &RString, 4, "Main" };
void Main_init(struct TMain *self); 
void M4_Main_main(struct TMain *self); 
struct __lcpl_rtti RMain = { &NMain, sizeof(struct TMain), &RIO, { Main_init, M6_Object_abort, M6_Object_typeName, M6_Object_copy, M2_IO_in, M2_IO_out, M4_Main_main } }; 
void Main_init(struct TMain *self){ IO_init((struct TIO*)self); } 
void M4_Main_main(struct TMain *self) 
{
  typedef struct TIO* (*TF1)(struct TIO *, struct TString *); 
  ((TF1)(self->rtti->vtable[5])) ((struct TIO *)self,&SC1); 
} 
void startup(void) { struct TMain *main=__lcpl_new(&RMain); M4_Main_main(main); } 

