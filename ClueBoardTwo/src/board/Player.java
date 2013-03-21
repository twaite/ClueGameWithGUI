package board;

import java.awt.Point;
import java.util.ArrayList;

public class Player {

	String name;
	String color;
	ArrayList<Card> cards = new ArrayList<Card>();
	Point Location = new Point();
	String lastRoomVisited = "";
	
	
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
	
	
	public void setLastRoomVisited( String roomName ) {
		this.lastRoomVisited = roomName;
	}
}
