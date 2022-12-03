import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calcolatrice extends JFrame implements ActionListener {
	
	private JPanel grid;
	private JTextField risultati;
	JButton [] tasti = new JButton[30];
	
	public Calcolatrice() {
	
		super("calcolatrice binaria");
		this.risultati = risultati();
		this.grid = grid();
		
		getContentPane().add(risultati);
		getContentPane().add(grid);
		
		setSize(350,500);
		setResizable(false);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().setBackground(new Color(88,88,88));
		disabilita();
		setVisible(true);
		}
	
	private JPanel grid() {
		
		JPanel grid = new JPanel();
		grid.setLayout(new GridLayout(6,5));
		grid.setPreferredSize(new Dimension(350,400));
		
		//prima riga
		tasti[0] = new JButton("A");
		tasti[1] = new JButton("Hex");
		tasti[2] = new JButton("Dec");
		tasti[3] = new JButton("Cc");
		tasti[4] = new JButton("<-");
		
		//seconda riga
		tasti[5] = new JButton("B");
		tasti[6] = new JButton("Oct");
		tasti[7] = new JButton("Bin");
		tasti[8] = new JButton("%");
		tasti[9] = new JButton("/");
		
		//terza riga
		tasti[10] = new JButton("C");
		for(int i = 11; i < 14; i++) {
			tasti[i] = new JButton("" + (i - 4));
		}
		tasti[14] = new JButton("*");
		
		//quarta riga
		tasti[15] = new JButton("D");
		for(int i = 16; i < 19; i++) {
			tasti[i] = new JButton("" + (i - 12));
		}
		tasti[19] = new JButton("-");
		
		//quinta riga
		tasti[20] = new JButton("E");
		for(int i = 21; i < 24; i++) {
			tasti[i] = new JButton("" + (i - 20));
		}
		tasti[24] = new JButton("+");
		
		//ultima riga
		tasti[25] = new JButton("F");
		tasti[26] = new JButton("+/-");
		tasti[27] = new JButton("0");
		tasti[28] = new JButton(",");
		tasti[29 ] = new JButton("=");
		
		//aggiunta dei tasti alla griglia
		for(int i = 0; i < 30; i++) {
			tasti[i].setFont(new Font("SansSerif",Font.BOLD,18));
			tasti[i].setForeground(new Color(255,255,255));
			tasti[i].setBackground(new Color(33,33,33));
			tasti[i].setBorderPainted(false);
			tasti[i].addActionListener(this);
			grid.add(tasti[i]);
		}
		
		for(int i = 0; i < 30; i+=5) {
			tasti[i].setBackground(new Color(33,33,33));
		}
		
		tasti[27].setBackground(new Color(33,33,33));
		
		grid.setBorder(null);
		return grid;
	}
	
	private JTextField risultati() {
		
		JTextField risultati = new JTextField("0");
		risultati.setHorizontalAlignment(JTextField.RIGHT);
		risultati.setFont(new Font("SansSerif", Font.BOLD, 30));
		risultati.setPreferredSize(new Dimension(350,150));
		risultati.setBackground(new Color(77,77,77));
		risultati.setForeground(new Color(255,255,255));
		risultati.setBorder(null);
		return risultati;
	}
	
	private double primoOperando;
	private double secondoOperando;
	private double risultato = 0;
	private String segno;
	private int base = 10;
	private double rimasugli = 0;
	
	public void actionPerformed(ActionEvent e) {
		
		try {
			switch(e.getActionCommand())
			{
				case "1": case "2": case "3": case "4": case "5": case "6": case "7": case "8": case "9": case "0":
				case "A": case "B": case "C": case "D": case "E": case "F": buttonNumero(e.getActionCommand()); break;
				case"%": case "+": case "-": case "*": case "/": buttonOperatore(e.getActionCommand()); break;
				case "=": buttonUguale();  risultati.setText("" + doubleToBase(risultato)); break;
				case "Cc": cancella(); break;
				case "<-": risultati.setText(risultati.getText().substring(0, risultati.getText().length() - 1)); break;
				case "+/-": opposto(); break;
				case ",": buttonNumero("."); break;
				case "Dec":
					rimasugli = baseToDoble(risultati.getText());
					base = 10;
					abilita();
					disabilita();
					risultati.setText("" + doubleToBase(rimasugli));
					break;
				case "Oct":
					rimasugli = baseToDoble(risultati.getText());
					base = 8;
					abilita();
					disabilita();
					risultati.setText("" + doubleToBase(rimasugli));
					break;
				case "Bin":
					rimasugli = baseToDoble(risultati.getText());
					base = 2;
					abilita();
					disabilita();
					risultati.setText("" + doubleToBase(rimasugli));
					break;
				case "Hex":
					rimasugli = baseToDoble(risultati.getText());
					base = 16;
					abilita();
					disabilita();
					risultati.setText("" + doubleToBase(rimasugli));
					break;
			}
		}
		catch(Exception e1) {
			risultati.setText(e1.toString());
		}
		
	}
	
	
	private void buttonNumero(String n) {
		
		String num = risultati.getText();
		if(risultati.getText().compareTo("0") == 0) 
			risultati.setText(n);
		else 
			risultati.setText(num + n);
	}
	
	private void buttonOperatore(String operatore) throws Exception {
		if(primoOperando != 0) throw new Exception("operazione ancora in corso");
		primoOperando = baseToDoble(risultati.getText());
		risultati.setText("");
		segno = operatore;
	}
	
	private void buttonUguale() throws Exception{
		secondoOperando = baseToDoble(risultati.getText());
		switch(segno) {
		case "+": 
			risultato = primoOperando + secondoOperando;
			break;
		case "-":
			risultato = primoOperando - secondoOperando;
			break;
		case "*":
			risultato = primoOperando * secondoOperando;
			break;
		case "/":
			if(secondoOperando == 0) throw new Exception("divisione non valida");
			risultato = primoOperando / secondoOperando;
			break;
		case"%":
			risultato = primoOperando % secondoOperando;
		}
		primoOperando = 0;
		secondoOperando = 0;
	}
	
	private void cancella() {
		primoOperando = 0;
		segno = "";
		secondoOperando = 0;
		risultato = 0;
		risultati.setText("0");
	}
	
	private void opposto() {
		
		double n = Double.valueOf(risultati.getText());
		double c = 2*n;
		double opposto = n - c;
		risultati.setText("" + opposto);
	}

	private void abilita() {
		for(int i = 0; i < 30; i++){
			tasti[i].setEnabled(true);
			tasti[i].setBackground(new Color(33,33,33));
			tasti[i].setForeground(new Color(255,255,255));
		}
	}

	private void disabilita() {

		switch (base) {
			case 2:
			for(int i = 11; i < 14; i++) {
				tasti[i].setBackground(new Color(0,0,0));
				tasti[i].setForeground(new Color(88,88,88));
				tasti[i].setEnabled(false);
			}

			for(int i = 16; i < 19; i++) {
				tasti[i].setBackground(new Color(0,0,0));
				tasti[i].setForeground(new Color(88,88,88));
				tasti[i].setEnabled(false);
			}

			tasti[22].setBackground(new Color(0,0,0));
			tasti[22].setForeground(new Color(88,88,88));
			tasti[22].setEnabled(false);
			tasti[23].setBackground(new Color(0,0,0));
			tasti[23].setForeground(new Color(88,88,88));
			tasti[23].setEnabled(false);
			case 8: 
			tasti[12].setBackground(new Color(0,0,0));
			tasti[12].setForeground(new Color(88,88,88));
			tasti[12].setEnabled(false);
			tasti[13].setBackground(new Color(0,0,0));
			tasti[13].setForeground(new Color(88,88,88));
			tasti[13].setEnabled(false);
			case 10:
			for(int i = 0; i < 30; i+=5) {
				tasti[i].setBackground(new Color(0,0,0));
				tasti[i].setForeground(new Color(88,88,88));
				tasti[i].setEnabled(false);
			} 
			break;
		}
	}

	private double baseToDoble(String s) {
		long dec = Long.parseLong(s, base);
		return Double.valueOf(dec);
	}

	private String doubleToBase(double n) {

		long doubleAsLong = Double.valueOf(n).longValue();
		String doubleAsString = "";
		switch (base) 
		{
			case 2: doubleAsString = Long.toBinaryString(doubleAsLong); break;
			case 8: doubleAsString = Long.toOctalString(doubleAsLong); break;
			case 10: doubleAsString = Long.toString(doubleAsLong); break;
			case 16: doubleAsString = Long.toHexString( doubleAsLong ); break;
		}
		return doubleAsString;
	}

	public static void main(String args[]) {
		Calcolatrice c = new Calcolatrice();
	}
}