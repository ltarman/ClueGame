package Board;

public class WalkwayCell extends BoardCell {
	public boolean isWalkway() {return true;}
	
	public WalkwayCell(int row, int col) {
		super(row,col);
	}
}