
package board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Set;

public class ComputerPlayer extends Player {
	ArrayList<Card> cardsSeen;
	
	public ComputerPlayer() {
		cardsSeen = new ArrayList<Card>();
	}

	public BoardCell pickLocation( Set<BoardCell> targets ) {
		return new BoardCell();
	}
	
	public Solution createSuggestion() {
		return new Solution();
	}
	
	public void updateSeen( Card cardSeen ) {
		cardsSeen.add(cardSeen);
	}
	
	public void setLocation(Point location) {
		this.Location = location;
	}
	
	public ArrayList<Card> getCardsSeenList() {
		return cardsSeen;
	}
}
