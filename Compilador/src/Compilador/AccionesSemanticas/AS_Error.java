package Compilador.AccionesSemanticas;

public class AS_Error extends AccionSemantica {

	@Override
	public int execute(StringBuffer buffer, char c) {
		return -2;
	}

	@Override
	public boolean acomodarLinea() {
		return false;
	}

}
