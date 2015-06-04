#include "lcpl_runtime.h" 
TString SC_empty={ &RString, 0, "" };
struct TString SC1={ &RString, 1, "(" };
struct TString SC2={ &RString, 1, "," };
struct TString SC3={ &RString, 1, ")" };
struct TString SC4={ &RString, 1, "-" };
struct TString SC5={ &RString, 1, "\n" };
struct TShape { struct __lcpl_rtti *rtti;int x;int y;struct TVertex* next;};
struct TShape { struct __lcpl_rtti *rtti;};
struct TShape { struct __lcpl_rtti *rtti;};
struct TShape { struct __lcpl_rtti *rtti;};
struct TShape { struct __lcpl_rtti *rtti;struct TShape* s;struct TVertex* vx;};
struct TShape { struct __lcpl_rtti *rtti;struct TShape* s;struct TVertex* vx;};
struct TShape { struct __lcpl_rtti *rtti;struct TShape* s;struct TVertex* vx;};
struct TShape { struct __lcpl_rtti *rtti;struct TPoly* p;struct TPoly* p3;};
struct TString NVertex={ &RString, 6, "Vertex" };
struct TString NShape={ &RString, 5, "Shape" };
struct TString NTriangle={ &RString, 8, "Triangle" };
struct TString NLine={ &RString, 4, "Line" };
struct TString NPoly={ &RString, 4, "Poly" };
struct TString NPoly3={ &RString, 5, "Poly3" };
struct TString NPoly1={ &RString, 5, "Poly1" };
struct TString NMain={ &RString, 4, "Main" };
void Vertex_init(struct TVertex *self);
void Shape_init(struct TShape *self);
void Triangle_init(struct TTriangle *self);
void Line_init(struct TLine *self);
void Poly_init(struct TPoly *self);
void Poly3_init(struct TPoly3 *self);
void Poly1_init(struct TPoly1 *self);
void Main_init(struct TMain *self);
typedef void (*TF_M6_Vertex_print)(struct TVertex*, struct TIO* stream);
typedef void (*TF_M6_Vertex_init)(struct TVertex*, int x, int y, struct TVertex* next);
typedef void (*TF_M6_Vertex_print_all)(struct TVertex*, struct TIO* stream);
void M6_Vertex_print(struct TVertex* self, struct TIO* stream);
void M6_Vertex_init(struct TVertex* self, int x, int y, struct TVertex* next);
void M6_Vertex_print_all(struct TVertex* self, struct TIO* stream);

typedef int (*TF_M5_Shape_sides)(struct TShape*);
int M5_Shape_sides(struct TShape* self);

typedef int (*TF_M8_Triangle_sides)(struct TTriangle*);
int M8_Triangle_sides(struct TTriangle* self);

typedef int (*TF_M4_Line_sides)(struct TLine*);
int M4_Line_sides(struct TLine* self);

typedef void (*TF_M4_Poly_print)(struct TPoly*, struct TIO* stream);
typedef struct TShape* (*TF_M4_Poly_baseShape)(struct TPoly*);
void M4_Poly_print(struct TPoly* self, struct TIO* stream);
struct TShape* M4_Poly_baseShape(struct TPoly* self);

typedef struct TShape* (*TF_M5_Poly3_baseShape)(struct TPoly3*);
struct TShape* M5_Poly3_baseShape(struct TPoly3* self);

typedef struct TShape* (*TF_M5_Poly1_baseShape)(struct TPoly1*);
struct TShape* M5_Poly1_baseShape(struct TPoly1* self);

typedef void (*TF_M4_Main_main)(struct TMain*);
void M4_Main_main(struct TMain* self);

typedef void (*TF_M6_Object_abort)(struct TObject*);
typedef struct TString* (*TF_M6_Object_typeName)(struct TObject*);
typedef struct TObject* (*TF_M6_Object_copy)(struct TObject*);

typedef struct TIO* (*TF_M2_IO_out)(struct TIO*, struct TString* msg);
typedef struct TString* (*TF_M2_IO_in)(struct TIO*);

typedef int (*TF_M6_String_length)(struct TString*);
typedef int (*TF_M6_String_toInt)(struct TString*);

struct __lcpl_rtti RMain = { &NMain, sizeof(struct TMain), &RIO, { Main_init, M6_Object_abort, M6_Object_typeName, M6_Object_copy, M2_IO_in, M2_IO_out, M4_Main_main } }; 
void Main_init(struct TMain *self){ IO_init((struct TIO*)self); } 
void M4_Main_main(struct TMain *self) 
{
  typedef struct TIO* (*TF1)(struct TIO *, struct TString *); 
  ((TF1)(self->rtti->vtable[5])) ((struct TIO *)self,&SC1); 
} 
void startup(void) { struct TMain *main=__lcpl_new(&RMain); M4_Main_main(main); } 

