package Board;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SplashScreen extends JOptionPane{
	
	public SplashScreen() {
		setName("The game has begun!");
		setSize(600, 200);


		String message = "You are Miss Scarlet (again), use the 'next player' button to take your turn. ";
		JOptionPane.showMessageDialog(null, message);
	}

	
	
}
