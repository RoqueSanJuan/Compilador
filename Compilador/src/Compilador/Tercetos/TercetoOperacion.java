package Compilador.Tercetos;

import java.util.Hashtable;

import Compilador.Simbolo;

public class TercetoOperacion extends Terceto implements Asignable {

	protected String tipo;
	protected String varAux;

	public TercetoOperacion(int nro, String operador, Object operando1, Object operando2, Hashtable<String,Simbolo> ts) {
		super(nro, operador, operando1, operando2, ts);
		this.setNombre(operador);
		this.varAux = null;
		this.tipo = ((Asignable)operando1).getTipo();
	}

	private void setNombre(String operador) {
		if (operador == "+")
			nombre = "Suma";
		if (operador == "-")
			nombre = "Resta";
		if (operador == "/")
			nombre = "Division";
		if (operador == "*")
			nombre = "Multiplicacion";
		if (operador.equals(":="))
			nombre = "Asignacion";
		if ((operador == ">") || (operador == ">=") || (operador == "<") || (operador == "<=") || (operador == "=="))
			nombre = "Comparacion";
		if (operador == "TO_ULONG")
			nombre = "TO_ULONG";
	}

	@Override
	public String getTipo() {
		return tipo;
	}

	@Override
	public void setTipo(String t) {
		this.tipo = t;
	}


	public String getVarAux() {
		return varAux;
	}
	
	
	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public String getLexema() {
		return Integer.toString(super.getNro());
	}

}
