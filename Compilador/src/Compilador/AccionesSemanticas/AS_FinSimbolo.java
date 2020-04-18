package Compilador.AccionesSemanticas;

public class AS_FinSimbolo extends AccionSemantica {

	
	// identifica que el token es un símbolo devolviéndolo. Estos son los símbolos que involucran un solo carácter.
	
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
