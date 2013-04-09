package board;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

//Naomi and Brandon
public class BoardCell {
	protected int row;
	protected int column;
	protected boolean isHumanTarget;
	protected boolean clicked;
	
	public BoardCell() {
	}
	
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
	
	public void setIsHumanTarget(boolean bool) {
		isHumanTarget = bool;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public Point getLocation() {
		return new Point(column, row);
	}
	
	public boolean getClicked() {
		if ( clicked ) {
			clicked = false;
			return true;
		}
		return false;
	}
	
	public  boolean getIsHumanTarget() {
		return isHumanTarget;
	}
	
	public String toString() {
		return "Board Cell - Row: " + row + " Column: " + column;
	}

	public void draw(Graphics g, Board board) {}
	
	public boolean containsClick(Point click) {
		Rectangle rect = new Rectangle(column * 25, row * 25, 25, 25);
		if (rect.contains(click)) {
			clicked = true;
			return true;
		}
		return false;
	}
}
