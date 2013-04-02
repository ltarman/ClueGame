package Board;

import java.awt.Color;
import java.awt.Graphics;

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
	
	@Override
	public void draw(Graphics g,int x, int y){
		g.setColor(Color.gray);
		g.drawRect(x,y,20,20);
		
		g.setColor(Color.blue);
		//set the color of an edge to where the door is
		if (doorDirection == DoorDirection.LEFT){g.drawLine(x, y, x, y+20);}
		else if(doorDirection == DoorDirection.RIGHT){g.drawLine(x+20, y, x, y+20);}
		else if(doorDirection == DoorDirection.DOWN){g.drawLine(x, y+20, x+20, y+20);}
		else if(doorDirection == DoorDirection.UP){g.drawLine(x, y, x+20, y);}
		
	}
}