package Compilador.AccionesSemanticas;

import java.util.HashMap;

public class AS_FinSimboloCompuesto extends AccionSemantica {

	HashMap<String,Integer> TablaToken; 
	
	/*DESCRIPCION
	 *identifica que el token es un símbolo compuesto devolviéndolo. Estos son los símbolos que involucran más de un carácter. */
	
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
