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
	private Board connectedBoard;
	private String personName;
	private String weaponName;	
	private notesDialog dialog;

	public PlayerSuggestionDialog(Card cardIn, Board boardIn) { 	
		dialog = new notesDialog(cardIn, boardIn);		
	}

	public class notesDialog extends JDialog{ //Necessary to have the panel be a pop-up
		public notesDialog(Card cardIn, Board boardIn) {
			connectedBoard = boardIn;
			createLayout(cardIn);
		}
	}
	
	private void createLayout(Card cardIn){
		setTitle("Suggestion Box");
		setSize(250, 400);
		ComboListener listener = new ComboListener();
		personList = createPersonCombo();
		weaponList = createWeaponCombo();
		personList.addActionListener(listener);
		weaponList.addActionListener(listener);
		
		currentRoom = createRoomBox(cardIn.getName());
		personName = personList.getSelectedItem().toString();
		weaponName = weaponList.getSelectedItem().toString();
		final Card locationCard = cardIn;
		
		
		JButton acceptButton = new JButton("Make This Guess");
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				personName = personList.getSelectedItem().toString();
				weaponName = weaponList.getSelectedItem().toString();
				connectedBoard.connectedGame.getPlayerList().get(0).humanSuggestion(personName, weaponName, locationCard);
				dispose();
				
			}
		});

		JButton cancelButton = new JButton("Cancel the Suggestion");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("You MUST make a guess.");
				dialog.dispose();
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

	private class ComboListener implements ActionListener {
		  public void actionPerformed(ActionEvent e)
		  {
		    if (e.getSource() == personList)
		    	personName = personList.getSelectedItem().toString();
		    else
		    	weaponName = weaponList.getSelectedItem().toString();
		  }
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
		final String roomName = roomIn;
		JTextField roomField = new JTextField();
		roomField.setText(roomIn);
		roomField.setBorder(new TitledBorder (new EtchedBorder(), "Your current room:"));
		return roomField;
	}

}
