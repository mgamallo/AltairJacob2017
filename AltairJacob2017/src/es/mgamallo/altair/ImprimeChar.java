package es.mgamallo.altair;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class ImprimeChar {
	private static void imprimeChar(int codigo) {
		try {
			Robot robot = new Robot();
			
			/*robot.mouseMove(400, 1111);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			robot.delay(100);
			*/
					robot.keyPress(codigo);
					// robot.delay(50);
					robot.keyRelease(codigo);
					System.out.println(codigo);

		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	

	public static void getChar(char c) {
		
		System.out.println(c);
		int codigo = 0;

		switch (c) {

		case '0':
			codigo = KeyEvent.VK_0;
			break;
		case '1':
			codigo = KeyEvent.VK_1;
			break;
		case '2':
			codigo = KeyEvent.VK_2;
			break;
		case '3':
			codigo = KeyEvent.VK_3;
			break;
		case '4':
			codigo = KeyEvent.VK_4;
			break;
		case '5':
			codigo = KeyEvent.VK_5;
			break;
		case '6':
			codigo = KeyEvent.VK_6;
			break;
		case '7':
			codigo = KeyEvent.VK_7;
			break;
		case '8':
			codigo = KeyEvent.VK_8;
			break;
		case '9':
			codigo = KeyEvent.VK_9;
			break;

		default:
			// codigo = KeyEvent.VK_C;
		}

		imprimeChar(codigo);

	}
	
	public static void main(String args[]){
		String cadena = "1234";
		
		for(char c : cadena.toCharArray()){
			getChar(c);
		}
	}
}
