package Board;

import java.awt.Color;
import java.awt.Graphics;

import Board.RoomCell.DoorDirection;

public class WalkwayCell extends BoardCell {
	public boolean isWalkway() {return true;}
	
	public WalkwayCell(int row, int col) {
		super(row,col);
	}
	
	@Override
	public void draw(Graphics g,int x, int y){
		g.setColor(Color.yellow);
		g.drawRect(x,y,20,20);
				
	}
}