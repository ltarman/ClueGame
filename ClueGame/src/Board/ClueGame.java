package Board;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Board.BadConfigFormatException;
import Board.Player;
import Board.BadConfigFormatException.errorType;


public class ClueGame extends JFrame{
	
	private ArrayList<Card> clueGameDeck; //Includes only the cards that are to be dealt to the players (doesn't include solution)
	private ArrayList<Card> clueGameFullDeck; // Includes all cards, necessary for computer guesses
	private ArrayList<Player> playerList;
	private static ArrayList<Card> solution;

	private ArrayList<Card> seenWeapons;
	private ArrayList<Card> seenPeople;
	private ArrayList<Card> seenRooms;
	
	private Board board;
	
	
	public ClueGame() throws FileNotFoundException {
		solution = new ArrayList<Card>();
		playerList = new ArrayList<Player>();
		clueGameDeck = new ArrayList<Card>();
		clueGameFullDeck = new ArrayList<Card>();
		
		loadCardList();
		loadPlayerList();
		
		seenWeapons = new ArrayList<Card>();
		seenPeople = new ArrayList<Card>();
		seenRooms = new ArrayList<Card>();
		
		setTitle("Clue Board");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		
	}
	
	public ClueGame(ArrayList<Player> playerListIn, ArrayList<Card> cardListIn) { //Used for testing
		playerList = playerListIn;
		clueGameDeck = cardListIn;
		seenWeapons = new ArrayList<Card>();
		seenPeople = new ArrayList<Card>();
		seenRooms = new ArrayList<Card>();
		solution = new ArrayList<Card>();
	}
	
	//used to draw the board for testing
	public void boardGuiInitalize(){
		//added so I can draw the board
		board = new Board();
		board.loadConfigFiles();
		add(board,BorderLayout.CENTER);
		setSize(1000,1000);
	}
	
	public void addToSeenCards(Card cardIn) {
		if(cardIn.getType() == Card.typeOfCard.WEAPON){ 
			seenWeapons.add(cardIn);
		} else if (cardIn.getType() == Card.typeOfCard.PERSON) {
			seenPeople.add(cardIn);
		} else {
			seenRooms.add(cardIn);
		}
	}
		
	public ArrayList<Player> getPlayerList() {
		return this.playerList;
	}
	
	public ArrayList<Card> getCardList() {
		return this.clueGameDeck;
	}
	
	public ArrayList<Card> getFullCardList() {
		return this.clueGameFullDeck;
	}

	public ArrayList<Card> getSolution() {
		return solution;
	}
	
	//used only for testing. Places a card into the solution.
	public void setSolution(Card set) {
		solution.add(set);
	}
	
	//Distributes the cards to the players. Players will not have any cards when instantiated, and will receive them with this function.
	public void dealCards() {
		Random randomGenerator = new Random();
		
		ArrayList<Card> distributionDeck = clueGameDeck;
		int playerCount = playerList.size();
		int currentPlayer = 0;
		Card randomCard ;
		
		while(distributionDeck.size() > 0) {
			randomCard = distributionDeck.get(randomGenerator.nextInt(distributionDeck.size()));
			playerList.get(currentPlayer).giveCard(randomCard);
			distributionDeck.remove(randomCard);
			
			currentPlayer++;
			if(currentPlayer == playerCount) {
				currentPlayer = 0;
			}
		}
	}
	
	
	
	
	//Checks a suggestion by looking at most players' cards
	public Card testSuggestion(Player tester, Card A, Card B, Card C) { 
		ArrayList<Card> foundCards = new ArrayList<Card>();
		Card tempCard;
		Random randomGenerator = new Random();
		
		for(int i = 0; i < playerList.size(); i++) { //iterates through the players
			if(i==playerList.indexOf(tester)) {} //Skips the guesser
			else {
				tempCard = playerList.get(i).disproveSuggestion(A, B, C); //Checks each player's hand for the cards
				if(tempCard != null){
					foundCards.add(tempCard);
				}
			}
		}
			
		if(foundCards.size() == 0) {
			return null;
		} else {
			return foundCards.get(randomGenerator.nextInt(foundCards.size()));//chooses a random card from those that have been found
		}
	}
	
	//Allows a computer to make a suggestion based on what it has seen, and what it hasn't. 
	//We will have to implement an alternate version for ACCUSATIONS
	public ArrayList<Card> computerSuggestion(ComputerPlayer computerSuggester) {
		//Lists for possible cards (the room is restricted to the current location of the computer)
		ArrayList<Card> potentialWeapons = new ArrayList<Card>();
		ArrayList<Card> potentialPeople = new ArrayList<Card>();

		
		ArrayList<Card> possibleSolution = new ArrayList<Card>(); //Used to make the guessed suggestion
		Random randomGenerator = new Random();
		
		
		for(int i = 0; i < clueGameDeck.size(); i++) { //Iterates through the game's list of cards
			Card checkedCard = clueGameDeck.get(i); 
			
			if(checkedCard.getType() == Card.typeOfCard.WEAPON) { //Is it a weapon?
				if(!seenWeapons.contains(checkedCard)) { //If the card is is the deck and is not in the list of seen weapons,
					potentialWeapons.add(checkedCard);   // The card is added to the set of possible cards
				} 
			}
			if(checkedCard.getType() == Card.typeOfCard.PERSON) { //Same as above, but for people.
				if(!seenPeople.contains(checkedCard)) {
					potentialPeople.add(checkedCard);
				}
			}
		}
		possibleSolution.add(potentialWeapons.get(randomGenerator.nextInt(potentialWeapons.size()))); //Chooses a random weapon to guess
		possibleSolution.add(potentialPeople.get(randomGenerator.nextInt(potentialPeople.size()))); //And a person

		//Creates a card that will be an exact duplicate of the card referring to the location of the room that the computer is currently in.
		//Since the card is only used for the guess, it won't cause problems (but be careful with the output of this function).
		possibleSolution.add(new Card(computerSuggester.getLocation(), Card.typeOfCard.ROOM)); 

		return possibleSolution;
	}
	
	public Boolean makeAccusation(ArrayList<Card> accuseSet) {
		Boolean goodSoFar = true;
		for(int i = 0; i<accuseSet.size(); i++) {
			if(!accuseSet.contains(solution.get(i))) {
				goodSoFar = false;
			}
		}
		return goodSoFar;
	}
	
	//will ultimately load all of the cards into the deck
	private void loadCardList(){
		//setting of the initial variables
		String line = null; 
		//passing the values into the readfile function
		Scanner peopleFile = readFile("people.txt");
		Scanner weaponsFile = readFile("weapons.txt");
		Scanner roomsFile = readFile("rooms.txt");
		
		//creates an array of files to iterate through with a for loop
		Scanner[] files = {peopleFile,weaponsFile,roomsFile};
		//Creates a linkedList of Lists to place the lists of people and ect into
		LinkedList<LinkedList<String>> stacks = new LinkedList<LinkedList<String>>();
		for(int i=0; i < 3; i++){
			
			
			//     Not sure on this; look later &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
			//try{  
				LinkedList<String> cards = new LinkedList<String>();
				while(files[i].hasNextLine()){
					line = files[i].nextLine();
					cards.add(line);
				}
				stacks.add(cards);
				/*
			}catch(BadConfigFormatException e){
				System.out.println("Bad file format for " + files[i]);
			}
			*/
			
		}
		//Lists of variable strings for each of the three categories
		LinkedList<String> peopleList = stacks.get(0);		
		LinkedList<String> weaponsList = stacks.get(1);		
		LinkedList<String> roomsList = stacks.get(2);		
		
		
		//this will pick out a random card from peopleList, weaponsList, and roomsList
		Random generator = new Random();
		
		//chooses a random person from people list and places it into the solution.
		//It will then remove that card from the list before it is placed in the main deck, but it is placed in the full deck.
		int r = generator.nextInt(peopleList.size());
		solution.add(new Card(peopleList.get(r), Card.typeOfCard.PERSON));
		clueGameFullDeck.add(new Card(peopleList.get(r),Card.typeOfCard.PERSON));
		peopleList.remove(r);

		//chooses a random weapon from weaponslist and places it into the solution
		//It will then remove that card from the list before it is placed in the main deck, but it is placed in the full deck
		r = generator.nextInt(weaponsList.size());
		solution.add(new Card(weaponsList.get(r), Card.typeOfCard.WEAPON));
		clueGameFullDeck.add(new Card(weaponsList.get(r),Card.typeOfCard.WEAPON));
		weaponsList.remove(r);

		//chooses a random room from roomslist and places it into the solution
		//It will then remove that card from the list before it is placed in the main deck, but it is placed in the full deck
		r = generator.nextInt(roomsList.size());
		solution.add(new Card(roomsList.get(r), Card.typeOfCard.ROOM));
		clueGameFullDeck.add(new Card(roomsList.get(r),Card.typeOfCard.ROOM));
		roomsList.remove(r);
		
		//loads the people into the deck
		for(int i=0; i<peopleList.size();i++){
			Card tempCard = new Card(peopleList.get(i),Card.typeOfCard.PERSON);
			clueGameDeck.add(tempCard);
			clueGameFullDeck.add(tempCard);
		}
		
		//loads the weapons into the deck
		for(int i=0; i<weaponsList.size();i++){
			Card tempCard = new Card(weaponsList.get(i),Card.typeOfCard.WEAPON);
			clueGameDeck.add(tempCard);
			clueGameFullDeck.add(tempCard);
		}
		
		//loads the rooms into the deck
		for(int i=0; i<roomsList.size();i++){
			Card tempCard = new Card(roomsList.get(i),Card.typeOfCard.ROOM);
			clueGameDeck.add(tempCard);
			clueGameFullDeck.add(tempCard);
		}
	}
	
	//Should change this so we can alter the file it checks
	private void loadPlayerList() throws FileNotFoundException{
		ArrayList<String> playerNames = new ArrayList<String>();
		ArrayList<String> initialPlayerLocations = new ArrayList<String>();
		ArrayList<String> playerColors = new ArrayList<String>();
		ArrayList<Card> emptyCardList = new ArrayList<Card>();
		
		int iteration = 0;
		
		File inFile = new File("PlayerInfo.txt");
		Scanner scanner = new Scanner(inFile);
		while(scanner.hasNextLine()){
			String[] lineOfData = scanner.nextLine().split(",");	// Converts a row of data in the file to an array of strings
			lineOfData[2] = lineOfData[2].trim();
			playerNames.add(lineOfData[0]);
			initialPlayerLocations.add(lineOfData[1]);
			playerColors.add(lineOfData[2]);
			
			if(playerNames.size() == 1) { //If only one player's worth of info has been added, then use it to make a human player.
				playerList.add(new HumanPlayer(playerNames.get(0),emptyCardList, initialPlayerLocations.get(0), playerColors.get(0)));
			} else { //Otherwise, add a computer player
				playerList.add(new ComputerPlayer(playerNames.get(iteration),emptyCardList, initialPlayerLocations.get(iteration), playerColors.get(iteration)));
			}
			iteration++;
		}
	}
	
	//used to find a file for the loadConfigFiles function
	private Scanner readFile(String file){
		Scanner in = null;
		try {
			FileReader reader = new FileReader(file);
			in = new Scanner(reader);
			
		} catch (FileNotFoundException e){
			System.out.println("Can't open file: " + file);
		}	
		return in;
		
	}

	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createFileExitItem());
		menu.add(createFileShowDetectiveNotes());
		return menu;
	}
	
	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		  class MenuItemListener implements ActionListener {
		    public void actionPerformed(ActionEvent e)
		    {
		       System.exit(0);
		    }
		  }
		  item.addActionListener(new MenuItemListener());
		  return item;
	}
	
private JMenuItem createFileShowDetectiveNotes() {
	JMenuItem item = new JMenuItem("Show Detective Notes");
	  class MenuItemListener implements ActionListener {
	    public void actionPerformed(ActionEvent e)
	    {
	       board.playerNotes.setVisible(true);
	    }
	  }
	  item.addActionListener(new MenuItemListener());
	  return item;
	}
}
