package Compilador.AccionesSemanticas;

import java.util.HashMap;

public class AS_FinSimboloCompuesto extends AccionSemantica {

	HashMap<String,Integer> TablaToken; 
	
	/*DESCRIPCION
	 *identifica que el token es un s�mbolo compuesto devolvi�ndolo. Estos son los s�mbolos que involucran m�s de un car�cter. */
	
	public AS_FinSimboloCompuesto(HashMap<String,Integer> TablaToken){
		this.TablaToken = TablaToken;			
	}
	
	@Override
	public int execute(StringBuffer buffer, char c) {
		buffer.append(c);
		return TablaToken.get(buffer.toString());
	}

	@Override
	public boolean acomodarLinea() {
		return false;
	}
	
}
