package board;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PlayerCardPanel extends JPanel {
	
	PlayerCardPanel(ArrayList<Card> cards) {
		setLayout(new GridLayout(5, 0));
		
		JPanel title = new JPanel();
		title.add(new JLabel("My Cards"));
		add(title);
		
		JPanel people = new JPanel();
		people.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		people.setLayout(new GridLayout(3, 0));
		JTextField peopleField;
		
		JPanel rooms = new JPanel();
		rooms.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		rooms.setLayout(new GridLayout(3, 0));
		JTextField roomsField;
		
		JPanel weapons = new JPanel();
		weapons.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		weapons.setLayout(new GridLayout(3, 0));
		JTextField weaponsField;
		
		for ( Card card : cards ) {
			if ( card.getCardType() == Card.CardType.PERSON ) {
				peopleField = new JTextField(10);
				peopleField.setText(card.getName());
				peopleField.setEditable(false);
				people.add(peopleField);
			}
			
			if ( card.getCardType() == Card.CardType.ROOM ) {
				roomsField = new JTextField(10);
				roomsField.setText(card.getName());
				roomsField.setEditable(false);
				rooms.add(roomsField);
			}
			
			if ( card.getCardType() == Card.CardType.WEAPON ) {
				weaponsField = new JTextField(10);
				weaponsField.setText(card.getName());
				weaponsField.setEditable(false);
				weapons.add(weaponsField);
			}
		}
		
		add(people);
		add(rooms);
		add(weapons);
			
	}
}
