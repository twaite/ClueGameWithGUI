
package board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Set;

public class ComputerPlayer extends Player {
	ArrayList<Card> cardsSeen;
	
	
	public ComputerPlayer() {
		cardsSeen = new ArrayList<Card>();
	}
	
	public ComputerPlayer (String name, String color, Point p) {
		this.name = name;
		this.color = color;
		this.Location = p;
	}

	public BoardCell pickLocation( Set<BoardCell> targets ) {
		int index = (int) ( Math.random() * targets.size() );
		ArrayList<RoomCell> doorsOfTargets = new ArrayList<RoomCell>();
		Object[] targetsArray = targets.toArray();
		RoomCell tempRoom, lastRoom = null;
		
		for (BoardCell b : targets) {
			if (b.isDoorway() ) {
				tempRoom = (RoomCell) b;
				if ( !(tempRoom.getInitial() == lastRoomVisited) ) {
					doorsOfTargets.add(tempRoom);
				} else {
					lastRoom = (RoomCell) b;
//					int number = Math.random() * 100;
//					if (number > 95) {
//						doorsOfTargets.add(tempRoom);
//					}
				}
			}
		}
		if (lastRoom == null) {
			if (doorsOfTargets.size() != 0) {
				return doorsOfTargets.get( (int) Math.random() * doorsOfTargets.size() );
			} else
				return (BoardCell) targetsArray[index];
		} else {
			return (BoardCell) targetsArray[index];
		}
		
		
		//return new BoardCell();
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
