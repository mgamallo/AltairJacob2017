package es.prueba;

public class Modific {

	
	public void getEventoNodo(){
		
		String desSel = ""
				+ ""
				+ "window.desSel = function(nod){"
					+ "nod.setAttribute('id','no');"
					+ "nod.style.fontWeight = 'normal';"
					+ "nod.style.color = 'black';"
				+ "}"
				+ "";
		
		String seleccionador = ""
				+ ""
				+ "window.selr = function(nodo,tipN){"
					+ "if(nodo.id.=='nodoSeleccionado'){"
						+ "desSel(nodo);"
					+ "}"
					+ "else{"
						+ "selSel(nodo);"
					+ "}"
				+ ""
				+ ""
				+ ""
				+ ""
				+ ""
				+ "window.selSerV(servicio);";
		
		String selSel = ""
				+ ""
				+ "window.selSel = function(nod){"
					+ "var nodoS = document.getElementById('nodoSeleccionado');"
					+ "if(nodoS != null && nodoS != undefined){"
						+ "nodoS.click();"
						+ "desSel(nodoS);"
					+ "}"
					+ "if(nodo.id != 'nodoSeleccionado'){"
						+ "nodo.setAttribute('id','nodoSeleccionado'); "
						+ "nodo.style.fontWeight = 'bolder';"
						+ "nodo.style.color = 'red';"
					+ "}"
					+ "else{"
					+ "desSel(nodo);"
				+ "}"
				+ "";
		
		String selSerV = ""
				+ "window.selSerV = function(servicio){"
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
		
		
		String cadena = ""
				+ ""
				+ ""
				+ "seleccionador = function(nodo, tipoN) {"
					+ "var nodoAncla = nodo;"
					+ "var tipoNodo = tipoN;"
					+ ""

					+ "else{"
	
						+ ""

						

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
							+ "if(cadenaPadre.search('360340-1-2') != -1){"
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
								+ "if(cadenaPadre.localeCompare('360340-1-2') != -1){"
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
				+ "";
		
		
		
		
		
	}
	
}
