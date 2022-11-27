import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calcolatrice extends JFrame implements ActionListener {
	
	private JPanel grid;
	private JPanel radioButtons;
	private JTextField risultati;
	
	public Calcolatrice() {
	
		super("calcolatrice binaria");
		this.risultati = risultati();
		this.radioButtons = radioButtons();
		this.grid = grid();
		
		getContentPane().add(risultati);
		getContentPane().add(radioButtons);
		getContentPane().add(grid);
		
		setSize(350,500);
		setResizable(false);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().setBackground(new Color(88,88,88));
		setVisible(true);
		}
	
	private JPanel grid() {
		
		JPanel grid = new JPanel();
		grid.setLayout(new GridLayout(6,5));
		grid.setPreferredSize(new Dimension(350,400));
		
		JButton [] tasti = new JButton[30];
		
		//prima riga
		tasti[0] = new JButton("A");
		tasti[1] = new JButton("<<");
		tasti[2] = new JButton(">>");
		tasti[3] = new JButton("Cc");
		tasti[4] = new JButton("<-");
		for(int i = 1; i < 5; i++) 
			tasti[i].setBackground(new Color(55,55,55));
		
		//seconda riga
		tasti[5] = new JButton("B");
		tasti[6] = new JButton("(");
		tasti[7] = new JButton(")");
		tasti[8] = new JButton("%");
		tasti[9] = new JButton("/");
		for(int i = 6; i < 10; i++) 
			tasti[i].setBackground(new Color(55,55,55));
		
		//terza riga
		tasti[10] = new JButton("C");
		for(int i = 11; i < 14; i++) {
			tasti[i] = new JButton("" + (i - 4));
			tasti[i].setBackground(new Color(33,33,33));
		}
		tasti[14] = new JButton("*");
		tasti[14].setBackground(new Color(55,55,55));
		
		//quarta riga
		tasti[15] = new JButton("D");
		for(int i = 16; i < 19; i++) {
			tasti[i] = new JButton("" + (i - 12));
			tasti[i].setBackground(new Color(33,33,33));
		}
		tasti[19] = new JButton("-");
		tasti[19].setBackground(new Color(55,55,55));
		
		//quinta riga
		tasti[20] = new JButton("E");
		for(int i = 21; i < 24; i++) {
			tasti[i] = new JButton("" + (i - 20));
			tasti[i].setBackground(new Color(33,33,33));
		}
		tasti[24] = new JButton("+");
		tasti[24].setBackground(new Color(55,55,55));
		
		//ultima riga
		tasti[25] = new JButton("F");
		tasti[26] = new JButton("+/-");
		tasti[27] = new JButton("0");
		tasti[28] = new JButton(",");
		tasti[29 ] = new JButton("=");
		for(int i = 26; i < 30; i++) 
			tasti[i].setBackground(new Color(55,55,55));
		
		//aggiunta dei tasti alla griglia
		for(int i = 0; i < 30; i++) {
			tasti[i].setFont(new Font("SansSerif",Font.BOLD,18));
			tasti[i].setForeground(new Color(255,255,255));
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
	
	private JPanel radioButtons() {
		
		JPanel radioButtons = new JPanel();
		JRadioButton[] basi = new JRadioButton[4];
		basi[0] = new JRadioButton("Esadecimale");
		basi[1] = new JRadioButton("decimale");
		basi[2] = new JRadioButton("ottale");
		basi[3] = new JRadioButton("binario");
		for(int i = 0; i < 4; i++) {
			radioButtons.add(basi[i]);	
			basi[i].setBackground(new Color(77,77,77));
			basi[i].setForeground(new Color(255,255,255));
			basi[i].setFont(new Font("SansSerif",Font.PLAIN, 13));
			basi[i].setBorderPainted(false);
		}
		radioButtons.setLayout(new FlowLayout()); 
		radioButtons.setBackground(new Color(77,77,77));
		radioButtons.setBorder(null);
		
		return radioButtons;
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
	
	public void actionPerformed(ActionEvent e) {
		
		try {
			switch(e.getActionCommand())
			{
				case "1": case "2": case "3": case "4": case "5": case "6": case "7": case "8": case "9": case "0":
				case "A": case "B": case "C": case "D": case "E": case "F": buttonNumero(e.getActionCommand()); break;
				case"%": case "+": case "-": case "*": case "/": buttonOperatore(e.getActionCommand()); break;
				case "=": buttonUguale();  risultati.setText("" + risultato); break;
				case "Cc": cancella(); break;
				case "<-": risultati.setText(risultati.getText().substring(0, risultati.getText().length() - 1)); break;
				case "+/-": opposto(); break;
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
		
		if(primoOperando != 0)
			buttonUguale();
		primoOperando = Double.valueOf(risultati.getText());
		risultati.setText("");
		segno = operatore;
	}
	
	private void buttonUguale() throws Exception{
		
		secondoOperando = Double.valueOf(risultati.getText());
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
}