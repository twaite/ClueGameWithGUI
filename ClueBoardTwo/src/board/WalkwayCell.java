package board;

import java.awt.Color;
import java.awt.Graphics;


//Brandon and Naomi
public class WalkwayCell extends BoardCell{
	public WalkwayCell(int row, int column) {
		super(row,column);
	}
	
	@Override
	public boolean isWalkway(){
		return true;
	}
	
	@Override
	public void draw(Graphics g, Board board) {
		if ( isHumanTarget ) {
			g.setColor(Color.CYAN);
		} else {
			g.setColor(Color.YELLOW);
		}
		g.fillRect(column *25, row *25, 25, 25);
		g.setColor(Color.BLACK);
		g.drawRect(column *25, row *25, 25, 25);
	}
}
