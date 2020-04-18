package Compilador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import Compilador.Parser;
import Compilador.AccionesSemanticas.AS_AgregarBuffer;
import Compilador.AccionesSemanticas.AS_Error;
import Compilador.AccionesSemanticas.AS_Error_AL;
import Compilador.AccionesSemanticas.AS_FinCadena;
import Compilador.AccionesSemanticas.AS_FinComentario;
import Compilador.AccionesSemanticas.AS_FinConst;
import Compilador.AccionesSemanticas.AS_FinSimbolo;
import Compilador.AccionesSemanticas.AS_FinSimboloCompuesto;
import Compilador.AccionesSemanticas.AS_FinSimboloSimple;
import Compilador.AccionesSemanticas.AS_Fin_ID_PR;
import Compilador.AccionesSemanticas.AS_NoAction;
import Compilador.AccionesSemanticas.AccionSemantica;

public class Analizador {
	
	static public String lexema = "";
	private static boolean acomodarLinea= false; // acomodar linea y tomar la lectura anterior
	protected static int asciiAnt = 0;
	private static int nroLinea= 1;
	static StringBuffer buffer = new StringBuffer();
	static Hashtable<String,Simbolo> TablaSimbolo = new Hashtable<String,Simbolo>();
	static int caracter = -2;
	private static HashMap<String, Integer> TablaToken = new HashMap<String,Integer>();
	
	
	static Diccionario diccionario = new Diccionario();
	

	

	//Acciones Semánticas
	static AccionSemantica AgregarBuffer = new AS_AgregarBuffer();
	static AccionSemantica Fin_ID_PR = new AS_Fin_ID_PR(TablaSimbolo, TablaToken);
	static AccionSemantica FinCons = new AS_FinConst(TablaSimbolo, TablaToken);
	static AccionSemantica FinSimbolo = new AS_FinSimbolo();
	static AccionSemantica FinSimboloComp = new AS_FinSimboloCompuesto(TablaToken);
	static AccionSemantica FinSimboloSimple = new AS_FinSimboloSimple();
	static AccionSemantica NoAction = new AS_NoAction();
	static AccionSemantica FinCadena = new AS_FinCadena(TablaSimbolo, TablaToken);
	static AccionSemantica Error = new AS_Error();
	static AccionSemantica Error_AL = new AS_Error_AL();	
	static AccionSemantica FinComentario = new AS_FinComentario();
		


										                                         
										 //Letra  Digito	/n	espacio	-	+	<	>	=	*	/	(	)	:	,	; 	_	%	.	tab / otro
	static int[][] matriz_de_estados = 	{   {1	,	2	,	0	,	0,	0,	0,	7,	8,	9,	0,	3,	0,	0,	9,	0,	0,	0,	6,	0,	0},
											{1	,	1	,	0	,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0},
											{0	,	2	,	0	,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
											{0	,	0	,	0	,	0,	0,	4,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
											{4	,	4	,	4	,	4,	4,	5,	4,	4,	4,	4,	4,	4,	4,	4,	4,	4,	4,	4,	4,	4},
											{4	,	4	,	4	,	4,	4,	4,	4,	4,	4,	4,	0,	4,	4,	4,	4,	4,	4,	4,	4,	4},
											{6	,	6	,	0	,	6,	6,	6,	6,	6,	6,	6,	6,	6,	6,	6,	6,	6,	6,	0,	6,	6},
											{0	,	0	,	0	,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
											{0	,	0	,	0	,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
											{0	,	0	,	0	,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
										};
	
	 													//Letra  					Digito						/n					espacio						-						+						<						>						=					*						/					(					)					:						,					; 				_					%					.				tab / otro
	static AccionSemantica[][] matriz_de_acciones = {	{AgregarBuffer		,	AgregarBuffer		,	NoAction			,	NoAction			,	FinSimbolo			,	FinSimbolo			,	AgregarBuffer		,	AgregarBuffer		,	AgregarBuffer	,	FinSimbolo			,	AgregarBuffer		,	FinSimbolo		,	FinSimbolo			,	AgregarBuffer	,	FinSimbolo		,	FinSimbolo		,	Error			,	NoAction		,FinSimbolo			,	Error			},
														{AgregarBuffer		,	AgregarBuffer		,	Fin_ID_PR			,	Fin_ID_PR			,	Fin_ID_PR			,	Fin_ID_PR			,	Fin_ID_PR			,	Fin_ID_PR			,	Fin_ID_PR		,	Fin_ID_PR			,	Fin_ID_PR		,	Fin_ID_PR		,	Fin_ID_PR			,	Fin_ID_PR		,	Fin_ID_PR		,	Fin_ID_PR		,	AgregarBuffer	,	Fin_ID_PR		,Fin_ID_PR			,	Fin_ID_PR		},
														{FinCons			,	AgregarBuffer		,	FinCons				,	FinCons				,	FinCons				,	FinCons				,	FinCons				,	FinCons				,	FinCons			,	FinCons				,	FinCons			,	FinCons			,	FinCons				,	FinCons			,	FinCons			,	FinCons			,	FinCons			,	FinCons			,FinCons			,	FinCons			},
														{FinSimboloSimple	,	FinSimboloSimple	,	FinSimboloSimple	,	FinSimboloSimple	,	FinSimboloSimple	,	NoAction			,	FinSimboloSimple	,	FinSimboloSimple	,	FinSimboloSimple,	FinSimboloSimple	,	FinSimboloSimple,	FinSimboloSimple,	FinSimboloSimple	,	FinSimboloSimple,	FinSimboloSimple,	FinSimboloSimple,	FinSimboloSimple,	FinSimboloSimple,FinSimboloSimple	,	FinSimboloSimple},
														{NoAction			, 	NoAction			, 	NoAction			, 	NoAction			, 	NoAction			, 	NoAction			, 	NoAction			, 	NoAction			, 	NoAction		, 	NoAction			, 	NoAction		, 	NoAction		, 	NoAction			, 	NoAction		, 	NoAction		, 	NoAction		, 	NoAction		, 	NoAction		,NoAction			, 	NoAction		},
														{NoAction			, 	NoAction			, 	NoAction			, 	NoAction			, 	NoAction			, 	NoAction			, 	NoAction			, 	NoAction			, 	NoAction		, 	NoAction			, 	FinComentario	,	NoAction		, 	NoAction			, 	NoAction		, 	NoAction		, 	NoAction		, 	NoAction		, 	NoAction		,NoAction			, 	NoAction		},
														{AgregarBuffer		,	AgregarBuffer		,	Error_AL			,	AgregarBuffer		,	AgregarBuffer		,	AgregarBuffer		,	AgregarBuffer		,	AgregarBuffer		,	AgregarBuffer	,	AgregarBuffer		,	AgregarBuffer	,	AgregarBuffer	,	AgregarBuffer		,	AgregarBuffer	,	AgregarBuffer	,	AgregarBuffer	,	AgregarBuffer	,	FinCadena		,AgregarBuffer		,	AgregarBuffer	},
														{FinSimboloSimple	,	FinSimboloSimple	,	FinSimboloSimple	,	FinSimboloSimple	,	FinSimboloSimple	,	FinSimboloSimple	,	FinSimboloSimple	,	FinSimboloComp		,	FinSimboloComp	,	FinSimboloSimple	,	FinSimboloSimple,	FinSimboloSimple,	FinSimboloSimple	,	FinSimboloSimple,	FinSimboloSimple,	FinSimboloSimple,	FinSimboloSimple,	FinSimboloSimple, FinSimboloSimple	,	FinSimboloSimple},
														{FinSimboloSimple	,	FinSimboloSimple	,	FinSimboloSimple	,	FinSimboloSimple	,	FinSimboloSimple	,	FinSimboloSimple	,	FinSimboloSimple	,	FinSimboloSimple	,	FinSimboloComp	,	FinSimboloSimple	,	FinSimboloSimple,	FinSimboloSimple,	FinSimboloSimple	,	FinSimboloSimple,	FinSimboloSimple,	FinSimboloSimple,	FinSimboloSimple,	FinSimboloSimple, FinSimboloSimple	,	FinSimboloSimple},
														{Error_AL			,	Error_AL			,	Error_AL			,	Error_AL			,	Error_AL			,	Error_AL			,	Error_AL			,	Error_AL			,	FinSimboloComp	,	Error_AL			,	Error_AL		,	Error_AL		,	Error_AL			,	Error_AL		,	Error_AL		,	Error_AL		,	Error_AL		,	Error_AL		, Error_AL		 	,	Error_AL		},
													};

 
	
	FileReader fr;
	static BufferedReader br;
	
	
	public static ArrayList<Simbolo> getAllAtributos(){
		ArrayList<Simbolo> atributo = new ArrayList<Simbolo>();
		for (Simbolo s : TablaSimbolo.values()) {
			if (s.getUso().equals("ATRIBUTO")) {
				atributo.add(s);
			}
		}
		return atributo;
	}
	
	
	
	public static ArrayList<Simbolo> getObjetos(){
		ArrayList<Simbolo> objetos = new ArrayList<Simbolo>();
		for (Simbolo s : TablaSimbolo.values()) {
			if (s.getUso().equals("OBJETO")) {
				objetos.add(s);
			}
		}
		return objetos;
	}
	
	public static ArrayList<Simbolo> getCadenas(){
		ArrayList<Simbolo> cadenas = new ArrayList<Simbolo>();
		for (Simbolo s : TablaSimbolo.values()) {
			if (s.getTipo().equals("Cadena")) {
				cadenas.add(s);
			}
		}
		return cadenas;
	}

	public static ArrayList<Simbolo> getObjeto_Atributo(){
		ArrayList<Simbolo> obj_atr = new ArrayList<Simbolo>();
		for (Simbolo s : TablaSimbolo.values()) {
			if (s.getUso().equals("OBJETO_ATRIBUTO")) {
				obj_atr.add(s);
			}
		}
		return obj_atr;
	}

	public Hashtable<String,Simbolo> getTablaSimbolo(){
		return this.TablaSimbolo;
	}

	public static ArrayList<Simbolo> getAtributos(Simbolo objeto){
		ArrayList<Simbolo> atributos = new ArrayList<Simbolo>();
		ArrayList<String> ambientes = new ArrayList <String>();
		ambientes.add(objeto.getTipo());
		Simbolo clase = TablaSimbolo.get(objeto.getTipo());
		ArrayList<String> herencias = clase.getHerencias();
		if (!herencias.isEmpty()) 
			for (String h : herencias) 
				ambientes.add(h);
		for (Simbolo s : TablaSimbolo.values()) {
			if (s.getUso().equals("ATRIBUTO"))
				if (ambientes.contains(s.getAmbiente())) {
					atributos.add(s);
				}
			}

		return atributos;
	}

	public static ArrayList<Simbolo> getVariables(){
		ArrayList<Simbolo> variables = new ArrayList<Simbolo>();
		for (Simbolo s : TablaSimbolo.values()) {
			if (s.getUso().equals("VARIABLE")) {
				variables.add(s);
			}
		}
		return variables;
	}

	public static ArrayList<Simbolo> getConstantes(){
		ArrayList<Simbolo> constantes = new ArrayList<Simbolo>();
		for (Simbolo s : TablaSimbolo.values()) {
			if (s.getUso().equals("CTE")) {
				constantes.add(s);
			}
		}
		return constantes;
	}
	
	public void cargarArchivo(String origen) throws IOException{

		fr = new FileReader(origen);
	    br = new BufferedReader(fr);
	}
	
	
	public Token getToken() throws IOException {
		Token t = new Token();
		
		if(asciiAnt == -1)    	//Fin del archivo, devuelve 0
		{
			t.setToken(0); 
			return t;
		}		
		
		int estadoSig = 0;
		int estado = 0;
		boolean hayToken = false;
		int ascii;
		do 
		{	if (acomodarLinea)
			{
				ascii = asciiAnt;
				acomodarLinea = false;
			}
			else {
				ascii = br.read(); 
				if(ascii == 13) {
					nroLinea++;
				}
			}
			asciiAnt = ascii;
			int columna = diccionario.asciiToColumna(ascii);
			estadoSig = matriz_de_estados[estado][columna];
			AccionSemantica AS = matriz_de_acciones[estado][columna];
			t.setToken(AS.execute(buffer, (char)ascii));
			acomodarLinea = AS.acomodarLinea();
			estado = estadoSig;
			if(t.getToken() > 0)
			{
				//if (buffer.length() > 0)
				t.setLexema(buffer.toString());
				t.setLinea(nroLinea);
				hayToken = true;
				buffer.delete(0, buffer.length());	
			}
			else if (asciiAnt == -1) {	
				t.setToken(0); 
				return t;
			}									//TRATAMIENTO DE ERRORES LÉXICOS
			else if (t.getToken() == -2){			
				System.out.println("Error: caracter inválido "+ascii+ " en la linea " + nroLinea);
			}else if (t.getToken() == -3){
				System.out.println("Warning en la linea "+nroLinea+": identificador supera la longitud máxima");
			}else if (t.getToken() == -4){
				System.out.println("Error en la linea "+nroLinea+": constante fuera del rango permitido");
			}
		}
		while (!hayToken);
		//System.out.println(t);
		return t;		
	}
	
	public void setHerencia(String simbolo, String padre) {
		ArrayList<String> herencias = new ArrayList<String>();
		herencias.add(padre);
		if (!TablaSimbolo.get(padre).getHerencias().isEmpty())
		for (int i = 0; i < TablaSimbolo.get(padre).getHerencias().size(); i++) {
		herencias.add(TablaSimbolo.get(padre).getHerencias().get(i));
		}
		TablaSimbolo.get(simbolo).setHerencias(herencias);
		}

		public void setHerencias(String simbolo, ArrayList<String> listaPadres) {
		ArrayList<String> herencias = new ArrayList<String>();
		for (int i = 0; i < listaPadres.size(); i++) {
		String padre = listaPadres.get(i);
		if (!herencias.contains(padre))
		herencias.add(padre);
		if (!TablaSimbolo.get(padre).getHerencias().isEmpty())
		for (int j = 0; j < TablaSimbolo.get(padre).getHerencias().size(); j++) {
		if (!herencias.contains(TablaSimbolo.get(padre).getHerencias().get(j)))
		herencias.add(TablaSimbolo.get(padre).getHerencias().get(j));
		}
		}
		TablaSimbolo.get(simbolo).setHerencias(herencias);
		}
		
		public static void limpiarBuffer() {
			buffer.delete(0, buffer.length());
		}
	
	public static void main(String[] args) throws IOException {			
		
		

		TablaToken.put("CTE", 257);
		TablaToken.put("ID", 258);
		TablaToken.put("if", 259);
		TablaToken.put("else", 260);
		TablaToken.put("end_if", 261);
		TablaToken.put("while", 262);
		TablaToken.put("print", 263);
		TablaToken.put("int", 264);
		TablaToken.put("begin", 265);
		TablaToken.put("end", 266);
		TablaToken.put("ulong", 267);
		TablaToken.put("do", 268);
		TablaToken.put("until", 269);	
		TablaToken.put("class", 270);
		TablaToken.put("extends", 271);
		TablaToken.put("to_ulong", 272);
		TablaToken.put("void", 273);
		TablaToken.put("CADENA", 274);
		TablaToken.put("<>", 275);
		TablaToken.put(">=", 276);
		TablaToken.put("<=", 277);
		TablaToken.put(":=", 278);
		TablaToken.put("==", 279);
		

		Analizador a = new Analizador();
		String file = args[0];  /*  rgs[0]; */
		a.cargarArchivo(file);		
		ArrayList<String> errores = new ArrayList();
		ArrayList<String> reconocidos = new ArrayList<String>();
		Parser p = new Parser(a, errores);
		p.yyparse();
		errores =  p.getErrores();
		reconocidos = p.getReconocidos();
		System.out.println("/*           Errores          */");
	    for(int i = 0; i < errores.size(); i++){
	        System.out.println(errores.get(i));
	    }
	    System.out.println("/*           Reconocidos          */");
	    for(int i = 0; i < reconocidos.size(); i++){
	        System.out.println(reconocidos.get(i));
	    }
		System.out.println("/*           Tabla de Simbolos          */");
		TablaSimbolo.entrySet().forEach(entry->{
			String format = "|%1$-15s|";
			String clave  = String.format(format,"Clave: "+entry.getKey()); 
		    System.out.println(clave + entry.getValue().imprimir() );  
		 });
		System.out.println("/*           Tercetos          */");
		for(int i=0; i< p.listaTercetos.size(); i++) {
			p.listaTercetos.get(i).imprimirTerceto();
		}
		
		//GeneradorAssembler GA = new GeneradorAssembler(a, p.listaTercetos);

		Utilidades.CrearTxtSalida(a, p);
	}
}