package boardTests;

import java.io.FileNotFoundException;

import javax.swing.JFrame;

import Board.Board;
import Board.ClueGame;

public class DrawTest {

	//this is just so I can draw the board and make sure it is working
	public static void main(String[] args) throws FileNotFoundException {
		ClueGame draw = new ClueGame();

		draw.boardGuiInitalize();
		
		draw.setVisible(true);
		draw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	

	}

}
