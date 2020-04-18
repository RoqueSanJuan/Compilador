package Compilador.Tercetos;

import java.util.Hashtable;

import Compilador.Simbolo;

public class Terceto{
	protected Object operando1;
	protected Object operando2;
	protected String operador;
	protected int nro;
	protected Hashtable<String,Simbolo> ts;
	protected String nombre;

	public Terceto(int nro, String operador, Object operando1, Object operando2, Hashtable<String,Simbolo> ts) {
		this.operando1 = operando1;
		this.operando2 = operando2;
		this.operador = operador;
		this.nro = nro;
		this.ts = ts;
		this.setNombre(operador);
	}

	private void setNombre(String operador) {
		if (operador == "BF")
			nombre = "BF";
		if (operador == "BD")
			nombre = "BD";
		else if (operador == "BI")
			nombre = "BI";
		else if (operador == "FIN")
			nombre = "FIN";
		else if (operador == "PRINT")
			nombre = "PRINT";
		else if (operador == ":=")
			nombre = "Asignacion";
		else if (operador == "FINIF")
			nombre = "FINIF";
		else if (operador == "LABEL")
			nombre = "Label";
		else if (operador == "INIDO")
			nombre = "INIDO";
		else if (operador == "FIN_DO")
			nombre = "FINDO";
		else if (operador == "FUNCTION")
			nombre = "FUNCTION";
		else if (operador == "CALL")
			nombre = "CALL";
		else if (operador == "RET")
			nombre = "RET";
	}

	public void imprimirTerceto() {
		System.out.println( nro  + " ( " + operador + "," + operando1 + "," + operando2 + " ) ");
	}

	@Override
	public String toString() {
		return "[" + nro + "]";
	}

	public String imprimir() {
		return "[" + nro + "]" + " :(" + operador + ", " + operando1.toString() + ", " + operando2.toString() + ")";
	}

	public boolean equals(Terceto t) {
		if (t.getOperador().equals(operador))
			return true;
		else
			return false;
	}
	
	public boolean esTerceto() {
		return true;
	}

	public String getNombre() {
		return nombre;
	}


	public int getNro() {
		return nro;
	}

	public void setNro(int nro) {
		this.nro = nro;
	}

	public Object getOperando2() {
		return operando2;
	}

	public void setOperando2(Object operando2) {
		this.operando2 = operando2;
	}

	public Object getOperando1() {
		return operando1;
	}

	public void setOperando1(Object operando1) {
		this.operando1 = operando1;
	}

	public Object getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}
}
