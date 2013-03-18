package board;
//Naomi and Brandon
public class BoardCell {
	private int row;
	private int column;
	
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public boolean isWalkway(){
		//return false if the cell is not a walkway
		return false;
	}
	

	public boolean isRoom(){
		//return false if the cell is not a room
		return false;
	}
	
	public boolean isDoorway(){
		//return false if the cell is not a doorway
		return false;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	//later add abstract method named draw
	
}
