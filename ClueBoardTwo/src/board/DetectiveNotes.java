package board;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog {
	
	public DetectiveNotes(ClueGame game, Board board) {
		setTitle("Detective Notes");
		setSize(700, 400);
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
		
		//Person Guess panel
		JPanel personGuess = new JPanel();
		personGuess.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		personGuess.setLayout(new GridLayout(0, 2));
		JComboBox personBox = new JComboBox();
		personBox.addItem("Unsure");
		for ( Player p : game.getPlayerList() ) {
			personBox.addItem(p.getName());
		}
		personGuess.add(personBox);
		add(personGuess);
		
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
		
		//Room Guess panel
		JPanel roomGuess = new JPanel();
		roomGuess.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		roomGuess.setLayout(new GridLayout(0, 2));
		JComboBox roomBox = new JComboBox();
		roomBox.addItem("Unsure");
		for ( Map.Entry<Character, String> entry : board.getRooms().entrySet() ) {
			if( entry.getKey() != 'X' && entry.getKey() != 'W') {
				roomBox.addItem(entry.getValue());
			}
		}
		roomGuess.add(roomBox);
		add(roomGuess);
		
		//Weapons panel
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
				
		//Weapon Guess panel
		JPanel weaponGuess = new JPanel();
		weaponGuess.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		weaponGuess.setLayout(new GridLayout(0, 2));
		JComboBox weaponBox = new JComboBox();
		for ( Card card : game.getCardList() ) {
			if ( card.getCardType() == Card.CardType.WEAPON ) {
				weaponBox.addItem(card.getName());
			}
		}
		weaponGuess.add(weaponBox);
		add(weaponGuess);
	}
}