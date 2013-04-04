package Board;

import java.awt.Color;
import java.awt.Graphics;

abstract public class BoardCell {
	private int row;
	private int column;
	private char inital;
	
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
	}
}