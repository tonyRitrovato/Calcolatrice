import javax.swing.*;
import java.awt.*;


public class Main {

	public static void main(String args[]) {
		try {
			Calcolatrice c = new Calcolatrice();
		}
		catch(Exception e) {
			JFrame error = new JFrame();
			error.getContentPane().add(new JLabel(e.toString()));
			error.setSize(200,200);
			error.setVisible(true);
		}
	}
}
