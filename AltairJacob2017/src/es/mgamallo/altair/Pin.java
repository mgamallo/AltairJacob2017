package es.mgamallo.altair;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class Pin {

	private String pin;

	public Pin() {

		boolean error = false;
		do {
			String texto = "Introduce el Pin";
			if (error) {
				texto = "Introduce el Pin de nuevo";
			}
			
			error = false;
			
			VentanaPin vp = new VentanaPin(texto);
			
			this.setPin(vp.getPin());
			
			System.out.println(pin);
			System.out.println(pin.length());
			
			if( this.pin.length() > 0 && this.pin.length() != 4 ){
				error = true;
			}
			System.out.println(error);

		} while (error);

	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}
	
	public static void main(String args[]){
		new Pin();
	}
}
