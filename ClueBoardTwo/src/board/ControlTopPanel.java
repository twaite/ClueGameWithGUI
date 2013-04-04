package board;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ControlTopPanel extends JPanel {
	
	public ControlTopPanel(ClueGame game) {
		setLayout(new GridLayout(0,3));
		JPanel questionPanel = new JPanel();
		questionPanel.setLayout(new GridLayout(3, 0));
		JPanel centeredLabel = new JPanel();
		centeredLabel.setLayout(new GridBagLayout());
		JLabel label = new JLabel("Whose turn?");
		centeredLabel.add(label);
		JPanel centeredText = new JPanel();
		centeredText.setLayout(new GridBagLayout());
		JTextField textField = new JTextField(15);
		textField.setFont(new Font("SansSerif", Font.BOLD, 12));
		centeredText.add(textField);
		questionPanel.add(centeredLabel);
		questionPanel.add(centeredText);
		add(questionPanel);
		
		//Add the next player button.
		JButton nextPlayer = new JButton("Next Player");
		
		//ButtonListener class
		class ButtonListener implements ActionListener {
			ClueGame game;
			
			public ButtonListener(ClueGame game) {
				super();
				this.game = game;
			}
			
			public void actionPerformed(ActionEvent e) {
				game.nextPlayer();
			}
		}
		
		nextPlayer.addActionListener(new ButtonListener(game));
		add(nextPlayer);
		
		//Add the accusation button.
		JButton accusationButton = new JButton("Make an accusation");
		add(accusationButton);
	}

}
