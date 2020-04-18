package Compilador.AccionesSemanticas;

import java.util.HashMap;
import java.util.Hashtable;

import Compilador.Simbolo;
import Compilador.AccionesSemanticas.AccionSemantica;

public class AS_Fin_ID_PR extends AccionSemantica {

	Hashtable<String,Simbolo> TablaSimbolo;
		HashMap<String,Integer> TablaToken; 
		Simbolo s;		
		
		public AS_Fin_ID_PR(Hashtable<String,Simbolo> TablaSimbolo, HashMap<String,Integer> TablaToken){
			this.TablaToken = TablaToken;
			this.TablaSimbolo = TablaSimbolo;			
		}	
	
	@Override
	public int execute(StringBuffer buffer, char c) {
		
		if (TablaToken.containsKey(buffer.toString())) {  								// Es Palabra Reservada?
			return TablaToken.get(buffer.toString());
		}else 
		{
			
			if(buffer.length() < 25){
				 s = new Simbolo(buffer.toString());			
				if(TablaSimbolo.contains(s))
				{
					return TablaToken.get("ID");
				}
				else
				{
					TablaSimbolo.put(s.getValor(),s);
					return TablaToken.get("ID");
				}
			}
			else {
				s = new Simbolo(buffer.substring(0,24));
				buffer.setLength(25);
				TablaSimbolo.put(s.getValor(),s);
				return -3; 														// ID FUERA DE RANGO
			}
		}
		
	}
		

	
	public Simbolo getSimbolo() {
		return this.s;
	}

	@Override
	public boolean acomodarLinea() {
		return true;
	}

}
