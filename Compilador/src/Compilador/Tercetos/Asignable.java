package Compilador.Tercetos;

public interface Asignable {
	public abstract String getTipo();
	
	public abstract void setTipo(String tipo);

	public abstract String getLexema();
	
	public boolean esTerceto();
}
