package Compilador.AccionesSemanticas;

import Compilador.Analizador;

public class AS_FinComentario extends AccionSemantica {

	@Override
	public int execute(StringBuffer buffer, char c) {
		buffer = new StringBuffer();
		Analizador.limpiarBuffer();
		return 0;
	}

	@Override
	public boolean acomodarLinea() {
		return false;
	}

}
