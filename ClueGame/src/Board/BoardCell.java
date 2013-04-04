package Board;

import java.awt.Color;
import java.awt.Graphics;

abstract public class BoardCell {
	private int row;
	private int column;
	private char inital;
	
	//these will define where the cell will be drawn in pixels
	//they represent the top left corner of each cell
	private int pixelX;
	private int pixelY;
	
	public char getInitial() {
		return inital;
	}
	
	public BoardCell(int row, int col) {
		this.row = row;
		this.column = col;
	}
	
	public boolean isWalkway() {return false;}
	public boolean isRoom() {return false;}
	public boolean isDoorway() {return false;}
	
	//abstract method for each BoardCell to draw itself
	public void draw(Graphics g,int x, int y) {
		g.setColor(Color.green);
		g.fillRect(x,y,20,20);
		g.setColor(Color.red);
		g.drawRect(x, y, 21, 21);
	}
}