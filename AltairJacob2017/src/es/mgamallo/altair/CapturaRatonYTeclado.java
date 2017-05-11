package es.mgamallo.altair;


import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.rmi.MarshalledObject;
import java.sql.Time;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseListener;

import com.jacob.com.Dispatch;
import com.jacob.com.Variant;


public class CapturaRatonYTeclado implements NativeKeyListener,
		NativeMouseInputListener {
	

	protected static final String LS = System.getProperty("line.separator");
	
	public static boolean barraEspaciadoraOn = true;
	
	int teclaActual = 0;
	int teclaAnterior = 0;
	
	public CapturaRatonYTeclado() {
		// TODO Auto-generated constructor stub

		 GlobalScreen.getInstance().addNativeKeyListener(this);
         GlobalScreen.getInstance().addNativeMouseListener(this);
         GlobalScreen.getInstance().addNativeMouseMotionListener(this);
         
         try {
			GlobalScreen.registerNativeHook();
			
			System.out.println("Teclado capturado");
			
		} catch (NativeHookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	// Métodos de ratón
	// **********************************************************

	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton() == 3){
			try {
				Robot robot  = new Robot();
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_2);
				robot.keyRelease(KeyEvent.VK_2);
				robot.keyRelease(KeyEvent.VK_CONTROL);
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	// Métodos de teclado
	// *************************************************************

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		// TODO Auto-generated method stub
		 // System.out.println("NativeKeyPressed " + e.getKeyCode());
		 // System.out.println("Tecla ... " + ((char) e.getKeyCode()));

		 	if(e.getKeyCode() == 106){   	// ç 129 * 106
		 		
		 		// Pasar al siguiente PDF
		 		
		 		if(InicioAltairJacob.xedoc1Activo){
		 		// Cerramos el Navegador
		 			if(InicioAltairJacob.xedoc1.navegador != null){
						// Utiles.pasarAlSiguientePdf(InicioAltairJacob.xedoc1, Utiles.coordenadaVentana, 500);
		 				HiloPasarSiguiente siguiente = 
		 						new HiloPasarSiguiente(InicioAltairJacob.xedoc1, Utiles.coordenadaVentana, 500);
		 				siguiente.start();
		 			}

		 			
		 		}else{
		 			
		 			if(InicioAltairJacob.xedoc2.navegador != null){
					//	Utiles.pasarAlSiguientePdf(InicioAltairJacob.xedoc2, 
					//			Utiles.coordenadaVentana, 500);
		 				
		 				HiloPasarSiguiente siguiente = 
		 						new HiloPasarSiguiente(InicioAltairJacob.xedoc2, Utiles.coordenadaVentana, 500);
		 				siguiente.start();
		 			}
		 		}
		 		
		 		
		 		
		 	}
		 

		 	/*
		 	
			if(e.getKeyCode() == 106){		// * 106
				
				if(InicioAltairJacob.xedoc1Activo){

					
				//	System.out.println("Xedoc1Activo... ");

					boolean masDocumentos = true;
				
					Dispatch documento = Dispatch.call(InicioAltairJacob.xedoc2.navegador, "document").toDispatch();
					Variant masElementos = Dispatch.call(documento, "getElementById","submitFormFirmar");
					// System.out.println("Nodo cex " + masElementos.toString());
					if(masElementos.toString().equals("null")){
						System.out.println("No hay mas documentos de Xedoc 2.");
						masDocumentos = false;
					}
					
					
				//	System.out.println("Empezamos a cargar xedoc 1");
					InicioAltairJacob.xedoc1Activo = false;
					GestionXedoc.carganuevoPdf(InicioAltairJacob.xedoc1, "Xedoc 1");
					

					

					
				}
				else{
					
				//	System.out.println("Xedoc1Activo false... ");
					
					boolean masDocumentos = true;
					
					Dispatch documento = Dispatch.call(InicioAltairJacob.xedoc1.navegador, "document").toDispatch();
					Variant masElementos = Dispatch.call(documento, "getElementById","submitFormFirmar");
					// System.out.println("Nodo cex " + masElementos.toString());
					if(masElementos.toString().equals("null")){
						System.out.println("No hay mas documentos de Xedoc 1.");
						masDocumentos = false;
					}
										
					
				//	System.out.println("Empezamos a cargar xedoc 2");
					InicioAltairJacob.xedoc1Activo = true;
					GestionXedoc.carganuevoPdf(InicioAltairJacob.xedoc2, "Xedoc 2");
					

				}
				

			}
			
			*/

			//  Flecha derecha
			if(e.getKeyCode() == 39){
			}
			
			//  Flecha abajo
			if(e.getKeyCode() == 40){
			}
			
			//  Flecha izquierda
			if(e.getKeyCode() == 37){
			}
			

			//	º 92
			if(e.getKeyCode() == 92){

				Navegador navegador;
				
				if (InicioAltairJacob.xedoc1Activo) {
					navegador = InicioAltairJacob.xedoc1;
					
				} else {
					navegador = InicioAltairJacob.xedoc2;

				}
					
				HiloIndexacion indexacion = new HiloIndexacion(navegador);
				indexacion.start();
			
			}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
	//	 System.out.println("NativeKeyReleased " + arg0.getKeyCode());
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
	//	 System.out.println("NativeKeyTyped " + arg0.getKeyCode());
	}

	

	

	
}
