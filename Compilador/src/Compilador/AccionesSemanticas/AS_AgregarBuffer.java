package Compilador.AccionesSemanticas;

public class AS_AgregarBuffer extends AccionSemantica {
	
	//agrega el car�cter a los que ya tiene, porque todav�a no se ha identificado un token.

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
