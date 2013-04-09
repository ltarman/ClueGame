package Board;

import java.awt.Color;
import java.awt.Graphics;

import Board.RoomCell.DoorDirection;

public class WalkwayCell extends BoardCell {
	public boolean isWalkway() {return true;}
	
	public WalkwayCell(int row, int col) {
		super(row,col);
		this.initial = 'W';
	}
	
	@Override
	public void draw(Graphics g,int x, int y){
		g.setColor(Color.yellow);
		g.fillRect(x+1,y+1,19,19);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, 20, 20);		
	}
}