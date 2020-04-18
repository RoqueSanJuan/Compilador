package Compilador;



public class Registros{
	
	
	public int reg_EAX = 0;
	public int reg_EBX = 0;
	public int reg_ECX = 0;
	public int reg_EDX = 0;
	
	
public Registros () {
	
}

public String encontrarTerceto(int i, String tipo) {
	
	if (reg_EAX == i && tipo.contentEquals("ulong"))
		return "EAX";
	if (reg_EBX == i && tipo.contentEquals("ulong"))
		return "EBX";
	if (reg_ECX == i && tipo.contentEquals("ulong"))
		return "ECX";
	if (reg_EDX == i && tipo.contentEquals("ulong"))
		return "EDX";
	
	if (reg_EAX == i && tipo.contentEquals("int"))
		return "AX";
	if (reg_EBX == i && tipo.contentEquals("int"))
		return "BX";
	if (reg_ECX == i && tipo.contentEquals("int"))
		return "CX";
	if (reg_EDX == i && tipo.contentEquals("int"))
		return "DX";
	return "";
}

public void ocuparRegistro(String registro, int terceto) {
	if (registro == "EAX" || registro == "AX" ) {
		reg_EAX = terceto;
	}
	if (registro == "EBX" || registro == "BX" ) {
		reg_EBX = terceto;
	}
	if (registro == "ECX" || registro == "CX") {
		reg_ECX = terceto;
	}
	if (registro == "EDX" || registro == "DX") {
		reg_EDX = terceto;
	}
}
public String getPrimerRegistroLibre(String tipo) {
	if (tipo.equals("ulong")) {
		if (reg_EAX == 0)
			return "EAX";
		if (reg_EBX == 0)
			return "EBX";
		if (reg_ECX == 0)
			return "ECX";
		if (reg_EDX == 0)
			return "EDX";		
		
	}else if (tipo.equals("int")) {			
		if (reg_EAX == 0)
			return "AX";		
		if (reg_EBX == 0)
			return "BX";
		if (reg_ECX == 0)
			return "CX";
		if (reg_EDX == 0)
			return "DX";	
	}
	return "";
	
}

public boolean estaOcupado(String reg) {
	switch (reg) {
		case ("EAX"):
			if (reg_EAX == 0) return false;
			break;
		case ("EBX"):
			if (reg_EBX == 0) return false;
			break;
		case ("ECX"):
			if (reg_EAX == 0) return false;
			break;
		case ("EDX"):
			if (reg_EAX == 0) return false;
			break;
			
		case ("AX"):
			if (reg_EAX == 0) return false;
			break;
		case ("BX"):
			if (reg_EBX == 0) return false;
			break;
		case ("CX"):
			if (reg_EAX == 0) return false;
			break;
		case ("DX"):
			if (reg_EAX == 0) return false;
			break;
		
	
	}
	return true;
}

}