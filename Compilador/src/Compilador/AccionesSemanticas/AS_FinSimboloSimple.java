package Compilador.AccionesSemanticas;

public class AS_FinSimboloSimple extends AccionSemantica {
	
	/* DESCRIPCION 
	 * Estos son un caso especial de los s�mbolos de un solo car�cter, que se da al reconocer < o >,
	   ya que se lee el siguiente car�cter, porque se podr�a dar el caso de que sean <= o >=*/

	@Override
	public int execute(StringBuffer buffer, char c) {
		
		return buffer.charAt(0);
		
	}

	@Override
	public boolean acomodarLinea() {
		return true;
	}

}
