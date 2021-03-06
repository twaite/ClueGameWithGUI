package board;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class Player {

	protected String name;
	protected String color;
	protected ArrayList<Card> cards = new ArrayList<Card>();
	protected ArrayList<Card> cardsOfGame = new ArrayList<Card>();
	protected Point location;
	protected char currentRoom;
	protected char lastRoomVisited;
	
	
	public Player () {
		 location = new Point();
	}
	
	public Player (String name, String color) {
		this.name = name;
		this.color = color;
		location = new Point();
	}
	
	public Player (String name, String color, Point p) {
		this.name = name;
		this.color = color;
		this.location = p;
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
		
		for (Card card : cards ) {
			if (card.getName().equals(personCard.getName()) && card.getCardType() == Card.CardType.PERSON) {
				cardsMatchingSuggestion.add(personCard);
			}
			if (card.getName().equals(weaponCard.getName()) && card.getCardType() == Card.CardType.WEAPON) {
				cardsMatchingSuggestion.add(weaponCard);
			}
			if (card.getName().equals(roomCard.getName()) && card.getCardType() == Card.CardType.ROOM) {
				cardsMatchingSuggestion.add(roomCard);
			}
		}
		
		if ( cardsMatchingSuggestion.size() == 0) {
			return null;
		}
		
		Random rand = new Random();
		int randIndex = rand.nextInt(cardsMatchingSuggestion.size());
		return cardsMatchingSuggestion.get(randIndex);
	}
	
	
	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}
	
	public Point getLocation() {
		return location;
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
		this.location = location;
	}
	
	public void draw(Graphics g, Board board) {
		Color c = convertColor(color);
		g.setColor(c);
		g.fillOval(((int) location.getX()) * 25 + 1, ((int) location.getY()) * 25 + 1, 25-1, 25-1);
		g.setColor(Color.BLACK);
		g.drawOval(((int) location.getX()) * 25 +1, ((int) location.getY()) * 25+1, 25-1, 25-1);
	}
	
	public Color convertColor( String strColor ) {
		Color color;
		try {
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color) field.get(null);
		} catch ( Exception e ) {
			color = null; // Not defined
		}
		return color;
	}
}
