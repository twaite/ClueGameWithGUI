package board;
//Naomi and Brandon
public class RoomCell extends BoardCell {
	public enum DoorDirection { UP, DOWN, LEFT, RIGHT, NONE };
	private DoorDirection doorDirection;
	private char roomInitial;
	
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
			default: 
				this.doorDirection = DoorDirection.NONE;
				break;
		}
	}

	public char getInitial() {
		return roomInitial;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	
	//Override draw when we add GUI

}
