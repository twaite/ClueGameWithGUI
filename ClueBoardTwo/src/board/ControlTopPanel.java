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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ControlTopPanel extends JPanel {
	JTextField currPlayerText;
	
	public ControlTopPanel(ClueGame game, ControlBottomPanel bottom) {
		setLayout(new GridLayout(0,3));
		JPanel questionPanel = new JPanel();
		questionPanel.setLayout(new GridLayout(3, 0));
		JPanel centeredLabel = new JPanel();
		centeredLabel.setLayout(new GridBagLayout());
		JLabel label = new JLabel("Whose turn?");
		centeredLabel.add(label);
		JPanel centeredText = new JPanel();
		centeredText.setLayout(new GridBagLayout());
		currPlayerText = new JTextField(15);
		currPlayerText.setFont(new Font("SansSerif", Font.BOLD, 12));
		currPlayerText.setText(game.getPlayerList().get(game.getTurnIndicator()).getName());
		centeredText.add(currPlayerText);
		questionPanel.add(centeredLabel);
		questionPanel.add(centeredText);
		add(questionPanel);
		
		//Add the next player button.
		JButton nextPlayer = new JButton("Next Player");
		
		//ButtonListener class
		class ButtonListener implements ActionListener {
			ClueGame game;
			ControlTopPanel panelTop;
			ControlBottomPanel panelBot;
			
			public ButtonListener(ClueGame game, ControlTopPanel panelTop, ControlBottomPanel panelBot) {
				super();
				this.game = game;
				this.panelTop = panelTop;
				this.panelBot = panelBot;
			}
			
			public void actionPerformed(ActionEvent e) {
				if ( !game.getBoard().getHumanMustFinish() ) {
					game.nextPlayer();
					panelTop.setCurrentPlayer(game.getPlayerList().get(game.getTurnIndicator()).getName());
					panelBot.setRoll(game.getRoll());
					panelBot.setGuess(game.getLastGuess());
					if (game.getResponse() != null) {
						panelBot.setResponse(game.getResponse().getName());
					} else {
						panelBot.setResponse("No new card");
					}
				} else {
					String dialogMessage = "You must finish your turn";
					String dialogTitle = "Error";
					JOptionPane.showMessageDialog(panelTop, dialogMessage, dialogTitle, JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		
		nextPlayer.addActionListener(new ButtonListener(game, this, bottom));
		add(nextPlayer);
		
		class accusationButtonListener implements ActionListener {
			private ClueGame game;
			private ControlBottomPanel bottom;
			
			public accusationButtonListener(ClueGame game, ControlBottomPanel bottom) {
				this.game = game;
				this.bottom = bottom;
			}
			
			public void actionPerformed(ActionEvent e) {
				if ((game.getPlayerList().get(game.getTurnIndicator()) instanceof HumanPlayer) && !game.getPlayerMoved()) {
					GuessDialog makeAccusation = new GuessDialog(null, true, game.getCardList(), game, bottom);
					makeAccusation.setVisible(true);
					game.setTurnIndicator(game.getTurnIndicator() + 1);
				}
			}
		}
		
		//Add the accusation button.
		JButton accusationButton = new JButton("Make an accusation");
		accusationButton.addActionListener(new accusationButtonListener(game, bottom));
		add(accusationButton);
	}
	
	public void setCurrentPlayer(String player) {
		currPlayerText.setText(player);
	}

}
