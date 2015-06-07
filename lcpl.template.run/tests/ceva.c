#include "lcpl_runtime.h" 
struct TString SC_empty={ &RString, 0, "" };
struct TString SC1={ &RString, 16, "This is a string" };
struct TString SC2={ &RString, 1, ">" };
struct TMain { struct __lcpl_rtti *rtti; int number; int initialized; struct TString* a_String;};
struct TString NMain={ &RString, 4, "Main" };
void Main_init(struct TMain *self);
typedef void (*TF_M4_Main_print)(struct TMain*, struct TString* hdr);
typedef void (*TF_M4_Main_main)(struct TMain*);
void M4_Main_print(struct TMain* self, struct TString* hdr);
void M4_Main_main(struct TMain* self);

typedef void (*TF_M6_Object_abort)(struct TObject*);
typedef struct TString* (*TF_M6_Object_typeName)(struct TObject*);
typedef struct TObject* (*TF_M6_Object_copy)(struct TObject*);

typedef struct TIO* (*TF_M2_IO_out)(struct TIO*, struct TString* msg);
typedef struct TString* (*TF_M2_IO_in)(struct TIO*);

typedef int (*TF_M6_String_length)(struct TString*);
typedef int (*TF_M6_String_toInt)(struct TString*);

struct __lcpl_rtti RMain = { &NMain, sizeof(struct TMain), &RIO, { Main_init, M6_Object_abort, M6_Object_typeName, M6_Object_copy, M2_IO_in, M2_IO_out, M4_Main_print, M4_Main_main} };
void Main_init(struct TMain *self){
IO_init((struct TIO*)self);
self->initialized = 1234;self->a_String = &SC1;
}
void M4_Main_print(struct TMain* self, struct TString* hdr){
({
({
struct TIO* _t1 = ((TF_M2_IO_out)(self->rtti->vtable[5]))((struct TMain*)self, hdr);
((TF_M2_IO_out)(_t1->rtti->vtable[5]))((struct TIO*)_t1, self->a_String);
});
;
hdr = M6_String_concat(hdr,hdr);
({
struct TIO* _t2 = ({
struct TIO* _t3 = ((TF_M2_IO_out)(self->rtti->vtable[5]))((struct TMain*)self, hdr);
((TF_M2_IO_out)(_t3->rtti->vtable[5]))((struct TIO*)_t3, __lcpl_intToString(self->number));
});
;
((TF_M2_IO_out)(_t2->rtti->vtable[5]))((struct TIO*)_t2, __lcpl_intToString((self->number)+(self->initialized)));
});
;

});;}
void M4_Main_main(struct TMain* self){
((TF_M4_Main_print)(self->rtti->vtable[6]))((struct TMain*)self, &SC2);}
void startup(void) { struct TMain *main=__lcpl_new(&RMain); M4_Main_main(main); } 

