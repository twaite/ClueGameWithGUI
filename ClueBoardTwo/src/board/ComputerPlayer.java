
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
		
		
		//for every boardcell in targets
		for (BoardCell b : targets) {
			//if the boardcell is a doorway
			if (b.isDoorway() ) {
				tempRoom = (RoomCell) b;
				//and if it is not the doorway to the last room visited
				if ( !(tempRoom.getInitial() == lastRoomVisited) ) {
					//add the doorway to a list of doors that are potential targets
					doorsOfTargets.add(tempRoom);
				//if it is the doorway of the last room visited	
				} else {
					//store the doorway in lastRoom
					lastRoom = (RoomCell) b;
//					int number = Math.random() * 100;
//					if (number > 95) {
//						doorsOfTargets.add(tempRoom);
//					}
				}
			}
		}
//		//if a doorway to the last visited room is not in our targets
//		if (lastRoom == null) {
			//and if we have other doors in our targets
			if (doorsOfTargets.size() != 0) {
				//randomly choose one of the other doors as the destination
				return doorsOfTargets.get( (int) Math.random() * doorsOfTargets.size() );
			//if there are no other doors in our targets	
			} else
				//randomly choose one of the walkway cells
				System.out.println(index);
				return (BoardCell) targetsArray[index];
		//other wise, if the doorway to the last room visited is not in our targets
//		} else {
//			if (doorsOfTargets.size() != 0) {
//				//randomly choose one of the other doors as the destination
//				return doorsOfTargets.get( (int) Math.random() * doorsOfTargets.size() );
//			//if there are no other doors in our targets	
//			} else
//				//randomly choose one of the walkway cells
//				return (BoardCell) targetsArray[index];
//		}
		
		
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
