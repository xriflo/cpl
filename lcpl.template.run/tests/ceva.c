#include "lcpl_runtime.h" 
struct TString SC_empty={ &RString, 0, "" };
struct TString SC1={ &RString, 1, "\n" };
struct Ti { struct __lcpl_rtti *rtti; int i; int J;};
struct Tj { struct __lcpl_rtti *rtti; int J;};
struct TMain { struct __lcpl_rtti *rtti; struct Tj* i;};
struct TString Ni={ &RString, 1, "i" };
struct TString Nj={ &RString, 1, "j" };
struct TString NMain={ &RString, 4, "Main" };
void i_init(struct Ti *self);
void j_init(struct Tj *self);
void Main_init(struct TMain *self);
typedef void (*TF_M1_i_I)(struct Ti*);
typedef void (*TF_M1_i_J)(struct Ti*);
typedef int (*TF_M1_i_i)(struct Ti*, int i);
void M1_i_I(struct Ti* self);
void M1_i_J(struct Ti* self);
int M1_i_i(struct Ti* self, int i);


typedef void (*TF_M4_Main_main)(struct TMain*);
void M4_Main_main(struct TMain* self);

typedef void (*TF_M6_Object_abort)(struct TObject*);
typedef struct TString* (*TF_M6_Object_typeName)(struct TObject*);
typedef struct TObject* (*TF_M6_Object_copy)(struct TObject*);

typedef struct TIO* (*TF_M2_IO_out)(struct TIO*, struct TString* msg);
typedef struct TString* (*TF_M2_IO_in)(struct TIO*);

typedef int (*TF_M6_String_length)(struct TString*);
typedef int (*TF_M6_String_toInt)(struct TString*);

struct __lcpl_rtti Rj = { &Nj, sizeof(struct Tj), &RObject, { j_init, M6_Object_abort, M6_Object_typeName, M6_Object_copy} };
struct __lcpl_rtti RMain = { &NMain, sizeof(struct TMain), &RObject, { Main_init, M6_Object_abort, M6_Object_typeName, M6_Object_copy, M4_Main_main} };
struct __lcpl_rtti Ri = { &Ni, sizeof(struct Ti), &Rj, { i_init, M6_Object_abort, M6_Object_typeName, M6_Object_copy, M1_i_I, M1_i_J, M1_i_i} };
void j_init(struct Tj *self){
Object_init((struct TObject*)self);

}
void Main_init(struct TMain *self){
Object_init((struct TObject*)self);
self->i = ((struct Tj*)__lcpl_cast(__lcpl_new(&Ri), &Rj));
}
void i_init(struct Ti *self){
j_init((struct Tj*)self);

}
void M4_Main_main(struct TMain* self){
({
struct Ti* _l_i = ((struct Ti*)__lcpl_cast(self->i, &Ri));
({({
struct TIO* _t1 = ({
struct TIO* _t2 = __lcpl_new(&RIO);
((TF_M2_IO_out)(_t2->rtti->vtable[5]))((struct TIO*)_t2, __lcpl_intToString(((TF_M1_i_i)(_l_i->rtti->vtable[6]))((struct Ti*)_l_i, 4)));
});
;
((TF_M2_IO_out)(_t1->rtti->vtable[5]))((struct TIO*)_t1, &SC1);
});
;});
})
;}
void M1_i_I(struct Ti* self){
self->i = self->J;}
void M1_i_J(struct Ti* self){
self->J = self->i;}
int M1_i_i(struct Ti* self, int i){
return ({
self->J = (i)+(1);
((TF_M1_i_I)(self->rtti->vtable[4]))((struct Ti*)self);
self->J = (i)*(2);
({
int _l_i = (i)+(2);
({({
_l_i = (_l_i)-(self->J);
((TF_M1_i_J)(self->rtti->vtable[5]))((struct Ti*)self);
(self->J)*(_l_i);

});;});
})
;

});;}
void startup(void) { struct TMain *main=__lcpl_new(&RMain); M4_Main_main(main); } 

