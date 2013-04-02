package board;

import java.awt.Color;
import java.awt.Graphics;

//Naomi and Brandon
public class RoomCell extends BoardCell {
	public enum DoorDirection { UP, DOWN, LEFT, RIGHT, NONE };
	private DoorDirection doorDirection;
	private char roomInitial;
	private boolean displayName;
	
	public RoomCell(int row, int column, char initial) {
		super(row,column);
		roomInitial = initial;
		doorDirection = DoorDirection.NONE;
	}
	
	public RoomCell(int row, int column, char initial, char direction) {
		super(row,column);
		roomInitial = initial;
		switch(direction) {
			case 'U' :
				this.doorDirection = DoorDirection.UP;
				break;
			case 'D' :
				this.doorDirection = DoorDirection.DOWN;
				break;
			case 'L' :
				this.doorDirection = DoorDirection.LEFT;
				break;
			case 'R' :
				this.doorDirection = DoorDirection.RIGHT;
				break;
			case 'N':
				this.doorDirection = DoorDirection.NONE;
				this.displayName = true;
				break;
			default: 
				this.doorDirection = DoorDirection.NONE;
				break;
		}
	}

	@Override
	public boolean isRoom(){
		return true;
	}
	
	@Override
	public boolean isDoorway() {
		if(doorDirection == DoorDirection.NONE)
			return false;
		else
			return true;
	}
	
	public char getInitial() {
		return roomInitial;
	}
	
	public void setInitial(char roomInitial) {
		this.roomInitial = roomInitial;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	@Override 
	public void draw (Graphics g, Board board) { 
		g.setColor(Color.GRAY); 
		g.fillRect( column * 25, row * 25, 25, 25);
		g.setColor(Color.BLUE);
		if (doorDirection == DoorDirection.LEFT) { 
			g.fillRect(column * 25, row * 25, 5, 25);
		} 
		if (doorDirection == DoorDirection.UP) { 
			g.fillRect(column *25, row *25,  25, 5); 
		}
		if (doorDirection == DoorDirection.RIGHT) { 
			g.fillRect((column+1)*25 -5, row *25, 5, 25); 
		}
		if (doorDirection == DoorDirection.DOWN) { 
			g.fillRect(column *25, (row+1) *25 - 5, 25, 5); 
		}
		if (displayName) {
			g.drawString(board.getRooms().get(roomInitial), column*25, row*25);			
		}

	}
}

