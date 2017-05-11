package es.mgamallo.altair;

import java.util.ArrayList;
import java.util.TreeMap;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;


public class XedocIndividualJacob {

	public static final String CONTINXENCIA = "Sen tipo(Continxencia)";
	
	public static final String INFORME_ALTA_URG = "Informe alta";
	public static final String INFORME_ALTA_URG_XEDOC = "Informe alta (URG)";
	
	public static final String AUTOSET = "Autoset";
	public static final String ESCALA_VALORACION = "Escala valoración funcional";
	public static final String EVALUACION_DA_SAUDE = "Evaluación da saude";
	public static final String FICHA_REHAB = "Ficha rehabilitación";
	public static final String FOTOTEST = "Fototest";
	public static final String TRANSFUSION = "Transfusión";
	public static final String FISIOTERAPIA_EXT = "Informe fisioterapia centro externo";
	public static final String PROTOCOLO_PCA = "Protocolo PCA";
	public static final String PROTOCOLO_PRECIRURXICO = "Protocolo precirúrxico";
	public static final String PROTOCOLO_VERTIGO = "Protocolo vértigo";

	public static final String SOLICITUDE = "Solicitude";
	public static final String TEST_UREASA = "Test de ureasa";

	private boolean excepcionTipo = false;
	
	private final String SERVICIO_DESCONOCIDO = "Des";
	
	private final String COLORFONDOCAJAS = "RGB(253,247,133)";
	
	public String nhc = "";
	public String servicio = "";
	
	private String aliasServicio = "";
	private String nombreServicio = "";
	
	public String nombreDocumento = "";
	public String tipoDocumento = "";
	public String titulo = "";
	private TreeMap<String, String> nombreDocumentosXedoc = new TreeMap<String, String>();
	private TreeMap<String, String> nombreDocumentosPrometeo = new TreeMap<String, String>();
	private TreeMap<String, String> nombreServicios = new TreeMap<String, String>();
	private boolean tieneTitulo = false;
	
	public String fecha = "";
	
	public String carpeta = "";
	public String nombreFichero = "";
	public int numeroDelPdf = 0;
	
	public String tipoSubida = "";       // x q i f e
	
		
	ActiveXComponent navegador;
	Dispatch documento;
	
	public XedocIndividualJacob(String nombreFichero, Navegador navegador){
		
		this.nombreFichero = nombreFichero;
		this.navegador = navegador.navegador;
		this.numeroDelPdf = navegador.numeroPdf +1;
		this.documento = Dispatch.call(this.navegador, "document").toDispatch();
		
		// System.out.println("Navegador.numeroPdf: " + navegador.numeroPdf);
		// System.out.println("Nº del Pdf: " + numeroDelPdf);
		
		
/*  Seguramente borrar  */
		LeerExcel leerExcel = new LeerExcel();
		leerExcel.leer("Documentos.xls");
//////////////////////////////////////////////////////	
		
		InicioAltairJacob.tablaDocumentos = leerExcel.getTabla();
		
		nombreDocumentosXedoc = leerExcel.nombreDocumentosXedoc;
		nombreDocumentosPrometeo = leerExcel.nombreDocumentosPrometeo;
		nombreServicios = leerExcel.nombreServicios;
		
		String nombreFicheroCorregido = nombreFichero.replaceAll(" _", "_");
		
		
		if(!nombreFichero.contains("@v1_")){
			
			String campos[] = null;
			
			campos = nombreFichero.split(" @");
			if(campos.length == 4){
				nhc = campos[1];
				servicio = campos[2];
				
				if(servicio.length() > 4){
					int blanco = servicio.indexOf(" ");
					fecha = servicio.substring(blanco + 1);
					servicio = servicio.substring(0,blanco);
					
					fecha = fecha.substring(0,2) + "/" + fecha.substring(2,4) + "/" + fecha.substring(4);
				}
				
				int index = campos[0].indexOf("$");
				if(index != -1){
					carpeta = campos[0].substring(index+1);
				}

				int indexf = campos[3].lastIndexOf(" r_f");
				campos[3] = campos[3].substring(0,indexf);
				
				nombreDocumento = campos[3];
				
				index = campos[3].indexOf("(");
				if(index != -1){
					tipoDocumento = campos[3].substring(0,index-1);
					titulo = campos[3].substring(index + 1,campos[3].length()-1);
					tieneTitulo = true;
				}
				else{
					tipoDocumento = campos[3];
				}
			}
		}
		else{
						
			String campos[] = null;
			
			campos = nombreFichero.split(" @v");
			
			System.out.println("Nº de campos " + campos.length);
			
			int fi;
			
			for(int k = 1;k<campos.length;k++){
				char c = campos[k].charAt(0);
				// System.out.println("Caracter " + c);
				switch (c) {
				case '1':
					nhc = campos[k].substring(2);
					break;
				case '2':
					fi = campos[k].indexOf(".pdf");
					if(fi == -1){
						tipoDocumento = campos[k].substring(2);
					}
					else{
						tipoDocumento = campos[k].substring(2,fi);
					}
					break;
				case '4':
					fi = campos[k].indexOf(".pdf");
					if(fi == -1){
						servicio = campos[k].substring(2);
					}
					else{
						servicio = campos[k].substring(2,fi);
					}
					break;
				case '5':
					fi = campos[k].indexOf(".pdf");
					if(fi == -1){
						titulo = campos[k].substring(2);
					}
					else{
						titulo = campos[k].substring(2,fi);
					}
					break;
				case '6':
					fi = campos[k].indexOf(".pdf");
					if(fi == -1){
						fecha = campos[k].substring(2);
					}
					else{
						fecha = campos[k].substring(2,fi);
					}
					
					fecha = fecha.substring(0,2) + "/" + fecha.substring(2,4) + "/" + fecha.substring(4);
					break;

				default:
					break;
				}
			}
			
			if(titulo.length() > 0){
				nombreDocumento = tipoDocumento + " (" + titulo + ")";
			}
			else{
				nombreDocumento = tipoDocumento;
			}
			
			
		}
		

		
		excepcionTipo = false;
		
		// System.out.println("Nombre antes de la conversión a Xedoc... " + tipoDocumento);
		
		String aux = nombreDocumentosPrometeo.get(tipoDocumento);
		if(aux != null){
			tipoDocumento = aux;
		}
		
		// System.out.println("Nombre después de la conversión a Xedoc... " + tipoDocumento);
		
		if(!nombreDocumentosXedoc.containsKey(tipoDocumento)){
			
			if(tipoDocumento.equals(INFORME_ALTA_URG)){
				tipoDocumento = INFORME_ALTA_URG_XEDOC;
			}
			else{
				titulo = tipoDocumento;
				/*
				if(tipoDocumento.equals(AUTOSET) 
						|| tipoDocumento.equals(ESCALA_VALORACION)
						|| tipoDocumento.equals(EVALUACION_DA_SAUDE)
						|| tipoDocumento.equals(FICHA_REHAB)
						|| tipoDocumento.equals(FOTOTEST)
						|| tipoDocumento.equals(TRANSFUSION)
						|| tipoDocumento.equals(FISIOTERAPIA_EXT)
						|| tipoDocumento.equals(PROTOCOLO_PCA)
						|| tipoDocumento.equals(PROTOCOLO_PRECIRURXICO)
						|| tipoDocumento.equals(PROTOCOLO_VERTIGO)
						|| tipoDocumento.equals(SOLICITUDE) 
						|| tipoDocumento.equals(TEST_UREASA)){
					tipoDocumento = " ";
					excepcionTipo = true;
				}
				else{
				*/
					tipoDocumento = CONTINXENCIA;
				
				tieneTitulo = true;
			}

		}
		
		// System.out.println("Servicio... " + servicio + ". campos3... " + nombreDocumento);
		tipoSubida = detectaTipoNodo(servicio, nombreDocumento);
		// System.out.println("El tipo de subida es... " + tipoSubida);
	}
	
	
	private static String detectaTipoNodo(String servicio, String nombreDocumento){
		
		String tipoNodo = "x";    // Nodo padre, sin excepciones
		
		for(int i=0;i<InicioAltairJacob.tablaDocumentos[0].length;i++){
			if(InicioAltairJacob.tablaDocumentos[0][i].equals(servicio)){
				for(int j=0;j<InicioAltairJacob.tablaDocumentos.length;j++){
					if(InicioAltairJacob.tablaDocumentos[j][0].equals(nombreDocumento)){
						tipoNodo = InicioAltairJacob.tablaDocumentos[j][i];
						break;
					}
				}
			}
		}
		
		return tipoNodo;
	}
	
	
	public void buscaNodo(boolean auto){
		
		String id = "";
	
		// System.out.println(servicio);
		// System.out.println(tipoSubida);
		
		if(servicio.equals("HOSP")){
			id = "HOS";
			if(tipoSubida.equals("q")){
				id = "QUI";
			}
		}
		else if(servicio.equals("URG")){
			id = "URG";
		}
		else if(servicio.equals("CIA")){
			id = "QUI";
			if(!tipoSubida.equals("q")){
				id = "HOS";
			}
		}
		else{
			id = servicio;
		}

		if(auto){
		// Resalta nombre paciente
			Dispatch nodoLin = Dispatch.call(documento, "getElementById",InicioAltairJacob.codigoCentro + "-14--" + nhc).toDispatch();
			Dispatch nodosAn = Dispatch.call(nodoLin, "getElementsByTagName","a").toDispatch();
			Dispatch nodoAn = Dispatch.get(nodosAn,"0").toDispatch();
			Dispatch nodoAnEstilo = Dispatch.get(nodoAn,"style").toDispatch();
			Dispatch.put(nodoAnEstilo,"color","green");
			Dispatch.put(nodoAnEstilo,"font-weight","bolder");
			Dispatch.put(nodoAnEstilo,"font-size","14px");
		}
	//	System.out.println(id);
		
		if(id.equals("HOS") || id.equals("URG") || id.equals("QUI")){
			
			
			String cadenaServicio = "";
			String nombreId = id + "-noSeleccionable-rama";
			// System.out.println(nombreId);
			
			Variant nodoVariant = Dispatch.call(documento, "getElementById",nombreId);
			// System.out.println(nodoVariant.toString());
			
			if(!nodoVariant.toString().equals("null")){

				Dispatch nodo = Dispatch.call(documento, "getElementById",nombreId).toDispatch();
				
				Variant nodoComprobar = Dispatch.get(nodo, "innerHTML");
				if(!nodoComprobar.toString().equals("null")){
				
					Dispatch nodoLis = Dispatch.call(nodo, "getElementsByTagName","li").toDispatch();
					int numeroDeUls = Integer.valueOf(Dispatch.get(nodoLis, "length").toString());
					Dispatch nodoLi = Dispatch.get(nodoLis, "0").toDispatch();
					String episodioHosp = Dispatch.get(nodoLi,"innerHTML").toString();
					
					if(episodioHosp.contains("HADO")){
						if(numeroDeUls > 1){
							nodoLi =  Dispatch.get(nodoLis, "1").toDispatch();
						}
					}
					
					Dispatch nodoA = Dispatch.call(nodoLi, "getElementsByTagName","a").toDispatch();
					Dispatch nodoAncla = Dispatch.get(nodoA, "0").toDispatch();
					Dispatch nodoAnclaEstilo = Dispatch.get(nodoAncla, "style").toDispatch();
					Dispatch.put(nodoAnclaEstilo, "fontWeight","bolder");
					Dispatch.put(nodoAnclaEstilo, "color", "red");
					Dispatch.call(nodoAncla, "setAttribute","id","nodoSeleccionado");
				//	Dispatch.call(nodoAncla, "click");
					
					nodoAncla = Dispatch.call(documento, "getElementById","nodoSeleccionado").toDispatch();
						Dispatch.call(navegador, "navigate","javascript:document.getElementById('nodoSeleccionado').click()");
					
					
					if(id.equals("HOS") || id.equals("URG")){
						String cadena = Dispatch.get(nodoAncla,"innerHTML").toString();
						int index = cadena.lastIndexOf("/");
						if(index != -1){
							String fecha = cadena.substring(index-5,index+5);
							Dispatch fechaVersion = Dispatch.call(documento, "getElementById","{hc}dataVersion-{hc}docExt").toDispatch();
							Dispatch.put(fechaVersion, "value", fecha);
						}
						
						index = cadena.lastIndexOf(">") + 1;
						cadenaServicio = cadena.substring(index);
						// System.out.println(cadenaServicio);
					}
					else{
						String cadena = Dispatch.get(nodoAncla,"innerHTML").toString();
						int index = cadena.lastIndexOf("/");
						String fecha = cadena.substring(index-5,index+5);
						Dispatch fechaVersion = Dispatch.call(documento, "getElementById","{hc}dataVersion-{hc}docExt").toDispatch();
						Dispatch.put(fechaVersion, "value", fecha);
						index = cadena.lastIndexOf(">") + 1;
						cadenaServicio = cadena.substring(index);
						// System.out.println(cadenaServicio);
						
					}
					
					
					
					
					
				}	
				else{
					cadenaServicio = "noexiste";
				}
		
				
				cadenaServicio = cadenaServicio.replaceAll("\\s", "");	
				// System.out.println("CadenaServicio ....  " + cadenaServicio);
				
				if(id.equals("HOS")){
					aliasServicio = cadenaServicio.substring(4,8);
				}
				else if(id.equals("URG")){
					aliasServicio = cadenaServicio.substring(0,4);
				}
				else if(id.equals("QUI")){
					aliasServicio = cadenaServicio.substring(0,4);
				}
				else{
					// System.out.println("El nodo no existe");
				}
				// System.out.println("Alias del Servicio " + aliasServicio);
			}
		}
		else{
			// Es una consulta y hay que saber primero si va a ir al nodo general o no
			// Programar
		
			if(!servicio.equals(SERVICIO_DESCONOCIDO)){
				aliasServicio = servicio;
				
				String cadena = "";
				
				id = servicio + "-noSeleccionable-rama";
				String idConsultaGeneral = InicioAltairJacob.codigoCentro + "-1-2-" + servicio;
				
				// System.out.println("  " + idConsultaGeneral);
				// System.out.println("  " + tipoSubida);
				
				boolean alaFecha;
				// System.out.println("Tipo de subida... " + tipoSubida);
				if(tipoSubida.equals("f") || tipoSubida.equals("e") || tipoSubida.equals("q") ){
					alaFecha = true;
				}
				else{
					alaFecha = false;
				}
			
				Variant nodoVariant = Dispatch.call(documento, "getElementById",id);
				// System.out.println(nodoVariant.toString());
				
				if(!nodoVariant.toString().equals("null")){
					Dispatch nodo = Dispatch.call(documento, "getElementById",id).toDispatch();
					
					// Comprobar que exite nodo
					Variant nodoComprobar = Dispatch.get(nodo, "innerHTML");
					if(!nodoComprobar.toString().equals("null")){
						if(alaFecha){
							Dispatch nodoLis = Dispatch.call(nodo, "getElementsByTagName","li").toDispatch();
							int tamNodoLis = Integer.valueOf(Dispatch.get(nodoLis, "length").toString());
							for(int i=1;i<tamNodoLis;i++){
								Dispatch nodoLi = Dispatch.get(nodoLis, String.valueOf(i)).toDispatch();
								String innerHTML = Dispatch.get(nodoLi,"innerHTML").toString();
								if(!innerHTML.contains("CC -")){
									Dispatch nodoA = Dispatch.call(nodoLi, "getElementsByTagName","a").toDispatch();
									Dispatch nodoAncla = Dispatch.get(nodoA, "0").toDispatch();
									
									// Saber si existe o no nodoAncla
									Dispatch nodoAnclaEstilo = Dispatch.get(nodoAncla, "style").toDispatch();
									Dispatch.put(nodoAnclaEstilo, "fontWeight","bolder");
									Dispatch.put(nodoAnclaEstilo, "color", "red");
									Dispatch.call(nodoAncla, "setAttribute","id","nodoSeleccionado");
									//	Dispatch.call(nodoAncla, "click");
									
									nodoAncla = Dispatch.call(documento, "getElementById","nodoSeleccionado").toDispatch();
									Dispatch.call(navegador, "navigate","javascript:document.getElementById('nodoSeleccionado').click()");
									// fin de la condicion
									
									break;
								}
							}
						}
						else{
							 nodo = Dispatch.call(documento, "getElementById",idConsultaGeneral).toDispatch();
							 Dispatch nodoA = Dispatch.call(nodo, "getElementsByTagName","a").toDispatch();
							 Dispatch nodoAncla = Dispatch.get(nodoA, "0").toDispatch();
								
							 // Saber si existe o no nodoAncla
								Dispatch nodoAnclaEstilo = Dispatch.get(nodoAncla, "style").toDispatch();
								Dispatch.put(nodoAnclaEstilo, "fontWeight","bolder");
								Dispatch.put(nodoAnclaEstilo, "color", "red");
								Dispatch.call(nodoAncla, "setAttribute","id","nodoSeleccionado");
							//	Dispatch.call(nodoAncla, "click");
								Dispatch.call(navegador, "navigate","javascript:document.getElementById('nodoSeleccionado').click()");

						}
					}		
				}
				else{
					 Dispatch nodo = Dispatch.call(documento, "getElementById",idConsultaGeneral).toDispatch();
					 Dispatch nodoA = Dispatch.call(nodo, "getElementsByTagName","a").toDispatch();
					 Dispatch nodoAncla = Dispatch.get(nodoA, "0").toDispatch();
						
					 // Saber si existe o no nodoAncla
						Dispatch nodoAnclaEstilo = Dispatch.get(nodoAncla, "style").toDispatch();
						Dispatch.put(nodoAnclaEstilo, "fontWeight","bolder");
						Dispatch.put(nodoAnclaEstilo, "color", "red");
						Dispatch.call(nodoAncla, "setAttribute","id","nodoSeleccionado");
					//	Dispatch.call(nodoAncla, "click");
						Dispatch.call(navegador, "navigate","javascript:document.getElementById('nodoSeleccionado').click()");

				}
			}
					
			if(fecha.length() > 0){
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Dispatch fechaVersion = Dispatch.call(documento, "getElementById","{hc}dataVersion-{hc}docExt").toDispatch();
					Dispatch.put(fechaVersion, "value", fecha);
			}

		}

	}
	
	public void seleccionarServicio(){
		
		// A partir del alias del servicio, obtenemos un rango de indices (7) donde buscarlo en el select
		
		if(!servicio.equals(SERVICIO_DESCONOCIDO)){
			
			String nombreCompletoServicioOpcion = aliasServicio + "-" + ((String) nombreServicios.get(aliasServicio));
			
			// System.out.println(nombreCompletoServicioOpcion);
			
			Dispatch selectServicio = Dispatch.call(documento, "getElementById","{hc}servicioEspecialidad-{hc}docExt").toDispatch();
			
			String opciones = Dispatch.get(selectServicio,"innerHTML").toString();
			Dispatch opcionesSelect = Dispatch.call(selectServicio, "getElementsByTagName","option").toDispatch();
			
		//	System.out.println(opciones);
		//	System.out.println("**************************************");
			String arrayOpciones[] = opciones.split("<option value=");
			for(int i=0;i<arrayOpciones.length;i++){
				if(arrayOpciones[i].contains(nombreCompletoServicioOpcion)){
					Dispatch opcion = Dispatch.get(opcionesSelect, String.valueOf(i-1)).toDispatch();
					String cadena = Dispatch.get(opcion, "innerHTML").toString();
					if(cadena.equals(nombreCompletoServicioOpcion)){
						Dispatch.put(opcion, "selected", "true");
						Dispatch cajaColoreada1 = Dispatch.call(documento, "getElementById","cajaColoreada1").toDispatch();
						Dispatch.put(cajaColoreada1, "value", nombreCompletoServicioOpcion);
					}

		//			System.out.println(nombreCompletoServicioOpcion);
					break;
				}
			}

		}
	}	
		
	
		public void seleccionarDocumento(){
			
			// System.out.println("Excepción:  " + excepcionTipo);
			// System.out.println(tipoDocumento + " antes de entrar en la condicion");
			
			if(!excepcionTipo){
				// String nombreDocumentoOpcion = (String) nombreDocumentos.get(tipoDocumento);
				
				// System.out.println(tipoDocumento);
				
				Dispatch selectTipoDocumento = Dispatch.call(documento, "getElementById","{hc}codDocEx-{hc}docExt").toDispatch();
				
				String opciones = Dispatch.get(selectTipoDocumento,"innerHTML").toString();
				Dispatch opcionesSelect = Dispatch.call(selectTipoDocumento, "getElementsByTagName","option").toDispatch();
				
			//	System.out.println(opciones);
			//	System.out.println("**************************************");
				String arrayOpciones[] = opciones.split("<option value=");
				for(int i=0;i<arrayOpciones.length;i++){
					if(arrayOpciones[i].contains(tipoDocumento)){
						Dispatch opcion = Dispatch.get(opcionesSelect, String.valueOf(i-1)).toDispatch();
						String cadena = Dispatch.get(opcion, "innerHTML").toString();
						if(cadena.equals(tipoDocumento)){
							Dispatch.put(opcion, "selected", "true");
							Dispatch cajaColoreada0 = Dispatch.call(documento, "getElementById","cajaColoreada0").toDispatch();
							Dispatch.put(cajaColoreada0, "value", tipoDocumento);
						}

			//			System.out.println(nombreCompletoServicioOpcion);
						break;
					}
				}

			}

			if(titulo.length() > 0){
				Dispatch cajaTitulo = Dispatch.call(documento, "getElementById","{hc}titulo-{hc}docExt").toDispatch();
				Dispatch cajaTituloEstilo = Dispatch.get(cajaTitulo, "style").toDispatch();
				Dispatch.put(cajaTituloEstilo, "width", "250px");
				Dispatch.put(cajaTitulo, "value", titulo);				
				Dispatch.put(cajaTituloEstilo, "backgroundColor", COLORFONDOCAJAS);
				Dispatch.put(cajaTituloEstilo, "font", "bold 18px arial, sans-serif");
				

			}

			
			
		}


		public void gestionEventosNodos(){
			
						
			String cadenaSelOff = ""
					+ ""
					+ "window.selOff = function(nod){"
						+ "nod.setAttribute('id','no');"
						+ "nod.style.fontWeight = 'normal';"
						+ "nod.style.color = 'black';"
					+ "}"
					+ "";
			
			String selOff = ""
					+ ""
					+ "window.selOff;"
					+ ""
					+ "setTimeout(function(){ " + cadenaSelOff + ";  }, 200);"
					+ "";
			
			String cadenaSelOn = ""
					+ ""
					+ "window.selOn = function(nodo){"
						+ "var nodoS = document.getElementById('nodoSeleccionado');"
						+ "if(nodoS != null && nodoS != undefined){"
							+ "nodoS.click();"
							+ "selOff(nodoS);"
						+ "}"
						+ "if(nodo.id != 'nodoSeleccionado'){"
							+ "nodo.setAttribute('id','nodoSeleccionado'); "
							+ "nodo.style.fontWeight = 'bolder';"
							+ "nodo.style.color = 'red';"
						+ "}"
						+ "else{"
							+ "selOff(nodo);"
						+ "}"
					+ "}"

	//							+ "document.getElementById('submitFormFirmar').focus();"
					
					+ "";
			
			String selOn = ""
					+ ""
					+ "window.selOn;"
					+ ""
					+ "setTimeout(function(){ " + cadenaSelOn + ";  }, 400);"
					+ "";
			
			String cadenaSelNodo = ""
					+ ""
					+ "window.selNodo = function(nodo,tipN){"
						+ "if(nodo.id == 'nodoSeleccionado'){"
							+ "selOff(nodo);"
						+ "}"
						+ "else{"
							+ "selOn(nodo);"
						+ "}"
						+ ""
						+ "selFecha(nodo,tipN);"
					+ "}"

					+ "";
			
			String selNodo = ""
					+ ""
					+ "window.selNodo;"
					+ ""
					+ "setTimeout(function(){ " + cadenaSelNodo + ";  }, 600);"
					+ ""
					+ ""
					+ "";
			
			
			String cadenaSelServ = ""
					+ ""
					+ "window.selServ = function(servicio){"
					+ "var selectServicio = document.getElementById('{hc}servicioEspecialidad-{hc}docExt');"
					+ "var opciones = selectServicio.options;"
					+ "for(var i = 0;i<opciones.length;i++){"
						+ "if(opciones[i].text.search(servicio) == 0){"
								+ "opciones[i].selected = 'true';"
								+ "document.getElementById('cajaColoreada1').value = opciones[i].innerHTML;"
								+ "break;"
						+ "}"
					+ "}"
	
					+ "document.getElementById('submitFormFirmar').focus();"
					+ ""
				+ "}"
					+ "";
			
			
			String selServ = ""
					+ ""
					+ "window.selServ;"
					+ ""
					+ "setTimeout(function(){ " + cadenaSelServ + ";  }, 800);"
					+ ""
					+ "";
			
			
			String cadenaSelFecha = ""
					+ ""
					+ "window.selFecha = function(nodo,tipN){"
						+ "var cadena = nodo.innerHTML;"
						+ "var ind = cadena.lastIndexOf('/ins>') + 2;"
						+ "cadena = cadena.slice(ind);"
						+ "var index = cadena.lastIndexOf('/');"
						+ "if(index != -1){"
							+ "var fecha = cadena.slice(index-5,index+5);"
							+ "document.getElementById('{hc}dataVersion-{hc}docExt').value = fecha;"
							//+ "alert(fecha);"
						+ "}"
						+ ""
						+ "index = cadena.lastIndexOf('>') + 1;"
						+ "cadena = cadena.slice(index);"
						+ "cadena = cadena.trim();"
						+ ""

						+ "var servicio = '';"
						+ "switch (tipN) {"
							+ "case 'h': servicio = cadena.slice(4,8);break;"
							+ "case 'u': servicio = cadena.slice(0,4);break;"
							+ "case 'q': var index2 = cadena.indexOf('-');servicio = cadena.slice(index2-4,index2);break;"
							+ "case 'c': var padre = nodo.parentNode;"
										+ "var cadenaPadre = padre.id;"
										+ "if(cadenaPadre.search('" + InicioAltairJacob.codigoCentro + "-1-2') != -1){"
											+ "servicio = cadenaPadre.slice(-4);"
											+ "if(servicio[0] == '-'){"
												+ "servicio = servicio.slice(1);"
											+ "}"
										+ "}"
										+ "else{"
											+ "var abuelo = padre.parentNode;"
											+ "var tatarabuelo = abuelo.parentNode;"
											+ "cadenaPadre = tatarabuelo.id;"
											+ "if(cadenaPadre.localeCompare('" + InicioAltairJacob.codigoCentro + "-1-2') != -1){"
												+ "servicio = cadenaPadre.slice(0,4);"
												+ "if(servicio[0] == '-'){"
													+ "servicio = servicio.slice(1);"
												+ "}"
											+ "}"
										+ "}"
						+ "}"
						+ ""
						// + "alert(servicio);"
						+ "selServ(servicio);"

					
					
					+ "}"
					+ "";
			
			
			String selFecha = ""
					+ ""
					+ "window.selFecha;"
					+ ""					
					+ "setTimeout(function(){ " + cadenaSelFecha + ";  }, 1000);"
					+ "";
			

			String suma = selOff + selOn + selServ + selNodo + ""
					+ "var hosp = document.getElementById('navegacion');"
					+ "hosp.setAttribute('name','navegacion');"
					
					+ "var hosp = document.getElementById('HOS-noSeleccionable-rama');"
					+ "if(hosp != null || hosp != undefined){"
						+ "var anclasH = hosp.getElementsByTagName('a');"
						+ "var tipoH = 'h';"
						+ "for(var i=1;i<anclasH.length;i++){"
							+ "anclasH[i].setAttribute('onclick','selNodo(this, \"h\")');"
						+ "}"
					+ "}"
			 
							

					;
			
			
			String suma2 = selFecha + ""
					+ ""
					+ "var qui = document.getElementById('QUI-noSeleccionable-rama');"
					+ "if(qui != null || qui != undefined){"
						+ "var anclasQ = qui.getElementsByTagName('a');"
						+ "var tipoQ = 'q';"
						+ "for(var i=1;i<anclasQ.length;i++){"
							+ "anclasQ[i].setAttribute('onclick','selNodo(this, \"q\" )');"
						+ "}"
					+ "}"  
					
					+ "var cex = document.getElementById('CEX-noSeleccionable-rama');"
					+ "if(cex != null || cex != undefined){"
						+ "var anclasQ = cex.getElementsByTagName('a');"
						+ "var tipoQ = 'c';"
						+ "for(var i=1;i<anclasQ.length;i++){"
							+ "anclasQ[i].setAttribute('onclick','selNodo(this, \"c\" )');"
						+ "}"
					+ "}" 

					+ "var urg = document.getElementById('URG-noSeleccionable-rama');"
					+ "if(urg != null || urg != undefined){"
						+ "var anclasU = urg.getElementsByTagName('a');"
						+ "var tipoU = 'u';"
						+ "for(var i=1;i<anclasU.length;i++){"
							+ "anclasU[i].setAttribute('onclick','selNodo(this, \"u\")');"
						+ "}"
					+ "}"
					+ "";
			
			
			Dispatch.call(navegador, "navigate","javascript:"+ suma);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Dispatch.call(navegador, "navigate","javascript:"+ suma2);
		}
		
		
		public void inicializaNodosHospUrgQui (){
			
			

			
			
			
			
			String cadena = ""
					+ ""
					+ ""
					+ "seleccionador = function(nodo, tipoN) {"
						+ "var nodoAncla = nodo;"
						+ "var tipoNodo = tipoN;"
						+ ""
						+ "if(nodoAncla.id.localeCompare('nodoSeleccionado') == 0){"
							+ "nodoAncla.setAttribute('id','no'); "
							+ "nodoAncla.style.fontWeight = 'normal';"
							+ "nodoAncla.style.color = 'black';"
						+ "}"
						+ "else{"
							+ "var nodoSeleccionado = document.getElementById('nodoSeleccionado');"
							+ "if(nodoSeleccionado != null && nodoSeleccionado != undefined){"
								+ "nodoSeleccionado.click();"
								+ "nodoSeleccionado.style.color = 'black';"
								+ "nodoSeleccionado.style.fontWeight = 'normal';"
								+ "nodoSeleccionado.setAttribute('id','no');"
								
							+ "};"
							+ ""
							+ "if(nodoAncla.id.localeCompare('nodoSeleccionado') != 0){"
								+ "nodoAncla.setAttribute('id','nodoSeleccionado'); "
								+ "nodoAncla.style.fontWeight = 'bolder';"
								+ "nodoAncla.style.color = 'red';"
							+ "}"
							+ "else{"
								+ "nodoAncla.setAttribute('id','no'); "
								+ "nodoAncla.style.fontWeight = 'normal';"
								+ "nodoAncla.style.color = 'black';"
							+ "}"
							+ ""
							+ "var cadena = nodoAncla.innerHTML;"
							+ "var ind = cadena.lastIndexOf('/ins>') + 2;"
							+ "cadena = cadena.slice(ind);"
							+ "var index = cadena.lastIndexOf('/');"
							+ "if(index != -1){"
								+ "var fecha = cadena.slice(index-5,index+5);"
								+ ""
								+ "document.getElementById('{hc}dataVersion-{hc}docExt').value = fecha;"
							+ "}"

							+ ""
							+ "var servicio = '';"
							+ "if(tipoNodo.localeCompare('h') == 0){"
							    + "index = cadena.lastIndexOf('>') + 1;"
							    + "cadena = cadena.slice(index);"
							    + "cadena = cadena.trim();"
								+ "servicio = cadena.slice(4,8);"
							+ "}"
							+ "else if(tipoNodo.localeCompare('u') == 0){"
							    + "index = cadena.lastIndexOf('>') + 1;"
							    + "cadena = cadena.slice(index);"
							    + "cadena = cadena.trim();"
								+ "servicio = cadena.slice(0,4);"
							+ "}"
							+ "else if(tipoNodo.localeCompare('q') == 0){"
								+ "index = cadena.lastIndexOf('>') + 1;"
							    + "cadena = cadena.slice(index);"
							    + "cadena = cadena.trim();"
								+ "var index2 = cadena.indexOf('-');"
								+ "servicio = cadena.slice(index2-4,index2);"
							+ "}"
							+ "else if(tipoNodo.localeCompare('c') == 0){"
								+ "var padre = nodoAncla.parentNode;"
								+ "var cadenaPadre = padre.id;"
								+ "if(cadenaPadre.search('" + InicioAltairJacob.codigoCentro + "-1-2') != -1){"
									+ "servicio = cadenaPadre.slice(-4);"
									+ "if(servicio[0] == '-'){"
											+ "servicio = servicio.slice(1);"
									+ "}"
								+ "}"
								+ "else{"
								//	+ "var comentario = document.getElementById('{hc}comentario-{hc}docExt');"
								//	+ "comentario.innerHTML = 'Es un nodo de consulta.';"
									+ "var abuelo = padre.parentNode;"
									+ "var tatarabuelo = abuelo.parentNode;"
									+ "cadenaPadre = tatarabuelo.id;"
								//	+ "comentario.innerHTML = comentario.innerHTML + ' este es el id del abuelo... ' + cadenaPadre;"
									+ "if(cadenaPadre.localeCompare('" + InicioAltairJacob.codigoCentro + "-1-2') != -1){"
										+ "servicio = cadenaPadre.slice(0,4);"
										+ "if(servicio[0] == '-'){"
											+ "servicio = servicio.slice(1);"
										+ "}"
									+ "}"
								+ "}"
							+ "}"
							
							+ ""
					//		+ "var cajaTitulo = document.getElementById('{hc}titulo-{hc}docExt');"
					//		+ "cajaTitulo.value = servicio;"
					
					
							+ "var selectServicio = document.getElementById('{hc}servicioEspecialidad-{hc}docExt');"
							+ "var opciones = selectServicio.options;"
							+ "for(var i = 0;i<opciones.length;i++){"
								+ "if(opciones[i].text.search(servicio) == 0){"
										+ "opciones[i].selected = 'true';"
										+ "document.getElementById('cajaColoreada1').value = opciones[i].innerHTML;"
										+ "break;"
								+ "}"
							+ "}"

							+ "document.getElementById('submitFormFirmar').focus();"
							
						+ "}"

						+ ""

			//			+ "nodoAncla.click();"
						+ "};"
					+ ""
					+ ""   
					+ ""
			//		+ "seleccionador = function(){ alert('hola');};"
					+ ""
					+ "var hosp = document.getElementById('HOS-noSeleccionable-rama');"
					+ "if(hosp != null || hosp != undefined){"
						+ "var anclasH = hosp.getElementsByTagName('a');"
						+ "var tipoH = 'h';"
						+ "for(var i=1;i<anclasH.length;i++){"
							+ "anclasH[i].setAttribute('onclick','seleccionador(this, \"h\")');"
						+ "}"
					+ "}"
			 
						
					+ "var urg = document.getElementById('URG-noSeleccionable-rama');"
					+ "if(urg != null || urg != undefined){"
						+ "var anclasU = urg.getElementsByTagName('a');"
						+ "var tipoU = 'u';"
						+ "for(var i=1;i<anclasU.length;i++){"
							+ "anclasU[i].setAttribute('onclick','seleccionador(this, \"u\")');"
						+ "}"
					+ "}"
							
					+ "var qui = document.getElementById('QUI-noSeleccionable-rama');"
					+ "if(qui != null || qui != undefined){"
						+ "var anclasQ = qui.getElementsByTagName('a');"
						+ "var tipoQ = 'q';"
						+ "for(var i=1;i<anclasQ.length;i++){"
							+ "anclasQ[i].setAttribute('onclick','seleccionador(this, \"q\" )');"
						+ "}"
					+ "}"  
					
					+ "var cex = document.getElementById('CEX-noSeleccionable-rama');"
					+ "if(cex != null || cex != undefined){"
						+ "var anclasQ = cex.getElementsByTagName('a');"
						+ "var tipoQ = 'c';"
						+ "for(var i=1;i<anclasQ.length;i++){"
							+ "anclasQ[i].setAttribute('onclick','seleccionador(this, \"c\" )');"
						+ "}"
					+ "}" 
					
					+ "alert('adios y hola');";
			
			
			
			String cad01 = ""
					+ ""
					+ ""
					+ "seleccionador = function(nodo, tipoN) {"
						+ "var nodoAncla = nodo;"
						+ "var tipoNodo = tipoN;"
						+ ""
						+ "if(nodoAncla.id.localeCompare('nodoSeleccionado') == 0){"
							+ "putIdNo(nodoAncla);"
						+ "}"
						+ "else{"
							+ "var nodoSeleccionado = document.getElementById('nodoSeleccionado');"
							+ "if(nodoSeleccionado != null && nodoSeleccionado != undefined){"
								+ "nodoSeleccionado.click();"
								+ "nodoSeleccionado.style.color = 'black';"
								+ "nodoSeleccionado.style.fontWeight = 'normal';"
								+ "nodoSeleccionado.setAttribute('id','no');"
								
							+ "};"
							+ ""
							+ "if(nodoAncla.id.localeCompare('nodoSeleccionado') != 0){"
								+ "nodoAncla.setAttribute('id','nodoSeleccionado'); "
								+ "nodoAncla.style.fontWeight = 'bolder';"
								+ "nodoAncla.style.color = 'red';"
							+ "}"
							+ "else{"
								+ "putIdNo(nodoAncla);"
							+ "}"
							+ ""
							+ "var cadena = nodoAncla.innerHTML;"
							+ "var ind = cadena.lastIndexOf('/ins>') + 2;"
							+ "cadena = cadena.slice(ind);"
							+ "var index = cadena.lastIndexOf('/');"
							+ "if(index != -1){"
								+ "var fecha = cadena.slice(index-5,index+5);"
								+ ""
								+ "document.getElementById('{hc}dataVersion-{hc}docExt').value = fecha;"
							+ "}"

							+ ""
							+ ""
							+ ""
							+ "var servicio = '';"
							+ ""
							+ "index = cadena.lastIndexOf('>') + 1;"
							+ "cadena = cadena.slice(index);"
							+ "cadena = cadena.trim();"
							+ ""
							+ ""
							+ "if(tipoNodo.localeCompare('h') == 0){"
								+ "servicio = cadena.slice(4,8);"
							+ "}"
							+ "else if(tipoNodo.localeCompare('u') == 0){"
								+ "servicio = cadena.slice(0,4);"
							+ "}"
							+ "else if(tipoNodo.localeCompare('q') == 0){"
								+ "var index2 = cadena.indexOf('-');"
								+ "servicio = cadena.slice(index2-4,index2);"
							+ "}"
							+ "else if(tipoNodo.localeCompare('c') == 0){"
								+ "var padre = nodoAncla.parentNode;"
								+ "var cadenaPadre = padre.id;"
								+ "if(cadenaPadre.search('" + InicioAltairJacob.codigoCentro + "-1-2') != -1){"
									+ "servicio = cadenaPadre.slice(-4);"
									+ "if(servicio[0] == '-'){"
											+ "servicio = servicio.slice(1);"
									+ "}"
								+ "}"
								+ "else{"
									+ "var abuelo = padre.parentNode;"
									+ "var tatarabuelo = abuelo.parentNode;"
									+ "cadenaPadre = tatarabuelo.id;"
									+ "if(cadenaPadre.localeCompare('" + InicioAltairJacob.codigoCentro + "-1-2') != -1){"
										+ "servicio = cadenaPadre.slice(0,4);"
										+ "if(servicio[0] == '-'){"
											+ "servicio = servicio.slice(1);"
										+ "}"
									+ "}"
								+ "}"
							+ "}"
							
							+ "selServicio(servicio);"
							
							
						+ "}"

					+ "};"
					+ ""
					+ ""
					+ "var qui = document.getElementById('QUI-noSeleccionable-rama');"
					+ "if(qui != null || qui != undefined){"
						+ "var anclasQ = qui.getElementsByTagName('a');"
						+ "var tipoQ = 'q';"
						+ "for(var i=1;i<anclasQ.length;i++){"
							+ "anclasQ[i].setAttribute('onclick','seleccionador(this, \"q\" )');"
						+ "}"
					+ "}"
					+ ""
			;
			

			
			String insertaEnElNodo = ""
					+ ""
					+ "if(this.id.localeCompare(\"nodoSeleccionado\") == 0){"
						+ "this.setAttribute(\"id\",\"no\");"
						+ "this.style.fontWeight = \"normal\";"
						+ "this.style.color = \"black\";"
					+ "}"
				/*	+ "else{"
							+ "var nodoSeleccionado = document.getElementById(\"nodoSeleccionado\");"
							+ "if(nodoSeleccionado != null && nodoSeleccionado != undefined){"
								+ "nodoSeleccionado.click();"
								+ "nodoSeleccionado.style.color = \"black\";"
								+ "nodoSeleccionado.style.fontWeight = \"normal\";"
								+ "nodoSeleccionado.setAttribute(\"id\",\"no\");"
								
							+ "};"
							+ ""
							+ "if(nodoAncla.id.localeCompare(\"nodoSeleccionado\") != 0){"
								+ "nodoAncla.setAttribute(\"id\",\"nodoSeleccionado\"); "
								+ "nodoAncla.style.fontWeight = \"bolder\";"
								+ "nodoAncla.style.color = \"red\";"
							+ "}"
							+ "else{"
								+ "nodoAncla.setAttribute(\"id\",\"no\"); "
								+ "nodoAncla.style.fontWeight = \"normal\";"
								+ "nodoAncla.style.color = \"black\";"
							+ "}"
					+ "}"
					*/
					;
			
			String cad011 = ""
					+ ""
				/*	+ ""
					+ "window.selServicio = function(servicio){"
						+ "alert('jalou');"
					+ "}"
					*/
				//	+ "setTimeout(function(){ alert('Hello'); }, 3000);"
					+ ""
				
					+ "window.selNombre;"
					+ ""
					+ "setTimeout(function(){ window.selNombre = function(nombre){alert(nombre); };}, 3000);"
					+ ""
					+ ""
					+ "var urg = document.getElementById('URG-noSeleccionable-rama');"
					+ "if(urg != null || urg != undefined){"
						+ "var anclasU = urg.getElementsByTagName('a');"
						+ "var tipoU = 'u';"
						+ "for(var i=1;i<anclasU.length;i++){"
							+ "anclasU[i].setAttribute('onclick','" + insertaEnElNodo + "');"
						+ "}"
					+ "}"
					+ ""
					+ ""
					+ ""
					+ ""
					+ ""
			//		+ "alert('adios');"
					+ ""
			/*		+ "window.hola = 'hola';"
					+ "alert('jalou');"
			/*		+ ""
					+ ""
					+ ""
					+ "window.selServicio = function(servicio) {"
						+ "var selectServicio = document.getElementById('{hc}servicioEspecialidad-{hc}docExt');"
						+ "var opciones = selectServicio.options;"
						+ "for(var i = 0;i<opciones.length;i++){"
							+ "if(opciones[i].text.search(servicio) == 0){"
									+ "opciones[i].selected = 'true';"
									+ "document.getElementById('cajaColoreada1').value = opciones[i].innerHTML;"
									+ "break;"
							+ "}"
						+ "}"

						+ "document.getElementById('submitFormFirmar').focus();"
					+ "}"
					+ ""
					+ ""
					+ "window.putIdNo = function(nodoAncla){"
						+ "nodoAncla.setAttribute('id','no'); "
						+ "nodoAncla.style.fontWeight = 'normal';"
						+ "nodoAncla.style.color = 'black';"
					+ "}"
					+ ""   */

					;
			
			
			
	
			
			
			
			
			
			String cad02 = ""
					
					/*
					+ "var hosp = document.getElementById('HOS-noSeleccionable-rama');"
					+ "if(hosp != null || hosp != undefined){"
						+ "var anclasH = hosp.getElementsByTagName('a');"
						+ "var tipoH = 'h';"
						+ "for(var i=1;i<anclasH.length;i++){"
							+ "anclasH[i].setAttribute('onclick','seleccionador(this, \"h\")');"
						+ "}"
					+ "}"
					
					+ "var urg = document.getElementById('URG-noSeleccionable-rama');"
					+ "if(urg != null || urg != undefined){"
						+ "var anclasU = urg.getElementsByTagName('a');"
						+ "var tipoU = 'u';"
						+ "for(var i=1;i<anclasU.length;i++){"
							+ "anclasU[i].setAttribute('onclick','seleccionador(this, \"u\")');"
						+ "}"
					+ "}"
												
					+ "var cex = document.getElementById('CEX-noSeleccionable-rama');"
					+ "if(cex != null || cex != undefined){"
						+ "var anclasQ = cex.getElementsByTagName('a');"
						+ "var tipoQ = 'c';"
						+ "for(var i=1;i<anclasQ.length;i++){"
							+ "anclasQ[i].setAttribute('onclick','seleccionador(this, \"c\" )');"
						+ "}"
					+ "}"
					+ "";
					 */
					
					
					+ ""
					+ " window.selServicio;"
					+ ""
					+ "setTimeout(function(){ window.selServicio = function(servicio){alert(servicio); };}, 3000);"
					+ ""
					+ "var hosp = document.getElementById('HOS-noSeleccionable-rama');"
					+ "if(hosp != null || hosp != undefined){"
						+ "var anclasH = hosp.getElementsByTagName('a');"
						+ "var tipoH = 'h';"
						+ "for(var i=1;i<anclasH.length;i++){"
							+ "anclasH[i].setAttribute('onclick','" + insertaEnElNodo + "');"
						+ "}"
					+ "}"
					
												
					+ "var cex = document.getElementById('CEX-noSeleccionable-rama');"
					+ "if(cex != null || cex != undefined){"
						+ "var anclasQ = cex.getElementsByTagName('a');"
						+ "var tipoQ = 'c';"
						+ "for(var i=1;i<anclasQ.length;i++){"
							+ "anclasQ[i].setAttribute('onclick','" + insertaEnElNodo + "');"
						+ "}"
					+ "}"
					+ "";
			
			String cadenaEjemplo = ""
					//+ "alert('adios');"
					+ "seleccionador = function(hola,adios) {"
						+ "alert(hola);"
						+ "alert(adios);"
					+ "};"
					// + "seleccionador('hola','adios');"
					;
			
			
			// System.out.println(cad01);
			// System.out.println();
			// System.out.println(cad011);
			

			
			
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Dispatch.call(navegador, "navigate","javascript:"+ cad02);
				
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			Dispatch.call(navegador, "navigate","javascript:"+ cad011);
			

			

		}
		
		
		public void ocultaNodos(){
			
			//  Cierra ramas no necesarias
			
			boolean ocultaNodosCEX = false;
			
			String cadena = "";
			
			if(servicio.equals("HOSP") || servicio.equals("CIA")){
				
				Variant nodoComprobar = Dispatch.call(documento, "getElementById","CEX-noSeleccionable-rama");
				// System.out.println("Nodo cex " + nodoComprobar.toString());
				if(!nodoComprobar.toString().equals("null")){
					Dispatch cex = Dispatch.call(documento, "getElementById","CEX-noSeleccionable-rama").toDispatch();
					Dispatch.call(cex,"setAttribute","class","jstree-unchecked jstree-closed");
				}
				
				nodoComprobar = Dispatch.call(documento, "getElementById","URG-noSeleccionable-rama");
				// System.out.println("Nodo urg " + nodoComprobar.toString());
				if(!nodoComprobar.toString().equals("null")){
					Dispatch urg = Dispatch.call(documento, "getElementById","URG-noSeleccionable-rama").toDispatch();
					Dispatch.call(urg,"setAttribute","class","jstree-unchecked jstree-closed");
				}
				
				nodoComprobar = Dispatch.call(documento, "getElementById","LIE-noSeleccionable-rama");
				// System.out.println("Nodo lie " + nodoComprobar.toString());
				if(!nodoComprobar.toString().equals("null")){
					Dispatch lie = Dispatch.call(documento, "getElementById","LIE-noSeleccionable-rama").toDispatch();
					Dispatch.call(lie,"setAttribute","class","jstree-unchecked jstree-closed");
				}				

			}
			else if(servicio.equals("URG")){
				
				Variant nodoComprobar = Dispatch.call(documento, "getElementById","CEX-noSeleccionable-rama");
				if(!nodoComprobar.toString().equals("null")){
					Dispatch cex = Dispatch.call(documento, "getElementById","CEX-noSeleccionable-rama").toDispatch();
					Dispatch.call(cex,"setAttribute","class","jstree-unchecked jstree-closed");
				}
								
				nodoComprobar = Dispatch.call(documento, "getElementById","LIE-noSeleccionable-rama");
				if(!nodoComprobar.toString().equals("null")){
					Dispatch lie = Dispatch.call(documento, "getElementById","LIE-noSeleccionable-rama").toDispatch();
					Dispatch.call(lie,"setAttribute","class","jstree-unchecked jstree-closed");
				}		
				
			}
			else{
				
				Variant nodoComprobar = Dispatch.call(documento, "getElementById","HOS-noSeleccionable-rama");
				if(!nodoComprobar.toString().equals("null")){
					Dispatch hos = Dispatch.call(documento, "getElementById","HOS-noSeleccionable-rama").toDispatch();
					Dispatch.call(hos,"setAttribute","class","jstree-unchecked jstree-closed");
				}
				
				nodoComprobar = Dispatch.call(documento, "getElementById","URG-noSeleccionable-rama");
				if(!nodoComprobar.toString().equals("null")){
					Dispatch urg = Dispatch.call(documento, "getElementById","URG-noSeleccionable-rama").toDispatch();
					Dispatch.call(urg,"setAttribute","class","jstree-unchecked jstree-closed");
				}
				
				nodoComprobar = Dispatch.call(documento, "getElementById","QUI-noSeleccionable-rama");
				if(!nodoComprobar.toString().equals("null")){
					Dispatch qui = Dispatch.call(documento, "getElementById","QUI-noSeleccionable-rama").toDispatch();
					Dispatch.call(qui,"setAttribute","class","jstree-unchecked jstree-closed");
				}
				
				nodoComprobar = Dispatch.call(documento, "getElementById","LIE-noSeleccionable-rama");
				if(!nodoComprobar.toString().equals("null")){
					Dispatch lie = Dispatch.call(documento, "getElementById","LIE-noSeleccionable-rama").toDispatch();
					Dispatch.call(lie,"setAttribute","class","jstree-unchecked jstree-closed");
				}				
				
				ocultaNodosCEX = true;
				
			}
			

			
			
			//  Oculta nodos no necesarios de consultas
			
			if(ocultaNodosCEX){
				// System.out.println("Empieza a ocultar nodos.");
				
				String id = servicio + "-noSeleccionable-rama";
				
				Variant nodoComprobar = Dispatch.call(documento, "getElementById",id);
				if(!nodoComprobar.toString().equals("null")){
					Dispatch nodo = Dispatch.call(documento, "getElementById",id).toDispatch();
					
					Variant nodoCexComprobar = Dispatch.call(documento, "getElementById","OTROS-noSeleccionable-rama");
					if(!nodoCexComprobar.toString().equals("null")){
						Dispatch cex = Dispatch.call(documento, "getElementById","OTROS-noSeleccionable-rama").toDispatch();
						Dispatch.call(cex,"setAttribute","class","jstree-unchecked jstree-closed");
					}
					
					Dispatch consultas = Dispatch.call(documento, "getElementById","CEX-noSeleccionable-rama").toDispatch();
					Dispatch listaConsultas = Dispatch.call(consultas, "getElementsByTagName","li").toDispatch();
					
					int numListas = Integer.valueOf(Dispatch.get(listaConsultas, "length").toString());
					for(int i=0;i<numListas;i++){
						Dispatch consulta = Dispatch.get(listaConsultas, String.valueOf(i)).toDispatch();
						String nombreId = Dispatch.get(consulta,"id").toString();
						
						if(nombreId.contains("-noSeleccionable-rama") && !nombreId.contains(id)){
							if(nombreId.contains("OTROS")){
								break;
							}
							else{
								Dispatch.call(consulta, "setAttribute", "class","jstree-unchecked jstree-closed");
							}
						}
						
					}

				}
				else{
					Dispatch consultas = Dispatch.call(documento, "getElementById","CEX-noSeleccionable-rama").toDispatch();
					Dispatch listaConsultas = Dispatch.call(consultas, "getElementsByTagname","li").toDispatch();
					
					int numListas = Integer.valueOf(Dispatch.get(listaConsultas, "length").toString());
					
					ArrayList<String> serviciosPaciente = new ArrayList<String>();
					
					
					for(int i=0;i<numListas;i++){
						Dispatch consulta = Dispatch.get(listaConsultas, String.valueOf(i)).toDispatch();
						String nombreId = Dispatch.get(consulta,"id").toString();
						
						if(nombreId.contains("-noSeleccionable-rama")){
							int index = nombreId.indexOf("-");
							String serv = nombreId.substring(0,index);
							
							if(!serv.contains("CEX") && !serv.contains("OTROS")){
								serviciosPaciente.add(serv);
							}
							if(serv.contains("OTROS")){
								break;
							}
						}
						
					}
					
					consultas = Dispatch.call(documento, "getElementById","OTROS-noSeleccionable-rama").toDispatch();
					listaConsultas = Dispatch.call(consultas, "getElementsByTagname","li").toDispatch();
					
					numListas = Integer.valueOf(Dispatch.get(listaConsultas, "length").toString());
					
					int j=0;
					for(int i=0;i<numListas;i++){
						Dispatch consulta = Dispatch.get(listaConsultas, String.valueOf(i)).toDispatch();
						String nombreId = Dispatch.get(consulta,"id").toString();
						
						if(j < serviciosPaciente.size()){
							if(!nombreId.contains(serviciosPaciente.get(j))){
								if(!nombreId.contains(aliasServicio)){
									Dispatch consultaEstilo = Dispatch.get(consulta, "style").toDispatch();
									Dispatch.put(consultaEstilo, "display", "none");
								}
							}
							else{
								j++;
							}
						}
						else if(!nombreId.contains(aliasServicio)){
							Dispatch consultaEstilo = Dispatch.get(consulta, "style").toDispatch();
							Dispatch.put(consultaEstilo, "display", "none");
						}
					}
					
				}
				
				
			}
			
		}
		
		
		static public void muestraNodosConsulta(ActiveXComponent xedoc){
			
			Dispatch documento = Dispatch.call(xedoc, "document").getDispatch();
			Dispatch consultas = Dispatch.call(documento, "getElementById","OTROS-noSeleccionable-rama").toDispatch();
			Dispatch listaConsultas = Dispatch.call(consultas, "getElementsByTagname","li").toDispatch();
			
			int numListas = Integer.valueOf(Dispatch.get(listaConsultas, "length").toString());
			
			int j=0;
			for(int i=0;i<numListas;i++){
				Dispatch consulta = Dispatch.get(listaConsultas, String.valueOf(i)).toDispatch();
				
				Dispatch consultaEstilo = Dispatch.get(consulta, "style").toDispatch();
				Dispatch.put(consultaEstilo, "display", "block");

			}
		}
		
		
		
		
		public void getFocus(){
			
			
			Dispatch identificadorPaciente = Dispatch.call(documento,"getElementById","{hc}docExt").toDispatch();
			Dispatch divs = Dispatch.call(identificadorPaciente,"getElementsByTagName","div").toDispatch();
			Dispatch div = Dispatch.call(divs,"0").toDispatch();
			
			Dispatch estiloDiv = Dispatch.get(div, "style").toDispatch();
			Dispatch.put(div, "innerHTML","" + numeroDelPdf + " de " + InicioAltairJacob.numPdfsTotales + "");
		//	Dispatch.put(estiloTablaAtributos, "font", "bold 28px arial, sans-serif");
			Dispatch.put(estiloDiv, "color", "yellow");
		//	Dispatch.put(estiloDiv, "textAlign", "center");
			
		//	 Dispatch.put(estiloTablaAtributos,"color","white");
		//	 Dispatch.put(estiloDiv,"background-color","rgb(28,95,162)");
			 Dispatch.put(estiloDiv,"font-weight","bolder");
			 Dispatch.put(estiloDiv,"font-size","230% ");
			 Dispatch.put(estiloDiv,"padding","10px 0 0 10px");
			 Dispatch.put(estiloDiv,"height","30px");
			

			
			
			Variant nodoComprobar = Dispatch.call(documento, "getElementById","submitFormFirmar");
			if(!nodoComprobar.toString().equals("null")){

				if(InicioAltairJacob.primerPdf){
					Dispatch.call(navegador, "navigate","javascript:document.getElementById('submitFormFirmar').focus()");
					InicioAltairJacob.primerPdf = false;
				}
				else{
					Dispatch.call(navegador, "navigate","javascript:document.getElementById('submitFormFirmar').focus()"); 
				}
				
			}

		}
}


