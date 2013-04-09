package Board;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CustomMessage extends JOptionPane {

	public CustomMessage(String message) {
		setSize(600, 200);
		JOptionPane.showMessageDialog(null, message);
	}

}
