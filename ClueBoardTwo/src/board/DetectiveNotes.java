package board;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog {
	
	public DetectiveNotes(ClueGame game, Board board) {
		setTitle("Detective Notes");
		setSize(400, 400);
		setLayout(new GridLayout(3, 2));
		
		//People panel
		JPanel people = new JPanel();
		people.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		people.setLayout(new GridLayout(3, 3));
			
		JPanel person;
		
		for ( Player p : game.getPlayerList() ) {
			person = new JPanel();
			person.add(new JCheckBox(p.getName()));
			people.add(person);
		}
		
		add(people);	
		
		//Rooms panel
		JPanel rooms = new JPanel();
		rooms.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		rooms.setLayout(new GridLayout(3, 3));
		
		JPanel room;
		
		for ( Map.Entry<Character, String> entry : board.getRooms().entrySet() ) {
			if( entry.getKey() != 'X' && entry.getKey() != 'W') {
				room = new JPanel();
				room.add(new JCheckBox(entry.getValue()));
				rooms.add(room);
			}
		}
		
		add(rooms);
		
		//Rooms panel
		JPanel weapons = new JPanel();
		weapons.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		weapons.setLayout(new GridLayout(3, 3));
		
		JPanel weapon;
		
		for ( Card card : game.getCardList() ) {
			if ( card.getCardType() == Card.CardType.WEAPON ) {
				weapon = new JPanel();
				weapon.add(new JCheckBox(card.getName()));
				weapons.add(weapon);
			}
		}
		
		add(weapons);
	}
}