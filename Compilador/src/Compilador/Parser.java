//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"
package Compilador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import Compilador.Tercetos.Asignable;
import Compilador.Tercetos.Terceto;
import Compilador.Tercetos.TercetoOperacion;


//#line 19 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character
Token yyaux;

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short CTE=257;
public final static short ID=258;
public final static short IF=259;
public final static short ELSE=260;
public final static short END_IF=261;
public final static short WHILE=262;
public final static short PRINT=263;
public final static short INT=264;
public final static short BEGIN=265;
public final static short END=266;
public final static short ULONG=267;
public final static short DO=268;
public final static short UNTIL=269;
public final static short CLASS=270;
public final static short EXTENDS=271;
public final static short TO_ULONG=272;
public final static short VOID=273;
public final static short CADENA=274;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
  0,    0,    0,    0,    3,    3,    3,    7,    7,    7,
 10,   10,    1,    1,    9,    9,    9,    9,    9,    9,
  9,    9,    9,    6,    6,    4,    4,    8,    8,    5,
  5,    5,    5,    5,    5,    5,    5,    5,   14,   13,
 13,   12,   12,   11,   11,    2,    2,    2,    2,    2,
  2,    2,   15,   15,   15,   15,   15,   16,   16,   16,
 16,   16,   16,   16,   16,   17,   17,   17,   17,   17,
 17,   17,   17,   17,   24,   25,   20,   21,   21,   22,
 23,   23,   23,   27,   27,   27,   27,   27,   27,   19,
 19,   19,   19,   19,   18,   18,   26,   26,   26,   26,
 26,   29,   29,   29,   30,   30,   28,   28,   31,   31,
};
final static short yylen[] = {                            2,
  2,    1,    2,    2,    1,    1,    1,    1,    1,    1,
  2,    1,    2,    1,    5,    4,    7,    7,    6,    6,
  4,    6,    6,    3,    3,    3,    3,    3,    3,    6,
  5,    7,    5,    5,    5,    4,    4,    4,    2,    1,
  1,    3,    3,    3,    3,    3,    2,    2,    2,    1,
  1,    2,    2,    1,    2,    1,    3,    1,    4,    4,
  3,    3,    3,    3,    1,    3,    3,    7,    7,    7,
  3,    6,    5,    5,    1,    4,    3,    4,    2,    1,
  3,    2,    2,    1,    1,    1,    1,    1,    1,    5,
  4,    4,    6,    3,    3,    3,    3,    3,    1,    4,
  4,    3,    3,    1,    1,    1,    1,    3,    1,    2,
};
final static short yydefred[] = {                         0,
  0,    0,   40,   41,    0,    0,    0,    9,   14,    8,
 10,    0,    0,    0,    0,    0,    0,   75,    0,    4,
  0,   50,   58,   65,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    1,   13,    0,    0,   52,    0,    0,
  0,    0,  109,    0,    0,    0,    0,    0,    0,    0,
106,    0,  104,  105,    0,    0,   48,    0,    0,    0,
  0,   49,    0,    0,   24,    0,   25,    0,    0,    0,
 12,    5,    6,    7,    0,    0,    0,    0,    0,   29,
 28,    0,   94,    0,   67,    0,    0,    0,  110,    0,
  0,    0,    0,   66,    0,   83,   84,   85,   89,   86,
  0,    0,   87,   88,    0,    0,    0,   62,    0,    0,
 63,   46,    0,   55,   64,    0,   71,    0,    0,   42,
 16,    0,    0,    0,    0,   39,   21,   11,    0,    0,
  0,    0,    0,    0,   91,  108,    0,    0,    0,   77,
  0,   80,   79,    0,    0,    0,    0,    0,  102,  103,
 60,   59,   57,    0,    0,   15,    0,    0,    0,    0,
 27,   26,    0,    0,    0,    0,    0,    0,   90,  101,
100,    0,    0,    0,    0,    0,    0,   73,   20,    0,
  0,   44,   22,   19,    0,    0,   36,    0,    0,   38,
  0,   37,    0,   23,   93,    0,    0,   78,    0,   72,
 76,   18,   17,    0,   33,   31,    0,   35,   34,   68,
 70,   69,    0,   30,   32,
};
final static short yydgoto[] = {                          6,
  7,   20,   71,   72,   73,   74,    9,   10,   11,   75,
125,   28,   76,   77,   58,   21,   22,   23,   24,   48,
 94,  144,   49,   25,  117,   50,  105,   26,   52,   53,
 54,
};
final static short yysindex[] = {                      -187,
195, -247,    0,    0, -183,    0,  170,    0,    0,    0,
  0, -211,    3,   12,  -33,  -37,  -10,    0, -179,    0,
 46,    0,    0,    0,  195, -163,  -24,   60, -238, -235,
-128,    3,   -6,    0,    0,   -8,   77,    0,  164,  101,
-103,  227,    0,  111,  119,  -97,  -27,  195,  121,  -43,
  0,   34,    0,    0,  130,  -36,    0, -101,  122,  249,
147,    0,  -85,  164,    0,  -68,    0,  -91, -249,  -67,
  0,    0,    0,    0,  -81,  -64,   38,  151,  -65,    0,
  0,   56,    0,   82,    0,   56,  -61,   65,    0,  164,
195,  203, -135,    0,  195,    0,    0,    0,    0,    0,
-41,  -41,    0,    0,  164,  -41,  -41,    0,  160,  161,
  0,    0,  249,    0,    0,  -30,    0,   56,  151,    0,
  0,  183,  -18, -235,  -60,    0,    0,    0,   -5,  145,
 79,  -59, -235,  -35,    0,    0,  166,   61,  -49,    0,
-49,    0,    0,  195,  -49,   34,   34,   56,    0,    0,
  0,    0,    0,   89,  171,    0,  262,  -39,  267,  283,
  0,    0,  174,  223,  211,  235,  317,  181,    0,    0,
  0,  195,  195,   -7,  195,  -17,  205,    0,    0,  327,
221,    0,    0,    0,  344,    6,    0,   26,  247,    0,
 29,    0,   30,    0,    0,   52,   58,    0,   59,    0,
  0,    0,    0,  249,    0,    0,   33,    0,    0,    0,
  0,    0,   45,    0,    0,
};
final static short yyrindex[] = {                         0,
  0,    0,    0,    0,    0,    0,  316,    0,    0,    0,
  0,    0,    0,   62,    0,    0,    0,    0,    0,    0,
156,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  0,  330,   62,    0,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    1,    0,    0,    0,    0,    0,    0,
  0,   25,    0,    0,    0,    0,    0,  158,  141,  143,
  0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  0,   13,    0,   76,    0,   97,    0,    0,    0,    0,
  0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0,    0,    0,    0,    0,   37,
  0,    0,  154,    0,    0,    0,    0,  128,  -51,    0,
  0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  0,    0,    0,  139,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0,   49,   73,  112,    0,    0,
  0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0,    0,  124,    0,    0,    0,
 78,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  0,  432,  -19,    0,    0,   50,  320,    0,    0,   39,
193,   55,   93,    0,  -15,    4,   14,    0,    0,    0,
  0,  -86,  -25,    0,    0,  458,    0,  472,   43,   40,
  0,
};
final static int YYTABLESIZE=648;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        101,
107,  102,   56,   46,  111,  169,   47,   43,  123,  154,
 27,   46,   96,   91,   46,  124,  103,   46,  104,   66,
 59,   92,    2,  200,   99,  158,   68,   46,    3,   19,
 60,    4,   69,   40,   65,   66,   61,   70,   66,   41,
107,  107,  107,  107,  114,  107,   36,  107,   97,    8,
 80,   40,  172,  161,  173,  128,    8,   41,  175,  107,
107,   38,  107,   59,   99,   99,   37,   99,    1,   99,
  2,   96,   98,   60,   29,  106,    3,  131,  132,    4,
107,   30,    5,   99,   99,   79,   99,   31,   97,   97,
155,   97,   12,   97,   61,   61,   82,  153,  101,   12,
102,  171,  128,  101,   62,  102,  122,   97,   97,   46,
 97,   81,   98,   98,   64,   98,   59,   98,   67,  165,
120,  134,  135,   74,  142,  143,   60,   95,  177,   78,
130,   98,   98,   46,   98,   81,   82,   82,   92,  128,
 56,   83,   54,  146,  147,  149,  150,  128,  188,  191,
193,   81,   81,   53,   84,   51,   87,   47,   88,   89,
128,   95,  159,   74,  112,  128,    2,   59,   59,   59,
108,  167,    3,  207,  121,    4,    2,   60,   60,   60,
113,   70,    3,  116,  127,    4,   95,  115,  213,  119,
126,   70,   59,  129,   66,  180,  136,   92,  185,  133,
151,  152,   60,  162,  160,  166,  170,   59,   46,   19,
142,  178,   96,   43,  186,   43,   44,   60,  181,  109,
168,  195,   42,   43,   44,   90,   43,   44,   90,   43,
 44,   97,   98,   99,   19,  100,   55,  110,   45,   43,
 44,   45,   19,  140,   45,  201,  157,   14,   15,   39,
 19,   27,   16,  198,   45,   57,  107,   18,  107,  107,
107,  107,   19,  107,  158,  107,  107,   39,  107,  107,
204,   46,   96,   96,   19,  107,  107,  107,   96,  107,
 99,   96,   99,   99,   99,   99,   19,   99,   19,   99,
 99,  205,   99,   99,  208,  209,   61,   61,  214,   99,
 99,   99,   61,   99,   97,   61,   97,   97,   97,   97,
215,   97,  210,   97,   97,    2,   97,   97,  211,  212,
137,   43,   44,   97,   97,   97,   35,   97,   98,    3,
 98,   98,   98,   98,  163,   98,   45,   98,   98,  107,
 98,   98,   45,  164,  176,   43,   44,   98,   98,   98,
182,   98,   82,  108,   82,   82,   82,   82,    0,   82,
 45,   82,   82,    0,   82,   82,    0,   81,    0,   81,
 81,   81,   81,    0,   81,    0,   81,   81,    0,   81,
 81,   74,   74,   74,   74,    0,   74,   95,   95,   74,
  0,   74,   74,   95,    0,    0,   95,    0,   92,   92,
 56,   56,   54,   54,   92,    0,   56,   92,   54,   56,
  0,   54,    0,   53,   53,   51,   51,   47,   47,   53,
 43,   44,   53,    0,   51,   32,   47,   33,   15,    0,
  0,    0,   16,    3,   17,   45,    4,   18,   34,    5,
  2,    0,    0,    0,    0,    0,    3,    0,  156,    4,
 13,    0,   14,   15,    0,   70,   63,   16,   13,   17,
 14,   15,   18,    0,    0,   16,    0,   17,   14,   15,
 18,    0,    0,   16,    0,  189,  190,    0,   18,   93,
 14,   15,    0,   43,   44,   16,   51,   85,  187,    0,
 18,    0,   14,   15,    0,    0,   82,   16,   45,   86,
192,    0,   18,    0,   14,   15,   14,   15,    0,   16,
 51,   16,  206,   51,   18,    0,   18,    0,   51,    2,
  0,  118,  139,  141,    2,    3,  145,  179,    4,    0,
  3,    0,  183,    4,   70,   51,    0,    0,    0,   70,
  2,    0,    0,    0,    0,  138,    3,   86,  184,    4,
  0,    0,    0,    0,    0,   70,    0,    0,    0,   51,
  0,   51,  148,    0,    0,    0,    0,    0,    0,    0,
  0,    0,   51,   51,    2,  174,   51,   51,   51,    0,
  3,    0,  194,    4,    2,    0,    0,   51,    0,   70,
  3,    0,  202,    4,    0,    0,    0,    0,    0,   70,
  0,    2,    0,  196,  197,    0,  199,    3,    0,  203,
  4,    0,    0,    0,    0,    0,   70,    0,    0,    0,
  0,    0,    0,    0,    0,   51,    0,    0,    0,    0,
  0,    0,    0,   86,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0,    0,    0,   51,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         43,
  0,   45,   40,   45,   41,   41,   40,   59,  258,   40,
258,   45,    0,   41,   45,  265,   60,   45,   62,   44,
 17,   47,  258,   41,    0,   44,  265,   45,  264,   40,
 17,  267,  271,   40,   59,   44,    0,  273,   44,   46,
 40,   41,   42,   43,   60,   45,  258,   47,    0,    0,
 59,   40,  139,   59,  141,   75,    7,   46,  145,   59,
 60,   59,   62,   60,   40,   41,   12,   43,  256,   45,
258,   59,    0,   60,  258,   42,  264,   40,   41,  267,
 47,  265,  270,   59,   60,   31,   62,  271,   40,   41,
116,   43,    0,   45,  274,   59,    0,  113,   43,    7,
 45,   41,  122,   43,   59,   45,   68,   59,   60,   45,
 62,    0,   40,   41,  278,   43,  113,   45,   59,   41,
 66,   40,   41,    0,  260,  261,  113,    0,  154,  258,
 76,   59,   60,   45,   62,   59,   40,   41,    0,  159,
  0,   41,    0,  101,  102,  106,  107,  167,  164,  165,
166,   40,   41,    0,  258,    0,   46,    0,   40,  257,
180,   41,  124,   40,  266,  185,  258,  164,  165,  166,
 41,  133,  264,  189,  266,  267,  258,  164,  165,  166,
 59,  273,  264,  269,  266,  267,   59,   41,  204,  258,
258,  273,  189,  258,   44,  157,  258,   59,  160,  265,
 41,   41,  189,   59,  265,  265,   41,  204,   45,   40,
260,   41,  256,  265,   41,  257,  258,  204,  258,  256,
256,   41,  256,  257,  258,  256,  257,  258,  256,  257,
258,  275,  276,  277,   40,  279,  274,  274,  272,  257,
258,  272,   40,   41,  272,   41,  265,  258,  259,  256,
 40,  258,  263,  261,  272,  266,  256,  268,  258,  259,
260,  261,   40,  263,   44,  265,  266,  256,  268,  269,
265,   45,  260,  261,   40,  275,  276,  277,  266,  279,
256,  269,  258,  259,  260,  261,   40,  263,   40,  265,
266,  266,  268,  269,  266,  266,  260,  261,  266,  275,
276,  277,  266,  279,  256,  269,  258,  259,  260,  261,
266,  263,  261,  265,  266,    0,  268,  269,  261,  261,
256,  257,  258,  275,  276,  277,    7,  279,  256,    0,
258,  259,  260,  261,  256,  263,  272,  265,  266,  278,
268,  269,  265,  265,  256,  257,  258,  275,  276,  277,
158,  279,  256,  278,  258,  259,  260,  261,   -1,  263,
272,  265,  266,   -1,  268,  269,   -1,  256,   -1,  258,
259,  260,  261,   -1,  263,   -1,  265,  266,   -1,  268,
269,  258,  259,  260,  261,   -1,  263,  260,  261,  266,
 -1,  268,  269,  266,   -1,   -1,  269,   -1,  260,  261,
260,  261,  260,  261,  266,   -1,  266,  269,  266,  269,
 -1,  269,   -1,  260,  261,  260,  261,  260,  261,  266,
257,  258,  269,   -1,  269,  256,  269,  258,  259,   -1,
 -1,   -1,  263,  264,  265,  272,  267,  268,    7,  270,
258,   -1,   -1,   -1,   -1,   -1,  264,   -1,  266,  267,
256,   -1,  258,  259,   -1,  273,   25,  263,  256,  265,
258,  259,  268,   -1,   -1,  263,   -1,  265,  258,  259,
268,   -1,   -1,  263,   -1,  265,  266,   -1,  268,   48,
258,  259,   -1,  257,  258,  263,   15,  261,  266,   -1,
268,   -1,  258,  259,   -1,   -1,   39,  263,  272,   42,
266,   -1,  268,   -1,  258,  259,  258,  259,   -1,  263,
 39,  263,  266,   42,  268,   -1,  268,   -1,   47,  258,
 -1,   64,   91,   92,  258,  264,   95,  266,  267,   -1,
264,   -1,  266,  267,  273,   64,   -1,   -1,   -1,  273,
258,   -1,   -1,   -1,   -1,   88,  264,   90,  266,  267,
 -1,   -1,   -1,   -1,   -1,  273,   -1,   -1,   -1,   88,
 -1,   90,  105,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
 -1,   -1,  101,  102,  258,  144,  105,  106,  107,   -1,
264,   -1,  266,  267,  258,   -1,   -1,  116,   -1,  273,
264,   -1,  266,  267,   -1,   -1,   -1,   -1,   -1,  273,
 -1,  258,   -1,  172,  173,   -1,  175,  264,   -1,  266,
267,   -1,   -1,   -1,   -1,   -1,  273,   -1,   -1,   -1,
 -1,   -1,   -1,   -1,   -1,  154,   -1,   -1,   -1,   -1,
 -1,   -1,   -1,  176,   -1,   -1,   -1,   -1,   -1,   -1,
 -1,   -1,   -1,   -1,   -1,   -1,   -1,  176,
};
}
final static short YYFINAL=6;
final static short YYMAXTOKEN=279;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'",null,"'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,"CTE","ID","IF","ELSE","END_IF","WHILE","PRINT",
"INT","BEGIN","END","ULONG","DO","UNTIL","CLASS","EXTENDS","TO_ULONG","VOID",
"CADENA","\"<=\"","\">=\"","\"<>\"","\":=\"","\"==\"",
};
final static String yyrule[] = {
"$accept : programa",
"programa : lista_declaraciones bloqueEjecutable",
"programa : lista_declaraciones",
"programa : lista_declaraciones error",
"programa : error bloqueEjecutable",
"declaraciones_dentro_de_clase : declaracionAtributo",
"declaraciones_dentro_de_clase : declaracionMetodo",
"declaraciones_dentro_de_clase : declaracionObjeto",
"declaraciones : declaracionVariable",
"declaraciones : declaracionObjeto",
"declaraciones : declaracionClase",
"lista_declaraciones_dentro_de_clase : lista_declaraciones_dentro_de_clase declaraciones_dentro_de_clase",
"lista_declaraciones_dentro_de_clase : declaraciones_dentro_de_clase",
"lista_declaraciones : lista_declaraciones declaraciones",
"lista_declaraciones : declaraciones",
"declaracionClase : CLASS ID BEGIN lista_declaraciones_dentro_de_clase END",
"declaracionClase : CLASS ID BEGIN END",
"declaracionClase : CLASS ID EXTENDS lista_herencias BEGIN lista_declaraciones_dentro_de_clase END",
"declaracionClase : CLASS ID EXTENDS ID BEGIN lista_declaraciones_dentro_de_clase END",
"declaracionClase : CLASS ID EXTENDS lista_herencias BEGIN END",
"declaracionClase : CLASS ID EXTENDS ID BEGIN END",
"declaracionClase : CLASS BEGIN lista_declaraciones_dentro_de_clase END",
"declaracionClase : CLASS ID EXTENDS BEGIN lista_declaraciones_dentro_de_clase END",
"declaracionClase : CLASS EXTENDS lista_de_ID BEGIN lista_declaraciones_dentro_de_clase END",
"declaracionObjeto : ID ID ';'",
"declaracionObjeto : ID lista_de_ID ';'",
"declaracionAtributo : tipo lista_de_ID ';'",
"declaracionAtributo : tipo ID ';'",
"declaracionVariable : tipo lista_de_ID ';'",
"declaracionVariable : tipo ID ';'",
"declaracionMetodo : iniMetodo '(' ')' BEGIN sentenciasEjecutables END",
"declaracionMetodo : iniMetodo '(' ')' BEGIN END",
"declaracionMetodo : iniMetodo '(' error ')' BEGIN sentenciasEjecutables END",
"declaracionMetodo : iniMetodo '(' BEGIN sentenciasEjecutables END",
"declaracionMetodo : iniMetodo ')' BEGIN sentenciasEjecutables END",
"declaracionMetodo : iniMetodo '(' ')' sentenciasEjecutables END",
"declaracionMetodo : iniMetodo '(' BEGIN END",
"declaracionMetodo : iniMetodo ')' BEGIN END",
"declaracionMetodo : iniMetodo '(' ')' END",
"iniMetodo : VOID ID",
"tipo : INT",
"tipo : ULONG",
"lista_de_ID : ID ',' lista_de_ID",
"lista_de_ID : ID ',' ID",
"lista_herencias : ID ',' lista_herencias",
"lista_herencias : ID ',' ID",
"bloqueEjecutable : BEGIN sentenciasEjecutables END",
"bloqueEjecutable : BEGIN sentenciasEjecutables",
"bloqueEjecutable : BEGIN END",
"bloqueEjecutable : sentenciaEjecutable ';'",
"bloqueEjecutable : sentenciaControl",
"bloqueEjecutable : sentenciaEjecutable",
"bloqueEjecutable : error ';'",
"sentenciasEjecutables : sentenciaEjecutable ';'",
"sentenciasEjecutables : sentenciaControl",
"sentenciasEjecutables : sentenciaControl sentenciasEjecutables",
"sentenciasEjecutables : sentenciaEjecutable",
"sentenciasEjecutables : sentenciaEjecutable ';' sentenciasEjecutables",
"sentenciaEjecutable : asignacion",
"sentenciaEjecutable : PRINT '(' CADENA ')'",
"sentenciaEjecutable : PRINT '(' error ')'",
"sentenciaEjecutable : PRINT '(' CADENA",
"sentenciaEjecutable : PRINT CADENA ')'",
"sentenciaEjecutable : PRINT '(' ')'",
"sentenciaEjecutable : '(' CADENA ')'",
"sentenciaEjecutable : llamadaMetodo",
"sentenciaControl : IF condIf cpoIf",
"sentenciaControl : IF error END_IF",
"sentenciaControl : IF '(' ')' bloqueEjecutable entroElse bloqueEjecutable END_IF",
"sentenciaControl : IF condicion ')' bloqueEjecutable entroElse bloqueEjecutable END_IF",
"sentenciaControl : IF '(' condicion bloqueEjecutable entroElse bloqueEjecutable END_IF",
"sentenciaControl : iniciaDo bloqueEjecutable cpoDo",
"sentenciaControl : iniciaDo bloqueEjecutable UNTIL '(' error ')'",
"sentenciaControl : iniciaDo bloqueEjecutable UNTIL condicion ')'",
"sentenciaControl : iniciaDo bloqueEjecutable UNTIL '(' condicion",
"iniciaDo : DO",
"cpoDo : UNTIL '(' condicion ')'",
"condIf : '(' condicion ')'",
"cpoIf : bloqueEjecutable entroElse bloqueEjecutable END_IF",
"cpoIf : bloqueEjecutable END_IF",
"entroElse : ELSE",
"condicion : expresion comparacion expresion",
"condicion : error expresion",
"condicion : expresion error",
"comparacion : \"<=\"",
"comparacion : \">=\"",
"comparacion : \"==\"",
"comparacion : '<'",
"comparacion : '>'",
"comparacion : \"<>\"",
"llamadaMetodo : ID '.' ID '(' ')'",
"llamadaMetodo : ID '.' ID ')'",
"llamadaMetodo : ID '.' ID '('",
"llamadaMetodo : ID '.' ID '(' error ')'",
"llamadaMetodo : ID '(' ')'",
"asignacion : identificador \":=\" expresion",
"asignacion : ID error expresion",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"expresion : TO_ULONG '(' expresion ')'",
"expresion : TO_ULONG '(' error ')'",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : constante",
"factor : identificador",
"identificador : ID",
"identificador : ID '.' ID",
"constante : CTE",
"constante : '-' CTE",
};

//#line 525 "gramatica.y"



private void print(String string) throws IOException {
	//System.out.println(string + " en linea " + this.lineactual);
	this.reconocidos.add(string + " en linea " + this.lineactual);
	//a.imprimirArchivo(string);
}

//#line 497 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}



//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string

//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse() throws IOException
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();        //get next character
        //yychar = yylex().getToken();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        //yyerror("syntax error");
    	  this.errores.add("syntax error" + " en linea: "+ this.lineactual);
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
    	yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
  //########## USER-SUPPLIED ACTIONS ##########
      case 1:
      //#line 14 "gramatica.y"
      {print("Reconoce bien el lenguaje");}
      break;
      case 2:
      //#line 15 "gramatica.y"
      {yyerror(val_peek(0), "Falta Bloque ejecutable (BEGIN END)");}
      break;
      case 3:
      //#line 16 "gramatica.y"
      {yyerror("Error en el Bloque ejecutable  ");}
      break;
      case 4:
      //#line 17 "gramatica.y"
      {yyerror("Error en el Bloque Declarativo ");}
      break;
      case 15:
      //#line 38 "gramatica.y"
      {if (!a.TablaSimbolo.get(val_peek(3).sval).estaDeclarado()){
      																									asignarUso(val_peek(3).sval, "CLASE");;
      																									setDeclarado(val_peek(3).sval);}
      																								 else
      																									yyerror( "El nombre de la clase ya fue utilizado");
            																							 yaCambie = false;
            																							 ambiente = "GLOBAL";
      																								 }
      break;
      case 16:
      //#line 47 "gramatica.y"
      {if (!a.TablaSimbolo.get(val_peek(2).sval).estaDeclarado()){
      																									asignarUso(val_peek(2).sval, "CLASE");
      																									setDeclarado(val_peek(2).sval);}
      																								 else 
      																									yyerror( "El nombre de la clase ya fue utilizado");
            																							 yaCambie = false;
            																							 ambiente = "GLOBAL";
      																								 }
      break;
      case 17:
      //#line 56 "gramatica.y"
      {if (!a.TablaSimbolo.get(val_peek(5).sval).estaDeclarado()){
      																										asignarUso(val_peek(5).sval, "CLASE");;
      																										setDeclarado(val_peek(5).sval);
      																										setHerencias(val_peek(5).sval);}
      																									else
      																										yyerror( "El nombre de la clase ya fue utilizado");
            																								yaCambie = false;
            																								ambiente = "GLOBAL";
      																									}
      break;
      case 18:
      //#line 66 "gramatica.y"
      {if (!a.TablaSimbolo.get(val_peek(5).sval).estaDeclarado())
      																									if (a.TablaSimbolo.get(val_peek(3).sval).estaDeclarado()){
      																										asignarUso(val_peek(5).sval, "CLASE");;
      																										setDeclarado(val_peek(5).sval);
      																										setHerencia(val_peek(5).sval, val_peek(3).sval);}
      																									else
      																										yyerror( "La clase "+val_peek(3).sval+" no esta declarada");
      																								 else 
      																									yyerror( "El nombre de la clase ya fue utilizado");
            																							 yaCambie = false;
            																							 ambiente = "GLOBAL";
      																								 }
      break;
      case 19:
      //#line 79 "gramatica.y"
      {if (!a.TablaSimbolo.get(val_peek(4).sval).estaDeclarado()){
      																									asignarUso(val_peek(4).sval, "CLASE");
      																									setDeclarado(val_peek(4).sval);
      																									setHerencias(val_peek(4).sval);}
      																								 else 
      																									yyerror( "El nombre de la clase ya fue utilizado");
            																							 yaCambie = false;
            																							 ambiente = "GLOBAL";
      																								 }
      break;
      case 20:
      //#line 89 "gramatica.y"
      {if (!a.TablaSimbolo.get(val_peek(4).sval).estaDeclarado())
      																									if (a.TablaSimbolo.get(val_peek(2).sval).estaDeclarado()){
      																										asignarUso(val_peek(4).sval, "CLASE");;
      																										setDeclarado(val_peek(4).sval);
      																										setHerencia(val_peek(4).sval, val_peek(2).sval);}
      																									else
      																										yyerror( "La clase "+val_peek(2).sval+" no esta declarada");
            																							 yaCambie = false;
            																							 ambiente = "GLOBAL";
      																								 }
      break;
      case 21:
      //#line 101 "gramatica.y"
      {yyerror(val_peek(3), "Falta nombre de la Clase");}
      break;
      case 22:
      //#line 102 "gramatica.y"
      {yyerror(val_peek(5), "Falta lista de clases padre");
            																							 yaCambie = false;
            																							 ambiente = "GLOBAL";
      																								 asignarAmbiente(val_peek(4).sval);}
      break;
      case 23:
      //#line 106 "gramatica.y"
      {yyerror("Falta nombre de la Clase");}
      break;
      case 24:
      //#line 110 "gramatica.y"
      {if(a.TablaSimbolo.get(val_peek(2).sval).estaDeclarado())
      																									if (!a.TablaSimbolo.get(val_peek(1).sval).estaDeclarado()){
      																										asignarUso(val_peek(1).sval, "OBJETO");
      																										AsignarTipo(val_peek(2).sval, val_peek(1).sval);
      																										asignarAmbiente(val_peek(1).sval);
      																										setDeclarado(val_peek(1).sval);}
      																									else 
      																										yyerror("El nombre de objeto ya fue utilizado");
      																								 else 
      																									yyerror("La clase "+ val_peek(2).sval + " no esta declarada");}
      break;
      case 25:
      //#line 121 "gramatica.y"
      {if(a.TablaSimbolo.get(val_peek(2).sval).estaDeclarado()){
      																									AsignarInfo(val_peek(2).sval, "OBJETO");}
      																								 else 
      																									yyerror("La clase "+ val_peek(2).sval + " no esta declarada");}
      break;
      case 26:
      //#line 126 "gramatica.y"
      {
      																								 AsignarInfo(val_peek(2).sval, "ATRIBUTO");}
      break;
      case 27:
      //#line 129 "gramatica.y"
      {if (!a.TablaSimbolo.get(val_peek(1).sval).estaDeclarado()){
      																									AsignarTipo(val_peek(2).sval, val_peek(1).sval);
      																									asignarUso(val_peek(1).sval,"ATRIBUTO");
      																									setDeclarado(val_peek(1).sval);
      																									asignarAmbiente(val_peek(1).sval);}
      																								 else 
      																									yyerror("El nombre del Atributo "+val_peek(1).sval+" ya fue utilizado");}
      break;
      case 28:
      //#line 139 "gramatica.y"
      {
      																								 AsignarInfo(val_peek(2).sval, "VARIABLE");}
      break;
      case 29:
      //#line 142 "gramatica.y"
      {if (!a.TablaSimbolo.get(val_peek(1).sval).estaDeclarado()){
      																									AsignarTipo(val_peek(2).sval, val_peek(1).sval);
      																									asignarUso(val_peek(1).sval,"VARIABLE");
      																									setDeclarado(val_peek(1).sval);
      																									asignarAmbiente(val_peek(1).sval);}
      																								 else 
      																									yyerror("El nombre del Variable "+val_peek(1).sval+" ya fue utilizado");}
      break;
      case 30:
      //#line 151 "gramatica.y"
      {
      																								asignarUso(val_peek(5).sval,"METODO");
      																								asignarAmbiente(val_peek(5).sval);
      																								setDeclarado(val_peek(5).sval);
      																								Terceto t = new Terceto(contadorTerceto,"RET","","",a.TablaSimbolo);
      																								contadorTerceto++;
      																								listaTercetos.add(t);
      																								}
      break;
      case 31:
      //#line 159 "gramatica.y"
      {
      																								 asignarUso(val_peek(4).sval,"METODO");
      																								 asignarAmbiente(val_peek(4).sval);
      																								 setDeclarado(val_peek(4).sval);
      																								Terceto t = new Terceto(contadorTerceto,"RET","","",a.TablaSimbolo);
      																								contadorTerceto++;
      																								listaTercetos.add(t);}
      break;
      case 32:
      //#line 166 "gramatica.y"
      {yyerror("No se permite pasaje por parámetros");}
      break;
      case 33:
      //#line 167 "gramatica.y"
      {yyerror("Falta ')'");}
      break;
      case 34:
      //#line 168 "gramatica.y"
      {yyerror("Falta '('");}
      break;
      case 35:
      //#line 169 "gramatica.y"
      {yyerror("Falta BEGIN");}
      break;
      case 36:
      //#line 170 "gramatica.y"
      {yyerror("Falta ')'");}
      break;
      case 37:
      //#line 171 "gramatica.y"
      {yyerror("Falta '('");}
      break;
      case 38:
      //#line 172 "gramatica.y"
      {yyerror("Falta BEGIN");}
      break;
      case 39:
      //#line 176 "gramatica.y"
      {Terceto t = new Terceto(contadorTerceto,"FUNCTION",val_peek(0).sval,"",a.TablaSimbolo);
      																								contadorTerceto++;
      																								listaTercetos.add(t);
      																								yyval.sval = val_peek(0).sval;
      																								}
      break;
      case 42:
      //#line 187 "gramatica.y"
      {																								 listaIdentificadores.add(val_peek(2).sval);
      																					
      																								 asignarAmbiente(val_peek(2).sval);}
      break;
      case 43:
      //#line 189 "gramatica.y"
      {listaIdentificadores.add(val_peek(2).sval);
      																								 listaIdentificadores.add(val_peek(0).sval);
      																								asignarAmbiente(val_peek(2).sval);
      																								asignarAmbiente(val_peek(0).sval);}
      break;
      case 44:
      //#line 195 "gramatica.y"
      {if (a.TablaSimbolo.get(val_peek(2).sval).estaDeclarado())
      																									listaHerencias.add(val_peek(2).sval);
      																								 else
      																									yyerror("La clase "+val_peek(2).sval+" no esta declarada");}
      break;
      case 45:
      //#line 200 "gramatica.y"
      {if (a.TablaSimbolo.get(val_peek(2).sval).estaDeclarado())
      																									listaHerencias.add(val_peek(2).sval);
      																								 else
      																									yyerror("La clase "+val_peek(2).sval+" no esta declarada");
      																								if (a.TablaSimbolo.get(val_peek(0).sval).estaDeclarado())
      																									listaHerencias.add(val_peek(0).sval);
      																								 else
      																									yyerror("La clase "+val_peek(0).sval+" no esta declarada");}
      break;
      case 47:
      //#line 215 "gramatica.y"
      {yyerror("Falta 'END' para cerrar el bloque de sentencias");}
      break;
      case 51:
      //#line 219 "gramatica.y"
      {yyerror("Falta ;");}
      break;
      case 52:
      //#line 220 "gramatica.y"
      {yyerror("Error en la sentencia ejecutable");}
      break;
      case 56:
      //#line 226 "gramatica.y"
      {yyerror("Falta ;");}
      break;
      case 59:
      //#line 232 "gramatica.y"
      {
      																										a.TablaSimbolo.get(val_peek(1).sval).setDeclarado();
      																										a.TablaSimbolo.get(val_peek(1).sval).setUso("CADENA");
      																										Terceto t = new Terceto(contadorTerceto,"PRINT",val_peek(1).sval,"",a.TablaSimbolo);
      																										contadorTerceto ++;
      																										listaTercetos.add(t);                                      
      																										yyval.obj = t;
      																								}
      break;
      case 60:
      //#line 238 "gramatica.y"
      {yyerror("Falta cadena para imprimir");}
      break;
      case 61:
      //#line 239 "gramatica.y"
      {yyerror("Falta parentesis de cierre en la sentencia de impresion");}
      break;
      case 62:
      //#line 240 "gramatica.y"
      {yyerror("Falta parentesis de apertura en la sentencia de impresion");}
      break;
      case 63:
      //#line 241 "gramatica.y"
      {yyerror("CADENA faltante en la sentencia de impresion");}
      break;
      case 64:
      //#line 242 "gramatica.y"
      {yyerror("'PRINT' faltante en la sentencia de impresion");}
      break;
      case 67:
      //#line 247 "gramatica.y"
      {yyerror("Declaración de IF errónea");}
      break;
      case 68:
      //#line 248 "gramatica.y"
      {yyerror("Falta condicion");}
      break;
      case 69:
      //#line 249 "gramatica.y"
      {yyerror("Falta '(' ");}
      break;
      case 70:
      //#line 250 "gramatica.y"
      {yyerror("Falta ')' ");}
      break;
      case 71:
      //#line 252 "gramatica.y"
      {
      																									Terceto t = new Terceto(contadorTerceto,"FIN_DO","-","-", a.TablaSimbolo);	
      																									listaTercetos.add(t);
      																									contadorTerceto++;
      																									Integer i = pilaTercetos.pop();
      																									listaTercetos.get(i).setOperando2(t);
      																									}
      break;
      case 72:
      //#line 259 "gramatica.y"
      {yyerror("Condicion inválida");}
      break;
      case 73:
      //#line 260 "gramatica.y"
      {yyerror(val_peek(3), "Falta ')' ");}
      break;
      case 74:
      //#line 261 "gramatica.y"
      {yyerror(val_peek(3), "Falta '(' ");}
      break;
      case 75:
      //#line 264 "gramatica.y"
      {
      																									inicioDO = contadorTerceto;
      																									Terceto inido = new Terceto(contadorTerceto, "INIDO", inicioDO, "", a.TablaSimbolo);
      																									listaTercetos.add(inido);
      																									contadorTerceto++;
      																									}
      break;
      case 76:
      //#line 272 "gramatica.y"
      {
      																									
      																									Terceto bd = new Terceto(contadorTerceto,"BD","","",a.TablaSimbolo);
      																									bd.setOperando1(listaTercetos.get(listaTercetos.size()-1));
      																									contadorTerceto++;
      																									listaTercetos.add(bd);
      																									pilaTercetos.push(bd.getNro());
      																									
      																									Terceto bi = new Terceto(contadorTerceto, "BI", "", "", a.TablaSimbolo);
      																									bi.setOperando1(listaTercetos.get(inicioDO));
      																									contadorTerceto++;
      																									listaTercetos.add(bi);
      																									}
      break;
      case 77:
      //#line 289 "gramatica.y"
      {
      																										/* CREO EL TERCETO CON BF , NOSE DONDE TERMINA PORQUE PUEDE TENER ELSE*/
      																										Terceto t = new Terceto(contadorTerceto,"BF",val_peek(1).obj,"",a.TablaSimbolo);
      																										t.setOperando1((Terceto)listaTercetos.get(listaTercetos.size()-1)); 			   	/* Agrego a op1 el terceto anterior (la condicion)*/
      																										contadorTerceto ++;
      																										listaTercetos.add(t); 
      																										pilaTercetos.push(t.getNro());                                         	/* agrego el terceto incompleto a la pila*/
      																										yyval.obj = t;
      																										
      																									}
      break;
      case 78:
      //#line 304 "gramatica.y"
      {	/*TERMINE LA ESTRUCTURA DEL IF COMPLETA CON ELSE							*/
      																										Integer i = pilaTercetos.pop();
      																										if (listaTercetos.get(i).getOperador() == "BI"){ 
      																											Terceto finif = new Terceto(contadorTerceto,"FINIF","-","-",a.TablaSimbolo);
      																											contadorTerceto ++;
      																											listaTercetos.add(finif);
      																											listaTercetos.get(i).setOperando1(listaTercetos.get(contadorTerceto-1));
      																										}
      																										
      																									
      																									}
      break;
      case 79:
      //#line 316 "gramatica.y"
      {
      																									/*TERMINE LA ESTRUCTURA DEL IF COMPLETA NO TIENE ELSE*/
      																									Integer i = pilaTercetos.pop();
      																									if (listaTercetos.get(i).getOperador() == "BI")
      																										listaTercetos.get(i).setOperando1(listaTercetos.size());
      																										
      																									Terceto finif = new Terceto(contadorTerceto,"FINIF","-","-",a.TablaSimbolo);
      																									contadorTerceto ++;
      																									listaTercetos.add(finif);
      																									if (listaTercetos.get(i).getOperador() == "BF")
      																										listaTercetos.get(i).setOperando2(listaTercetos.get(listaTercetos.size()-1));
      																									
      																									}
      break;
      case 80:
      //#line 331 "gramatica.y"
      {	Terceto t = new Terceto(contadorTerceto,"BI","-","-",a.TablaSimbolo);    /* NO SE DONDE TERMINA EL IF COMPLETO*/
      																								contadorTerceto ++;
      																								listaTercetos.add(t);
      																								Integer i = pilaTercetos.pop();
      																								if (listaTercetos.get(i).getOperador() == "BF")               /* VOY A COMPLETAR EL TERCETO CREADO EN LA CONDICION*/
      																								{
      																						
      																									Terceto label_bi = new Terceto(contadorTerceto-1,"LABEL",listaTercetos.size(),"-",a.TablaSimbolo);
      																									contadorTerceto ++;
      																									listaTercetos.add(label_bi);
      																									listaTercetos.get(i).setOperando2(listaTercetos.get(contadorTerceto-1));       /*LE SETEO EL ULTIMO TERCETO LEIDO*/
      																								}
      																								else 
      																									listaTercetos.get(i).setOperando1(listaTercetos.get(contadorTerceto-1));
      																								pilaTercetos.push(t.getNro());                                              /* AGREGO EL INCOMPLETO A LA PILA*/
      																								yyval.obj = t;
      																							}
      break;
      case 81:
      //#line 350 "gramatica.y"
      {	
      																									String operador = (String)val_peek(1).obj;
      																									TercetoOperacion t = new TercetoOperacion(contadorTerceto,operador,val_peek(2).obj,val_peek(0).obj,a.TablaSimbolo);
      																									contadorTerceto ++;
      																									listaTercetos.add(t);     
      																									yyval.obj = t;
      																								}
      break;
      case 82:
      //#line 357 "gramatica.y"
      {yyerror("Error en la parte izquierda de la condición");}
      break;
      case 83:
      //#line 358 "gramatica.y"
      {yyerror("Error en la parte derecha de la condición");}
      break;
      case 84:
      //#line 361 "gramatica.y"
      {yyval.obj = "<=";}
      break;
      case 85:
      //#line 362 "gramatica.y"
      {yyval.obj = ">=";}
      break;
      case 86:
      //#line 363 "gramatica.y"
      {yyval.obj = "==";}
      break;
      case 87:
      //#line 364 "gramatica.y"
      {yyval.obj = "<";}
      break;
      case 88:
      //#line 365 "gramatica.y"
      {yyval.obj = ">";}
      break;
      case 89:
      //#line 366 "gramatica.y"
      {yyval.obj = "<>";}
      break;
      case 90:
      //#line 370 "gramatica.y"
      {
      																								Terceto t = new Terceto(contadorTerceto, "CALL", val_peek(2).sval, "", a.TablaSimbolo);
      																								listaTercetos.add(t);
      																								contadorTerceto++;
      																								}
      break;
      case 91:
      //#line 375 "gramatica.y"
      {yyerror("Falta '('");}
      break;
      case 92:
      //#line 376 "gramatica.y"
      {yyerror("Falta ')'");}
      break;
      case 93:
      //#line 377 "gramatica.y"
      {yyerror("No se permite pasaje de parámetros");}
      break;
      case 94:
      //#line 378 "gramatica.y"
      {yyerror("Error en la llamada a Metodo");}
      break;
      case 95:
      //#line 381 "gramatica.y"
      {
      																								Simbolo s = a.TablaSimbolo.get(((Simbolo)val_peek(2).obj).getValor());
      																								String tipo1 = s.getTipo();
      																								String tipo2 = ((Asignable)val_peek(0).obj).getTipo();
      																									if(!declaradoYaccesible(((Simbolo)val_peek(2).obj).getValor())){
      																										yyerror("Variable no declarada");
      																									}
      																									else{
      																										if(tipo1.equals(tipo2)){
      																											Terceto t = new TercetoOperacion(contadorTerceto, ":=",s,val_peek(0).obj,a.TablaSimbolo);
      																											contadorTerceto++;
      																											listaTercetos.add(t);
      																											yyval.obj = t;
      																										}
      																										else{
      																											yyerror("Asignacion entre variables de tipos no compatible");
      																										}
      																									}
      																								}
      break;
      case 96:
      //#line 400 "gramatica.y"
      {yyerror("Operador de asignacion erróneo");}
      break;
      case 97:
      //#line 404 "gramatica.y"
      {
      																								Asignable asOp1 = ((Asignable)val_peek(2).obj);
      																								Asignable asOp2 = ((Asignable)val_peek(0).obj);

      																								if (asOp1.getTipo().equals(asOp2.getTipo())){
      																									TercetoOperacion t = new TercetoOperacion(contadorTerceto,"+",asOp1,asOp2,a.TablaSimbolo);
      																									contadorTerceto ++;
      																									listaTercetos.add(t);
      																									yyval.obj = t;
      																								}
      																								else
      																									yyerror("Error semantico: suma de tipos incompatibles");
      																								}
      break;
      case 98:
      //#line 418 "gramatica.y"
      {
      																								Asignable asOp1 = ((Asignable)val_peek(2).obj);
      																								Asignable asOp2 = ((Asignable)val_peek(0).obj);

      																								if (asOp1.getTipo().equals(asOp2.getTipo())){
      																									TercetoOperacion t = new TercetoOperacion(contadorTerceto,"-",asOp1,asOp2,a.TablaSimbolo);
      																									contadorTerceto ++;
      																									listaTercetos.add(t);
      																									yyval.obj = t;
      																								}
      																								else
      																									yyerror("Error semantico: resta de tipos incompatibles");
      																								}
      break;
      case 100:
      //#line 432 "gramatica.y"
      																									{
      																								   
      																										if(((Asignable)val_peek(1).obj).getTipo().equals("int")){
      																											TercetoOperacion t = new TercetoOperacion(contadorTerceto,"TO_ULONG",(Asignable)val_peek(1).obj,"",a.TablaSimbolo);
      																											t.setTipo("ulong");
      																											contadorTerceto++;
      																											listaTercetos.add(t);
      																											yyval.obj = t;
      																										}
      																										else{
      																											yyerror("Error semántico: No se puede convertir la expresion a ulong");
      																										}
      																									
      																									}
      break;
      case 101:
      //#line 448 "gramatica.y"
      {yyerror("Expresión inválida");}
      break;
      case 102:
      //#line 452 "gramatica.y"
      {
      																								Asignable asOp1 = ((Asignable)val_peek(2).obj);
      																								Asignable asOp2 = ((Asignable)val_peek(0).obj);

      																								if (asOp1.getTipo().equals(asOp2.getTipo())){
      																									TercetoOperacion t = new TercetoOperacion(contadorTerceto,"*",asOp1,asOp2,a.TablaSimbolo);
      																									contadorTerceto ++;
      																									listaTercetos.add(t);
      																									yyval.obj = t;
      																								}
      																								else
      																									yyerror("Error semantico: resta de tipos incompatibles");
      																								}
      break;
      case 103:
      //#line 466 "gramatica.y"
      {
      																								Asignable asOp1 = ((Asignable)val_peek(2).obj);
      																								Asignable asOp2 = ((Asignable)val_peek(0).obj);

      																								if (asOp1.getTipo().equals(asOp2.getTipo())){
      																									TercetoOperacion t = new TercetoOperacion(contadorTerceto,"/",asOp1,asOp2,a.TablaSimbolo);
      																									contadorTerceto ++;
      																									listaTercetos.add(t);
      																									yyval.obj = t;
      																								}
      																								else
      																									yyerror("Error semantico: division de tipos incompatibles");
      																								}
      break;
      case 107:
      //#line 486 "gramatica.y"
      { Simbolo s = a.TablaSimbolo.get(val_peek(0).sval);                                                         yyval.obj = s;}
      break;
      case 108:
      //#line 487 "gramatica.y"
      {
												  Simbolo attrib = a.TablaSimbolo.get(val_peek(0).sval);
												  Simbolo objeto = a.TablaSimbolo.get(val_peek(2).sval);
      											  ArrayList<Simbolo> aux = a.getAtributos(objeto);
      											  if (aux.contains(attrib)) {
      											  String nombre = objeto.getValor() + "_" + attrib.getValor();
      											  String tipo = attrib.getTipo();
      											  if (!a.TablaSimbolo.containsKey(nombre)) {
      												Simbolo obj_attr = new Simbolo (nombre, tipo);
      												obj_attr.setUso("OBJETO_ATRIBUTO");
      												obj_attr.setDeclarado();
      												obj_attr.setAmbiente(ambiente);
      												a.TablaSimbolo.put(nombre, obj_attr);
      												yyval.obj = obj_attr;	
      											  }
      											  Simbolo s = a.TablaSimbolo.get(nombre);
      											  yyval.obj = s;
      											 
      											  }
      											  else
      											  yyerror(val_peek(1), "El atributo no esta declarado");
      											 
      							                }
      break;
      case 109:
      //#line 512 "gramatica.y"
      {
      																								Simbolo s = a.TablaSimbolo.get(val_peek(0).sval);
      																								yyval.obj = s;
      																								}
      break;
      case 110:
      //#line 516 "gramatica.y"
      {
      																								Simbolo s = check_rango(val_peek(0).sval);
      																								yyval.obj = s;
      																								}
      break;
      //#line 1292 "Parser.java"
      //########## END OF USER-SUPPLIED ACTIONS ##########
 }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar =  yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################


//## run() --- for Thread #######################################
/**
* A default run method, used for operating this parser
* object in the background.  It is intended for extending Thread
* or implementing Runnable.  Turn off with -Jnorun .
* @throws IOException 
*/
public void run() throws IOException
{
yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
* Default constructor.  Turn off with -Jnoconstruct .

*/

Analizador a;
ArrayList<String> listaIdentificadores = new ArrayList<String>();
ArrayList<String> listaIdentificadores_clase = new ArrayList<String>();
ArrayList<String> listaHerencias = new ArrayList<String>();

private String ambiente = "GLOBAL";
private boolean cambioAmbiente;
private boolean yaCambie;


private String herencia ="";
private boolean recuperoHerencia;

Stack<Integer> pilaTercetos = new Stack<Integer>();
ArrayList<String> errores = new ArrayList<String>();
ArrayList<String> reconocidos = new ArrayList<String>();
ArrayList<Terceto> listaTercetos = new ArrayList<Terceto>();
Token t;
int inicioDO;
int contadorTerceto = 0;
int contadorVarAux = 0;
int lineactual;

public Parser(Analizador a, ArrayList<String> errores)
{
this.a = a;
this.errores = errores;
}

public int yylex() throws IOException
{
Token token = a.getToken();
if (token.getToken() ==270) { //Viene la palabra reservada CLASS
	cambioAmbiente = true;
}

if(token.getToken()== 258 && cambioAmbiente && !yaCambie) { //Viene un ID despues de Class
	ambiente = token.getLexema();
	Analizador.TablaSimbolo.get(token.getLexema()).setAmbiente("GLOBAL");
	cambioAmbiente = false;
	yaCambie = true;
}else
if (token.getToken() == 271)
	recuperoHerencia = true;
if(recuperoHerencia && token.getToken() == 258) {
	herencia = token.getLexema();
	recuperoHerencia = false;
}
this.lineactual = token.getLinea();
yylval = new ParserVal(t);
yylval.sval = token.getLexema();
return token.getToken();
}

public void asignarUso(String Simbolo, String uso) {
	Analizador.TablaSimbolo.get(Simbolo).setUso(uso);
}

public void setHerencias(String simbolo) {
	a.setHerencias(simbolo, listaHerencias);
	listaHerencias.clear();
}
public void setHerencia(String simbolo, String padre) {
	a.setHerencia(simbolo, padre);
}


public void AsignarTipo(String tipo, String Simbolo) {
	
	Analizador.TablaSimbolo.get(Simbolo).setTipo(tipo);
}
public void AsignarInfo(String tipo, String uso)
{
for(int i=0; i< listaIdentificadores.size(); i++) {
	if(!Analizador.TablaSimbolo.get(listaIdentificadores.get(i)).estaDeclarado()) {
		Analizador.TablaSimbolo.get(listaIdentificadores.get(i)).setTipo(tipo);
		Analizador.TablaSimbolo.get(listaIdentificadores.get(i)).setUso(uso);
		Analizador.TablaSimbolo.get(listaIdentificadores.get(i)).setDeclarado();
		Analizador.TablaSimbolo.get(listaIdentificadores.get(i)).setAmbiente(ambiente);
	}else yyerror("El identificador "+ listaIdentificadores.get(i) + " en la linea "+ this.lineactual + "ya se encuentra declarado");
}
listaIdentificadores.clear();
}


public void asignarAmbiente(String simbolo){
	Analizador.TablaSimbolo.get(simbolo).setAmbiente(ambiente);
}

public void setDeclarado(String simbolo) {
	Analizador.TablaSimbolo.get(simbolo).setDeclarado();
}

public boolean declaradoYaccesible(String simbolo) {
if (Analizador.TablaSimbolo.get(simbolo).estaDeclarado())
	if(Analizador.TablaSimbolo.get(simbolo).getAmbiente() == "GLOBAL" ||
	   Analizador.TablaSimbolo.get(simbolo).getAmbiente() == ambiente )
		return true;
	if(listaHerencias.isEmpty()) {
		if(herencia != "") {
			if(Analizador.TablaSimbolo.get(herencia).getHerencias().contains(Analizador.TablaSimbolo.get(simbolo).getAmbiente()))
				return true;
			else 
				return false;
		}
		else {
			return false;
		}
	}
	else  {
		for (int i = 0; i< listaHerencias.size();i++) {
				if(Analizador.TablaSimbolo.get(listaHerencias.get(i)).getHerencias().contains (Analizador.TablaSimbolo.get(simbolo).getAmbiente()) )
					return true;
			else return false;
		}
	}
	return false;
}


public Simbolo check_rango(String num){
	String Lexema = num;
	int cte_neg = (Integer.valueOf(Lexema)*(-1));
	Simbolo s_neg = new Simbolo(Lexema, "int"); 
	s_neg.setUso("CTE");
	if (cte_neg > -32768) {               //DENTRO DE RANGO MENOR
		s_neg.setValor("-"+Lexema);                                      
		if(Analizador.TablaSimbolo.contains(s_neg)){											//si contengo el negativo
			Analizador.TablaSimbolo.get(s_neg.getValor()).increase_Ref();         		//incremento 1 la ref
			}
		s_neg.setValor(Lexema);
		if (Analizador.TablaSimbolo.contains(s_neg)){                                		//Si esta el positivo en la tabla
			if(Analizador.TablaSimbolo.get(s_neg.getValor()).get_Ref() > 1 ) {               //y tengo mas de 1
				Analizador.TablaSimbolo.get(s_neg.getValor()).decrease_Ref();                //le bajo 1 ref 
				s_neg.setValor("-" + Lexema);		  								//y le seteo el negativo para agregarlo
				if( Analizador.TablaSimbolo.contains(s_neg) ) {             					//chequeo si existe el negativo
					Analizador.TablaSimbolo.get(s_neg).increase_Ref();        				//existe , le incremento el contador
					return s_neg;
				}
				else {                                                     			//no existe , lo agrego como negativo
					Analizador.TablaSimbolo.put(s_neg.getValor(), s_neg);
					return s_neg;
				}			
			}
			else { 																	//si la referencia es 1 , lo cree por la AS
				Analizador.TablaSimbolo.remove(s_neg.getValor());
				s_neg.setValor("-" + Lexema);
				Analizador.TablaSimbolo.put(s_neg.getValor() , s_neg);
				return s_neg;
			}
		}
		else {
				s_neg.setValor("-" + Lexema);
				if( Analizador.TablaSimbolo.contains(s_neg) ) {             					//chequeo si existe el negativo
					Analizador.TablaSimbolo.get(s_neg).increase_Ref();        				//existe , le incremento el contador
					return s_neg;
				}
				else {                                                     			//no existe , lo agrego como negativo
					Analizador.TablaSimbolo.put(s_neg.getValor(), s_neg);
					return s_neg;
				}			
		}
	}
	else {
		String valorant = s_neg.getValor();
		s_neg.setValor("-"+ 32767);
		this.errores.add("Warning : Valor negativo Fuera de rango");
		if(Analizador.TablaSimbolo.contains(s_neg)){											//si contengo el negativo
			Analizador.TablaSimbolo.get("-"+ s_neg.getValor()).increase_Ref();         		//incremento 1 la ref
			return s_neg;
		}
		else {
			Analizador.TablaSimbolo.put(s_neg.getValor(), s_neg);
			Analizador.TablaSimbolo.remove(valorant);
			return s_neg;
		}
	}
		
}


/*
public void yyerror(Token t, String error) {

	//System.out.println(error);
	this.errores.add(error);
}*/
public void yyerror(String error) {
	this.errores.add(error + " en linea " + this.lineactual );
}
public void yyerror(ParserVal pv, String s){
  this.errores.add("Error: en linea "  + " - " + s);
  //System.out.println("Error: en linea " + pv.t.getLinea() + " - " + s);
}

public ArrayList<String> getErrores(){
	return this.errores;
}
public ArrayList<String> getReconocidos(){
	return this.reconocidos;
}



/**
* Create a parser, setting the debug to true or false.
* @param debugMe true for debugging, false for no debug.
*/
public Parser(boolean debugMe)
{
yydebug=debugMe;
}
//###############################################################

}
//#