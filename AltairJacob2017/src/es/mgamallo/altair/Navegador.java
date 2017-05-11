package es.mgamallo.altair;

import com.jacob.activeX.ActiveXComponent;

public class Navegador {

	int id;
	
	ActiveXComponent navegador = null;
	String nombre = "";
	int numeroPdf = 0;
	
	Navegador(String nombre, int id){
		this.nombre = nombre;
		this.id = id;
	}
}
