package Compilador.Tercetos;

public class Operando {
	public String op;
	int numero;
	
	public Operando (String op) {
		this.op = op;
	}
	public void setNumero(Integer i) {
		numero = i;
	}
	public int getNumero() {
		return numero;
	}
}
