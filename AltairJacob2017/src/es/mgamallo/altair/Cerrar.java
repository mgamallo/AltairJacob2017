package es.mgamallo.altair;


import java.io.IOException;

import javax.swing.JOptionPane;

public class Cerrar {

	static public void cerrarTodo(){
		String cmd1 = "taskkill.exe /F /IM AcroRd32.exe ";
		String cmd2 = "taskkill.exe /F /IM /T iexplore.exe ";
		String cmd3 = "taskkill.exe /F /IM /T Acrobat.exe ";
		
		Process hijo1, hijo2, hijo3;
		
		cerrarIexplorer();
		
		try {
			hijo1 = Runtime.getRuntime().exec(cmd1);
			
			/*
			if(Inicio.esWin64){
				hijo1 = Runtime.getRuntime().exec(cmd3);
			}
			else{
				hijo1 = Runtime.getRuntime().exec(cmd1);
			}
			*/
			
		//	hijo2 = Runtime.getRuntime().exec(cmd2);
			
			// No se porqué con las siguientes instrucciones se bloquea en el ordenador 2.
			/*
			hijo1.waitFor();
			hijo2.waitFor();
			*/
			Thread.sleep(300);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error IOException al cerrar procesos");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error InterruptedException al cerrar procesos");
		}

	}
	
	static public void cerrarIexplorer(){
		
		String cmd2 = "taskkill.exe /F /IM iexplore.exe /T";
		
		Process hijo2;
		
		try {

			System.out.println("Empieza la matanza");
			hijo2 = Runtime.getRuntime().exec(cmd2);

			//			hijo2.waitFor();
			System.out.println("sleep");
			Thread.sleep(500);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error IOException al cerrar procesos");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error InterruptedException al cerrar procesos");
			
		} catch(IllegalThreadStateException e){
			e.printStackTrace();
		}

	}
	
	static public void cerrarFirefox(){

		String cmd2 = "taskkill.exe /F /IM firefox.exe /T";
		
		Process hijo2;
		
		try {

			System.out.println("Empieza la matanza");
			hijo2 = Runtime.getRuntime().exec(cmd2);

			//			hijo2.waitFor();
			System.out.println("sleep");
			Thread.sleep(500);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error IOException al cerrar procesos");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error InterruptedException al cerrar procesos");
			
		} catch(IllegalThreadStateException e){
			e.printStackTrace();
		}

	}
}
