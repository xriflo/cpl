#include "lcpl_runtime.h" 
struct TString SC_empty={ &RString, 0, "" };
struct TString SC1={ &RString, 1, "a" };
struct TString SC2={ &RString, 1, "b" };
struct TString SC3={ &RString, 1, "c" };
struct TString SC4={ &RString, 1, " " };
struct TMain { struct __lcpl_rtti *rtti;};
struct TString NMain={ &RString, 4, "Main" };
void Main_init(struct TMain *self);
typedef int (*TF_M4_Main_sum)(struct TMain*, int a, int b);
typedef struct TString* (*TF_M4_Main_first)(struct TMain*, struct TString* x, struct TString* y, struct TString* z);
typedef int (*TF_M4_Main_recurse)(struct TMain*, int x, struct TString* y, int z);
typedef int (*TF_M4_Main_fact)(struct TMain*, int n);
typedef void (*TF_M4_Main_main)(struct TMain*);
int M4_Main_sum(struct TMain* self, int a, int b);
struct TString* M4_Main_first(struct TMain* self, struct TString* x, struct TString* y, struct TString* z);
int M4_Main_recurse(struct TMain* self, int x, struct TString* y, int z);
int M4_Main_fact(struct TMain* self, int n);
void M4_Main_main(struct TMain* self);

typedef void (*TF_M6_Object_abort)(struct TObject*);
typedef struct TString* (*TF_M6_Object_typeName)(struct TObject*);
typedef struct TObject* (*TF_M6_Object_copy)(struct TObject*);

typedef struct TIO* (*TF_M2_IO_out)(struct TIO*, struct TString* msg);
typedef struct TString* (*TF_M2_IO_in)(struct TIO*);

typedef int (*TF_M6_String_length)(struct TString*);
typedef int (*TF_M6_String_toInt)(struct TString*);

struct __lcpl_rtti RMain = { &NMain, sizeof(struct TMain), &RObject, { Main_init, M6_Object_abort, M6_Object_typeName, M6_Object_copy, M4_Main_sum, M4_Main_first, M4_Main_recurse, M4_Main_fact, M4_Main_main} };
void Main_init(struct TMain *self){
Object_init((struct TObject*)self);

}
int M4_Main_sum(struct TMain* self, int a, int b){
return ;}
struct TString* M4_Main_first(struct TMain* self, struct TString* x, struct TString* y, struct TString* z){
return ({
z;
y;
x;

});}
int M4_Main_recurse(struct TMain* self, int x, struct TString* y, int z){
return ((TF_M4_Main_recurse)(self->rtti->vtable[6]))((struct TMain*)self, , y, !(z));}
int M4_Main_fact(struct TMain* self, int n){
return ((n)<(1)) ? (1) : ((n)*(((TF_M4_Main_fact)(self->rtti->vtable[7]))((struct TMain*)self, (n)-(1))));}
void M4_Main_main(struct TMain* self){
({
struct TIO* _l_s = __lcpl_new(&RIO);
(({
((TF_M2_IO_out)(_l_s->rtti->vtable[5]))((struct TIO*)_l_s, ((TF_M4_Main_first)(self->rtti->vtable[5]))((struct TMain*)self, &SC1, &SC2, &SC3));
((TF_M2_IO_out)(_l_s->rtti->vtable[5]))((struct TIO*)_l_s, );
((TF_M2_IO_out)(_l_s->rtti->vtable[5]))((struct TIO*)_l_s, &SC4);
((TF_M2_IO_out)(_l_s->rtti->vtable[5]))((struct TIO*)_l_s, );

}));
})
;}
void startup(void) { struct TMain *main=__lcpl_new(&RMain); M4_Main_main(main); } 

