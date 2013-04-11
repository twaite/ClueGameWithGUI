package board;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameControlGUI extends JPanel{
	ControlBottomPanel panelSouth;
	
	public GameControlGUI(ClueGame clue) {
		//Set Size and Layout
		setSize(800, 200);
		setLayout(new GridLayout(2, 0));
		
		//Set up south panel.
		panelSouth = new ControlBottomPanel();
		
		//Set up north panel.
		ControlTopPanel panelNorth = new ControlTopPanel(clue, panelSouth);
		
		add(panelNorth, BorderLayout.NORTH);
		add(panelSouth, BorderLayout.SOUTH);
		setVisible(true);
	
	}
	
}
