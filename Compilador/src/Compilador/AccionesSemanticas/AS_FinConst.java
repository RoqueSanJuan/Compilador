package Compilador.AccionesSemanticas;

import java.util.HashMap;
import java.util.Hashtable;

import Compilador.Simbolo;

public class AS_FinConst extends AccionSemantica {
	Hashtable<String,Simbolo> TablaSimbolo;
	HashMap<String,Integer> TablaToken;  
	Simbolo s;

	long rangoMenor =  -32768;
	long rangoMayorInt = 32767;
	long rangoMayorUlong = 4294967295L;
	
	
	public AS_FinConst(Hashtable<String,Simbolo> TablaSimbolo, HashMap<String,Integer> TablaToken){
		this.TablaToken = TablaToken;
		this.TablaSimbolo = TablaSimbolo;			
	}
	
	@Override
	public int execute(StringBuffer buffer, char c) {
		this.s = new Simbolo(buffer.toString());
		this.s.setUso("CTE");
		long Const = Long.parseLong(buffer.toString());
		if((Const>=rangoMenor) && (Const<=rangoMayorInt)){ 	//SI ESTA EN RANGO DE INT
			s.setTipo("int");
			if(TablaSimbolo.contains(this.s) ){  			//SI ESTA EN LA TABLA
				TablaSimbolo.get(s.getValor()).increase_Ref();
				
				return TablaToken.get("CTE");
			}
			else{                                			// SI NO ESTA EN LA TABLA
				TablaSimbolo.put(s.getValor(),s);
				return TablaToken.get("CTE");
			}
		}
		else{                                    // SI NO ESTA EN RANGO
			if(Const<rangoMenor){                // SI ES MENOR AL MENOR VALOR POSIBLE 
					return -4;						// -4 ES WARNING DE PASADO DE RANGO
				}
			}
			if(Const>rangoMayorInt){                      //SI ES MAYOR AL MAXIMO RANGO
				if(Const<rangoMayorUlong) {
				s.setTipo("ulong");
				}
				if(TablaSimbolo.contains(s) ){          //SI LO TIENE
					return TablaToken.get("CTE");	// 
				}
				else{                                         // SI NO LO TIENE LO AGREGA
					TablaSimbolo.put(s.getValor(),s);
					return  TablaToken.get("CTE"); // 
				}
			}
			else {
				return -4 ;  // -4 ES WARNING DE PASADO DE RANGO
			}
	}

	@Override
	public boolean acomodarLinea() {
		return true;
	}

}
