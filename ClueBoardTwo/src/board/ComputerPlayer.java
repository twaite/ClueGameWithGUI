
package board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	ArrayList<Card> cardsSeen;
	
	
	public ComputerPlayer() {
		cardsSeen = new ArrayList<Card>();
	}
	
	//Constructor for testing purposes. (In GameActionTests)
	public ComputerPlayer(char currentRoom) {
		cardsSeen = new ArrayList<Card>();
		this.currentRoom = currentRoom;
	}
	
	public ComputerPlayer (String name, String color, Point p) {
		cardsSeen = new ArrayList<Card>();
		this.name = name;
		this.color = color;
		this.location = p;
	}
	
	public void makeMove(HashSet<BoardCell> targets) {
		BoardCell target = pickLocation(targets);
		int row = target.getRow();
		int col = target.getColumn();
		location = new Point(col, row);
		//System.out.println("Row: " + row + " Column: " + col);
	}
	
	/*public BoardCell pickLocation(Set<BoardCell> targets) {
		Random rand = new Random();
		int r = 0;
		r = rand.nextInt(targets.size());
		
		for ( BoardCell bc : targets ) {
			if ( bc.isRoom() ) {
				RoomCell cell = (RoomCell) bc;
				if ( lastRoomVisited == cell.getInitial() )
					return bc;
			}
		}
		
		int i = 0;
		for ( BoardCell bc : targets ) {
			if ( i == r ) return bc;
			++i;
		}
		
		return null;
	}*/

	public BoardCell pickLocation( HashSet<BoardCell> targets ) {
		/*for (BoardCell cell : targets) {
		    System.out.println(cell);
		}*/
		int index = (int) ( Math.random() * targets.size() );
		ArrayList<RoomCell> doorsOfTargets = new ArrayList<RoomCell>();
		Object[] targetsArray = targets.toArray();
		ArrayList<BoardCell> targetsArrayList = new ArrayList<BoardCell>();
		RoomCell tempRoom, lastRoom = null;
		
		for (Object o : targetsArray) {
			targetsArrayList.add((BoardCell) o);
		}
		
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
				}
			}
		}
			if (doorsOfTargets.size() != 0) {
				//randomly choose one of the other doors as the destination
				RoomCell newBoardCell = doorsOfTargets.get( (int) Math.random() * doorsOfTargets.size() );
				currentRoom = newBoardCell.getInitial();
				return newBoardCell;
			//if there are no other doors in our targets	
			} else
				//randomly choose one of the walkway cells
				return targetsArrayList.get(index);
	}
	
	public Solution createSuggestion() {
		boolean foundPlayer = false;
		boolean foundWeapon = false;
		Card randCard = new Card();
		Solution suggestion = new Solution();
		int randIndex;
		ArrayList<Card> cardsNotSeen = new ArrayList<Card>();
		
		//set the room
		suggestion.setRoom(initialToString(currentRoom));
		
		//populate cardsNotSeen with all of the cards that are not in cardsSeen
		for (Card c : this.cardsOfGame) {
			if (cardsSeen.contains(c) == false) {
				cardsNotSeen.add(c);
			}
		}
		
		while (!foundPlayer || !foundWeapon) {
			//set the person
			if (!foundPlayer) {
				randIndex = (int) ( Math.random() * cardsNotSeen.size() );
				randCard = cardsNotSeen.get(randIndex);
				if (randCard.getCardType() == Card.CardType.PERSON) {
					foundPlayer = true;
					suggestion.setPerson(randCard.getName());
				}
			}
			
			//set the weapon
			if (!foundWeapon) {
				randIndex = (int) ( Math.random() * cardsNotSeen.size() );
				randCard = cardsNotSeen.get(randIndex);
				if (randCard.getCardType() == Card.CardType.WEAPON) {
					foundWeapon = true;
					suggestion.setWeapon(randCard.getName());
				}
			}
		
		}
		
		
		return suggestion;
	
	}
	
	public void updateSeen( Card cardSeen ) {
		cardsSeen.add(cardSeen);
	}
	
	public ArrayList<Card> getCardsSeenList() {
		return cardsSeen;
	}
}
