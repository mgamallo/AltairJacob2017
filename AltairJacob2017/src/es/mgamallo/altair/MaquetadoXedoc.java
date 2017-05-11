package es.mgamallo.altair;


import java.awt.Point;
import java.util.List;
import java.util.Scanner;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;


public class MaquetadoXedoc {

	Navegador navegador;
	Dispatch documento;
	String nombreXedoc;
	
	String colorFondo = "#10324c";
	String colorFondoInterno = "#9db7cc";
	String colorFondoCajas = "RGB(253,247,133)";
	
	String claseInputs = "custom-combobox-input ui-widget "
			+ "ui-widget-content ui-state-default ui-corner-left ui-autocomplete-input";
	
	private int numeroPantallas;
	
	boolean errorDeContexto = false;
	boolean maquetado;
	boolean manual = false;
	
	boolean inicializaTanda;
	/*
	 * @param maquetado  Si es un maquetado manual. True.
	 */
	public MaquetadoXedoc(Navegador navegador, String nombreXedoc, boolean inicializarTanda, boolean maquetado, boolean manual){
		this.navegador = navegador;
		this.nombreXedoc = nombreXedoc;
		this.documento = Dispatch.call(navegador.navegador, "document").getDispatch();
		this.numeroPantallas = InicioAltairJacob.numeroPantallas;
		this.maquetado = maquetado;
		this.manual = manual;
		this.inicializaTanda = inicializarTanda;
		
		inicializaMaquetado();
		
		// maquetado01();
	//	dispatchMaquetado01();
		dispatchMaquetado01b(0);
	}
	
	
	public void inicializaMaquetado() {

		// Dimension dimensionSize;

		if (InicioAltairJacob.numeroPantallas == 1) {
			// dimensionSize = new Dimension(1919,1172);

			Dispatch.put(navegador.navegador, "height", 1172);
			Dispatch.put(navegador.navegador, "width", 1919);
			Dispatch.put(navegador.navegador, "top", 0);
			Dispatch.put(navegador.navegador, "left", 0);

		} 
		else {
			// dimensionSize = new Dimension(2050,1251);
			Dispatch.put(navegador.navegador, "height", 1251);
			Dispatch.put(navegador.navegador, "width", 2055);
			Dispatch.put(navegador.navegador, "top", 0);
			Dispatch.put(navegador.navegador, "left", 0);

		}

	}
	
	
	public void dispatchMaquetado01b(int cuenta){
		
		try {				
		//  Fondo y encabezado
		Dispatch fondoPagina = Dispatch.call(documento,"getElementById","page").toDispatch();
		Dispatch fondoPaginaEstilo = Dispatch.call(fondoPagina,"style").toDispatch();
		Dispatch.put(fondoPaginaEstilo,"background",colorFondo);

		Dispatch bodys = Dispatch.call(documento, "getElementsByTagName","body").toDispatch();
		Dispatch body = Dispatch.get(bodys, "0").toDispatch();
		Dispatch bodyEstilo = Dispatch.get(body, "style").toDispatch();
		Dispatch.put(bodyEstilo, "background", colorFondo);
		
		Dispatch botonSalir = Dispatch.call(documento, "getElementById","botonSalir").toDispatch();
		Dispatch.put(botonSalir, "innerHTML",nombreXedoc);
		Dispatch botonSalirEstilo = Dispatch.get(botonSalir, "style").toDispatch();
		Dispatch.put(botonSalirEstilo, "font","bold 28px arial, sans-serif");
		Dispatch.put(botonSalirEstilo, "color","red");
		
		Dispatch entornoLogin = Dispatch.call(documento, "getElementById","entornoLogin").toDispatch();
		Dispatch entornoLoginStilo = Dispatch.get(entornoLogin, "style").toDispatch();
		Dispatch.put(entornoLoginStilo, "display","none");
		
		Dispatch branding = Dispatch.call(documento, "getElementById","branding").toDispatch();
		Dispatch brandingEstilo = Dispatch.get(branding, "style").toDispatch();
		Dispatch.put(brandingEstilo, "display","none");
		
		Dispatch header = Dispatch.call(documento, "getElementById","header").toDispatch();
		Dispatch headerStilo = Dispatch.get(header, "style").toDispatch();
		Dispatch.put(headerStilo, "height","35px");
		
		// Columna izquierda
		Dispatch columnaI = Dispatch.call(documento, "getElementById","columnaIzquierdaEdicion").toDispatch();
		Dispatch columnaIEstilo = Dispatch.get(columnaI, "style").toDispatch();
		Dispatch.put(columnaIEstilo, "height","1070px");                            //     <--------------------------
		Dispatch.put(columnaIEstilo, "width","800px");
		
		Dispatch completePreview = Dispatch.call(documento, "getElementById","completePreview").toDispatch();
		Dispatch completePreviewEstilo = Dispatch.get(completePreview, "style").toDispatch();
		Dispatch.put(completePreviewEstilo, "height","1100px");						//     <--------------------------
		Dispatch.put(completePreviewEstilo, "width","800px");
		
		// Dispatch.put(completePreviewEstilo, "width","calc(100% - 730px);");
		
		Dispatch previewer = Dispatch.call(documento, "getElementById","previewer").toDispatch();
		Dispatch previewerEstilo = Dispatch.get(previewer, "style").toDispatch();
		Dispatch.put(previewerEstilo, "height","1100px");                         //     <--------------------------
		Dispatch.put(previewerEstilo, "width","800px");		
		
		//  Columna Derecha
		
		String ancho = "";
		String altoArbol = "950px";
		String altoTablameritos = "950px";
		
		if(numeroPantallas == 1){
			ancho = "800px";
			altoArbol = "830px";
			altoTablameritos = "850px";
		}
		else{
			ancho = "980px";
		}
		
		
		
		Dispatch labelAtributo;

			Dispatch divOcultarAsociados = Dispatch.call(documento, "getElementById","divOcultarAsociados").toDispatch();
			Dispatch divOcultarAsociadosEstilo = Dispatch.get(divOcultarAsociados, "style").toDispatch();
			Dispatch.put(divOcultarAsociadosEstilo, "display","none");
			
			Dispatch columnaD = Dispatch.call(documento, "getElementById","columnaDerechaEdicion").toDispatch();
			Dispatch columnaDEstilo = Dispatch.get(columnaD, "style").toDispatch();
			Dispatch.put(columnaDEstilo, "width","1000px");
			Dispatch.put(columnaDEstilo, "marginLeft",ancho);
			
			Dispatch tablaAtributos = Dispatch.call(documento, "getElementById","tablaAtributos").toDispatch();
			Dispatch tablaAtributosEstilo = Dispatch.get(tablaAtributos, "style").toDispatch();
			Dispatch.put(tablaAtributosEstilo, "border","none");
			Dispatch.put(tablaAtributosEstilo, "background",colorFondoInterno);
			Dispatch.put(tablaAtributosEstilo, "color","#000000");
			Dispatch.put(tablaAtributosEstilo, "minWidth","520px");
			Dispatch.put(tablaAtributosEstilo, "width","520px");
			
			Dispatch edicionForm = Dispatch.call(documento, "getElementById","edicionForm").toDispatch();
			Dispatch edicionFormEstilo = Dispatch.get(edicionForm, "style").toDispatch();
			Dispatch.put(edicionFormEstilo, "width","1000px");
			
			Dispatch tablaElementosAjax = Dispatch.call(documento, "getElementById","tablaElementosAjax").toDispatch();
			Dispatch tablaElementosAjaxEstilo = Dispatch.get(tablaElementosAjax, "style").toDispatch();
//	Dispatch.put(tablaElementosAjaxEstilo, "marginTop","-70px");
			Dispatch.put(tablaElementosAjaxEstilo, "marginTop","30px");
			Dispatch.put(tablaElementosAjaxEstilo, "height",altoArbol);
			Dispatch.put(tablaElementosAjaxEstilo, "width","450px");
					
			Dispatch tablaMeritos = Dispatch.call(documento, "getElementById","tablaMeritos").toDispatch();
			Dispatch tablaMeritosEstilo = Dispatch.get(tablaMeritos, "style").toDispatch();
			Dispatch.put(tablaMeritosEstilo, "border","none");
			Dispatch.put(tablaMeritosEstilo, "background",colorFondoInterno);
			Dispatch.put(tablaMeritosEstilo, "height",altoTablameritos);
			Dispatch.put(tablaMeritosEstilo, "minWidth","400px");
			
			Dispatch tablaAtributosAjax = Dispatch.call(documento, "getElementById","tablaAtributosAjax").toDispatch();
			Dispatch tablaAtributosAjaxEstilo = Dispatch.get(tablaAtributosAjax, "style").toDispatch();
			Dispatch.put(tablaAtributosAjaxEstilo, "marginLeft","470px");
			Dispatch.put(tablaAtributosAjaxEstilo, "minWidth","500px");
			
			if(InicioAltairJacob.numeroPantallas == 1){
				Dispatch.put(tablaAtributosAjaxEstilo, "marginTop","-759px");
			}
			else{
				Dispatch.put(tablaAtributosAjaxEstilo, "marginTop","-960px");
			}
			
			Dispatch arbol = Dispatch.call(documento, "getElementById","arbol").toDispatch();
			Dispatch arbolEstilo = Dispatch.get(arbol, "style").toDispatch();
			Dispatch.put(arbolEstilo, "background",colorFondoInterno);
			Dispatch.put(arbolEstilo, "height",altoArbol);
			Dispatch.put(arbolEstilo, "width","440px");
			
			Dispatch tablaDocumento = Dispatch.call(documento, "getElementById","tablaDocumento").toDispatch();
			Dispatch tablaDocumentoEstilo = Dispatch.get(tablaDocumento, "style").toDispatch();
			Dispatch.put(tablaDocumentoEstilo, "border","none");
			Dispatch.put(tablaDocumentoEstilo, "background",colorFondoInterno);
			Dispatch.put(tablaDocumentoEstilo, "width","520px");
			Dispatch.put(tablaDocumentoEstilo, "minWidth","520px");
			
			Dispatch colPropDinamica = Dispatch.call(documento, "getElementById","colPropDinamica").toDispatch();
			Dispatch colPropDinamicaEstilo = Dispatch.get(colPropDinamica, "style").toDispatch();
			Dispatch.put(colPropDinamicaEstilo, "display","none");
			
			Dispatch colPropDinamicaAncha = Dispatch.call(documento, "getElementById","colPropDinamicaAncha").toDispatch();
			Dispatch colPropDinamicaAnchaEstilo = Dispatch.get(colPropDinamicaAncha, "style").toDispatch();
//		Dispatch.put(colPropDinamicaAnchaEstilo, "display","none");
			Dispatch.put(colPropDinamicaAnchaEstilo, "width", "300px");
			Dispatch.put(colPropDinamicaAnchaEstilo, "textAlign", "left");
			
			colPropDinamica = Dispatch.call(documento,"querySelectorAll","#colPropDinamica").toDispatch();
			int tama = Integer.valueOf(Dispatch.get(colPropDinamica,"length").toString());
			 
			 // System.out.println("Tamaño de los colPropDinamica... " + tama);
			 
			 for(int i=5;i<tama-12;i++){
				 Dispatch estiloCaja = Dispatch.get(colPropDinamica, String.valueOf(i)).toDispatch();
				 Dispatch estiloCajaEstilo = Dispatch.get(estiloCaja, "style").toDispatch();
				 Dispatch.put(estiloCajaEstilo, "height", "auto");
			 }
			
			
			Dispatch legends = Dispatch.call(documento, "getElementsByTagName","legend").getDispatch();
			 
			 int numeroInputs = Integer.valueOf(Dispatch.get(legends,"length").toString());
			 // System.out.println("Número de legends... " + numeroInputs);
			 
			 for(int i=0;i<numeroInputs;i++){
				 Dispatch leyenda = Dispatch.call(legends,String.valueOf(i)).getDispatch();
				 Dispatch leyendaEstilo = Dispatch.get(leyenda, "style").toDispatch();
				 Dispatch.put(leyendaEstilo, "width","400px");
				 Dispatch.put(leyendaEstilo, "paddingTop","40px");
			 }

			 
				Dispatch loadContexto = Dispatch.call(documento, "getElementById","loadContexto").getDispatch();
				Variant nombrePacient = Dispatch.get(loadContexto,"innerHTML");
				String nombrePaciente = nombrePacient.getString();
				
//			System.out.println("Nombre del paciente  " + nombrePaciente);
				
				int index = nombrePaciente.indexOf("(");
				if(index != -1){
					nombrePaciente = nombrePaciente.substring(0,index);
				}
				if(nombrePaciente.contains("Contexto Vacío")){
					nombrePaciente = "Contexto Vacío";
					errorDeContexto = true;
				}
				
				// System.out.println("Nombre del paciente  " + nombrePaciente);
				
				
				// Otros
				Dispatch contextoMenuSuperior = Dispatch.call(documento, "getElementById","contextoMenuSuperior").toDispatch();
				Dispatch contextoMenuSuperiorEstilo = Dispatch.get(contextoMenuSuperior, "style").toDispatch();
				Dispatch.put(contextoMenuSuperiorEstilo, "marginRight","150px");
				Dispatch.put(contextoMenuSuperiorEstilo, "height","50px");
				
				Dispatch nav = Dispatch.call(documento,"getElementById","nav").toDispatch();
				Dispatch estiloNav = Dispatch.get(nav, "style").toDispatch();
				Dispatch.put(estiloNav,"top","40px");
				Dispatch.put(estiloNav,"border-bottom","none");
				
				Dispatch content = Dispatch.call(documento,"getElementById","content").toDispatch();
				Dispatch estiloContent = Dispatch.get(content, "style").toDispatch();
				Dispatch.put(estiloContent,"margin-top","-50px");
				
				
				Dispatch.put(loadContexto, "innerHTML",nombrePaciente);
				Dispatch loadContextoEstilo = Dispatch.get(loadContexto, "style").toDispatch();
			//	Dispatch.put(loadContextoEstilo, "marginLeft","-1700px");                              // <--------------------||
				Dispatch.put(loadContextoEstilo, "color","yellow");
				Dispatch.put(loadContextoEstilo, "fontSize","30px");
			//	Dispatch.put(loadContextoEstilo, "width","1100px");
				Dispatch.put(loadContextoEstilo, "padding","10px");
			//	Dispatch.put(loadContextoEstilo, "textAlign", "center");
				Dispatch.put(loadContextoEstilo, "font-weight","bolder");
				
				Dispatch comprimirA = Dispatch.call(documento, "getElementById","selectDisplayButtonsTree").toDispatch();
				Dispatch comprimirAEstilo = Dispatch.get(comprimirA, "style").toDispatch();
				Dispatch.put(comprimirAEstilo, "left","1200px");
				Dispatch.put(comprimirAEstilo, "display","none");	
				
				Dispatch textArea = Dispatch.call(documento, "getElementById","{hc}comentario-{hc}docExt").toDispatch();
				Dispatch textAreaEstilo = Dispatch.get(textArea, "style").toDispatch();
				Dispatch.put(textAreaEstilo, "width","475px");
				Dispatch.put(textAreaEstilo, "marginLeft","20px");	
				
				
				
				// Cajas amarillas
				Dispatch cajasAmarillas = Dispatch.call(documento, "querySelectorAll",".custom-combobox-input").getDispatch();
				 
				 int numeroCajas = Integer.valueOf(Dispatch.get(cajasAmarillas,"length").toString());
				 // System.out.println("Número de cajas... " + numeroCajas);

				 
				 for(int i=0, conteo = 0;i<numeroCajas;i++){
					 Dispatch caja = Dispatch.call(cajasAmarillas,String.valueOf(i)).getDispatch();
					 Dispatch cajaEstilo = Dispatch.get(caja, "style").toDispatch();
					 Variant v1 = Dispatch.get(cajaEstilo, "backgroundColor");
					 
					 if(v1.toString().contains("255")){
						 if(i != 0){
							 Dispatch.put(cajaEstilo, "backgroundColor",colorFondoCajas);
							 String ide = "cajaColoreada" + conteo;
							 conteo++;
							 Dispatch.call(caja, "setAttribute", "id",ide);
						 }
						 
						 Dispatch.put(cajaEstilo, "paddingLeft","20px");
						 Dispatch.put(cajaEstilo, "marginLeft","20px");
						 Dispatch.put(cajaEstilo, "width","465px");
						 Dispatch.put(cajaEstilo, "color","red");
						 Dispatch.put(cajaEstilo, "font","bold 18px arial, sans-serif");
						 
						 if(conteo == 1){
							 break;
						 }
					 }
				 }
				
				 
				 Dispatch cajas = Dispatch.call(documento, "querySelectorAll",".inputWithButton").getDispatch();
				 numeroCajas = Integer.valueOf(Dispatch.get(cajas,"length").toString());
				 // System.out.println("Número de cajas 2 ... " + numeroCajas);
				 
				 Dispatch caja = Dispatch.call(cajas,String.valueOf(0)).getDispatch();
				 Dispatch cajaEstilo = Dispatch.get(caja, "style").toDispatch();
				 Variant v1 = Dispatch.get(cajaEstilo, "backgroundColor");
				 Dispatch.put(cajaEstilo, "backgroundColor",colorFondoCajas);
				 String ide = "cajaColoreada2";
				 Dispatch.call(caja, "setAttribute", "id",ide);

					 
					 Dispatch.put(cajaEstilo, "paddingLeft","20px");
					 Dispatch.put(cajaEstilo, "marginLeft","20px");
					 Dispatch.put(cajaEstilo, "width","465px");
					 Dispatch.put(cajaEstilo, "color","red");
					 Dispatch.put(cajaEstilo, "font","bold 18px arial, sans-serif");
					 
				 
				
				 Dispatch titulo = Dispatch.call(documento, "getElementById","{hc}titulo-{hc}docExt").toDispatch();
				 Dispatch tituloEstilo = Dispatch.get(titulo, "style").toDispatch();
				 Dispatch.put(tituloEstilo,  "font","bold 20px arial, sans-serif");
				 Dispatch.put(tituloEstilo, "backgroundColor",colorFondoCajas);
				
				 Dispatch fecha = Dispatch.call(documento, "getElementById","{hc}dataVersion-{hc}docExt").toDispatch();
				 Dispatch fechaEstilo = Dispatch.get(fecha, "style").toDispatch();
				 Dispatch.put(fechaEstilo,  "font","bold 20px arial, sans-serif");
				 Dispatch.put(fechaEstilo, "backgroundColor",colorFondoCajas);
				 

				 
				 labelAtributo = Dispatch.call(documento, "getElementById","labelAtributo").toDispatch();
				 Dispatch labelAtributoEstilo = Dispatch.get(labelAtributo, "style").toDispatch();
				 Dispatch.put(labelAtributoEstilo, "display","none");
//		 Dispatch.put(labelAtributoEstilo, "width","500px");
//		 Dispatch.put(labelAtributoEstilo, "color","green");

			 
			 

		 

	 		 
			 Dispatch estiloCajas = Dispatch.call(documento, "querySelectorAll",".custom-combobox").getDispatch();
			 int tam = Integer.valueOf(Dispatch.get(estiloCajas,"length").toString());
			 
	//		 System.out.println("Tamaño de los custom-combobox... " + tam);
			 
			 for(int i=0;i<tam-2;i++){
				 Dispatch estiloCaja = Dispatch.get(estiloCajas, String.valueOf(i)).toDispatch();
				 Dispatch estiloCajaEstilo = Dispatch.get(estiloCaja, "style").toDispatch();
				 Dispatch.put(estiloCajaEstilo, "width", "520px");
			 }
	
			 Dispatch spamWithButton = Dispatch.call(documento, "querySelectorAll",".spamWithButton").getDispatch();
			 Dispatch spamWithButton0 = Dispatch.get(spamWithButton, String.valueOf(0)).toDispatch();
			 Dispatch estilospamWithButton0 = Dispatch.get(spamWithButton0, "style").toDispatch();
			 Dispatch.put(estilospamWithButton0, "width", "520px");
			 
				//  Nhc servicio documento
			 
			 Dispatch primaryNav = Dispatch.call(documento, "getElementById","primary-nav").toDispatch();
			 Dispatch estiloPrimaryNav = Dispatch.get(primaryNav,"style").toDispatch();
			 Dispatch.put(estiloPrimaryNav,"margin-left","980px");
			 			 
			 Dispatch anclas = Dispatch.call(primaryNav, "getElementsByTagName","a").toDispatch();
			 tam = Integer.valueOf(Dispatch.get(anclas,"length").toString());
//			 System.out.println(tam);
			 
			 for(int i=0;i<tam;i++){
				 Dispatch ancla= Dispatch.get(anclas, String.valueOf(i)).toDispatch();
		 		 Dispatch anclaEstilo = Dispatch.get(ancla, "style").toDispatch();
				 Dispatch.put(anclaEstilo, "marginRight","30px");
			//	 Dispatch.put(anclaEstilo, "marginBottom","10px");
				 Dispatch.put(anclaEstilo, "fontSize","28px");
				 Dispatch.put(anclaEstilo, "color","yellow");
				 Dispatch.put(anclaEstilo, "marginTop", "40px");
				 
				 if(i==0){
					 Dispatch.call(ancla, "setAttribute","id","nhcNav");
					 Dispatch.put(ancla, "innerHTML", "00000");
					 Dispatch estiloAncla = Dispatch.get(ancla,"style").toDispatch();
					 Dispatch.put(estiloAncla,"margin-left", "0px");
				 }
				 else if(i==1){
					 Dispatch.call(ancla, "setAttribute","id","servicioNav");
					 Dispatch.put(ancla, "innerHTML", "XXXX");
					 Dispatch.put(anclaEstilo, "marginRight", "0px");
				 }
				 else{
					 Dispatch.call(ancla, "setAttribute","id","tituloNav");
					 Dispatch.put(ancla, "innerHTML", "XXXXXXXXXX");
				 }
			 }
			 
			 
			 System.out.println("El nombre del pdf de xedoc 2 es... " + InicioAltairJacob.nombrePdfXedoc2);
			 
			 String nombreCompletoPdf = Dispatch.get(labelAtributo,"innerHTML").toString();
			 
			 if(nombreXedoc.equals("Xedoc 1")){
				 InicioAltairJacob.nombrePdfXedoc1 = nombreCompletoPdf;
			 }
			 else{
				 InicioAltairJacob.nombrePdfXedoc2 = nombreCompletoPdf;
			 }
			 
			 nombreCompletoPdf = nombreCompletoPdf.trim();
			 
			 // System.out.println(nombreCompletoPdf);		 
			 
			 
			 Dispatch footer = Dispatch.call(documento, "getElementById","footer").toDispatch();
			 Dispatch footerEstilo = Dispatch.get(footer, "style").toDispatch();
			 Dispatch.put(footerEstilo, "display", "none");
			 
			 Dispatch nuevaSeccionEdiciones = Dispatch.call(documento, "all","nuevaSeccionEdicion").toDispatch();
			 tam = Integer.valueOf(Dispatch.get(nuevaSeccionEdiciones,"length").toString());
			 // System.out.println(tam);
			 
			 for(int i=4;i<tam;i++){
				 Dispatch nuevaSeccionEdicion = Dispatch.get(nuevaSeccionEdiciones, String.valueOf(i)).toDispatch();
				 Dispatch nuevaSeccionEdicionEstilo = Dispatch.get(nuevaSeccionEdicion, "style").toDispatch();
				 Dispatch.put(nuevaSeccionEdicionEstilo, "display", "none");
			 }
			 
			 
			 Dispatch labelCabeceraMetadatos = Dispatch.call(documento, "getElementById","cabecera_metadatos").toDispatch();
			 Dispatch.put(labelCabeceraMetadatos,"innerHTML",nombreCompletoPdf);
			 Dispatch estiloLabelCabeceraMetadatos = Dispatch.get(labelCabeceraMetadatos,"style").toDispatch();
			 Dispatch.put(estiloLabelCabeceraMetadatos,"color","white");
			 Dispatch.put(estiloLabelCabeceraMetadatos,"background-color","rgb(28,95,162)");
			 Dispatch.put(estiloLabelCabeceraMetadatos,"font-weight","bolder");
			 Dispatch.put(estiloLabelCabeceraMetadatos,"font-size","130%");
			 Dispatch.put(estiloLabelCabeceraMetadatos,"padding","10px");
			 Dispatch.put(estiloLabelCabeceraMetadatos,"line-height","20px");
			 
			 Dispatch botonesInferioresEdicion = Dispatch.call(documento, "getElementById","botonesInferioresEdicion").toDispatch();
			 Dispatch estiloBotonesInferioresEdicion = Dispatch.get(botonesInferioresEdicion,"style").toDispatch();
			 Dispatch.put(estiloBotonesInferioresEdicion, "text-align", "center");
			 
			 
			 
			 
			 
			 
			 
				boolean errorNhc = false;
				
				XedocIndividualJacob xedoc = new XedocIndividualJacob(nombreCompletoPdf, navegador);
			 
				errorNhc = putNHC(navegador.navegador, xedoc);
			 
				System.out.println("Error en el nhc..." + errorNhc);
				
							
				if(!errorNhc || maquetado){
					
					if(!errorDeContexto){
						
						xedoc.buscaNodo(!manual);
						try {
		/*					Thread.sleep(100);
							xedoc.seleccionarServicio();   */
							Thread.sleep(100);
							xedoc.seleccionarDocumento();

							Thread.sleep(100);
						//	xedoc.inicializaNodosHospUrgQui();
							xedoc.gestionEventosNodos();
							
							Thread.sleep(100);
							xedoc.ocultaNodos();
							Thread.sleep(100);
							xedoc.getFocus();
							

							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
				
				// Columna izquierda
		//		Dispatch columnaI = Dispatch.call(documento, "getElementById","columnaIzquierdaEdicion").toDispatch();
		//		Dispatch columnaIEstilo = Dispatch.get(columnaI, "style").toDispatch();
		//		Dispatch.put(columnaIEstilo, "height","1070px");                            //     <--------------------------
		//		Dispatch.put(columnaIEstilo, "width","800px");
				
		//		Dispatch completePreview = Dispatch.call(documento, "getElementById","completePreview").toDispatch();
		//		Dispatch completePreviewEstilo = Dispatch.get(completePreview, "style").toDispatch();
		//		Dispatch.put(completePreviewEstilo, "height","1100px");						//     <--------------------------
		//		Dispatch.put(completePreviewEstilo, "width","800px");
				
				if (InicioAltairJacob.numeroPantallas == 1) {
					// dimensionSize = new Dimension(1919,1172);

					Dispatch.put(navegador.navegador, "height", 1172);
				//	Dispatch.put(navegador, "width", 1919);

				} 
				else {
					// dimensionSize = new Dimension(2050,1251);
					Dispatch.put(navegador.navegador, "height", 1251);
				//	Dispatch.put(navegador, "width", 2055);
				}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Error. " + navegador.nombre + ". Volvemos a maquetar.");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(cuenta < 5){
				dispatchMaquetado01b(++cuenta);
				System.out.println(navegador.nombre + ". Ciclo " + cuenta);
			}
			else{
				System.out.println("Fallo en bucle. " + navegador.nombre
						+ ". " + cuenta + " ciclos.");
			}
			
		}
	}
	
	
	
	public void dispatchMaquetado01(){
		
		
		//  Fondo y encabezado
		Dispatch fondoPagina = Dispatch.call(documento,"getElementById","page").toDispatch();
		Dispatch fondoPaginaEstilo = Dispatch.call(fondoPagina,"style").toDispatch();
		Dispatch.put(fondoPaginaEstilo,"background",colorFondo);

		Dispatch bodys = Dispatch.call(documento, "getElementsByTagName","body").toDispatch();
		Dispatch body = Dispatch.get(bodys, "0").toDispatch();
		Dispatch bodyEstilo = Dispatch.get(body, "style").toDispatch();
		Dispatch.put(bodyEstilo, "background", colorFondo);
		
		Dispatch botonSalir = Dispatch.call(documento, "getElementById","botonSalir").toDispatch();
		Dispatch.put(botonSalir, "innerHTML",nombreXedoc);
		Dispatch botonSalirEstilo = Dispatch.get(botonSalir, "style").toDispatch();
		Dispatch.put(botonSalirEstilo, "font","bold 28px arial, sans-serif");
		Dispatch.put(botonSalirEstilo, "color","red");
		
		Dispatch entornoLogin = Dispatch.call(documento, "getElementById","entornoLogin").toDispatch();
		Dispatch entornoLoginStilo = Dispatch.get(entornoLogin, "style").toDispatch();
		Dispatch.put(entornoLoginStilo, "display","none");
		
		Dispatch branding = Dispatch.call(documento, "getElementById","branding").toDispatch();
		Dispatch brandingEstilo = Dispatch.get(branding, "style").toDispatch();
		Dispatch.put(brandingEstilo, "display","none");
		
		Dispatch header = Dispatch.call(documento, "getElementById","header").toDispatch();
		Dispatch headerStilo = Dispatch.get(header, "style").toDispatch();
		Dispatch.put(headerStilo, "height","35px");
		
		// Columna izquierda
		Dispatch columnaI = Dispatch.call(documento, "getElementById","columnaIzquierdaEdicion").toDispatch();
		Dispatch columnaIEstilo = Dispatch.get(columnaI, "style").toDispatch();
		Dispatch.put(columnaIEstilo, "height","1070px");                            //     <--------------------------
		Dispatch.put(columnaIEstilo, "width","800px");
		
		Dispatch completePreview = Dispatch.call(documento, "getElementById","completePreview").toDispatch();
		Dispatch completePreviewEstilo = Dispatch.get(completePreview, "style").toDispatch();
		Dispatch.put(completePreviewEstilo, "height","1100px");						//     <--------------------------
		Dispatch.put(completePreviewEstilo, "width","800px");

		
		//	Fondo y encabezado Javascript
		
		String fondoYencabezado = ""
				+ ""
				+ "var fondoPagina = document.getElementById('page');"
				+ "fondoPagina.style.background = '" + colorFondo + "';"
				+ "var bodys = document.getElementsByTagname('body');"
				+ "bodys[0].style.background = '" + colorFondo + "';"

				+ "var botonSalir = document.getElementById('botonSalir');"
				+ "botonSalir.innerHTML = '" + nombreXedoc + "';"
				+ "botonSalir.style.font = 'bold 28px arial, sans-serif';"
				+ "botonSalir.style.color = 'red';"
				+ "var branding = document.getElementById('branding');"
				+ "branding.style.display = 'none';"
				
				+ "var entornoLogin = document.getElementById('entornoLogin');"
				+ "entornoLogin.style.display = 'none';"
				+ "var header = document.getElementById('header');"
				+ "header.style.height = '0px';"
				+ "";
		
		
		String columnaIzquierda = ""
				+ ""
				+ "var columnaI = document.getElementById('columnaIzquierdaEdicion');"
				+ "columnaI.style.height = '1100px';"
				+ "columnaI.style.width = '800px';"
				+ ""
				+ "var completePreview = document.getElementById('completePreview');"
				+ "completePreview.style.height = '1100px';"
				+ "completePreview.style.width = '800px';"
				+ ""
				+ "var previewer = document.getElementById('previewer');"
				+ "if(previewer != null && previewer != undefined){"
					+ "previewer.style.height = '1200px';"
					+ "previewer.style.width = '800px';"
				+ "}"
				
				+ "";
		
				
		
		//  Columna Derecha
		
		String ancho = "";
		String altoArbol = "950px";
		String altoTablameritos = "950px";
		
		if(numeroPantallas == 1){
			ancho = "800px";
			altoArbol = "830px";
			altoTablameritos = "850px";
		}
		else{
			ancho = "960px";
		}

		
		
		Dispatch divOcultarAsociados = Dispatch.call(documento, "getElementById","divOcultarAsociados").toDispatch();
		Dispatch divOcultarAsociadosEstilo = Dispatch.get(divOcultarAsociados, "style").toDispatch();
		Dispatch.put(divOcultarAsociadosEstilo, "display","none");
		
		Dispatch columnaD = Dispatch.call(documento, "getElementById","columnaDerechaEdicion").toDispatch();
		Dispatch columnaDEstilo = Dispatch.get(columnaD, "style").toDispatch();
		Dispatch.put(columnaDEstilo, "width","1000px");
		Dispatch.put(columnaDEstilo, "marginLeft",ancho);
		
		Dispatch tablaAtributos = Dispatch.call(documento, "getElementById","tablaAtributos").toDispatch();
		Dispatch tablaAtributosEstilo = Dispatch.get(tablaAtributos, "style").toDispatch();
		Dispatch.put(tablaAtributosEstilo, "border","none");
		Dispatch.put(tablaAtributosEstilo, "background",colorFondoInterno);
		Dispatch.put(tablaAtributosEstilo, "color","#000000");
		Dispatch.put(tablaAtributosEstilo, "minWidth","520px");
		Dispatch.put(tablaAtributosEstilo, "width","520px");
		
		Dispatch edicionForm = Dispatch.call(documento, "getElementById","edicionForm").toDispatch();
		Dispatch edicionFormEstilo = Dispatch.get(edicionForm, "style").toDispatch();
		Dispatch.put(edicionFormEstilo, "width","1000px");
		
		Dispatch tablaElementosAjax = Dispatch.call(documento, "getElementById","tablaElementosAjax").toDispatch();
		Dispatch tablaElementosAjaxEstilo = Dispatch.get(tablaElementosAjax, "style").toDispatch();
		Dispatch.put(tablaElementosAjaxEstilo, "marginTop","-70px");
		Dispatch.put(tablaElementosAjaxEstilo, "height",altoArbol);
		Dispatch.put(tablaElementosAjaxEstilo, "width","450px");
				
		Dispatch tablaMeritos = Dispatch.call(documento, "getElementById","tablaMeritos").toDispatch();
		Dispatch tablaMeritosEstilo = Dispatch.get(tablaMeritos, "style").toDispatch();
		Dispatch.put(tablaMeritosEstilo, "border","none");
		Dispatch.put(tablaMeritosEstilo, "background",colorFondoInterno);
		Dispatch.put(tablaMeritosEstilo, "height",altoTablameritos);
		Dispatch.put(tablaMeritosEstilo, "minWidth","400px");
		
		Dispatch tablaAtributosAjax = Dispatch.call(documento, "getElementById","tablaAtributosAjax").toDispatch();
		Dispatch tablaAtributosAjaxEstilo = Dispatch.get(tablaAtributosAjax, "style").toDispatch();
		Dispatch.put(tablaAtributosAjaxEstilo, "marginLeft","470px");
		Dispatch.put(tablaAtributosAjaxEstilo, "minWidth","500px");
		
		if(InicioAltairJacob.numeroPantallas == 1){
			Dispatch.put(tablaAtributosAjaxEstilo, "marginTop","-759px");
		}
		else{
			Dispatch.put(tablaAtributosAjaxEstilo, "marginTop","-930px");
		}
		
		Dispatch arbol = Dispatch.call(documento, "getElementById","arbol").toDispatch();
		Dispatch arbolEstilo = Dispatch.get(arbol, "style").toDispatch();
		Dispatch.put(arbolEstilo, "background",colorFondoInterno);
		Dispatch.put(arbolEstilo, "height",altoArbol);
		Dispatch.put(arbolEstilo, "width","440px");
		
		Dispatch tablaDocumento = Dispatch.call(documento, "getElementById","tablaDocumento").toDispatch();
		Dispatch tablaDocumentoEstilo = Dispatch.get(tablaDocumento, "style").toDispatch();
		Dispatch.put(tablaDocumentoEstilo, "border","none");
		Dispatch.put(tablaDocumentoEstilo, "background",colorFondoInterno);
		Dispatch.put(tablaDocumentoEstilo, "width","520px");
		Dispatch.put(tablaDocumentoEstilo, "minWidth","520px");
		
		Dispatch colPropDinamica = Dispatch.call(documento, "getElementById","colPropDinamica").toDispatch();
		Dispatch colPropDinamicaEstilo = Dispatch.get(colPropDinamica, "style").toDispatch();
		Dispatch.put(colPropDinamicaEstilo, "display","none");
		
		Dispatch legends = Dispatch.call(documento, "getElementsByTagName","legend").getDispatch();
		 
		 int numeroInputs = Integer.valueOf(Dispatch.get(legends,"length").toString());
//		 System.out.println("Número de legends... " + numeroInputs);
		 
		 for(int i=0;i<numeroInputs;i++){
			 Dispatch leyenda = Dispatch.call(legends,String.valueOf(i)).getDispatch();
			 Dispatch leyendaEstilo = Dispatch.get(leyenda, "style").toDispatch();
			 Dispatch.put(leyendaEstilo, "width","400px");
			 Dispatch.put(leyendaEstilo, "paddingTop","40px");
		 }

		
		/* volvemos columna izquierda */ 
		Dispatch previewer = Dispatch.call(documento, "getElementById","previewer").toDispatch();
		Dispatch previewerEstilo = Dispatch.get(previewer, "style").toDispatch();
		Dispatch.put(previewerEstilo, "height","1100px");                         //     <--------------------------
		Dispatch.put(previewerEstilo, "width","800px");		 
		/* fin */
		
		Dispatch loadContexto = Dispatch.call(documento, "getElementById","loadContexto").getDispatch();
		Variant nombrePacient = Dispatch.get(loadContexto,"innerHTML");
		String nombrePaciente = nombrePacient.getString();
		
//		System.out.println("Nombre del paciente  " + nombrePaciente);
		
		int index = nombrePaciente.indexOf("(");
		if(index != -1){
			nombrePaciente = nombrePaciente.substring(0,index);
		}
		if(nombrePaciente.contains("Contexto Vacío")){
			nombrePaciente = "Contexto Vacío";
			errorDeContexto = true;
		}
		
		System.out.println("Nombre del paciente  " + nombrePaciente);
		
		
		// Otros
		Dispatch contextoMenuSuperior = Dispatch.call(documento, "getElementById","contextoMenuSuperior").toDispatch();
		Dispatch contextoMenuSuperiorEstilo = Dispatch.get(contextoMenuSuperior, "style").toDispatch();
	//	Dispatch.put(contextoMenuSuperiorEstilo, "marginRight","400px");
		
		Dispatch.put(loadContexto, "innerHTML",nombrePaciente);
		Dispatch loadContextoEstilo = Dispatch.get(loadContexto, "style").toDispatch();
	//	Dispatch.put(loadContextoEstilo, "marginLeft","-1700px");                              // <--------------------||
		Dispatch.put(loadContextoEstilo, "color","yellow");
		Dispatch.put(loadContextoEstilo, "fontSize","30px");
	//	Dispatch.put(loadContextoEstilo, "width","1100px");
		Dispatch.put(loadContextoEstilo, "padding","10px");
	//	Dispatch.put(loadContextoEstilo, "textAlign", "center");
		Dispatch.put(loadContextoEstilo, "font-weight", "bolder");
		
		Dispatch comprimirA = Dispatch.call(documento, "getElementById","selectDisplayButtonsTree").toDispatch();
		Dispatch comprimirAEstilo = Dispatch.get(comprimirA, "style").toDispatch();
		Dispatch.put(comprimirAEstilo, "left","1200px");
		Dispatch.put(comprimirAEstilo, "display","none");	
		
		Dispatch textArea = Dispatch.call(documento, "getElementById","{hc}comentario-{hc}docExt").toDispatch();
		Dispatch textAreaEstilo = Dispatch.get(textArea, "style").toDispatch();
		Dispatch.put(textAreaEstilo, "width","475px");
		Dispatch.put(textAreaEstilo, "marginLeft","20px");	
		
		
		// Cajas amarillas
		Dispatch cajasAmarillas = Dispatch.call(documento, "querySelectorAll",".custom-combobox-input").getDispatch();
		 
		 int numeroCajas = Integer.valueOf(Dispatch.get(cajasAmarillas,"length").toString());
//		 System.out.println("Número de cajas... " + numeroCajas);
		 
		 for(int i=0, conteo = 0;i<numeroCajas;i++){
			 Dispatch caja = Dispatch.call(cajasAmarillas,String.valueOf(i)).getDispatch();
			 Dispatch cajaEstilo = Dispatch.get(caja, "style").toDispatch();
			 Variant v1 = Dispatch.get(cajaEstilo, "backgroundColor");
			 
			 if(v1.toString().contains("255")){
				 if(i != 0){
					 Dispatch.put(cajaEstilo, "backgroundColor",colorFondoCajas);
					 String ide = "cajaColoreada" + conteo;
					 conteo++;
					 Dispatch.call(caja, "setAttribute", "id",ide);
				 }
				 
				 Dispatch.put(cajaEstilo, "paddingLeft","20px");
				 Dispatch.put(cajaEstilo, "marginLeft","20px");
				 Dispatch.put(cajaEstilo, "width","465px");
				 Dispatch.put(cajaEstilo, "color","red");
				 Dispatch.put(cajaEstilo, "font","bold 18px arial, sans-serif");
				 
				 if(conteo == 2){
					 break;
				 }
			 }
		 }
		 
 		 Dispatch fecha = Dispatch.call(documento, "getElementById","{hc}dataVersion-{hc}docExt").toDispatch();
 		 Dispatch fechaEstilo = Dispatch.get(fecha, "style").toDispatch();
		 Dispatch.put(fechaEstilo,  "font","bold 20px arial, sans-serif");
		 Dispatch.put(fechaEstilo, "backgroundColor",colorFondoCajas);
		 
 		 Dispatch labelAtributo = Dispatch.call(documento, "getElementById","labelAtributo").toDispatch();
 		 Dispatch labelAtributoEstilo = Dispatch.get(labelAtributo, "style").toDispatch();
		 Dispatch.put(labelAtributoEstilo, "paddingLeft","10px");
		 Dispatch.put(labelAtributoEstilo, "width","500px");
		 Dispatch.put(labelAtributoEstilo, "color","green");
 		 
		 Dispatch estiloCajas = Dispatch.call(documento, "querySelectorAll",".custom-combobox").getDispatch();
		 int tam = Integer.valueOf(Dispatch.get(estiloCajas,"length").toString());
		 
		 for(int i=0;i<tam-5;i++){
			 Dispatch estiloCaja = Dispatch.get(estiloCajas, String.valueOf(i)).toDispatch();
			 Dispatch estiloCajaEstilo = Dispatch.get(estiloCaja, "style").toDispatch();
			 Dispatch.put(estiloCajaEstilo, "width", "520px");
		 }

		 
		//  Nhc servicio documento
		 
		 Dispatch primaryNav = Dispatch.call(documento, "getElementById","primary-nav").toDispatch();
		 Dispatch anclas = Dispatch.call(primaryNav, "getElementsByTagName","a").toDispatch();
		 tam = Integer.valueOf(Dispatch.get(anclas,"length").toString());
//		 System.out.println(tam);
		 
		 for(int i=0;i<tam;i++){
			 Dispatch ancla= Dispatch.get(anclas, String.valueOf(i)).toDispatch();
	 		 Dispatch anclaEstilo = Dispatch.get(ancla, "style").toDispatch();
			 Dispatch.put(anclaEstilo, "marginRight","30px");
		//	 Dispatch.put(anclaEstilo, "marginBottom","10px");
			 Dispatch.put(anclaEstilo, "fontSize","28px");
			 Dispatch.put(anclaEstilo, "color","yellow");
			 Dispatch.put(anclaEstilo, "marginTop", "40px");
			 
			 if(i==0){
				 Dispatch.call(ancla, "setAttribute","id","nhcNav");
				 Dispatch.put(ancla, "innerHTML", "");
			 }
			 else if(i==1){
				 Dispatch.call(ancla, "setAttribute","id","servicioNav");
				 Dispatch.put(ancla, "innerHTML", "");
				 Dispatch.put(anclaEstilo, "marginRight", "0px");
			 }
			 else{
				 Dispatch.call(ancla, "setAttribute","id","tituloNav");
				 Dispatch.put(ancla, "innerHTML", "");
			 }
		 }System.out.println("El nombre del pdf de xedoc 2 es... " + InicioAltairJacob.nombrePdfXedoc2);
		 
		 String nombreCompletoPdf = Dispatch.get(labelAtributo,"innerHTML").toString();
		 
		 if(nombreXedoc.equals("Xedoc 1")){
			 InicioAltairJacob.nombrePdfXedoc1 = nombreCompletoPdf;
		 }
		 else{
			 InicioAltairJacob.nombrePdfXedoc2 = nombreCompletoPdf;
		 }
		 
		 System.out.println(nombreCompletoPdf);
		 
		 
		 Dispatch nuevaSeccionEdiciones = Dispatch.call(documento, "all","nuevaSeccionEdicion").toDispatch();
		 tam = Integer.valueOf(Dispatch.get(nuevaSeccionEdiciones,"length").toString());
		 // System.out.println(tam);
		 
		 for(int i=2;i<tam;i++){
			 Dispatch nuevaSeccionEdicion = Dispatch.get(nuevaSeccionEdiciones, String.valueOf(i)).toDispatch();
			 Dispatch nuevaSeccionEdicionEstilo = Dispatch.get(nuevaSeccionEdicion, "style").toDispatch();
			 Dispatch.put(nuevaSeccionEdicionEstilo, "display", "none");
		 }
		 
		 Dispatch colPropDinamicaAnchas = Dispatch.call(documento, "all","colPropDinamicaAncha").toDispatch();
		 tam = Integer.valueOf(Dispatch.get(colPropDinamicaAnchas,"length").toString());
		 // System.out.println(tam);
		 
		 for(int i=0;i<tam;i++){
			 Dispatch colPropDinamicaAncha = Dispatch.get(colPropDinamicaAnchas, String.valueOf(i)).toDispatch();
			 Dispatch colPropDinamicaAnchaEstilo = Dispatch.get(colPropDinamicaAncha, "style").toDispatch();
			 Dispatch.put(colPropDinamicaAnchaEstilo, "width", "300px");
			 Dispatch.put(colPropDinamicaAnchaEstilo, "textAlign", "left");
		 }
		 
		 Dispatch footer = Dispatch.call(documento, "getElementById","footer").toDispatch();
		 Dispatch footerEstilo = Dispatch.get(footer, "style").toDispatch();
		 Dispatch.put(footerEstilo, "display", "none");
		 
			boolean errorNhc = false;
			
			XedocIndividualJacob xedoc = new XedocIndividualJacob(nombreCompletoPdf, navegador);
		 
			errorNhc = putNHC(navegador.navegador, xedoc);
		 
			System.out.println("Error en el nhc..." + errorNhc);
			
						
			if(!errorNhc || maquetado){
				
				if(!errorDeContexto){
					
					xedoc.buscaNodo(!manual);
					try {
						Thread.sleep(100);
						xedoc.seleccionarServicio();
						Thread.sleep(100);
						xedoc.seleccionarDocumento();

						Thread.sleep(100);
					//	xedoc.inicializaNodosHospUrgQui();
						xedoc.gestionEventosNodos();
						
						Thread.sleep(100);
						xedoc.ocultaNodos();
						Thread.sleep(100);
						xedoc.getFocus();
						

						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			
		 
		 
		// Dispatch.call(documento, "eval","alert('hola');");
		
	}
	
	public void maquetado01(){
		
		String fondoYEncabezado = ""
				+ ""
				+ "var fondoPagina = document.getElementById('page');"
				+ "fondoPagina.style.background = '" + colorFondo + "';" 
				+ ""
				+ "var botonSalir = document.getElementById('botonSalir');"
				+ "botonSalir.innerHTML = '" + nombreXedoc + "';"
				+ "botonSalir.style.font = 'bold 28px arial, sans-serif';"
				+ "botonSalir.style.color = 'red';"
				+ ""
				+ "var entornoLogin = document.getElementById('entornoLogin');"
				+ "entornoLogin.style.display = 'none';"
				+ ""
				+ "var branding = document.getElementById('branding');"
				+ "branding.style.display = 'none';"
				+ ""
				+ "var header = document.getElementById('header');"
				+ "header.style.height = '0px';"
				+ "";
		
		//  Columna Izquierda
		
		
		
		
		String columnaIzquierda = ""
				+ ""
				+ "var columnaI = document.getElementById('columnaIzquierdaEdicion');"
				+ "columnaI.style.height = '1200px';"
				+ "columnaI.style.width = '800px';"
				+ ""
				+ "var completePreview = document.getElementById('completePreview');"
				+ "completePreview.style.height = '1200px';"
				+ "completePreview.style.width = '800px';"
				+ ""
				+ "var previewer = document.getElementById('previewer');"
				+ "if(previewer != null && previewer != undefined){"
					+ "previewer.style.height = '1200px';"
					+ "previewer.style.width = '800px';"
				+ "}"

				+ "";
		
		
		Dispatch.call(navegador.navegador, "navigate","javascript:" + fondoYEncabezado 
				+ columnaIzquierda 
				+ "alert('hola 1');");
		
		
		//  Columna Derecha
		
		String ancho = "";
		String altoArbol = "1000px";
		String altoTablameritos = "1000px";
		
		if(numeroPantallas == 1){
			ancho = "800px";
			altoArbol = "830px";
			altoTablameritos = "850px";
		}
		else{
			ancho = "960px";
		}
		
		String columnaDerecha1 = ""
				+ ""
				+ "var divOcultarAsociados = document.getElementById('divOcultarAsociados');"
				+ "divOcultarAsociados.style.display = 'none';"
				+ ""
				+ "var columnaD = document.getElementById('columnaDerechaEdicion');"
				+ "columnaD.style.width = '1000px';"
				+ "columnaD.style.marginLeft = '" + ancho + "';"
				
				
				+ "var tablaAtributos = document.getElementById('tablaAtributos');"
				+ "tablaAtributos.style.border = 'none';"
				+ "tablaAtributos.style.background = '" + colorFondoInterno + "';"
				+ "tablaAtributos.style.color = '#000000';"
				+ "tablaAtributos.style.minWidth = '520px';"
				+ "tablaAtributos.style.width = '520px';"
				+ ""
				+ "var edicionForm = document.getElementById('edicionForm');"
				+ "edicionForm.style.width = '1000px';"
				
				+ "var tablaElementosAjax = document.getElementById('tablaElementosAjax');"
				+ "tablaElementosAjax.style.marginTop = '-70px';"
				+ "tablaElementosAjax.style.height = '" + altoArbol + "';"
				+ "tablaElementosAjax.style.width = '450px';"
				
				+ "";
		
		String columnaDerecha2 = ""
				+ ""
				
				+ "var tablaMeritos = document.getElementById('tablaMeritos');"
				+ "tablaMeritos.style.background = '" + colorFondoInterno + "';"
				+ "tablaMeritos.style.border = 'none';"
				+ "tablaMeritos.style.height = '" + altoTablameritos + "';"
				+ "tablaMeritos.style.minWidth = '400px';"
				+ ""
				+ "var arbol = document.getElementById('arbol');"
				+ "arbol.style.height = '" + altoArbol + "';"
				+ "arbol.style.width = '440px';"
				+ "arbol.style.background = '" + colorFondoInterno + "';"
				+ ""
				
				+ "var tablaAtributosAjax = document.getElementById('tablaAtributosAjax');"
				+ "tablaAtributosAjax.style.marginLeft = '470px';"
			//	+ "tablaAtributosAjax.style.marginTop = '-930px';"
				+ "tablaAtributosAjax.style.miWidth = '500px';"
				
				+ "var colPropDinamica = document.getElementById('colPropDinamica');"
				+ "colPropDinamica.style.display = 'none';"
				
				+ "var tablaDocumento = document.getElementById('tablaDocumento');"
				+ "tablaDocumento.style.background = '" + colorFondoInterno + "';"
				+ "tablaDocumento.style.border = 'none';"
				+ "tablaDocumento.style.width = '520px';"
				+ "tablaDocumento.style.minWidth = '520px';"
				
				+ "var legend = document.getElementsByTagName('legend');"
		//		+ "alert(legend.length);"
				+ "for(var i=0;i<legend.length;i++){"
					+ "legend[i].style.width = '400px';"
					+ "legend[i].style.paddingTop = '40px';"
				+ "}";
		
		if(InicioAltairJacob.numeroPantallas == 1){
			columnaDerecha2 += "tablaAtributosAjax.style.marginTop = '-759px';";
		}
		else{
			columnaDerecha2 += "tablaAtributosAjax.style.marginTop = '-930px';";
		}
		
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		Dispatch documento = Dispatch.call(navegador, "document").getDispatch();
		Dispatch loadContexto = Dispatch.call(documento, "getElementById","loadContexto").getDispatch();
		Dispatch.put(loadContexto,"innerHTML","Holaaaaa");
		*/
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Dispatch.call(navegador.navegador, "navigate","javascript:" 
				+ columnaDerecha1 
				+ columnaDerecha2  + "alert('hola 2');");
		
		
		Dispatch documento = Dispatch.call(navegador.navegador, "document").getDispatch();
		Dispatch loadContexto = Dispatch.call(documento, "getElementById","loadContexto").getDispatch();
		Variant nombrePacient = Dispatch.get(loadContexto,"innerHTML");
		String nombrePaciente = nombrePacient.getString();
		/*
		WebElement paciente = driver.findElement(By.id("loadContexto"));
		String nombrePaciente = paciente.getAttribute("innerHTML").trim();
		*/
		
		System.out.println("Nombre del paciente  " + nombrePaciente);
		
		int index = nombrePaciente.indexOf("(");
		if(index != -1){
			nombrePaciente = nombrePaciente.substring(0,index);
		}
		if(nombrePaciente.contains("Contexto Vacío")){
			nombrePaciente = "Contexto Vacío";
			errorDeContexto = true;
		}
		
		System.out.println("Nombre del paciente  " + nombrePaciente);

/*		
		List<WebElement> nuevaSeccionEdicion = driver.findElements(By.id("nuevaSeccionEdicion"));
		
		System.out.println(nuevaSeccionEdicion.size());
*/		
		
		String otros1 = ""
				+ ""
				
				+ "var contextoMenuSuperior = document.getElementById('contextoMenuSuperior');"
				+ "contextoMenuSuperior.style.marginRight = '400px';"
				+ ""
				+ "var loadContexto = document.getElementById('loadContexto');"
				+ "loadContexto.innerHTML = '" + nombrePaciente + "';"
				+ "loadContexto.style.marginLeft = '-1000px';"
				+ "loadContexto.style.width = '800px';"
				+ "loadContexto.style.color = 'yellow';"
				+ "loadContexto.style.fontSize = '25px';"
				+ ""
				
				+ "var comprimirA = document.getElementById('selectDisplayButtonsTree');"
				+ "comprimirA.style.left = '1200px';"
				+ "comprimirA.style.display = 'none';"
				
				+ "var comprimirB = document.getElementById('selectDisplayButtonsTree');"
				+ "comprimirB.style.left = '1200px';"
				+ "comprimirB.style.display = 'none';"
				
				+ "var textArea = document.getElementById('{hc}comentario-{hc}docExt');"
				+ "textArea.style.width = '475px';"
				+ "textArea.style.marginLeft = '20px';"
				;
		
		
		String codigo = fondoYEncabezado + columnaIzquierda;
				
		
		/*
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(codigo);
		*/
		
		
		 
		 
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
				Dispatch.call(navegador.navegador, "navigate","javascript:" 
				+ otros1  + "alert('hola 3');");
		
		/*
		js.executeScript(columnaDerecha1);
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		driver.findElement(By.id("tablaMeritos"));
		
		js.executeScript(columnaDerecha2);
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		js.executeScript(otros1);
*/
		
		String cajasAmarillas = ""
				+ ""
				+ "var cajasAmarillas = document.getElementsByClassName('custom-combobox-input');"
				+ "var tam = cajasAmarillas.length;"
				+ "var conteo = 0;"
				+ "for(var i=0;i<tam-2;i++){"
					+ "var comparacion = cajasAmarillas[i].style.backgroundColor.search('255');"
					// + "alert(cajasAmarillas[i].style.backgroundColor);"
					+ "if(comparacion != -1){"
						+ "if(i != 0){"
							+ "cajasAmarillas[i].style.backgroundColor = '" + colorFondoCajas + "';"
							+ "var ide = 'cajaColoreada' + conteo;"
							+ "conteo = conteo + 1;"
							+ "cajasAmarillas[i].setAttribute('id',ide);"						
						+ "}"

						+ "cajasAmarillas[i].style.paddingLeft = '20px';"
						+ "cajasAmarillas[i].style.marginLeft = '20px';"
						+ "cajasAmarillas[i].style.width = '465px';"
						+ "cajasAmarillas[i].style.color = 'red';"
						+ "cajasAmarillas[i].style.font = 'bold 18px arial, sans-serif';"

						+ "if(conteo == 2){"
						+ 	"break;"
						+ "}"
					+ "}"
				+ "}"
				+ "var fecha = document.getElementById('{hc}dataVersion-{hc}docExt');"
				+ "fecha.style.backgroundColor = '" + colorFondoCajas + "';"
				+ "fecha.style.font = 'bold 20px arial, sans-serif';"
				+ ""
				+ "var labelAtributo = document.getElementById('labelAtributo');"
				+ "labelAtributo.style.width = '500px';"
				+ "labelAtributo.style.color = 'green';"
				+ "labelAtributo.style.paddingLeft = '10px';"
				+ ""
				+ "var estiloCajas = document.getElementsByClassName('custom-combobox');"
				+ "var tam = estiloCajas.length;"
				// + "alert(tam);"
				+ "for(var i=0;i<tam-5;i++){"
		//			+ "document.getElementById('{hc}titulo-{hc}docExt').value = tam;"
					+ "estiloCajas[i].style.width = '520px';"
				+ "}"
				+ "";
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Dispatch.call(navegador.navegador, "navigate","javascript:" 
		+ cajasAmarillas  + "alert('hola 4');");
		
		
	}
	
	public boolean putNHC(ActiveXComponent navegador, XedocIndividualJacob xedoc){
		
		// System.out.println("Colocando nhcs... ");
		
		Dispatch documento = Dispatch.call(navegador,"document").toDispatch();
		Dispatch nhcF = Dispatch.call(documento,"getElementById","nhcNav").toDispatch();
		Dispatch servF = Dispatch.call(documento,"getElementById","servicioNav").toDispatch();
		Dispatch nombreF = Dispatch.call(documento,"getElementById","tituloNav").toDispatch();
		
		Dispatch.put(nhcF, "innerHTML", xedoc.nhc);
		Dispatch.put(servF, "innerHTML", xedoc.servicio);
		Dispatch.put(nombreF, "innerHTML", xedoc.nombreDocumento);
		
		Dispatch loadContexto = Dispatch.call(documento, "getElementById","loadContexto").toDispatch();
		String nombrePaciente = Dispatch.get(loadContexto, "innerHTML").toString();
		
		Dispatch nhcFEstilo = Dispatch.get(nhcF,"style").toDispatch();
	//	Dispatch.put(nhcFEstilo, "marginLeft", "980px");
		
		if(nombrePaciente.contains(xedoc.nhc)){
			Dispatch.put(nhcFEstilo, "color", "yellow");
			return false;
		}
		else{
			Dispatch.put(nhcFEstilo, "color", "red");
			return true;
		}
		
	}
}
