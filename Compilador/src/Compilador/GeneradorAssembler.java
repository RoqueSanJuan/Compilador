package Compilador;

import java.util.ArrayList;
import java.util.Hashtable;

import Compilador.Tercetos.*;

public class GeneradorAssembler {
	
	Analizador a;
	ArrayList<Terceto> listaTercetos;
	ArrayList<String> assembler = new ArrayList<>();
	ArrayList<String> Metodos = new ArrayList<>();
	boolean InMetodo = false;
	
	
	Registros r = new Registros();
	
	
	public GeneradorAssembler(Analizador a, ArrayList<Terceto> lt) {
		this.a = a;
		this.listaTercetos = lt;
		generarCodigo();
		System.out.println("/*           Assembler          */");
		for(int i=0; i< assembler.size(); i++) {
			System.out.println(assembler.get(i));
		}
	}
	
	public ArrayList<String> getAssembler(){
		return this.assembler;
	}
	
	public void generarEncabezado() {
		agregarString(".386" );
		agregarString (".model flat, stdcall");
		agregarString ( ".stack 200h ;Tamanio de la pila");
		agregarString ( "option casemap :none");
		agregarString ( "include C:\\masm32\\include\\windows.inc");
		agregarString ( "include C:\\masm32\\include\\kernel32.inc");
		agregarString ( "include C:\\masm32\\include\\user32.inc");
		agregarString ( "include C:\\masm32\\include\\masm32.inc");
		agregarString ( "includelib C:\\masm32\\lib\\kernel32.lib");
		agregarString ( "includelib C:\\masm32\\lib\\user32.lib");
		agregarString ( "includelib C:\\masm32\\lib\\masm32.lib");
	}
	
	
	public void generarFinal() {
		agregarString("JMP LABEL_END");
		setLabelDivisionCero();
		setLabelPerdidaInfo();
		agregarString("LABEL_END:");
		agregarString("invoke ExitProcess, 0");
		generarMetodos();
		agregarString("end start");
	}
	
	public void agregarString(String cadena) {
		if(!InMetodo) {
			assembler.add(cadena);
		}
		else {
			Metodos.add(cadena);
		}
	}
	
	public void generarData(){
		agregarString(".data");
		ArrayList <Simbolo> variables = a.getVariables();
		ArrayList <Simbolo> constantes = a.getConstantes();
		ArrayList <Simbolo> cadenas = a.getCadenas();	
		ArrayList <Simbolo> objeto_atributo = a.getObjeto_Atributo();	
		ArrayList <Simbolo> atributos = a.getAllAtributos();

		for (Simbolo c : constantes) { 														//DECLARA CONSTANTES
		if (c.getTipo().equals("int"))
			if(c.getValor().substring(0,1).contentEquals("-"))
				agregarString("_" + c.getValor().replace("-", "n") + " DW " + c.getValor());
			else
			agregarString("_" + c.getValor() + " DW " + c.getValor());
		else if (c.getTipo().equals("ulong"))
			agregarString("_" + c.getValor() + " DD " + c.getValor());
			}
		for (Simbolo v : variables) { 														//DECLARA VARIABLES
			if (v.getTipo().equals("int"))
				agregarString("_" + v.getValor() + " DW ?" );
			else if (v.getTipo().equals("ulong"))
				agregarString("_" + v.getValor() + " DD ?" );
			}
		for (Simbolo oa : objeto_atributo) {												//DECLARA ATRIBUTO DE OBJETOS
			if (oa.getTipo().equals("int"))
				agregarString("_" + oa.getValor() + " DW ?" );
			else if (oa.getTipo().equals("ulong"))
				agregarString("_" + oa.getValor() + " DD ?" );
			}	
		for (Simbolo a : atributos) {														//DECLARA ATRIBUTO DE METODOS
			if (a.getTipo().equals("int"))
				agregarString("_" + a.getValor() + " DW ?" );
			else if (a.getTipo().equals("ulong"))
				agregarString("_" + a.getValor() + " DD ?" );
			}		
		for (Simbolo ca : cadenas) {														//DECLARA CADENAS
			if (ca.getTipo().equals("Cadena"))
			 	agregarString(ca.valor.replace(" ", "_") + " DB  '"+ ca.valor+"' , 0");
			}
		}
	
	public void generarCodigo() {
		generarEncabezado();						//LISTO
		generarData();								//LISTO
		agregarString("msjDivision_Cero DB 'Error: Divisor igual a cero' , 0");
		agregarString("msjPerdida_Info DB 'Error: Perdida de informacion en la conversion' , 0");
		agregarString(".code");
		agregarString("start:");	
		for (Terceto t : listaTercetos) {
			
			if (t.getNombre().equals("Suma")){								//LISTO
				getCodSuma(t);
			}
			if (t.getNombre().equals("Resta")){								//LISTO
				getCodResta(t);
			}
			if (t.getNombre().equals("Asignacion")){						//LISTO
				getCodAsig(t);
			}
			if (t.getNombre().equals("Multiplicacion")){					//FALTA
				getCodMult(t);
			}
			if (t.getNombre().equals("Division")){							//LISTO
				getCodDiv(t);
			}
			if (t.getNombre().equals("Comparacion")){						//LISTO
				getCodComp(t);
			}
			if (t.getNombre().equals("FINIF")){
				getCodLabel(t);
			}
			if (t.getNombre().equals("FINDO")){
				getCodLabel(t);
			}
			if (t.getNombre().equals("INIDO")){
				getCodLabel(t);
			}
			if (t.getNombre().equals("BI")){
				getCodBI(t);
			}
			if (t.getNombre().equals("BF")){
				getCodBF(t);
			}
			if (t.getNombre().equals("BD")){
				getCodBD(t);
			}
			if (t.getNombre().equals("Label")){
				getCodLabel(t);
			}
			if (t.getNombre().equals("PRINT")) {
				getCodPrint(t);
			}
			if (t.getNombre().equals("TO_ULONG")) {
				getCodConversion(t);
			}
			if (t.getNombre().equals("RET")) {
				getCodRet(t);
			}
			if (t.getNombre().equals("FUNCTION")) {
				getCodFunction(t);
			}
			if (t.getNombre().equals("CALL")) {
				getCodCall(t);
			}
		}
		generarFinal();	
	}
	
	
	public void generarMetodos() {
		for(String metodo: Metodos) {
			agregarString(metodo);
		}
	}
	
	
	
	///////////////////////////////          SITUACION 4.A          ///////////////////////////////
	///////////////////////////////          SUMA          ///////////////////////////////
private void getCodSuma(Terceto t) {
		
		Object o1= t.getOperando1();
		Object o2= t.getOperando2();
		String tipo = ((TercetoOperacion)t).getTipo();
		
			if(((Asignable) o1).esTerceto() && !((Asignable) o2).esTerceto()) {         														    //T(op, [terceto], algo)
				
				if(o2.toString().substring(0,1).equals("-"))
					agregarString("ADD " + r.encontrarTerceto(((Terceto)o1).getNro(), tipo) + ", " + "_"+o2.toString().replace("-", "n"));
				else
					agregarString("ADD " + r.encontrarTerceto(((Terceto)o1).getNro(), tipo) + ", " + "_"+o2);
				r.ocuparRegistro(r.encontrarTerceto(((Terceto)o1).getNro(), tipo), t.getNro());
			}
			else if(((Asignable) o1).esTerceto() && ((Asignable) o2).esTerceto())  {    															//T(op, [terceto], [terceto])
				agregarString("ADD " +  r.encontrarTerceto(((Terceto)o1).getNro(), tipo) + ", " +  r.encontrarTerceto(((Terceto)o2).getNro(), tipo));
				r.ocuparRegistro(r.encontrarTerceto(((Terceto)o2).getNro(), tipo), 0);
				r.ocuparRegistro(r.encontrarTerceto(((Terceto)o1).getNro(), tipo), t.getNro());			
			}
			else if (!((Asignable) o1).esTerceto() && ((Asignable) o2).esTerceto())	{								//T(op, algo, [terceto])
				if(o1.toString().substring(0,1).equals("-"))
					agregarString("ADD " + r.encontrarTerceto(((Terceto)o2).getNro(), tipo) + ", " + "_"+o1.toString().replace("-", "n"));
				else
					agregarString("ADD " + r.encontrarTerceto(((Terceto)o2).getNro(), tipo) + ", " + "_"+o1);
				r.ocuparRegistro(r.encontrarTerceto(((Terceto)o2).getNro(), tipo), t.getNro());
			}			
			else if ( !((Asignable) o1).esTerceto() && !((Asignable) o2).esTerceto() ){															//T(op, algo, algo)
				if(o1.toString().substring(0,1).equals("-"))
					agregarString("MOV " + r.getPrimerRegistroLibre(tipo) + ", " + "_"+o1.toString().replace("-", "n"));
				else
					agregarString("MOV " + r.getPrimerRegistroLibre(tipo) + ", " + "_"+o1);
				r.ocuparRegistro(r.getPrimerRegistroLibre(tipo), t.getNro());
				if(o2.toString().substring(0,1).equals("-"))
					agregarString("ADD " + r.encontrarTerceto(t.getNro(), tipo) + ", " + "_"+o2.toString().replace("-", "n"));			
				else
					agregarString("ADD " + r.encontrarTerceto(t.getNro(), tipo) + ", " + "_"+o2);		
			}
		}


	///////////////////////////////          MULTIPLICACION          ///////////////////////////////

	private void getCodMult(Terceto t){
		
		Object o1= t.getOperando1();
		Object o2= t.getOperando2();
		String tipo = ((TercetoOperacion)t).getTipo();
		
			if (tipo.equals("ulong")){
					if(((Asignable) o1).esTerceto() && !((Asignable) o2).esTerceto()) {    			
						agregarString("MOV EDX, 0");
						agregarString("MUL, " + "_"+o2);
						r.ocuparRegistro("EAX", t.getNro());
					}		
					else if ( !((Asignable) o1).esTerceto() && !((Asignable) o2).esTerceto() ){															//T(op, algo, algo)
						if (r.estaOcupado("EAX")) {											// EN CASO DE TENER OCUPADO EAX GUARDO EN ECX
							agregarString("MOV ECX, EAX");
							agregarString("MOV EAX, 0");
							r.ocuparRegistro("ECX", r.reg_EAX);
							r.ocuparRegistro("EAX", 0);
						}
						agregarString("MOV EAX, " + "_"+ o1);
						r.ocuparRegistro("EAX", t.getNro());
						agregarString("MUL " + "_"+o2);			
					}
			}
			
			else if (tipo.equals("int")){
				if	(((Asignable) o1).esTerceto() && !((Asignable) o2).esTerceto()) {         														    //T(op, [terceto], algo)
					
					if(o2.toString().substring(0,1).equals("-"))
						agregarString("MOV BX, " + "_"+ o2.toString().replace("-", "n"));
					else
						agregarString("MOV BX, " + "_"+ o2);

					agregarString("CMP AX, 0");
					agregarString("JS LABEL_OPERADOR1_Negativo");
					
					agregarString("CMP BX, 0");
					agregarString("JS LABEL_OPERADOR2_Negativo");
					
					agregarString("MOV DX, 0");									//LOS DOS OPERADORES POSITIVOS
					agregarString("MUL BX");	
					agregarString("JMP LABEL_FIN_MUL");
					
					agregarString("LABEL_OPERADOR1_Negativo:");						//OPERANDO1 NEGATIVO
					agregarString("MOV DX, -1");
					agregarString("IMUL, BX");	
					agregarString("JMP LABEL_FIN_MUL");
					
					agregarString("LABEL_OPERADOR2_Negativo:");				//OPERANDO2 NEGATIVO Y OPERANDO1 POSITIVO
					agregarString("MOV DX, 0");
					agregarString("IMUL, BX");
					
					agregarString("LABEL_FIN_MUL:");					
					
					r.ocuparRegistro("AX", t.getNro());			
					
				}	
				else if ( !((Asignable) o1).esTerceto() && !((Asignable) o2).esTerceto() ){															//T(op, algo, algo)
					
					if (r.estaOcupado("EAX")) {								// EN CASO DE TENER OCUPADO EAX GUARDO EN ECX
						agregarString("MOV ECX, EAX");
						agregarString("MOV EAX, 0");
						r.ocuparRegistro("ECX", r.reg_EAX);
						r.ocuparRegistro("EAX", 0);
					}
					
					if(o1.toString().substring(0,1).equals("-"))
						agregarString("MOV AX, " + "_"+ o1.toString().replace("-", "n"));
					else
						agregarString("MOV AX, " + "_"+ o1);
					if(o1.toString().substring(0,1).equals("-"))
						agregarString("MOV BX, " + "_"+ o2.toString().replace("-", "n"));
					else
						agregarString("MOV BX, " + "_"+ o2);
					
					agregarString("CMP AX, 0");
					agregarString("JS LABEL_OPERADOR1_Negativo");
					
					agregarString("CMP BX, 0");
					agregarString("JS LABEL_OPERADOR2_Negativo");
					
					agregarString("MOV DX, 0");								//LOS DOS OPERADORES POSITIVOS
					agregarString("MUL BX");	
					agregarString("JMP LABEL_FIN_MUL");
					
					agregarString("LABEL_OPERADOR1_Negativo:");				//OPERADOR1 NEGATIVO
					agregarString("MOV DX, -1");
					agregarString("IMUL BX");	
					agregarString("JMP LABEL_FIN_MUL");
					
					agregarString("LABEL_OPERADOR2_Negativo:");				//OPERADOR2 NEGATIVO Y OPERADOR1 POSITIVO
					agregarString("MOV DX, 0");
					agregarString("IMUL BX");
					
					agregarString("LABEL_FIN_MUL:");					
					
					r.ocuparRegistro("AX", t.getNro());			
				}
			}
			
}

	

///////////////////////////////          ASIGNACION          ///////////////////////////////	
	
	private void getCodAsig(Terceto t) {
	Object o1= t.getOperando1();
	Object o2= t.getOperando2();
	String tipo = ((TercetoOperacion)t).getTipo();
	if (!((Asignable) o1).esTerceto() && ((Asignable) o2).esTerceto())	{		
		if (o1.toString().substring(0,1).equals("-"))//T(op, algo, [terceto])
			agregarString("MOV "  + "_"+o1.toString().replace("-", "n") +", " + r.encontrarTerceto(((Terceto)o2).getNro(), tipo) );
		else
			agregarString("MOV "  + "_"+o1 +", " + r.encontrarTerceto(((Terceto)o2).getNro(), tipo) );
		r.ocuparRegistro(r.encontrarTerceto(((Terceto)o2).getNro(), tipo), 0);
	}			
	else  if ( !((Asignable) o1).esTerceto() && !((Asignable) o2).esTerceto() ){										//T(op, algo, algo)
		if (o2.toString().substring(0,1).equals("-"))	{
			agregarString("MOV " + r.getPrimerRegistroLibre(tipo) + ", " + "_"+o2.toString().replace("-", "n"));
			r.ocuparRegistro(r.getPrimerRegistroLibre(tipo), t.getNro());
			agregarString("MOV " + "_"+o1 + ", " + r.encontrarTerceto(t.getNro(), tipo) );
			r.ocuparRegistro(r.encontrarTerceto(t.getNro(), tipo), 0);	
		}
		else {
			agregarString("MOV " + r.getPrimerRegistroLibre(tipo) + ", " + "_"+o2);
			r.ocuparRegistro(r.getPrimerRegistroLibre(tipo), t.getNro());
			agregarString("MOV " + "_"+o1 + ", " + r.encontrarTerceto(t.getNro(), tipo) );
			r.ocuparRegistro(r.encontrarTerceto(t.getNro(), tipo), 0);		
		}
	}
	}
	
	///////////////////////////           BI-BF           ///////////////////////////////////
	

	private void getCodBF(Terceto t) {
		TercetoOperacion comparacion = ((TercetoOperacion)t.getOperando1());
		int val_op2 = ((Terceto) t.getOperando2()).getNro();
		if (comparacion.getTipo() == "ulong")
			agregarString(this.getSaltoUlong(comparacion.getOperador()) + "Label_" + val_op2);
		else
			agregarString(this.getSaltoInteger(comparacion.getOperador()) + "Label_" + val_op2);
	}
	
	
	private void getCodBD(Terceto t) {
		TercetoOperacion comparacion = ((TercetoOperacion)t.getOperando1());
		int val_op2 = ((Terceto) t.getOperando2()).getNro();
		if (comparacion.getTipo() == "ulong")
			agregarString(this.getSaltoUlongDO(comparacion.getOperador()) + "Label_" + val_op2);
		else
			agregarString(this.getSaltoIntegerDO(comparacion.getOperador()) + "Label_" + val_op2);
	}



	private void getCodBI(Terceto t) {
		//int val_op = ((Terceto) t.getOperando1()).getNro();
		agregarString(new String("JMP Label_" + ((Terceto) t.getOperando1()).getNro()));
	}
	
	///////////////////////////                SALTOS                   /////////////////////////////////
	
	private String getSaltoInteger(Object operador) { // VERIFICAR BIEN EN
		if (operador.equals(">")) {
			// Saltas por Menor igual
			return "JLE ";
		}
		if (operador.equals("<")) {
			// Saltas por Mayor Igual
			return "JGE ";
		}
		if (operador.equals(">=")) {
			// Saltas por Menor
			return "JL ";
		}
		if (operador.equals("<=")) {
			// Saltas por Mayor
			return "JG ";
		}
		if (operador.equals("==")) {
			// Saltas por distinto
			return "JNE ";
		}
		if (operador.equals("<>")) {
			// Saltas por igual
			return "JE ";
		}
		return null;
	}

	private String getSaltoIntegerDO(Object operador) { // VERIFICAR BIEN EN
		if (operador.equals(">")) {
			// Saltas por Menor igual
			return "JG ";
		}
		if (operador.equals("<")) {
			// Saltas por Mayor Igual
			return "JL ";
		}
		if (operador.equals(">=")) {
			// Saltas por Menor
			return "JGE ";
		}
		if (operador.equals("<=")) {
			// Saltas por Mayor
			return "JLE ";
		}
		if (operador.equals("==")) {
			// Saltas por distinto
			return "JE ";
		}
		if (operador.equals("<>")) {
			// Saltas por igual
			return "JNE ";
		}
		return null;
	}
	private String getSaltoUlong(Object operador) { 
		if (operador.equals(">")) {
			// Saltas por Menor igual op1 <= op2
			return "JBE ";
		}
		if (operador.equals("<")) {
			// Saltas por Mayor Igual op1 >= op2
			return "JNAE ";
		}
		if (operador.equals(">=")) {
			// Saltas por Menor op1 < op2
			return "JB ";
		}
		if (operador.equals("<=")) {
			// Saltas por Mayor op1 > op2
			return "JA ";
		}
		if (operador.equals("==")) {
			// Saltas por distinto op1 != op2
			return "JNE ";
		}
		if (operador.equals("<>")) {
			// Saltas por igual
			return "JE ";
		}
		return null;
	}
	
	private String getSaltoUlongDO(Object operador) { 
		if (operador.equals(">")) {
			// Saltas por Menor igual op1 <= op2
			return "JA ";
		}
		if (operador.equals("<")) {
			// Saltas por Mayor Igual op1 >= op2
			return "JB ";
		}
		if (operador.equals(">=")) {
			// Saltas por Menor op1 < op2
			return "JNAE ";
		}
		if (operador.equals("<=")) {
			// Saltas por Mayor op1 > op2
			return "JBE ";
		}
		if (operador.equals("==")) {
			// Saltas por distinto op1 != op2
			return "JE ";
		}
		if (operador.equals("<>")) {
			// Saltas por igual
			return "JNE ";
		}
		return null;
	}

	///////////////////////////                         PRINT         VER            //////////////////////////
	private void getCodPrint(Terceto t) {
		if(!InMetodo) {
		agregarString("invoke MessageBox, NULL, addr " +  ((String)t.getOperando1()).replace(" ", "_") + ", addr "
				+ ((String)t.getOperando1()).replace(" ", "_") + ", MB_OK");}
		else {
		Metodos.add("invoke MessageBox, NULL, addr " +  ((String)t.getOperando1()).replace(" ", "_") + ", addr "
					+ ((String)t.getOperando1()).replace(" ", "_") + ", MB_OK");
		}
	}
	
	//////////////////////////                   LABEL                   /////////////////////////////
	
	private void getCodLabel(Terceto t) 
	{
		agregarString("Label_" + t.getNro()+":");
	}
	
//////////////////////////					COMP                 /////////////////////////////
	private void getCodComp(Terceto t) 
	{
	Object o1= t.getOperando1();
	Object o2= t.getOperando2();
	String tipo = ((TercetoOperacion)t).getTipo();
	
	
		if(((Asignable) o1).esTerceto() && !((Asignable) o2).esTerceto()) {										//T(op, [terceto], algo)
			if (o2.toString().substring(0,1).equals("-"))
				agregarString("CMP " + r.encontrarTerceto(((Terceto)o1).getNro(), tipo) + ", " + "_"+o2.toString().replace("-", "n"));
			else
				agregarString("CMP " + r.encontrarTerceto(((Terceto)o1).getNro(), tipo) + ", " + "_"+o2);
			r.ocuparRegistro(r.encontrarTerceto(((Terceto)o1).getNro(), tipo), 0);
			
		}
		else if(((Asignable) o1).esTerceto() && ((Asignable) o2).esTerceto())  {    															//T(op, [terceto], [terceto])
			agregarString("CMP " +  r.encontrarTerceto(((Terceto)o1).getNro(), tipo) + ", " +  r.encontrarTerceto(((Terceto)o2).getNro(), tipo));
			r.ocuparRegistro(r.encontrarTerceto(((Terceto)o2).getNro(), tipo), 0);
			r.ocuparRegistro(r.encontrarTerceto(((Terceto)o1).getNro(), tipo), 0);			
		}
		else if (!((Asignable) o1).esTerceto() && ((Asignable) o2).esTerceto())	{																//T(op, algo, [terceto])
			if (o1.toString().substring(0,1).equals("-"))
				agregarString("CMP "  + r.encontrarTerceto(((Terceto)o2).getNro(), tipo) + ", " + "_"+o1.toString().replace("-", "n"));
			else
				agregarString("CMP "  + r.encontrarTerceto(((Terceto)o2).getNro(), tipo) + ", " + "_"+o1);
			r.ocuparRegistro(r.encontrarTerceto(((Terceto)o2).getNro(), tipo), 0);
		}			
		else if ( !((Asignable) o1).esTerceto() && !((Asignable) o2).esTerceto() ){															//T(op, algo, algo)
			if(o1.toString().substring(0,1).equals("-"))
				agregarString("MOV " + r.getPrimerRegistroLibre(tipo) + ", " + "_"+o1);
			else
				agregarString("MOV " + r.getPrimerRegistroLibre(tipo) + ", " + "_"+o1.toString().replace("-", "n"));
			r.ocuparRegistro(r.getPrimerRegistroLibre(tipo), t.getNro());
			if(o1.toString().substring(0,1).equals("-"))
				agregarString("CMP " + r.encontrarTerceto(t.getNro(), tipo) + ", " + "_"+o2);
			else
				agregarString("CMP " + r.encontrarTerceto(t.getNro(), tipo) + ", " + "_"+o2.toString().replace("-", "n"));
			r.ocuparRegistro(r.encontrarTerceto(t.getNro(), tipo), 0);
		}
	}
	
///////////////////////////////          SITUACION 4.B         ///////////////////////////////
	
///////////////////////////////          RESTA          ///////////////////////////////	
	
private void getCodResta(Terceto t) {
		

		Object o1= t.getOperando1();
		Object o2= t.getOperando2();
		String tipo = ((TercetoOperacion)t).getTipo();
		
		
			if(((Asignable) o1).esTerceto() && !((Asignable) o2).esTerceto()) {         														    //T(op, [terceto], algo)
				if(o2.toString().substring(0,1).equals("-"))	
					agregarString("SUB " + r.encontrarTerceto(((Terceto)o1).getNro(), tipo) + ", " + "_"+o2.toString().replace("-", "n"));
				else
					agregarString("SUB " + r.encontrarTerceto(((Terceto)o1).getNro(), tipo) + ", " + "_"+o2);
				r.ocuparRegistro(r.encontrarTerceto(((Terceto)o1).getNro(), tipo), t.getNro());
				
			}
			else if(((Asignable) o1).esTerceto() && ((Asignable) o2).esTerceto())  {    															//T(op, [terceto], [terceto])
				agregarString("SUB " +  r.encontrarTerceto(((Terceto)o1).getNro(), tipo) + ", " +  r.encontrarTerceto(((Terceto)o2).getNro(), tipo));
				r.ocuparRegistro(r.encontrarTerceto(((Terceto)o2).getNro(), tipo), 0);
				r.ocuparRegistro(r.encontrarTerceto(((Terceto)o1).getNro(), tipo), t.getNro());			
			}
			else if (!((Asignable) o1).esTerceto() && ((Asignable) o2).esTerceto())	{																//T(op, algo, [terceto])
				if(o1.toString().substring(0,1).equals("-"))	
					agregarString("SUB "  + r.encontrarTerceto(((Terceto)o2).getNro(), tipo) + ", " + "_"+o1.toString().replace("-", "n"));
				else
					agregarString("SUB "  + r.encontrarTerceto(((Terceto)o2).getNro(), tipo) + ", " + "_"+o1);
				r.ocuparRegistro(r.encontrarTerceto(((Terceto)o2).getNro(), tipo), t.getNro());
			}			
			else if ( !((Asignable) o1).esTerceto() && !((Asignable) o2).esTerceto() ){															//T(op, algo, algo)
				if(o1.toString().substring(0,1).equals("-"))
					agregarString("MOV " + r.getPrimerRegistroLibre(tipo) + ", " + "_"+o1.toString().replace("-", "n"));
				else
					agregarString("MOV " + r.getPrimerRegistroLibre(tipo) + ", " + "_"+o1);
				r.ocuparRegistro(r.getPrimerRegistroLibre(tipo), t.getNro());
				if(o2.toString().substring(0,1).equals("-"))
					agregarString("SUB " + r.encontrarTerceto(t.getNro(), tipo) + ", " + "_"+o2.toString().replace("-", "n"));
				else
					agregarString("SUB " + r.encontrarTerceto(t.getNro(), tipo) + ", " + "_"+o2);			
			}
		
	}

	
	
///////////////////////////////          DIVISION          ///////////////////////////////	

	private void getCodDiv(Terceto t) {
		
		Object o1= t.getOperando1();
		Object o2= t.getOperando2();
		String tipo = ((TercetoOperacion)t).getTipo();
		
			if (tipo.equals("ulong")){
					if(((Asignable) o1).esTerceto() && !((Asignable) o2).esTerceto()) {         														    //T(op, [terceto], algo)
						agregarString("CMP " +  "_"+o2 +  ", " + 0);																						   
						agregarString("JE LABEL_Division_Cero");						
						agregarString("MOV EDX, 0");
						agregarString("DIV, " + "_"+o2 );
						r.ocuparRegistro("EAX", t.getNro());
					}		
					else if ( !((Asignable) o1).esTerceto() && !((Asignable) o2).esTerceto() ){															//T(op, algo, algo)
						agregarString("CMP " +  "_"+o2 +  ", " + 0);																						   
						agregarString("JE LABEL_Division_Cero");				
						if (r.estaOcupado("EAX")) {											// EN CASO DE TENER OCUPADO EAX GUARDO EN ECX
							agregarString("MOV ECX, EAX");
							agregarString("MOV EAX, 0");
							r.ocuparRegistro("ECX", r.reg_EAX);
							r.ocuparRegistro("EAX", 0);
						}
						agregarString("MOV EAX, " + "_"+o1);
						r.ocuparRegistro("EAX", t.getNro());
						agregarString("DIV " + "_"+o2);			
					}
			}
			
			else if (tipo.equals("int")){
				if(((Asignable) o1).esTerceto() && !((Asignable) o2).esTerceto()) {         														    //T(op, [terceto], algo)
					if(o2.toString().substring(0,1).equals("-")) {
						agregarString("CMP " +  "_"+o2.toString().replace("-","n") +  ", " + 0);																						   
						agregarString("JE LABEL_Division_Cero");
						agregarString("MOV BX, " + "_"+ o2.toString().replace("-","n"));
					}
					else {
						agregarString("CMP " +  "_"+o2 +  ", " + 0);																						   
						agregarString("JE LABEL_Division_Cero");
						agregarString("MOV BX, " + "_"+ o2);
					}
					agregarString("CMP AX, 0");
					agregarString("JS LABEL_Dividendo_Negativo");
					
					agregarString("CMP BX, 0");
					agregarString("JS LABEL_Divisor_Negativo");
					
					agregarString("MOV DX, 0");								//LOS DOS OPERADORES POSITIVOS
					agregarString("DIV BX");	
					agregarString("JMP LABEL_FIN_DIV");
					
					agregarString("LABEL_Dividendo_Negativo:");				//DIVIDENDO NEGATIVO
					agregarString("MOV DX, -1");
					agregarString("IDIV, BX");	
					agregarString("JMP LABEL_FIN_DIV");
					
					agregarString("LABEL_Divisor_Negativo:");				//DIVISOR NEGATIVO Y DIVIDENDO POSITIVO
					agregarString("MOV DX, 0");
					agregarString("IDIV, BX");
					
					agregarString("LABEL_FIN_DIV:");					
					
					r.ocuparRegistro("AX", t.getNro());			
					
				}	
				else if ( !((Asignable) o1).esTerceto() && !((Asignable) o2).esTerceto() ){															//T(op, algo, algo)
					if(o2.toString().substring(0,1).equals("-")) 
						agregarString("CMP " +  "_"+o2.toString().replace("-", "n") +  ", " + 0);
					else
						agregarString("CMP " +  "_"+o2 +  ", " + 0);
					agregarString("JE LABEL_Division_Cero");
					
					if (r.estaOcupado("EAX")) {								// EN CASO DE TENER OCUPADO EAX GUARDO EN ECX
						agregarString("MOV ECX, EAX");
						agregarString("MOV EAX, 0");
						r.ocuparRegistro("ECX", r.reg_EAX);
						r.ocuparRegistro("EAX", 0);
					}
					
					if(o1.toString().substring(0,1).equals("-")) 
						agregarString("MOV AX, " + "_"+ o1.toString().replace("-", "n"));
					else
						agregarString("MOV AX, " + "_"+ o1);
					if(o2.toString().substring(0,1).equals("-")) 
						agregarString("MOV BX, " + "_"+ o2.toString().replace("-", "n"));
					else
						agregarString("MOV BX, " + "_"+ o2);
					agregarString("CMP AX, 0");
					agregarString("JS LABEL_Dividendo_Negativo");
					
					agregarString("CMP BX, 0");
					agregarString("JS LABEL_Divisor_Negativo");
					
					agregarString("MOV DX, 0");								//LOS DOS OPERADORES POSITIVOS
					agregarString("DIV BX");	
					agregarString("JMP LABEL_FIN_DIV");
					
					agregarString("LABEL_Dividendo_Negativo:");				//DIVIDENDO NEGATIVO
					agregarString("MOV DX, -1");
					agregarString("IDIV BX");	
					agregarString("JMP LABEL_FIN_DIV");
					
					agregarString("LABEL_Divisor_Negativo:");				//DIVISOR NEGATIVO Y DIVIDENDO POSITIVO
					agregarString("MOV DX, 0");
					agregarString("IDIV BX");
					
					agregarString("LABEL_FIN_DIV:");					
					
					r.ocuparRegistro("AX", t.getNro());			
				}
			}
			
}
	
	

	
private void setLabelDivisionCero(){
		agregarString ("LABEL_Division_Cero:");
		agregarString("invoke MessageBox, NULL, addr msjDivision_Cero, addr msjDivision_Cero, MB_OK");
		agregarString("JMP LABEL_END");
}

private void setLabelPerdidaInfo(){
	agregarString ("LABEL_Perdida_de_Info:");
	agregarString("invoke MessageBox, NULL, addr msjPerdida_Info, addr msjPerdida_Info, MB_OK");
	agregarString("JMP LABEL_END");
}	


///////////////////////////////         CONVERSIÓN EXPLICITA      ///////////////////////////////	


private void getCodConversion(Terceto t) {
	Object o1 = t.getOperando1();
	String tipo = ((TercetoOperacion)t).getTipo();
	
	if(((Asignable)o1).esTerceto()) {
		agregarString("CMP "+ "_"+ o1 + ", "+0);
		agregarString("JS LABEL_Perdida_de_Info");
		agregarString("MOV ECX, 0");
		agregarString("MOV CX, "+ r.encontrarTerceto(((TercetoOperacion)o1).getNro(), "int"));
		agregarString("MOV EBX, ECX");
		r.ocuparRegistro("EBX", t.getNro());
	}
	else {
		if(o1.toString().substring(0,1).equals("-"))
			agregarString("CMP "+ "_"+ o1.toString().replace("-", "n") + ", "+0);
		else
			agregarString("CMP "+ "_"+ o1 + ", "+0);
		agregarString("JS LABEL_Perdida_de_Info");	
		agregarString("MOV ECX, 0");
		if(o1.toString().substring(0,1).equals("-"))
			agregarString("MOV CX, "+ "_" + o1.toString().replace("-", "n"));
		else
			agregarString("MOV CX, "+ "_" + o1);
		agregarString("MOV EBX, ECX");
		r.ocuparRegistro("EBX", t.getNro());	
	}
}

///////////////////////              RETURN                ////////////////////////////
 
private void getCodRet(Terceto t) {
	InMetodo= false;
	Metodos.add("RET");
}

/////////////////////            DECLARACION  METODO               //////////////////////////////

private void getCodFunction(Terceto t) {
	InMetodo = true;
	Object o1 = t.getOperando1();
	Metodos.add("FUNCTION_"+ o1.toString()+ ":");
}

/////////////////////           EJECUCION  METODO               //////////////////////////////

private void getCodCall(Terceto t) {
	Object metodo = t.getOperando1();
	ArrayList<Simbolo> obj_attr = a.getObjeto_Atributo();	
	String ambiente = a.TablaSimbolo.get(metodo).getAmbiente();

	Hashtable<String, String> aux = new Hashtable<String, String>();
	
	for (Simbolo oa : obj_attr)
	{

		String attr = "";
		boolean AlbertoPresidente = false;
		for (int i = 0; i< (oa.getValor()).length(); i++) {			
			if (AlbertoPresidente) {
				attr += oa.getValor().charAt(i);
			}
			if(oa.getValor().charAt(i) == '_')
				AlbertoPresidente = true;
		}

		ArrayList<Simbolo> atributos = a.getAllAtributos();
		for (Simbolo q : atributos) System.out.println(q);
		Simbolo at = a.TablaSimbolo.get(attr);
		if (atributos.contains( a.TablaSimbolo.get(attr))) 
		{
			
			agregarString("MOV " + r.getPrimerRegistroLibre(oa.getTipo()) + ", " + "_"+oa.toString());
			agregarString("MOV " + "_"+at.toString() +  ", " + r.getPrimerRegistroLibre(oa.getTipo()));
			aux.put(oa.toString(), at.toString());
		}
		
	}	
	agregarString("CALL FUNCTION_"+ metodo.toString());
	for (String s : aux.keySet()) {
		agregarString("MOV " + r.getPrimerRegistroLibre(a.TablaSimbolo.get(aux.get(s)).getTipo()) + ", " + "_"+aux.get(s).toString());
		agregarString("MOV " + "_"+s.toString() +  ", " + r.getPrimerRegistroLibre(a.TablaSimbolo.get(s).getTipo()));
	}
}

}
