package Compilador.AccionesSemanticas;

public class AS_AgregarBuffer extends AccionSemantica {
	
	//agrega el carácter a los que ya tiene, porque todavía no se ha identificado un token.

	@Override
	public int execute(StringBuffer buffer, char c) {
			buffer.append(c);
		return 0;
	}
	
	@Override
	public boolean acomodarLinea(){
		return false;
	}
	

	
	
}
