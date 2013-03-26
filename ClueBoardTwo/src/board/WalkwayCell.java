package board;


//Brandon and Naomi
public class WalkwayCell extends BoardCell{
	public WalkwayCell(int row, int column) {
		super(row,column);
	}
	
	@Override
	public boolean isWalkway(){
		return true;
	}
	
	//Override draw method when we add GUI

}
