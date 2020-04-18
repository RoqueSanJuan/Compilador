package Compilador.AccionesSemanticas;

public class AS_NoAction extends AccionSemantica {
	
	//no hace nada
	

	@Override
	public int execute(StringBuffer buffer, char c) {
		return 0;
	}
	
	@Override
	public boolean acomodarLinea(){
		return false;
	}
	

}
