package Board;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class PlayerSuggestionDialog extends JFrame{
	
	public JComboBox personList, weaponList; //The lists of people, weapons
	public JTextField currentRoom;
	
	private notesDialog dialog;

	public PlayerSuggestionDialog() { //Displays the detective notes panel
		setTitle("Detective Notes");
		setSize(100, 150);
		dialog = new notesDialog();
	}

	public class notesDialog extends JDialog{ //Necessary to have the panel be a pop-up
		public notesDialog() {
			createLayout();
		}
	}
	
	private void createLayout(){
		personList = createPersonCombo();
		weaponList = createWeaponCombo();
		currentRoom = createRoomBox("fg");

		JButton acceptButton = new JButton("Make This Guess");
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("GUESS");
			}
		});

		JButton cancelButton = new JButton("Cancel the Suggestion");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("You MUST make a guess.");
			}

		});
		

		JPanel fullPanel = new JPanel();
		fullPanel.setLayout(new GridLayout(5,1));
		add(fullPanel, BorderLayout.CENTER);
		
		
		fullPanel.add(currentRoom);
		fullPanel.add(personList);
		fullPanel.add(weaponList);
		fullPanel.add(acceptButton);
		fullPanel.add(cancelButton);
	}


	private JComboBox createPersonCombo()
	{
		JComboBox combo = new JComboBox();
		combo.addItem("Miss Scarlet");
		combo.addItem("Colonel Mustard");
		combo.addItem("Mr. Green");
		combo.addItem("Mrs. White");
		combo.addItem("Mrs. Peacock");
		combo.addItem("Professor Plum");
		combo.setBorder(new TitledBorder (new EtchedBorder(), "Guess a person:"));
		return combo;
	}

	private JComboBox createWeaponCombo()
	{
		JComboBox combo = new JComboBox();
		combo.addItem("Candlestick");
		combo.addItem("Lead Pipe");
		combo.addItem("Knife");
		combo.addItem("Revolver");
		combo.addItem("Wrench");
		combo.addItem("Rope");
		combo.setBorder(new TitledBorder (new EtchedBorder(), "Guess a weapon:"));
		return combo;
	}

	private JTextField createRoomBox(String roomIn) {
		JTextField roomField = new JTextField();
		roomField.setText(roomIn);
		roomField.setBorder(new TitledBorder (new EtchedBorder(), "Your current room:"));
		return roomField;
	}

}
