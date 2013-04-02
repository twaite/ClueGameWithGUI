package board;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class GUI extends JFrame {
	Board board;
	ClueGame clue;
	
	public GUI() {
		clue = new ClueGame();
		board = clue.getBoard();
		setSize((board.getNumColumns()+1)*25, (board.getNumRows() + 2)*25);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(board, BorderLayout.CENTER);
		setVisible(true);
	}
	public static void main(String[] args) {
		GUI test = new GUI();
		test.setVisible(true);
	}
}
