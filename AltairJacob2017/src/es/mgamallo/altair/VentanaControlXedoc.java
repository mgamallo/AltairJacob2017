package es.mgamallo.altair;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;





import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.SliderUI;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;




public class VentanaControlXedoc extends JFrame{

	private java.awt.Point coordenadasRaton = new java.awt.Point();

	
	JPanel panel1 = new JPanel();
	JPanel panelMover = new JPanel();
	
	JPanel panel2 = new JPanel(new FlowLayout());
	JButton jBiniciar = new JButton("Iniciar");
	JButton jBxedoc1 = new JButton("Xedoc 1");
	JButton jBxedoc2 = new JButton("Xedoc 2");
	JButton jBianus = new JButton("Ianus");
	JButton jBbandeja1 = new JButton("Bandeja 1");
JButton jBbandeja2 = new JButton("Bandeja 2");
	JButton jBprometeo = new JButton("Prometeo");
	JButton jBmaquetar = new JButton("Maquetar Todo");
	JButton jBpdf1 = new JButton("Recargar Xedoc 1");
	JButton jBpdf2 = new JButton("Recargar Xedoc 2");
	JButton jBsalir = new JButton("Salir");
	JButton jBpegarTitulo = new JButton("Pegar Titulo");
	JButton jBmaquetarXedoc1 = new JButton("Maquetar");
	JButton jBmaquetarXedoc2 = new JButton("Maquetar 2");
	JButton jBrecargarArbol = new JButton("Mostrar Nodos");
	JButton jBsaltarPdf = new JButton("Saltar pdf");
	
	JButton jBpin = new JButton("Pin");
	
	JButton jBrecargarPrograma = new JButton("Reiniciar Bandejas");
	JButton jBactualizarLista = new JButton("Actualizar lista");
	
	JComboBox comboInicio = new JComboBox();
	JComboBox comboDiasCaptura = new JComboBox();
	
	JLabel etiquetaVacia = new JLabel("      ");

	static int RETARDO_INICIAL = Retardo.RETARDO_CARGA_NAVEGADOR;
	JSlider sliderRetardos = new JSlider(JSlider.HORIZONTAL,2000,7000,RETARDO_INICIAL);
	JLabel etiquetaRetardos = new JLabel("" + RETARDO_INICIAL);
	
	static boolean xedoc1inicializado = false;
	
	public VentanaControlXedoc() {
		// TODO Auto-generated constructor stub
		
		
		setSize(570,100);
		setResizable(true);
		getContentPane().add(panel1);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setAlwaysOnTop(true);
		setUndecorated(true);
		
		if(InicioAltairJacob.numeroPantallas == 1){
			setLocation(Coordenada.COORDENADA_VENTANACONTROL_1P);
		}
		else{
			setLocation(Coordenada.COORDENADA_VENTANACONTROL_2P);
		}
		
		
		
		sliderRetardos.setMinorTickSpacing(100);
		sliderRetardos.setMajorTickSpacing(500);
	//	sliderRetardos.setPaintLabels(true);
		sliderRetardos.setForeground(Color.gray);
		sliderRetardos.setPaintTicks(true);
	//	sliderRetardos.setBackground(Color.white);
		
		sliderRetardos.addChangeListener(new MiAccion());
		

		
		panel1.setBackground(Color.blue);
		panel1.setLayout(new BorderLayout());
		panel1.add(panel2,BorderLayout.CENTER);
		panel1.add(panelMover, BorderLayout.WEST);
		
		panel2.setBackground(new Color(255,255,204));

		panel2.add(jBactualizarLista);
		panel2.add(comboInicio);
		
	//	panel2.add(jBmaquetar);
		panel2.add(jBxedoc1);
		panel2.add(jBxedoc2);
		panel2.add(jBbandeja1);
		panel2.add(jBbandeja2);
		
		panel2.add(jBiniciar);
	//	panel2.add(jBianus);
		panel2.add(comboDiasCaptura);
	//	panel2.add(jBprometeo);

		panel2.add(jBpegarTitulo);
		panel2.add(jBmaquetarXedoc1);
		panel2.add(jBmaquetarXedoc2);
		panel2.add(jBrecargarArbol);
		panel2.add(jBsaltarPdf);
		
		panel2.add(jBpdf1);
		panel2.add(jBpdf2);
		
		panel2.add(jBrecargarPrograma);
		
		panel2.add(jBpin);
		panel2.add(jBsalir);
		
		
		panel2.add(sliderRetardos);
		panel2.add(etiquetaRetardos);
		
		etiquetaRetardos.setFont(new Font("Arial", Font.BOLD, 16));
		
		Dispatch documento = Dispatch.call(InicioAltairJacob.bandejaXedoc1.navegador,"document").getDispatch();
		Dispatch row = Dispatch.call(documento, "getElementById","row").getDispatch();
		Dispatch filas = Dispatch.call(row, "getElementsByTagName","tr").getDispatch();
		int numeroFilas = Integer.parseInt(Dispatch.get(filas, "length").toString()) - 1;
		
	//	System.out.println(numeroFilas);
		
	//	System.out.println("El número de filas es... " + numeroFilas);
		
		for(int i=0;i<numeroFilas;i++){
			comboInicio.addItem(String.valueOf(i+1));
		}
		
		for(int i= 1;i<25;i++){
			int num = i*20;
			comboDiasCaptura.addItem(String.valueOf(num));
		}
		
		jBactualizarLista.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				/*
				WebElement tabla = bandejaXedoc1.findElement(By.id("row"));
				List<WebElement> filas = tabla.findElements(By.tagName("tr"));
				int numeroFilas = filas.size() - 1;
				
				System.out.println("El número de filas es... " + numeroFilas);
				
				comboInicio.removeAllItems();
				
				for(int i=0;i<numeroFilas;i++){
					comboInicio.addItem(String.valueOf(i+1));
				}
				InicioAltairJacob.numPdfsTotales = numeroFilas;
				*/
				
				comboInicio.removeAllItems();
				
				Dispatch documento = Dispatch.call(InicioAltairJacob.bandejaXedoc1.navegador, "document").toDispatch();
				Dispatch tabla = Dispatch.call(documento, "getElementById","row").toDispatch();
				Dispatch celdas = Dispatch.call(tabla, "getElementsByTagName","td").toDispatch();
				int tamaño = Integer.valueOf(Dispatch.get(celdas, "length").toString()) / 5 ;
				
				InicioAltairJacob.listaPdfs = new String[tamaño];
				
				for(int i=0;i<tamaño;i++){
					Dispatch celda = Dispatch.get(celdas, String.valueOf(i*5+2)).toDispatch();
					InicioAltairJacob.listaPdfs[i] = Dispatch.get(celda,"innerHTML").toString();
					comboInicio.addItem(String.valueOf(i+1));
				}

				InicioAltairJacob.numPdfsTotales = tamaño;
				
			}
		});
		
		comboInicio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		comboDiasCaptura.setSelectedItem(String.valueOf(InicioAltairJacob.RANGO_DIAS_CONSULTA));
		comboDiasCaptura.setToolTipText("Rango de dias del contexto");
		comboDiasCaptura.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				InicioAltairJacob.RANGO_DIAS_CONSULTA = Integer.valueOf(comboDiasCaptura.getSelectedItem().toString());
			}
		});
		
		jBiniciar.setBackground(Color.pink);
		jBiniciar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				InicioAltairJacob.pin = new Pin();
				
				int pdfSeleccionado = comboInicio.getSelectedIndex();
				
				GestionXedoc.insertarCodigoBandeja("Bandeja Xedoc 1", pdfSeleccionado, pdfSeleccionado + 1);
				
				xedoc1inicializado = true;
				
				jBmaquetar.setBackground(Color.green);
				jBiniciar.setBackground(Color.DARK_GRAY);
				jBiniciar.setEnabled(false);
				
				
				jBxedoc1.setBackground(Color.green);
				
				if(InicioAltairJacob.xedoc2.numeroPdf != -1){
					Dispatch.put(InicioAltairJacob.xedoc2.navegador, "visible","false");
				}
				
				jBxedoc2.setBackground(Color.gray);
				Dispatch.put(InicioAltairJacob.bandejaXedoc1.navegador, "visible","false");
				jBbandeja1.setBackground(Color.gray);
				Dispatch.put(InicioAltairJacob.bandejaXedoc2.navegador, "visible","false");
				jBbandeja2.setBackground(Color.gray);
				
				panelMover.setBackground(Color.green);
			}
		});

		jBxedoc1.setBackground(Color.GRAY);
		jBxedoc1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				gestionXedoc1();

			}
		});

		jBxedoc2.setBackground(Color.gray);
		jBxedoc2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gestionXedoc2();
			}
		});
		
		
		jBianus.setBackground(Color.DARK_GRAY);
		jBianus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			if(jBianus.getBackground() == Color.DARK_GRAY){
			//	Dispatch.put(GestionJacobXedoc.ianusApoyoXedoc, "visible","true");
				jBianus.setBackground(Color.green);
			}
			else{
			//	Dispatch.put(GestionJacobXedoc.ianusApoyoXedoc, "visible","false");
				jBianus.setBackground(Color.DARK_GRAY);
			}
				

			}
		});
		
		
		jBbandeja1.setBackground(Color.green);
		jBbandeja1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(xedoc1inicializado){
					if(jBbandeja1.getBackground() != Color.GRAY){
						Dispatch.put(InicioAltairJacob.bandejaXedoc1.navegador, "visible","false");
						jBbandeja1.setBackground(Color.gray);

					}
					else{
						Dispatch.put(InicioAltairJacob.bandejaXedoc1.navegador, "visible","true");
						jBbandeja1.setBackground(Color.green);

					}
						
				}
			}
		});
		
		
		jBbandeja2.setBackground(Color.green);
		jBbandeja2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(xedoc1inicializado){
					if(jBbandeja2.getBackground() != Color.GRAY){
						Dispatch.put(InicioAltairJacob.bandejaXedoc2.navegador, "visible","false");
						jBbandeja2.setBackground(Color.gray);

					}
					else{
						Dispatch.put(InicioAltairJacob.bandejaXedoc2.navegador, "visible","true");
						jBbandeja2.setBackground(Color.green);

					}
						
				}
			}
		});
				
		jBprometeo.setVisible(true);
		jBprometeo.setBackground(Color.darkGray);
		jBprometeo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			if(jBprometeo.getBackground() == Color.DARK_GRAY){
			//	Inicio.panelPrincipal.frame.setVisible(true);
				jBprometeo.setBackground(Color.green);
			}
			else{
			//	Inicio.panelPrincipal.frame.setVisible(false);
				jBprometeo.setBackground(Color.DARK_GRAY);
			}
				

			}
		});
		
		jBpdf1.setVisible(false);
		jBpdf1.setBackground(Color.GRAY);
		jBpdf1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			//	Dispatch.call(Inicio.documento1.xedoc, "refresh");
			
				/*	
			InicioAltairJacob.driverBandejaXedoc.navigate().refresh();
			*/
			}
		});
		
		jBpdf2.setVisible(false);
		jBpdf2.setBackground(Color.GRAY);
		jBpdf2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			//	Dispatch.call(Inicio.documento2.xedoc, "refresh");

				/*
			InicioAltairJacob.driverXedoc2.navigate().refresh();	
				*/
			}
		});
		
		jBmaquetar.setVisible(false);
		jBmaquetar.setBackground(Color.gray);
		jBmaquetar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				Variant visible = Dispatch.get(InicioAltairJacob.xedoc1.navegador, "visible");
				System.out.println(visible.toString());
				
			//	new MaquetadoXedoc(InicioAltairJacob.xedoc1, "Xedoc 1", false);
				
				
				/*
				if(jBmaquetar.getBackground() == Color.GRAY){
					jBmaquetar.setBackground(Color.green);
					
				}
				else{
				//	Inicio.panelPrincipal.frame.setVisible(false);
					jBmaquetar.setBackground(Color.GRAY);
					
					if(InicioAltairJacob.xedoc1Activo){
						new MaquetadoXedocSelenium(InicioAltairJacob.driverBandejaXedoc, "Xedoc 1", false);
					}
					else{
						new MaquetadoXedocSelenium(InicioAltairJacob.driverXedoc2, "Xedoc 2", false);

					}
					

					
				//	new MaquetadoXedoc(Inicio.documento1.xedoc, "Xedoc 1");
					
					try {
						Thread.sleep(200);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				//	new MaquetadoXedoc(Inicio.documento2.xedoc, "Xedoc 2");
				//	Dispatch.put(Inicio.documento2.xedoc,"visible","false");
				}
				
				 */
			}
		});
		
		jBsalir.setBackground(Color.red);
		jBsalir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
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
					e1.printStackTrace();
				}
				finally {
					System.exit(0);
					dispose();
				}

			}
		});
		
		jBpin.setVisible(true);
		jBpin.setBackground(Color.ORANGE);
		jBpin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				InicioAltairJacob.pin = new Pin();
			}
		});
		
		jBpegarTitulo.setVisible(false);
		jBpegarTitulo.setBackground(Color.cyan);
		jBpegarTitulo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			/*	ActiveXComponent xedoc;
				
				if(Inicio.xedoc1onTop){
					xedoc = Inicio.documento1.xedoc;
				}
				else{
					xedoc = Inicio.documento2.xedoc;
				}
				
				
				MaquetadoXedoc maquetadoSoloTitulo = new MaquetadoXedoc(xedoc, true);
				maquetadoSoloTitulo.putTitulo();
				
				*/
			}
		});
		
		jBmaquetarXedoc1.setBackground(Color.cyan);
		jBmaquetarXedoc1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				Variant visible = Dispatch.get(InicioAltairJacob.xedoc1.navegador, "visible");
				
		//		System.out.println("Xedoc 1 visible... " + visible.toString());
				boolean vis = Boolean.valueOf(visible.toString());
		//		System.out.println("Xedoc 1 visible... " + vis);
				
				if(vis){
					new MaquetadoXedoc(InicioAltairJacob.xedoc1, "Xedoc 1", false, true, true);
				}
				else{
					
					visible = Dispatch.get(InicioAltairJacob.xedoc2.navegador, "visible");
					vis = Boolean.valueOf(visible.toString());
					
					if(vis){
				
					new MaquetadoXedoc(InicioAltairJacob.xedoc2, "Xedoc 2", false, true, true);
					}
				}
					
				
				/*
				String cadena = ""
						+ ""
						+ "var submit = document.getElementById('submitFormFirmar');"
						+ "if(submit != null && submit != undefined){"
							+ "submit.focus();"
						+ "}"
						+ "";
				
				JavascriptExecutor js = (JavascriptExecutor) InicioAltairJacob.driverBandejaXedoc;
				js.executeScript(cadena);
				*/
				}
		});
		
		jBmaquetarXedoc2.setVisible(false);
		jBmaquetarXedoc2.setBackground(Color.cyan);
		jBmaquetarXedoc2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					
				new MaquetadoXedoc(InicioAltairJacob.xedoc2, "Xedoc 2", false, true, true);
					
				
				/*
				String cadena = ""
						+ ""
						+ "var submit = document.getElementById('submitFormFirmar');"
						+ "if(submit != null && submit != undefined){"
							+ "submit.focus();"
						+ "}"
						+ "";
				
				JavascriptExecutor js = (JavascriptExecutor) InicioAltairJacob.driverBandejaXedoc;
				js.executeScript(cadena);
				*/
			}
		});
		
		jBrecargarArbol.setVisible(true);
		jBrecargarArbol.setBackground(Color.cyan);
		jBrecargarArbol.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				
				
				
				
				ActiveXComponent navegador;
				
				if(InicioAltairJacob.xedoc1Activo){
					navegador = InicioAltairJacob.xedoc1.navegador;
				}
				else{
					navegador = InicioAltairJacob.xedoc2.navegador;
				}
				
				XedocIndividualJacob.muestraNodosConsulta(navegador);
				
				/*
				JavascriptExecutor js = (JavascriptExecutor) driver;
				
				
				String cadena = ""
						+ ""
						+ "var consultas = document.getElementById('OTROS-noSeleccionable-rama');"
						+ "var listaConsultas = consultas.getElementsByTagName('li');"
						+ ""
						+ "var numListas = listaConsultas.length;"
						+ ""
						+ "for(var i=0;i<numListas;i++){"
							+ "listaConsultas[i].style.display = 'block';"
						+ "}"
						+ "";
					
					js.executeScript(cadena);
				*/
			}
			
		});
		
		
		jBrecargarPrograma.setVisible(true);
		jBrecargarPrograma.setBackground(Color.yellow);
		jBrecargarPrograma.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				
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
					e1.printStackTrace();
				}finally {
					InicioAltairJacob.ventana.dispose();
				}
				
				String aux;
				if(InicioAltairJacob.esChop){
					aux = "CHOP";
				}
				else{
					aux = "Salnés";
				}
				
				String comando = "java -jar AltairJacob.jar " + InicioAltairJacob.user.getUsername() 
						+ " " + InicioAltairJacob.user.getPassword() + " " + aux ;
				
				try {
					Runtime.getRuntime().exec(comando);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				System.exit(0);
				dispose();
			}
		});
		
		jBsaltarPdf.setVisible(false);
		jBsaltarPdf.setBackground(Color.magenta);
		jBsaltarPdf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				/*
				ActiveXComponent xedoc;
				
				String texto = "Xedoc ";
				if(Inicio.xedoc1onTop){
					xedoc = Inicio.documento1.xedoc;
					texto += "1";
				}
				else{
					xedoc = Inicio.documento2.xedoc;
					texto += "2";
				}
				
				Dispatch documento = Dispatch.get(xedoc, "document").getDispatch();
				Dispatch siguiente = Dispatch.call(documento,"getElementById","siguiente").getDispatch();
			
				String contenidoDeSiguiente;
				try {
					contenidoDeSiguiente = Dispatch.get(siguiente,"innerHTML").toString();
					System.out.println("Contenido de siguiente1... " + contenidoDeSiguiente );
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					contenidoDeSiguiente = null;
				}
				
				if(contenidoDeSiguiente != null){
					Dispatch.call(siguiente, "click");
					Inicio.saltarXedoc = true;
				}
			*/
			}
		});
		
		
		panelMover.setBackground(Color.red);
		panelMover.add(etiquetaVacia);

		panelMover.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getComponent() == panelMover){
					coordenadasRaton = e.getPoint();
					System.out.println("Pinche en el panel");
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		panelMover.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				java.awt.Point punto = MouseInfo.getPointerInfo().getLocation();
				setLocation(punto.x - coordenadasRaton.x, punto.y
						- coordenadasRaton.y);
			}
		});
		
		setVisible(false);
		
		
	}

	
	public void gestionXedoc1(){
		
		
		if(xedoc1inicializado){
			if(jBxedoc1.getBackground() != Color.GRAY){
				Dispatch.put(InicioAltairJacob.xedoc1.navegador, "visible","false");
				jBxedoc1.setBackground(Color.gray);

			}
			else{
				
				Dispatch.put(InicioAltairJacob.xedoc1.navegador, "visible","true");
				if(jBxedoc2.getBackground() != Color.gray){
					Dispatch.put(InicioAltairJacob.xedoc2.navegador, "visible","false");
					jBxedoc2.setBackground(Color.gray);
				}
				jBxedoc1.setBackground(Color.green);

			}
				
		}
		
	}
	
	public void gestionXedoc2(){
		 
		if(xedoc1inicializado){
			if(jBxedoc2.getBackground() != Color.GRAY){

				Dispatch.put(InicioAltairJacob.xedoc2.navegador, "visible","false");
				jBxedoc2.setBackground(Color.gray);
			}
			else{
				if(jBxedoc1.getBackground() != Color.gray){
					Dispatch.put(InicioAltairJacob.xedoc1.navegador, "visible","false");
					jBxedoc1.setBackground(Color.gray);
				}
				Dispatch.put(InicioAltairJacob.xedoc2.navegador, "visible","true");
				jBxedoc2.setBackground(Color.green);
			}
		}
		
		
	}
	
	
	public class MiAccion implements ChangeListener{

		public void stateChanged(ChangeEvent e){

		int evaluo = sliderRetardos.getValue();

		String retardo = Integer.toString(evaluo);

		etiquetaRetardos.setText(retardo);
		VentanaControlXedoc.RETARDO_INICIAL = evaluo;

		}

		} 
}
