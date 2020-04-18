package Compilador.AccionesSemanticas;

public abstract class AccionSemantica {
	protected static int counter = 0;
	

	public abstract int execute (StringBuffer buffer, char c);
	
	
	
	public abstract boolean acomodarLinea();
}
