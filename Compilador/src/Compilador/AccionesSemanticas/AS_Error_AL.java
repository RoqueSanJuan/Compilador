package Compilador.AccionesSemanticas;

public class AS_Error_AL extends AccionSemantica {

	@Override
	public int execute(StringBuffer buffer, char c) {
			buffer = new StringBuffer();
		return -2;
	}

	@Override
	public boolean acomodarLinea() {
		// TODO Auto-generated method stub
		return true;
	}

}
