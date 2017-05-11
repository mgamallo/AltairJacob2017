package es.prueba;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PruebaIE {

    // Declaración de la variable que almacenará el objeto OLE
    static ActiveXComponent IE;
    public static void main(String[] args){
      // Inicialización de la librería
      ComThread.InitSTA();
      // Creacón del objeto
      IE = new ActiveXComponent("InternetExplorer.Application");
      // Cambio de una propiedad del objeto
      Dispatch.put(IE, "Visible", true);

      // Invocación de un procedimiento
      Dispatch.call(IE, "Navigate", "http://www.google.es");
      
      
      
      Variant cadena = Dispatch.call(IE, "readyState");
      
      while(true){
    	  
    	  cadena = Dispatch.call(IE, "readyState");
    	  System.out.println(cadena.toString());
    	  if(Integer.valueOf(cadena.toString()) ==4){
    		  System.out.println("CArgado");
    		  break;
    	  }
      }
      
      Dispatch theDocument = Dispatch.call(IE, "Document").getDispatch();
      
      Dispatch.put(theDocument,"title","Hola pepe");
      
     // for(int i=0;i<theDocument.)
      
      System.out.println(cadena.toString());

      System.out.println(System.getProperty("sun.arch.data.model"));
      
      
      
      
      //Espera de 10 segundos
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(PruebaIE.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    /*****  Para ejecutar javascript ********************************************************************/    
        
     //   Dispatch window = Dispatch.get(theDocument, "parentwindow").toDispatch();
     //   System.out.println(Dispatch.call(window,"execScript","alert('1+1');").toString());
      
      Dispatch window = Dispatch.get(theDocument, "parentWindow").toDispatch();
      System.out.println(Dispatch.call(window,"execScript","alert('1+1');").toString());
      
      // Llamada al método que cierra la aplicación
     // Dispatch.call(IE, "Quit");
  }
}
