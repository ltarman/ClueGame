package Board;

abstract public class BoardCell {
	private int row;
	private int column;
	
	public BoardCell(int row, int col) {
		this.row = row;
		this.column = col;
	}
	
	public boolean isWalkway() {return false;}
	public boolean isRoom() {return false;}
	public boolean isDoorway() {return false;}
}