package Board;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class DetectiveNotesDialog extends JFrame{

	public JComboBox personList, weaponList, roomList; //The lists of people, weapons, and rooms
	public JPanel personChecklist, weaponChecklist, roomChecklist; //The check-boxes

	private notesDialog dialog;

	public DetectiveNotesDialog() { //Displays the detective notes panel
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Detective Notes");
		setSize(600, 800);

		dialog = new notesDialog();
		
		dialog.setVisible(true);
		
	}

	public class notesDialog extends JDialog{ //Necessary to have the panel be a pop-up
		public notesDialog() {
			createLayout();
		}
	}
	
	private void createLayout(){
		personList = createPersonCombo();
		weaponList = createWeaponCombo();
		roomList = createRoomCombo();

		personChecklist = createPersonChecklist(); //These are composed of panels themselves
		weaponChecklist = createWeaponChecklist();
		roomChecklist = createRoomChecklist();


		JPanel fullPanel = new JPanel();
		fullPanel.setLayout(new GridLayout(3,2));
		add(fullPanel, BorderLayout.CENTER);

		fullPanel.add(personChecklist); 
		fullPanel.add(personList);
		fullPanel.add(weaponChecklist);
		fullPanel.add(weaponList);
		fullPanel.add(roomChecklist);
		fullPanel.add(roomList);

	}



	private JPanel createPersonChecklist() {
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new GridLayout(3,2));
		add(subPanel, BorderLayout.CENTER);
		subPanel.add(new JCheckBox("Miss Scarlet"));
		subPanel.add(new JCheckBox("Colonel Mustard"));
		subPanel.add(new JCheckBox("Mr. Green"));
		subPanel.add(new JCheckBox("Mrs. White"));
		subPanel.add(new JCheckBox("Mrs. Peacock"));
		subPanel.add(new JCheckBox( "Professor Plum"));
		subPanel.setBorder(new TitledBorder (new EtchedBorder(), "Suspect People"));
		return subPanel;
	}

	private JPanel createWeaponChecklist() {
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new GridLayout(3,2));
		add(subPanel, BorderLayout.CENTER);
		subPanel.add(new JCheckBox("Candlestick"));
		subPanel.add(new JCheckBox("Lead Pipe"));
		subPanel.add(new JCheckBox("Knife"));
		subPanel.add(new JCheckBox("Revolver"));
		subPanel.add(new JCheckBox("Wrench"));
		subPanel.add(new JCheckBox( "Rope"));
		subPanel.setBorder(new TitledBorder (new EtchedBorder(), "Suspect Weapons"));
		return subPanel;
	}

	private JPanel createRoomChecklist() {
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new GridLayout(5,2));
		add(subPanel, BorderLayout.CENTER);
		subPanel.add(new JCheckBox("Lounge"));
		subPanel.add(new JCheckBox("Dining Room"));
		subPanel.add(new JCheckBox("Kitchen"));
		subPanel.add(new JCheckBox("Ballroom"));
		subPanel.add(new JCheckBox("Conservatory"));
		subPanel.add(new JCheckBox( "Billiard Room"));
		subPanel.add(new JCheckBox("Library"));
		subPanel.add(new JCheckBox("Study"));
		subPanel.add(new JCheckBox("Hall"));
		subPanel.setBorder(new TitledBorder (new EtchedBorder(), "Suspect Rooms"));
		return subPanel;
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
		combo.setBorder(new TitledBorder (new EtchedBorder(), "Best Guess: Person"));
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
		combo.setBorder(new TitledBorder (new EtchedBorder(), "Best Guess: Weapon"));
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
		combo.setBorder(new TitledBorder (new EtchedBorder(), "Best Guess: Room"));
		return combo;
	}
}



