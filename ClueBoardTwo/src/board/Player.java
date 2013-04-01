package board;

import java.awt.Point;
import java.util.ArrayList;

public class Player {

	protected String name;
	protected String color;
	protected ArrayList<Card> cards = new ArrayList<Card>();
	protected ArrayList<Card> cardsOfGame = new ArrayList<Card>();
	protected Point Location = new Point();
	protected char currentRoom;
	protected char lastRoomVisited;
	
	
	public Player () {
		
	}
	
	public Player (String name, String color) {
		this.name = name;
		this.color = color;
	}
	
	public Player (String name, String color, Point p) {
		this.name = name;
		this.color = color;
		this.Location = p;
	}
	
	/************************************************************************************************************
 	* Parameters: String person, String room, String weapon
 	* 
 	************************************************************************************************************/
	public Card disproveSuggestion( String person, String weapon, String room ) {
		Card personCard = new Card(person, Card.CardType.PERSON);
		Card weaponCard = new Card(weapon, Card.CardType.WEAPON);		
		Card roomCard = new Card(room, Card.CardType.ROOM);
		ArrayList<Card> cardsMatchingSuggestion = new ArrayList<Card>();
		
		if (cards.contains(personCard)) {
			cardsMatchingSuggestion.add(personCard);
		}
		if (cards.contains(weaponCard)) {
			cardsMatchingSuggestion.add(weaponCard);
		}
		if (cards.contains(roomCard)) {
			cardsMatchingSuggestion.add(roomCard);
		}
		if ( cardsMatchingSuggestion.size() == 0) {
			return null;
		}
		
		int randIndex = (int) ( Math.random() * cardsMatchingSuggestion.size() );
		
		return cardsMatchingSuggestion.get(randIndex);
	}
	
	
	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}
	
	public Point getLocation() {
		return Location;
	}
	
	public ArrayList<Card> getCardList() {
		return cards;
	}
	
	public void setCardsOfGameList( ArrayList<Card> newCards ) {
		this.cardsOfGame = new ArrayList<Card>(newCards);
	}
	
	
	public void setLastRoomVisited( char roomInitial ) {
		this.lastRoomVisited = roomInitial;
	}
	
	public String initialToString( char initial ) {
		switch (initial) {
		case ('c'):
		case ('C'):
			return "Conservatory";
		case ('k'):
		case ('K'):
			return "Kitchen";
		case ('b'):
		case ('B'):
			return "Ballroom";
		case ('r'):
		case ('R'):
			return "Billiard Room";
		case ('l'):
		case ('L'):
			return "Lounge";
		case ('s'):
		case ('S'):
			return "Study";
		case ('d'):
		case ('D'):
			return "Dining Room";
		case ('o'):
		case ('O'):
			return "Lounge";
		case ('h'):
		case ('H'):
			return "Hall";
		}
		
		return null;
			
	}
	
	public void setLocation(Point location) {
		this.Location = location;
	}
}
