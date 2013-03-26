package Board;

public class RoomCell extends BoardCell {
	public enum DoorDirection {UP,DOWN,LEFT,RIGHT,NONE;}
	
	private DoorDirection doorDirection;
	private char room;
	
	public RoomCell(int row, int col, char room, DoorDirection direction) {
		super(row,col);
		this.room = room;
		this.doorDirection = direction;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return room;
	}
	
	public void setDirection(DoorDirection direction) {
		this.doorDirection = direction;
	}

	@Override
	public boolean isRoom() {return true;}
	
	@Override
	public boolean isDoorway() {
		if(doorDirection.equals(DoorDirection.NONE))
			return false;
		return true;
	}
}