package board;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class GUI extends JFrame {
	private Board board;
	private ClueGame clue;
	private DetectiveNotes notes;
	private GameControlGUI control;
	private PlayerCardPanel cards;
	private ControlBottomPanel bottom;
	
	public GUI() {
		clue = new ClueGame(this);
		board = clue.getBoard();
		control = new GameControlGUI(clue);
		clue.deal();
		cards = new PlayerCardPanel(clue.getHumanPlayer().getCardList());
		setSize(705, 745);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue");
		add(cards, BorderLayout.EAST);
		add(control, BorderLayout.SOUTH);
		add(board, BorderLayout.CENTER);
		setVisible(true);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		bottom = control.panelSouth;
	}
	
	public static void main(String[] args) {
		String dialogMessage = "You are Orezy, press Next Player to begin play";
		String dialogTitle = "Welcome to Clue";
		GUI test = new GUI();
		test.setVisible(true);
		JOptionPane.showMessageDialog(test, dialogMessage, dialogTitle, JOptionPane.INFORMATION_MESSAGE);
	}
	
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createDetectiveNotes());
		menu.add(createFileExitItem());
		return menu;
	}
	
	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	private JMenuItem createDetectiveNotes() {
		JMenuItem item = new JMenuItem("Notes");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				notes = new DetectiveNotes(clue, board);
				notes.setVisible(true);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	public ControlBottomPanel getControlBottomPanel() {
		return bottom;
	}
}
