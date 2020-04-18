package Compilador;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class Utilidades {



	public static void CrearTxtSalida(Analizador a, Parser p) {
			try {
				String ruta = "Resultados.txt";
				BufferedWriter salida = new BufferedWriter(new FileWriter(ruta));
				
				salida.write("/*  Resultados de la compilacion  */");
				salida.newLine();
				salida.newLine();

				Hashtable<String,Simbolo> tablaSimbolo= a.getTablaSimbolo();
				salida.write("/*  Tabla de Simbolos  */");
				salida.newLine();
				salida.newLine();
				tablaSimbolo.entrySet().forEach(entry->{
					String format = "|%1$-15s|";
					String clave  = String.format(format,"Clave: "+entry.getKey()); 
				    try {
						salida.write(clave + entry.getValue().imprimir() );
						salida.newLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 });
				salida.newLine();

				salida.write("/*  Tercetos  */");
				salida.newLine();
				for(int i=0; i< p.listaTercetos.size(); i++) {
					salida.write(p.listaTercetos.get(i).imprimir());
					salida.newLine();
				}
				salida.newLine();
				salida.newLine();
				ArrayList<String> errores;
				errores =  p.getErrores();
				if (errores.size() > 0) {
					salida.newLine();
					salida.write("/*  Errores  */");
					salida.newLine();
					salida.newLine();
				    for(int i = 0; i < errores.size(); i++){
				        salida.write(errores.get(i));
				        salida.newLine();
				    }
				}
				else {
					salida.newLine();
					salida.write("/*  No Existen Errores  */");
					salida.newLine();
					salida.write("  Se creo el codigo Assembler en el archivo Salida.asm");
					salida.newLine();
					salida.newLine();
					GeneradorAssembler GA = new GeneradorAssembler(a, p.listaTercetos);
					CrearArchivoSalidaAssembler(GA);
				}
				
				
				salida.close();
			} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
	
		public static void CrearArchivoSalidaAssembler(GeneradorAssembler GA) throws IOException {
			String ruta = "Salida.asm";
			BufferedWriter salida = new BufferedWriter(new FileWriter(ruta));
			ArrayList<String> assembler = GA.getAssembler();
			for(int i=0; i< assembler.size(); i++) {
				salida.write(assembler.get(i));
				salida.newLine();
			}
			salida.close();
		}
	


	
	
}