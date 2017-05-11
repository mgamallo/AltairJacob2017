package es.mgamallo.altair;

import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.SwingUtilities;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class HiloPasarSiguiente extends Thread {

	Navegador navegador;
	Point coordenadaVentana;
	int retardoMaximo;

	public HiloPasarSiguiente(Navegador navegador, Point coordenadaVentana, int retardoMaximo) {
		// TODO Auto-generated constructor stub
		
		this.navegador = navegador;
		this.coordenadaVentana = coordenadaVentana;
		this.retardoMaximo = retardoMaximo;
	}
	
	public void run(){
		Utiles.pasarAlSiguientePdf(navegador, coordenadaVentana, retardoMaximo);
	}
}
