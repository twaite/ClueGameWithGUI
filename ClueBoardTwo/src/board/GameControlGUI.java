package board;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameControlGUI extends JPanel{

	public GameControlGUI(ClueGame clue) {
		//Set Size and Layout
		setSize(800, 200);
		setLayout(new GridLayout(2, 0));
		
		//Set up north panel.
		ControlTopPanel panelNorth = new ControlTopPanel(clue);
		add(panelNorth, BorderLayout.NORTH);
		
		//Set up south panel.
		ControlBottomPanel panelSouth = new ControlBottomPanel();
		add(panelSouth, BorderLayout.SOUTH);
		setVisible(true);

	}
	
}
