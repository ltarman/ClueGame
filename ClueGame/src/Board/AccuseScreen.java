package Board;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AccuseScreen extends JOptionPane{

	public AccuseScreen(ArrayList<Card> guess, boolean win, Player player) {
		setSize(600, 200);
		String status = "";
		String message = player.getName() + " accused " + guess.get(0).getName() + " with the " + guess.get(1).getName() + " in the " + guess.get(2).getName();
		if (win){
			status = player.getName() + " wins!";
		}else{
			status =  player.getName() + " was wrong!";
		}

		JOptionPane.showMessageDialog(null, message);
		JOptionPane.showMessageDialog(null, status);
	}

}
