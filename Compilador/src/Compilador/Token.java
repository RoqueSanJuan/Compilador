package Compilador;

public class Token {
		
	int token;
	int linea;
	static String lexema = "";
	
	public Token ()
	{
	}
	
	public Token (int t, int l)
	{
		this.token = t;
		this.linea = l;
	}

	public int getToken() {
		return token;
	}

	public void setLexema(String lexema) {
		Token.lexema = lexema;
	}
	
	public void setToken(int token) {
		this.token = token;
	}

	public int getLinea() {
		return linea;
	}

	public void setLinea(int linea) {
		this.linea = linea;
	}
	
	@Override
	public String toString() {
		String a;
		if (Token.lexema == "") 
			a =  "[Token: " + this.token +  "]";
		
		else
			a = "[Token: " + this.token + " Lexema: " + Token.lexema + "]";
		return a;
	}

	public String getLexema() {
		// TODO Auto-generated method stub
		return Token.lexema ;
	}

}


