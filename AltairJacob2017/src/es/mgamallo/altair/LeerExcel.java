package es.mgamallo.altair;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeMap;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.biff.CountryCode;
import jxl.biff.WorkbookMethods;
import jxl.read.biff.BiffException;

public class LeerExcel {

	
	private String[] servicios;
	private String[] nombres;
	private String[][] tablaDocumentos;
	public String[][] asociacionesDocumentos;
	private String[][] habituales;
	private int[][] coordenadasAsociar;
	
	public String[][] tablaNormas;
	
	private Nodo[] nodos;
	
	public TreeMap<String, String> nombreServicios = new TreeMap<String, String>();
	public TreeMap<String, String> nombreDocumentosXedoc = new TreeMap<String, String>();
	public TreeMap<String, String> nombreDocumentosPrometeo = new TreeMap<String, String>();
	
	public void leer(String archivoFuente){
		
		WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setEncoding("ISO-8859-1");
        wbSettings.setLocale(new Locale("es", "ES"));
        wbSettings.setExcelDisplayLanguage("ES"); 
        wbSettings.setExcelRegionalSettings("ES"); 
        wbSettings.setCharacterSet(CountryCode.SPAIN.getValue());
        
        Workbook archivoExcel;
		try {
			archivoExcel = Workbook.getWorkbook(new File(archivoFuente));
			
	        Sheet hoja = archivoExcel.getSheet(0);
	        
	        //	Obtiene los nombres
	        int numColumnas = 0;
	        int numFilas = 0;
	        
	        while(!hoja.getCell(numColumnas,0).getContents().toString().equals("@finH")){
	        	numColumnas++;
	        }
	        
	        while(!hoja.getCell(0,numFilas).getContents().toString().equals("@finV")){
	        	numFilas++;
	        }
	        
	        // System.out.println("Num filas = " + numFilas + ". Num columnas = " + numColumnas);
	        
	        servicios = new String[numColumnas - 14];
	        nombres = new String[numFilas - 1];
	        
	        tablaDocumentos = new String[nombres.length + 1][servicios.length +1];
	        
	        //	Tabla de todos los documentos
	        for(int fila=0;fila<nombres.length+1;fila++){
	        	for(int columna=0;columna<servicios.length +1;columna++){
	        		if(columna == 0){
	        			tablaDocumentos[fila][columna] = hoja.getCell(columna,fila).getContents().toString();
	        		}
	        		else{
	        			tablaDocumentos[fila][columna] = hoja.getCell(columna+1,fila).getContents().toString();
	        		}
	        	}
	        }
	        
	        /*
	        for(int fila=0;fila<nombres.length;fila++){
	        	for(int columna=0;columna<servicios.length;columna++){
	        		System.out.print(tablaDocumentos[fila][columna]);
	        	}
	        	System.out.println();
	        }
	        */
	        
	        // Lista de todos los servicios
	        for(int columna = 0;columna<servicios.length;columna++){
	        	servicios[columna] = hoja.getCell(columna + 2,0).getContents().toString();
	        }
	        
	        // Lista de todos los nombres
	        for(int fila = 0;fila<nombres.length;fila++){
	        	nombres[fila] = hoja.getCell(0,fila + 1).getContents().toString();
	        }
	        
	        /*
	        for(int columna = 0;columna<servicios.length;columna++){
	        	System.out.println(servicios[columna]); 
	        }
	        for(int fila = 0;fila<nombres.length;fila++){
	        	System.out.println(nombres[fila]); 
	        }
	        */
	        
	        //  Habituales **********************************************
	        // 0..... comunes
	        // 1..... habituales 1
	        // 2..... habituales 2
	        // 3..... habituales urgencias
	        
	        habituales = new String[nombres.length][4];
	        
	        for(int fila = 0; fila < nombres.length;fila++){
	        	for(int columna = 0;columna < 4; columna++){
	        		habituales[fila][columna] = hoja.getCell(columna + servicios.length + 2 + 7,fila +1).getContents().toString();
	        	}
	        }
	        
	        /*
	        for(int fila = 0; fila < nombres.length;fila++){
	        	for(int columna = 0;columna < 4; columna++){
	        		System.out.print((fila+2) + " " + habituales[fila][columna]);
	        	}
	        	System.out.println();
	        }
	        */
	        
	        //	Asociaciones
	        asociacionesDocumentos = new String[nombres.length][6];
	        

	        for(int fila = 0; fila < nombres.length;fila++){
	        	for(int columna = 0;columna < 6; columna++){
	        		asociacionesDocumentos[fila][columna] = hoja.getCell(columna + servicios.length + 2 + 1,fila +1).getContents().toString();
	        	}
	        	
	        	//  Obtiene la correspondencia nombres prometeo/ianus con Xedoc
	        	
	        	String comodin = hoja.getCell(servicios.length + 13 ,fila + 1).getContents().toString();
	        	if(comodin.equals("0") || comodin.equals("")){
	        		comodin = hoja.getCell(0,fila +1).getContents().toString();
	        	}
	        	
	        	nombreDocumentosPrometeo.put(hoja.getCell(0,fila +1).getContents().toString(), comodin);
	        }
	        
	        /*
	        for(int fila = 0; fila < nombres.length;fila++){
	        	for(int columna = 0;columna < 6; columna++){
	        		System.out.print((fila+2) + " " + asociacionesDocumentos[fila][columna]);
	        	}
	        	System.out.println();
	        }
	        */
	        
	        /*
	        System.out.println("Numero de documentos prometeo... " + nombreDocumentosPrometeo.size());
	        
	        for( Iterator it = nombreDocumentosPrometeo.keySet().iterator(); it.hasNext();) {
	        	String clave = (String)it.next();
	        	String valor = (String)nombreDocumentosPrometeo.get(clave);
	        	System.out.println(clave + " : " + valor);
	        }
	        */
	        
	        
	        //	Obtiene los nombres de los nodos
	        
	        hoja = archivoExcel.getSheet(1);
	        numFilas = 0;
	        while(!hoja.getCell(0,numFilas).getContents().toString().equals("@finV")){
	        	numFilas++;
	        }
	        
	        // System.out.println("numero de filas es... " + numFilas);
	        
	        nodos = new Nodo[numFilas];
	        
	        //	Array con los nombres de los nodos
	        for(int fila=0;fila<nodos.length;fila++){
	        		nodos[fila] = new Nodo();
	        		nodos[fila].alias = hoja.getCell(0,fila).getContents().toString();
	        		nodos[fila].nombre = hoja.getCell(1,fila).getContents().toString();
	        }
	        
	        
	        //	Obtiene las coordenadas
	        
	        hoja = archivoExcel.getSheet(2);
	        numFilas = 0;
	        
	        coordenadasAsociar = new int[6][4];
	        for(int fila=0;fila<6;fila++){
	        	for(int columna = 0;columna<4;columna++){
	        		coordenadasAsociar[fila][columna] = Integer.parseInt(hoja.getCell(1 + columna,2 +fila).getContents().toString());

	        	}
	        }

	        // Obtiene las nuevas coordenadas
	        
	        hoja = archivoExcel.getSheet(3);
	        
	/*  Esta parte se va a borrar */
	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice[] gs = ge.getScreenDevices();
	///////////////////////////////////////////////////////////////
			
			int numeroPantallas = gs.length;
	        
	        int iniciofila = 0;
	  //      if(Inicio.numeroPantallas == 1){
	        if(numeroPantallas == 1){
	        	iniciofila = 11;
	        }
	        
	        
	        coordenadasAsociar = new int[10][4];
	        for(int fila=iniciofila, fil = 0;fila<10 + iniciofila;fila++,fil++){
	        	for(int columna = 0;columna<4;columna++){

	        		coordenadasAsociar[fil][columna] = Integer.parseInt(hoja.getCell(1 + columna,2 +fila).getContents().toString());
	        	}
	        }	        
	        
	        
	        /*
	        for(int i=0;i<10;i++){
	        	for(int j=0;j<4;j++){
	        		System.out.print(coordenadasAsociar[i][j] + "\t");
	        	}
	        	System.out.println();
	        }
	        */
	        
	        
	        // Obtiene el nombre de los servicios para Xedoc
	        hoja = archivoExcel.getSheet(7);

	        numFilas = 0;
	        numColumnas = 2;
	        while(!hoja.getCell(0,numFilas).getContents().toString().equals("#finV")){
	        	numFilas++;
	        }
	        
	        for(int fila=0;fila<numFilas;fila++){
	        	nombreServicios.put(hoja.getCell(0,fila).getContents().toString(), 
	        			hoja.getCell(2,fila).getContents().toString());
	        }
	        
	        /*
	        for( Iterator it = nombreServicios.keySet().iterator(); it.hasNext();) {
	        	String clave = (String)it.next();
	        	String valor = (String)nombreServicios.get(clave);
	        	System.out.println(clave + " : " + valor);
	        }
	        */
	        
	        // Obtiene el nombre de los documentos para Xedoc
	        hoja = archivoExcel.getSheet(8);

	        numFilas = 0;
	        numColumnas = 2;
	        while(!hoja.getCell(0,numFilas).getContents().toString().equals("#finV")){
	        	numFilas++;
	        }
	        
	        for(int fila=0;fila<numFilas;fila++){
	        	nombreDocumentosXedoc.put(hoja.getCell(0,fila).getContents().toString(), 
	        			hoja.getCell(1,fila).getContents().toString());
	        }
	        
	       /*
	        for( Iterator it = nombreDocumentosXedoc.keySet().iterator(); it.hasNext();) {
	        	String clave = (String)it.next();
	        	String valor = (String)nombreDocumentosXedoc.get(clave);
	        	System.out.println(clave + " : " + valor);
	        }
	        */
	        
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public String[] getServicios(){
		return servicios;
	}
	
	public String[] getNombres(){
		return nombres;
	}
	
	public String[][] getTabla(){
		return tablaDocumentos;
	}
	
	public String[][] getHabituales(){
		return habituales;
	}
	
	public String[][] getAsociaciones(){
		return asociacionesDocumentos;
	}
	
	public Nodo[] getNodos(){
		/*
		for(int i=0;i<nodos.length;i++){
			System.out.println(nodos[i].alias);
		}
		*/
		return nodos;
	}
	
	public int[][] getCoordenadasAsociar(){
		return coordenadasAsociar;
	}
	
	static public void main(String args[]){
		LeerExcel leerExcel = new LeerExcel();
		leerExcel.leer("Documentos.xls");
	}

	
}
