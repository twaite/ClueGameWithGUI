package board;

import java.awt.Point;
import java.util.ArrayList;

public class Player {

	String name;
	String color;
	ArrayList<Card> cards = new ArrayList<Card>();
	Point Location = new Point();
	char lastRoomVisited;
	
	
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
	public Card disproveSuggestion( String person, String room, String weapon ) {
		return new Card();
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
	
	
	public void setLastRoomVisited( char roomInitial ) {
		this.lastRoomVisited = roomInitial;
	}
}
