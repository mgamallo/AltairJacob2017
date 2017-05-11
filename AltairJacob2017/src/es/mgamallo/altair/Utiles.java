package es.mgamallo.altair;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JOptionPane;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class Utiles {

	static Point coordenadaAceptar = new Point(600, 700);
	static Point coordenadaAceptar2 = new Point(500,600);
	static Point coordenadaVentana = new Point(500, 700);

	
	public static ActiveXComponent capturaWeb(){
		
		ActiveXComponent oWindow = null;
        
        int iCount = InicioAltairJacob.oWindows.getProperty("Count").getInt();
        // System.out.println("iCount: " + iCount);        
		
        for (int i=iCount-1,j= 1; i >iCount-2 ; i--,j++) {
            oWindow = InicioAltairJacob.oWindows.invokeGetComponent("Item", new Variant(i));     
            String sLocName = oWindow.getProperty("LocationName").getString();
            String sFullName = oWindow.getProperty("FullName").getString();
            String sUrl =  oWindow.getProperty("LocationURL").getString();
            
            boolean isIE = sFullName.toLowerCase().endsWith("iexplore.exe");
            boolean bVisible = oWindow.getProperty("Visible").getBoolean();
            // System.out.println("i: " + i + ", loc: " + sLocName + ", name: " + sFullName + ", isIE: " + isIE + ", vis: " + bVisible);
            
        }
        
        return oWindow;

	}	
	
	
	public static boolean indexarDocumento(Navegador navegador){
		
		try {
			Robot robot = new Robot();
			Point p[] = new Point[2];
			if(InicioAltairJacob.numeroPantallas == 1){
				p[0] = Coordenada.COORDENADA_CERTIFICADO_1P;
			}
			else{
				p[0] = Coordenada.COORDENADA_CERTIFICADO_2Pa;
				p[1] = Coordenada.COORDENADA_CERTIFICADO_2Pb;
			}
			
			Color color[] = new Color[2];
			color[0] = robot.getPixelColor(p[0].x, p[0].y);
			
			if(InicioAltairJacob.numeroPantallas == 2){
				color[1] = robot.getPixelColor(p[1].x, p[1].y);
			}
			
			System.out.println("Color antes de indexar: " + color[0].getRGB());
			
			indexar(navegador);
			
			Point coordenada = detectaVentanaCertificado(p, color);
			
			
			

			
			if(coordenada != null){
				
				//	Le damos aceptar a la ventana del certificado
				robot.mouseMove(coordenada.x, coordenada.y);
				
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				
				robot.delay(100);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.delay(50);
				robot.keyRelease(KeyEvent.VK_ENTER);
				
				
				if(InicioAltairJacob.numeroPantallas == 1){
					p[0] = Coordenada.COORDENADA_PIN_1P;
				}
				else{
					p[0] = Coordenada.COORDENADA_PIN_2P;
				}
				
				p[1] = null;
				color[1] = null;

				color[0] = robot.getPixelColor(p[0].x, p[0].y);
				coordenada = detectaPin(p, color);
				
				if(coordenada != null){
					
					System.out.println(InicioAltairJacob.pin.getPin());
					robot.mouseMove(p[0].x, p[0].y);
	/*				robot.delay(1600);
					robot.mouseMove(p.x, p.y);  */
					robot.delay(100);
					
					String pin = InicioAltairJacob.pin.getPin();
					for(int i=0;i<pin.length();i++){
						
						robot.mouseMove(p[0].x, p[0].y);
						robot.delay(10);
						robot.mousePress(InputEvent.BUTTON1_MASK);
						robot.mouseRelease(InputEvent.BUTTON1_MASK);
						robot.delay(10);
						
						ImprimeChar.getChar(pin.charAt(i));
						robot.delay(10);
						
						
					//	JOptionPane.showMessageDialog(null, i);
					}
					
					robot.delay(200);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);
					robot.delay(200);  
					
				}
			}
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	public static Point detectaVentanaCertificado(Point p[], Color color[]){
		
		return detectaVentanaModal(p, color, Retardo.RETARDO_MAX_CERTIFICADO);

	}
	
	public static Point detectaPin(Point p[], Color color[]){
				
		return detectaVentanaModal(p, color, Retardo.RETARDO_MAX_PIN);
	}
	
	

	public static Point detectaVentanaModal(Point p[], Color color[], int retardoMaximo) {

		boolean encontrada = false;
		int maximo = retardoMaximo + 5000;
		int tiempo = 0;
		Point coordenada = null;
		
		try {

			Robot robot = new Robot();

			do {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				System.out.println("Tiempo: " + tiempo + ". Color: " +  robot.getPixelColor(p.x,
						p.y).getRGB() );
				*/
				
				if(color[0].getRGB() != robot.getPixelColor(p[0].x,p[0].y).getRGB()){
					coordenada = p[0];
				}
				
				if( (color[1] != null) && 
					(InicioAltairJacob.numeroPantallas == 2) && 
						  (color[1].getRGB() != robot.getPixelColor(p[1].x,p[1].y).getRGB() )){
					coordenada = p[1];
				}
				
				if( coordenada != null	 ){
					encontrada = true;

					System.out.println("Color del punto en " + coordenada.x + ", " + coordenada.y + ": " 
							+ robot.getPixelColor(coordenada.x, coordenada.y).getRGB());
				}

				tiempo += 50;
				// System.out.println(tiempo);

			} while (!encontrada && tiempo < maximo);
			
			System.out.println("Encontrada ventana en tiempo: " + tiempo);

		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return coordenada;
	}
	
	
	public static boolean detectaVentanaModalCierre(Color color, int retardoMaximo) {

		boolean encontrada = false;
		int maximo = retardoMaximo;
		int tiempo = 0;
		
		try {

			Robot robot = new Robot();
			color = robot.getPixelColor(coordenadaVentana.x,
					coordenadaVentana.y);

			do {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (color != robot.getPixelColor(coordenadaVentana.x,
						coordenadaVentana.y)) {
					encontrada = true;
					
					robot.mouseMove(coordenadaAceptar2.x, coordenadaAceptar2.y);
					
					robot.mousePress(InputEvent.BUTTON1_MASK);
					robot.mouseRelease(InputEvent.BUTTON1_MASK);
					
					robot.delay(100);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.delay(100);
					robot.keyRelease(KeyEvent.VK_ENTER);
				}

				tiempo += 50;
				// System.out.println(tiempo);

			} while (!encontrada && tiempo < maximo);

		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return encontrada;
	}
	

	public static void indexar(Navegador navegador){

		Dispatch.call(navegador.navegador, "Navigate","javascript:document.getElementById('submitFormFirmar').click()"); 

	}
	
	public static void cierraNavegador(Navegador navegador, Point p, int retardoMaximo){
		
		Dispatch.call(navegador.navegador, "Quit");
		Robot robot;
		try {
			robot = new Robot();
			Color color = robot.getPixelColor(p.x, p.y);
			Utiles.detectaVentanaModalCierre(color, retardoMaximo);

			
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	
	public static void abreNavegador(Navegador navegador){
		
		int conteo = navegador.numeroPdf;
		if(conteo + 2 < GestionXedoc.numPdfsTotales){
			// Aún hay pdfs en la bandeja
			
			navegador.numeroPdf = navegador.numeroPdf + 2;
			String ejecutar = "document.getElementById('a" + navegador.numeroPdf + "').click();";
			
			switch (navegador.id) {
			case 1:
				// Xedoc 1
				Dispatch.call(InicioAltairJacob.bandejaXedoc1.navegador, "navigate","javascript:" + ejecutar);
				InicioAltairJacob.xedoc1Activo = false;
				Dispatch.put(InicioAltairJacob.xedoc2.navegador, "visible","true");
				InicioAltairJacob.ventana.jBxedoc1.setBackground(Color.gray);
				InicioAltairJacob.ventana.jBxedoc2.setBackground(Color.green);
				break;
			case 2:
				// Xedoc 2
				Dispatch.call(InicioAltairJacob.bandejaXedoc2.navegador, "navigate","javascript:" + ejecutar);
				InicioAltairJacob.xedoc1Activo = true;
				Dispatch.put(InicioAltairJacob.xedoc1.navegador, "visible","true");
				InicioAltairJacob.ventana.jBxedoc2.setBackground(Color.gray);
				InicioAltairJacob.ventana.jBxedoc1.setBackground(Color.green);
				break;
			default:
				break;
			}
			
			//	Capturamos el navegador
			try {
				Thread.sleep(Retardo.RETARDO_CAPTURA_NAVEGADOR);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			navegador.navegador = capturaWeb();	
			
			boolean errorMaqueta = false;
			int contador = 0;
			do{
			//	1º Formateo
			try {
				Dispatch.put(navegador.navegador, "visible","false");
				Dispatch doc1 = Dispatch.call(navegador.navegador, "document").getDispatch();
				Dispatch.put(doc1,"title",navegador.nombre);
				Dispatch.put(navegador.navegador,"toolbar",false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error maquetando en Utiles. Captura Navegador.");
				System.out.println("Ciclo de error: " + ++contador);
				errorMaqueta = true;
			}	
			}while(errorMaqueta && contador < 4);
       		//	Conseguimos el foco en el xedoc visible
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
				
				// robot.keyPress(KeyEvent.VK_TAB);
				// robot.keyRelease(KeyEvent.VK_TAB);
				
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
       		
			       		
			try {
				Thread.sleep(Retardo.RETARDO_CARGA_NAVEGADOR);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//	2º Formateo
			
						
			HiloMaquetadoInicio maquetado = new HiloMaquetadoInicio(navegador, navegador.nombre, true);
			maquetado.start();
		}
		else {
			// En el caso de que sea el penultimo pdf
			if (conteo + 1 < GestionXedoc.numPdfsTotales) {
				switch (navegador.id) {
				case 1:
					// Xedoc 1
					InicioAltairJacob.xedoc1Activo = false;
					Dispatch.put(InicioAltairJacob.xedoc2.navegador, "visible",
							"true");
					InicioAltairJacob.ventana.jBxedoc1
							.setBackground(Color.gray);
					InicioAltairJacob.ventana.jBxedoc2
							.setBackground(Color.green);
					break;
				case 2:
					// Xedoc 2
					InicioAltairJacob.xedoc1Activo = true;
					Dispatch.put(InicioAltairJacob.xedoc1.navegador, "visible",
							"true");
					InicioAltairJacob.ventana.jBxedoc2
							.setBackground(Color.gray);
					InicioAltairJacob.ventana.jBxedoc1
							.setBackground(Color.green);
					break;
				default:
					break;
				}
			}
			else{
				Dispatch.put(InicioAltairJacob.bandejaXedoc1.navegador, "visible","true");
				Dispatch.put(InicioAltairJacob.bandejaXedoc2.navegador, "visible","true");
				
				String texto = "javascript:var formulario = document.getElementById('pendientesForm');"
						+ "var inputs = formulario.getElementsByTagName('input');"
						+ "inputs[3].click();";
				
				Dispatch.call(InicioAltairJacob.bandejaXedoc1.navegador, "Navigate",texto); 
				Dispatch.call(InicioAltairJacob.bandejaXedoc2.navegador, "Navigate",texto); 

				InicioAltairJacob.ventana.jBxedoc1.setBackground(Color.gray);
				InicioAltairJacob.ventana.jBxedoc2.setBackground(Color.green);
				
				InicioAltairJacob.ventana.jBiniciar.setEnabled(true);
				InicioAltairJacob.ventana.jBiniciar.setBackground(Color.pink);
			}
		}
		
		

	}
	
	//*********  Métodos para cargar contexto
	
	private static String extraerNHC(String nombreFichero){
		
		String nhc = "";
		
		String campos[] = nombreFichero.split(" @");
		if(campos.length == 4){
			nhc = campos[1];
		}
		
		return nhc;
	}
	
	
	private static String[] getFechasContexto(){
		
		String fechas[] = new String[2];
		
		String fechaInicio = "";
		String fechaFin = "";

		int diaHoy = 1;
		int mesHoy = 1;
		int añoHoy = 1;
		
		int diaHaceUnMes = 1;
		int mesHaceUnMes = 1;
		int añoHaceUnMes = 1;
		
		Calendar calendario = Calendar.getInstance();
		diaHoy = calendario.get(Calendar.DAY_OF_MONTH);
		mesHoy = calendario.get(Calendar.MONTH) + 1;
		añoHoy = calendario.get(Calendar.YEAR);
		
		fechaFin = diaHoy + "/" + mesHoy + "/" + añoHoy;

		calendario.add(Calendar.DAY_OF_MONTH,- InicioAltairJacob.RANGO_DIAS_CONSULTA);
		
		diaHaceUnMes = calendario.get(Calendar.DAY_OF_MONTH);
		mesHaceUnMes = calendario.get(Calendar.MONTH) + 1;
		añoHaceUnMes = calendario.get(Calendar.YEAR);
		
		fechaInicio = diaHaceUnMes + "/" + mesHaceUnMes + "/" + añoHaceUnMes;
		
		fechas[0] = fechaInicio;
		fechas[1] = fechaFin;
		
		return fechas;

	}
	
	
	public static boolean cargaContexto(Navegador navegador){
		
		Dispatch documento = Dispatch.call(navegador.navegador,"document").getDispatch();
		Dispatch labelAtributo = Dispatch.call(documento, "getElementById","labelAtributo").toDispatch();
		Variant mFvariant = Dispatch.get(labelAtributo,"innerHTML");
		String nombrePdf = mFvariant.toString();
		System.out.println(nombrePdf);

		if(nombrePdf.contains(" @v")){
			return false;
		}
		else{
			String nhc = extraerNHC(nombrePdf);
			
			Dispatch loadContexto = Dispatch.call(documento, "getElementById","loadContexto").toDispatch();
			mFvariant = Dispatch.get(loadContexto,"innerHTML");
			String nombrePacienteContexto = mFvariant.toString();
			
			System.out.println(nhc);
			System.out.println(nombrePacienteContexto);
			
			if(!nombrePacienteContexto.contains(nhc)){
				
				String fechaInicio = "";
				String fechaFin = "";
				
				String fechas[] = getFechasContexto();
				fechaInicio = fechas[0];
				fechaFin = fechas[1];
				
				String cadena = ""
					+ ""
					+ "function cargaContexto(){"
						+ "document.getElementById('contextoMenuSuperior').click();"
						+ "var nhcElement = document.getElementById('{hc}numeroHC');"
						+ "nhcElement.value= '" + nhc + "';"
						+ "var fechaI = document.getElementById('FechaIni');"
						+ "var fechaF = document.getElementById('FechaFin');"
						+ "var periodo = document.getElementById('Periodo');"
						+ "periodo.options[0].selected = true;"
						+ "fechaI.value = '" + fechaInicio + "';"
						+ "fechaF.value = '" + fechaFin + "';"
						//+ "alert('En medio de la carga de contexto');"
						+ "document.getElementById('submitFormContexto').click();"
						+ "var claveEntera = nhcElement.value + '-" + InicioAltairJacob.codigoCentro + "';"
						+ ""
						+ "var numCiclos = 0;"
						+ "var intervalo = setInterval(ciclos,1100);"
						
						+ "function ciclos(){"
							+ "var formResult = document.getElementById('formResult');"
							+ "var tabla = formResult.getElementsByTagName('table');"
							+ "var numeroTablas = tabla.length;"
							+ "if(numeroTablas > 0){"
								+ "clearInterval(intervalo);"
								+ "cambiarContexto(claveEntera);"
							+ "}"
							+ "var error = document.getElementById('advertenciaContexto');"
							+ "if(error != null || error != undefined || numCiclos > 10){"
								+ "clearInterval(intervalo);"
								+ "cambiarContexto(claveEntera);"
							+ "}"
							+ "numCiclos++;"   
						+ "}"
					+ "};"
					+ ""
					+ "cargaContexto();"
					;
					
				
				
				Dispatch.call(navegador.navegador,"navigate","javascript:" + cadena);
	
			}
			
			return true;
		}
		

		
	}
	
	
	
	
	public static void pasarAlSiguientePdf(Navegador navegador, Point p, int retardoMaximo){
		cierraNavegador(navegador, p, retardoMaximo);
		abreNavegador(navegador);
		
		
	}
}
