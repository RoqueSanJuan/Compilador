package Compilador.AccionesSemanticas;

public class AS_FinSimbolo extends AccionSemantica {

	
	// identifica que el token es un s�mbolo devolvi�ndolo. Estos son los s�mbolos que involucran un solo car�cter.
	
	@Override
	
	
	
	public int execute(StringBuffer buffer, char c) {
		buffer = new StringBuffer();				
		return c;
	}
	
	@Override
	public boolean acomodarLinea(){
		return false;
	}

	
}
