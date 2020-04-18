package Compilador.AccionesSemanticas;

public class AS_FinSimboloSimple extends AccionSemantica {
	
	/* DESCRIPCION 
	 * Estos son un caso especial de los símbolos de un solo carácter, que se da al reconocer < o >,
	   ya que se lee el siguiente carácter, porque se podría dar el caso de que sean <= o >=*/

	@Override
	public int execute(StringBuffer buffer, char c) {
		
		return buffer.charAt(0);
		
	}

	@Override
	public boolean acomodarLinea() {
		return true;
	}

}
