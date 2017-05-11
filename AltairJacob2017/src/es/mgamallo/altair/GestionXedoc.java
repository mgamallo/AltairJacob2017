package es.mgamallo.altair;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;




public class GestionXedoc {

	public static int RANGO_DIAS_CONSULTA = 100;
	
	public static int numPdfsTotales = 0;
	public static String listaPdfs[];
	
	
	public static void capturaNavegadorXedoc1y2(){
        
		int iCount = InicioAltairJacob.oWindows.getProperty("Count").getInt();
        // System.out.println("iCount: " + iCount);       
		
		int navegadores = 2;
		if(InicioAltairJacob.xedoc2.numeroPdf == -1){
			navegadores = 1;
		}
		
        for (int i=iCount-1,j= 1; i >iCount-navegadores -1 ; i--,j++) {
            ActiveXComponent oWindow = InicioAltairJacob.oWindows.invokeGetComponent("Item", new Variant(i));     
            String sLocName = oWindow.getProperty("LocationName").getString();
            String sFullName = oWindow.getProperty("FullName").getString();
            String sUrl =  oWindow.getProperty("LocationURL").getString();
            
            boolean isIE = sFullName.toLowerCase().endsWith("iexplore.exe");
            boolean bVisible = oWindow.getProperty("Visible").getBoolean();
            // System.out.println("i: " + i + ", loc: " + sLocName + ", name: " + sFullName + ", isIE: " + isIE + ", vis: " + bVisible);
            
            if(j==2 || (j == 1 && navegadores == 1)){
            	InicioAltairJacob.xedoc1.navegador = oWindow;
                Dispatch doc1 = Dispatch.call(InicioAltairJacob.xedoc1.navegador, "document").getDispatch();
                Dispatch.put(doc1,"title","Xedoc 1");
	       	//	Dispatch.put(InicioAltairJacob.xedoc1.navegador,"menubar",false);
	       		Dispatch.put(InicioAltairJacob.xedoc1.navegador,"toolbar",false);	
            }
            if(j==1 && navegadores == 2){
            	InicioAltairJacob.xedoc2.navegador = oWindow;
                Dispatch doc1 = Dispatch.call(InicioAltairJacob.xedoc2.navegador, "document").getDispatch();
                
                int cuenta = 0;
                boolean error;
                do{
                	
                     error = false;
                    try {
						Dispatch.put(doc1,"title","Xedoc 2");
						Dispatch.put(InicioAltairJacob.xedoc2.navegador,"toolbar",false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						error = true;
					}	
                }while(error && cuenta <3);
                

            }           

        }
	}
	

	
	public static void iniciarIndexación(int filaInicial1, int filaInicial2){
		
		ArrayList<ActiveXComponent> listaNavegadores = new ArrayList<ActiveXComponent>();
		listaNavegadores.add(InicioAltairJacob.bandejaXedoc1.navegador);
		listaNavegadores.add(InicioAltairJacob.bandejaXedoc2.navegador);
		
		int tamaño = 0;
		
		for(int z=0 ;z<2;z++){
			
			Dispatch documento = Dispatch.call(listaNavegadores.get(z), "document").toDispatch();
			Dispatch tabla = Dispatch.call(documento, "getElementById","row").toDispatch();
			Dispatch celdas = Dispatch.call(tabla, "getElementsByTagName","td").toDispatch();
			
			int tam = Integer.valueOf(Dispatch.get(celdas, "length").toString()) / 5 ;
			
			if(z == 0){
				tamaño = tam;
				InicioAltairJacob.listaPdfs = new String[tamaño];
			}
			
			
			for(int i=0;i<tamaño;i++){
				Dispatch celda = Dispatch.get(celdas, String.valueOf(i*5+2)).toDispatch();
				
				if(z == 0){
					InicioAltairJacob.listaPdfs[i] = Dispatch.get(celda,"innerHTML").toString();
				}
				
				Dispatch celdaAncla = Dispatch.get(celdas, String.valueOf(i*5+4)).toDispatch();
				Dispatch anclas = Dispatch.call(celdaAncla,"getElementsByTagName","a").toDispatch();
				
				// int tamañoAnclas = Integer.valueOf(Dispatch.get(anclas,"length").toString());
				// System.out.println("Tamaño anclas " + tamañoAnclas);
				
				Dispatch ancla_blank = Dispatch.get(anclas,"0").toDispatch();


				Dispatch.put(ancla_blank,"target","_blank");


				Dispatch.call(ancla_blank,"setAttribute","id","a"+String.valueOf(i));
			}
			
			/*
			if(z == 0){
				for(int i=0;i<tamaño;i++){
					System.out.println(InicioAltairJacob.listaPdfs[i]);
				}
			}
		    */
		}
		
		numPdfsTotales = tamaño;
		InicioAltairJacob.numPdfsTotales = numPdfsTotales;
		
		// JOptionPane.showMessageDialog(null, "Ver");
		
		InicioAltairJacob.xedoc1.numeroPdf = filaInicial1;
		InicioAltairJacob.xedoc2.numeroPdf = -1;
		
		String ejecutar1 = "document.getElementById('a" + filaInicial1 + "').click();";
		String ejecutar2 = "";
		
		System.out.println(InicioAltairJacob.numPdfsTotales + ". " + (filaInicial2));
		
		if(InicioAltairJacob.numPdfsTotales > filaInicial2){
			InicioAltairJacob.xedoc2.numeroPdf = filaInicial2;
			ejecutar2= "document.getElementById('a" + filaInicial2 + "').click();";
		}
		


		
		
		Dispatch.call(InicioAltairJacob.bandejaXedoc1.navegador, "navigate","javascript:" + ejecutar1);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(InicioAltairJacob.xedoc2.numeroPdf != -1){
			Dispatch.call(InicioAltairJacob.bandejaXedoc2.navegador, "navigate","javascript:" + ejecutar2);

		}
		
		
//		InicioAltairJacob.getReadyState(InicioAltairJacob.bandejaXedoc2, 4, 320);	

		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		capturaNavegadorXedoc1y2();
		
		
		//  Veeeeeeeeeeeeeeeeerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr
		//	AÑadir retardo
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		HiloMaquetadoInicio maquetado1 = new HiloMaquetadoInicio(InicioAltairJacob.xedoc1, "Xedoc 1", true);
		maquetado1.start();
		
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(InicioAltairJacob.xedoc2.numeroPdf != -1){
			HiloMaquetadoInicio maquetado2 = new HiloMaquetadoInicio(InicioAltairJacob.xedoc2, "Xedoc 2", true);
			maquetado2.start();
		}

		
		
	}
	
	
	public static void insertarCodigoBandeja(String nombreXedoc, int filaInicial1, int filaInicial2){
		
		iniciarIndexación(filaInicial1, filaInicial2);
		
	}

	



	public static void carganuevoPdf(Navegador navegador, String nombreXedoc){
		
		boolean hayMasDocumentos = true;
		
		
		Dispatch documento = Dispatch.call(navegador.navegador, "document").toDispatch();
		
		
		// Comprobamos que no hemos avanzado manualmente el xedoc.
		// Si ya lo hemos avanzado, pasamos directamente al segundo siguiente
		
		Dispatch nombrePdf = Dispatch.call(documento, "getElementById","labelAtributo").toDispatch();
		Variant comprobarNombre;
		int numCiclos = 0;
		do{
			try {
				Thread.sleep(200);
				numCiclos++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			comprobarNombre = Dispatch.get(nombrePdf, "innerHTML");
		}while(comprobarNombre.toString().equals("null"));
		
		
		// System.out.println(numCiclos);
		// System.out.println(comprobarNombre);
		
		/* Comprobamos que:
		 * Ej. Xedoc 1. Pulsamos *.
		 * Comprueba que el pdf del Xedoc 1, no coincida con el pdf del Xedoc 2.
		 * Si coincidieran, implicaría que ya le dimos a siguiente manualmente.
		 */
		boolean coincide = true;
		if(nombreXedoc.equals("Xedoc 1")){
			System.out.println(InicioAltairJacob.nombrePdfXedoc2);
			if(comprobarNombre.toString().equals(InicioAltairJacob.nombrePdfXedoc2)){
				System.out.println("Coincide. Hemos avanzado manualmente");
			}
			else{
				System.out.println("No coincide. Modo automatico.");
				coincide = false;
			}
		}
		else{
			System.out.println(InicioAltairJacob.nombrePdfXedoc1);
			if(comprobarNombre.toString().equals(InicioAltairJacob.nombrePdfXedoc1)){
				System.out.println("Coincide. Hemos avanzado manualmente");
			}
			else{
				System.out.println("No coincide. Modo automatico.");
				coincide = false;
			}
		
		}
		
		
		if(!coincide){
			System.out.println("Va a clickar en siguiente");

			try {

				Variant siguiente = Dispatch.call(documento, "getElementById", "siguiente");
				System.out.println("Siguiente " + siguiente.toString());
				if (siguiente.toString().equals("null")) {
					System.out.println("No hay mas documentos de " + nombreXedoc);
					hayMasDocumentos = false;
					Dispatch.call(navegador.navegador, "navigate", "http://intranetchopo.sergas.local/");
				} else {
					
					Robot robot = new Robot();
					Color color = robot.getPixelColor(Utiles.coordenadaVentana.x, 
							Utiles.coordenadaVentana.y);
					
					Dispatch.call(navegador.navegador, "navigate", "javascript:document.getElementById('siguiente').click();");
					
					boolean encontrada = Utiles.detectaVentanaModalCierre(color, 500);
					System.out.println("Encontrada ventana modal: " + encontrada);
					
				}

				// Dispatch siguiente = Dispatch.call(documento,
				// "getElementById","siguiente").toDispatch();
				// Dispatch.call(siguiente, "click");

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();

				System.out.println("No hay mas documentos.");
				hayMasDocumentos = false;
				Dispatch.call(navegador.navegador, "navigate", "http://intranetchopo.sergas.local/");

			}
			
			boolean error = true;
			
			while(hayMasDocumentos && error){
			
			//  hay que darle una vuelta a todo esto. De momento solo se ejecuta una sóla vez.
				error = false;
				
				System.out.println("2ª iteración..... ************************************************************");
				
			//	System.out.println("Checkea si hay un alert...");
			//	checkAlert(driver);
				
				try {
					Thread.sleep(/* 400 */ 1000);              //*******************************
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("Esperamos a que cargue el nuevo documento");
				InicioAltairJacob.getReadyState(navegador.navegador, 3, 10);
				
				nombrePdf = Dispatch.call(documento, "getElementById","labelAtributo").toDispatch();
				numCiclos = 0;
				do{
					try {
						Thread.sleep(200);
						numCiclos++;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					comprobarNombre = Dispatch.get(nombrePdf, "innerHTML");
				}while(comprobarNombre.toString().equals("null"));
				System.out.println(numCiclos);
				System.out.println(comprobarNombre);
				
				
				if(nombreXedoc.equals("Xedoc 1")){
					System.out.println(InicioAltairJacob.nombrePdfXedoc2);
					if(comprobarNombre.toString().equals(InicioAltairJacob.nombrePdfXedoc2)){
						System.out.println("Coincide");
					}
					else{
						System.out.println("no coincide. vamos mal.");
		//				error = true;
		//				JOptionPane.showMessageDialog(null, "Error. No coinciden los nombres");
					}
				}
				else{
					System.out.println(InicioAltairJacob.nombrePdfXedoc1);
					if(comprobarNombre.toString().equals(InicioAltairJacob.nombrePdfXedoc1)){
						System.out.println("Coincide");
					}
					else{
		//				System.out.println("no coincide. vamos mal.");
		//				error = true;
		//				JOptionPane.showMessageDialog(null, "Error. No coinciden los nombres");
						
					}
				
				}

				
			}
			
			
			
		}
		
		// Hacemos el segundo siguiente
		
		Variant tablaMeritos = Dispatch.call(documento, "getElementById","tablaMeritos");
		System.out.println("Siguiente " + tablaMeritos.toString());
		if(tablaMeritos.toString().equals("null")){
			System.out.println("No cargó la tabla de méritos en " + nombreXedoc);
			hayMasDocumentos = false;
			//Dispatch.call(navegador, "navigate","http://intranetchopo.sergas.local/");
		}
		else{
			
			System.out.println("Encuentra la tabla de meritos 1");
			System.out.println("Va a clickar en siguiente 2");

			Variant siguiente = Dispatch.call(documento, "getElementById","siguiente");
			System.out.println("Siguiente " + siguiente.toString());
			if(siguiente.toString().equals("null")){
				System.out.println("No hay mas documentos de " + nombreXedoc);
				hayMasDocumentos = false;
				Dispatch.call(navegador.navegador, "navigate","http://intranetchopo.sergas.local/");
			}
			else{
				Dispatch.put(navegador.navegador, "height", 700);
				Dispatch.call(navegador.navegador,"navigate","javascript:document.getElementById('siguiente').click();");
				
				try {
					Thread.sleep(200);
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_ENTER);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(nombreXedoc.equals("Xedoc 1")){
				InicioAltairJacob.ventana.jBxedoc1.setBackground(Color.green);
				InicioAltairJacob.ventana.gestionXedoc1();
				InicioAltairJacob.ventana.jBxedoc2.setBackground(Color.gray);
				InicioAltairJacob.ventana.gestionXedoc2();
			}
			else{
				InicioAltairJacob.ventana.jBxedoc2.setBackground(Color.green);
				InicioAltairJacob.ventana.gestionXedoc2();
				InicioAltairJacob.ventana.jBxedoc1.setBackground(Color.gray);
				InicioAltairJacob.ventana.gestionXedoc1();
				
			}

			
			Robot robot;
			try {
				robot = new Robot();
				
				Point p = MouseInfo.getPointerInfo().getLocation();
														
				robot.delay(300);
				robot.mouseMove(600, 18);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				robot.delay(200);
				robot.mouseMove(p.x, p.y);
				
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			
		}

		


		//	Cargamos el contexto.....
		
		if(hayMasDocumentos){
			
			
			System.out.println("Busca la tabla de meritos 2... ");
			
			
			
			//	try {
			//		Thread.sleep(/* 400 */ 2000);                        
			//	} catch (InterruptedException e) {
				
			//	e.printStackTrace();
			//	}
			
			
			
			InicioAltairJacob.getReadyState(navegador.navegador, 4, 40);
			
		
					
		
					try {
						Thread.sleep(Retardo.RETARDO_CARGA_NAVEGADOR);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					new MaquetadoXedoc(navegador, nombreXedoc, false, false, false);
					
					
				// }


			
		}
		

		

		

	}
	



	
	
	public static void reInicializaXedocs(){
		
		for(int i=0;i<3;i++){
			Cerrar.cerrarIexplorer();
		}
		
		try {
			
			Dispatch.call(InicioAltairJacob.xedoc2.navegador, "Quit");
			Dispatch.call(InicioAltairJacob.xedoc1.navegador, "Quit");
			Dispatch.call(InicioAltairJacob.bandejaXedoc1.navegador, "Quit");
			Dispatch.call(InicioAltairJacob.bandejaXedoc2.navegador, "Quit");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println("Error. Algún navegador ya estaba cerrado.");
		}
		
		InicioAltairJacob.xedoc1.navegador = null;
		InicioAltairJacob.xedoc2.navegador = null;
		InicioAltairJacob.bandejaXedoc1.navegador = null;
		InicioAltairJacob.bandejaXedoc2.navegador = null;
		
		InicioAltairJacob.capturaWebs();
		
		InicioAltairJacob.cargaUsuarioXedoc(InicioAltairJacob.bandejaXedoc1.navegador, InicioAltairJacob.RUTAXEDOC);
		InicioAltairJacob.cargaUsuarioXedoc(InicioAltairJacob.bandejaXedoc2.navegador, InicioAltairJacob.RUTAXEDOC);

		InicioAltairJacob.colocaWebsXedoc();
		
	//	System.out.println("Selecciono bandeja...");
		
		InicioAltairJacob.selectMiBandeja(InicioAltairJacob.bandejaXedoc1.navegador);
		InicioAltairJacob.getReadyState(InicioAltairJacob.xedoc2.navegador, 4, 20);
		InicioAltairJacob.selectMiBandeja(InicioAltairJacob.xedoc2.navegador);
		
		InicioAltairJacob.inicializaBandeja(InicioAltairJacob.bandejaXedoc1.navegador, "Bandeja Xedoc 1");	
		InicioAltairJacob.inicializaBandeja(InicioAltairJacob.xedoc2.navegador, "Bandeja Xedoc 2");	
		
		InicioAltairJacob.getReadyState(InicioAltairJacob.bandejaXedoc1.navegador, 4, 40);
		InicioAltairJacob.getReadyState(InicioAltairJacob.bandejaXedoc2.navegador, 4, 40);
		
		InicioAltairJacob.ventana.comboInicio.setSelectedIndex(0);
		InicioAltairJacob.ventana.panelMover.setBackground(Color.red);
	}
}
