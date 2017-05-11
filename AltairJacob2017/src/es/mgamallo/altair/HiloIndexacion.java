package es.mgamallo.altair;

import java.awt.Toolkit;

import javax.swing.SwingUtilities;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class HiloIndexacion extends Thread {

	Navegador navegador;

	public HiloIndexacion(Navegador navegador) {
		// TODO Auto-generated constructor stub
		
		this.navegador = navegador;
	}
	
	public void run(){
		Utiles.indexarDocumento(navegador);
	}
}
