package board;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlBottomPanel extends JPanel {
	private JTextField rollReturn;
	private JTextField guessField;
	private JTextField resultField;

	public ControlBottomPanel()
	{
		setLayout(new GridLayout(0, 3));
		
		JPanel die = new JPanel();
		JLabel roll = new JLabel("Roll");
		rollReturn = new JTextField(10);
		rollReturn.setEditable(false);
		die.add(roll);
		die.add(rollReturn);
		die.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		add(die);
		
		JPanel guess = new JPanel();
		guess.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		JLabel guessText = new JLabel("Guess");
		guess.add(guessText);
		guessField = new JTextField(20);
		guessField.setEditable(false);
		guess.add(guessField);
		add(guess);
		
		JPanel result = new JPanel();
		result.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		JLabel resultText = new JLabel("Response");
		result.add(resultText);
		resultField = new JTextField(10);
		resultField.setEditable(false);
		result.add(resultField);
		add(result);	
	}
	
	public void setRoll(int roll) {
		rollReturn.setText(Integer.toString(roll));
	}
	
	public void setGuess(Solution guess) {
		guessField.setText(guess.toString());
	}
	
	public void setResponse(String response) {
		resultField.setText(response);
	}
}
