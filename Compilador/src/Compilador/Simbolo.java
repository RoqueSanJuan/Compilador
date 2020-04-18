package Compilador;

import java.util.ArrayList;

import Compilador.Tercetos.Asignable;

public class Simbolo implements Asignable{
	public String valor;       //lexema
	public String tipo = "";   // int - ulong - null
	public int cant_ref; 
	private String uso ="";        //que es : clase - metodo - variable
	private String ambiente ="GLOBAL";   //clase - global
	private boolean declarado = false;
	private String estaDeclarado = "NO";
	private ArrayList<String> herencias = new ArrayList<String>();

	public Simbolo(String valor) {
	this.valor = valor;
	this.cant_ref = 1;
	}

	public Simbolo(String valor, String tipo) {
	this.valor = valor;
	this.tipo = tipo;
	}


	public String getValor() {
	return valor;
	}

	public void setValor(String valor) {
	this.valor = valor;
	}

	@Override
	public String getTipo() {
	return tipo;
	}

	@Override
	public void setTipo(String tipo) {
	this.tipo = tipo;
	}

	public String getUso() {
	return uso;
	}

	public void setUso(String uso) {
	this.uso = uso;
	}


	public String getAmbiente() {
	return ambiente;
	}

	public void setAmbiente(String ambiente) {
	this.ambiente = ambiente;
	}


	public int get_Ref() {
	return this.cant_ref;
	}

	public void increase_Ref() {
	this.cant_ref++;
	}

	public void decrease_Ref() {
	this.cant_ref--;
	}

	public void setDeclarado() {
	declarado = true;
	estaDeclarado = "SI";
	}

	public boolean estaDeclarado() {
	return this.declarado;
	}

	@Override
	public boolean equals(Object o) {
	Simbolo s = (Simbolo) o;
	if (this.valor.equals(s.getValor())  /*&& this.ambiente.equals(s.getAmbiente()) && this.tipo.equals(s.getTipo()) && this.uso.equals(s.getUso())*/) {  
	return true;
	}
	return false;
	}

	@SuppressWarnings("unchecked")
	public void setHerencias(ArrayList<String> herencias) {
	this.herencias = (ArrayList<String>) herencias.clone();
	}

	public ArrayList<String> getHerencias(){
	return this.herencias;
	}

	@Override
	public boolean esTerceto() {
		return false;
	}

	public String imprimir() {
		String format = "|%1$-15s|%2$-15s|%3$-25s|%4$-35s|%5$-15s|\n";
		   String output = String.format(format, "Valor: "+ this.valor, "Tipo: "+ this.tipo,  "Uso: " + this.uso,  "Ambiente: " +this.ambiente, "Declarado: " + this.estaDeclarado);
		  return output; //" Valor: "+ this.valor + " Tipo: "+ this.tipo + " Uso: " + this.uso +" Ambiente: " +this.ambiente;
	}
	@Override
	public String toString() {
	   String output = this.valor ;
	   return output;//" Valor: "+ this.valor + " Tipo: "+ this.tipo + " Uso: " + this.uso +" Ambiente: " +this.ambiente;
	}

	@Override
	public String getLexema() {
		// TODO Auto-generated method stub
		return null;
	}
}