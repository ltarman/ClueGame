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


public class PlayerAccuseDialog extends JFrame{
	
	public JComboBox personList, weaponList, roomList; //The lists of people, weapons
	//public JTextField currentRoom;
	private ClueGame connectedGame;
	private String personName;
	private String weaponName;
	private String roomName;
	
	private notesDialog dialog;

	public PlayerAccuseDialog(ClueGame gameIn) { 
		
		dialog = new notesDialog(gameIn);
		//dialog.setVisible(true);
		
	}

	public class notesDialog extends JDialog{ //Necessary to have the panel be a pop-up
		public notesDialog(ClueGame gameIn) {
			//setTitle("Suggestion Box");
			//setSize(250, 400);
			connectedGame = gameIn;
			createLayout();
		}
	}
	
	private void createLayout(){
		setTitle("Accusation!");
		setSize(250, 400);
		ComboListener listener = new ComboListener();
		personList = createPersonCombo();
		weaponList = createWeaponCombo();
		roomList = createRoomCombo();
		personList.addActionListener(listener);
		weaponList.addActionListener(listener);
		
		personName = personList.getSelectedItem().toString();
		weaponName = weaponList.getSelectedItem().toString();
		roomName = roomList.getSelectedItem().toString();
		//final Card locationCard = cardIn;
		
		
		JButton acceptButton = new JButton("Make This Accusation");
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{

				personName = personList.getSelectedItem().toString();
				weaponName = weaponList.getSelectedItem().toString();
				roomName = roomList.getSelectedItem().toString();
				//Card newPersonCard = new Card(personName, Card.typeOfCard.PERSON);
				//Card newWeaponCard = new Card(weaponName, Card.typeOfCard.WEAPON);
				
				
				//System.out.println("Accuse choices are: " + personName +" " + weaponName + " " + roomName);
				connectedGame.getPlayerList().get(0);
				connectedGame.getPlayerList().get(0).humanAccusation(personName, weaponName, roomName);
				
				//System.out.println(newPersonCard.getName() + " IFGI " + newWeaponCard.getName() + " IFGI " + locationCard.getName());
				//System.out.println(connectedBoard.connectedGame.testSuggestion(connectedBoard.connectedGame.getPlayerList().get(0),
				//		locationCard, newPersonCard, newWeaponCard).getName());
				
				//System.out.println("ACCUSE");
				//fullPanel.dispose();
				setVisible(false);
				
			}
		});

		JButton cancelButton = new JButton("Cancel the Suggestion");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Guess later, then.");
				setVisible(false);
			}

		});
		

		JPanel fullPanel = new JPanel();
		fullPanel.setLayout(new GridLayout(5,1));
		add(fullPanel, BorderLayout.CENTER);
		
		
		fullPanel.add(roomList);
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
	
	private JComboBox createRoomCombo()
	{
		JComboBox combo = new JComboBox();
		combo.addItem("Study");
		combo.addItem("Kitchen");
		combo.addItem("Limbo");
		combo.addItem("Lounge");
		combo.addItem("Dining Room");
		combo.addItem("Kitchen");
		combo.addItem("Ballroom");
		combo.addItem("Conservatory");
		combo.addItem( "Billiard Room");
		combo.addItem("Library");
		combo.addItem("Study");
		combo.addItem("Hall");
		combo.setBorder(new TitledBorder (new EtchedBorder(), "Guess a room:"));
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
