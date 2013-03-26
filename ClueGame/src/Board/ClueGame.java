package Board;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import Board.BadConfigFormatException;
import Board.Player;


public class ClueGame {
	
	private ArrayList<Card> cardList;
	private ArrayList<Player> playerList;
	private static ArrayList<Card> solution;

	private ArrayList<Card> seenWeapons;
	private ArrayList<Card> seenPeople;
	private ArrayList<Card> seenRooms;
	
	
	public ClueGame() {
		solution = new ArrayList<Card>();
		loadCardList();
		seenWeapons = new ArrayList<Card>();
		seenPeople = new ArrayList<Card>();
		seenRooms = new ArrayList<Card>();
	}
	
	public ClueGame(ArrayList<Player> playerListIn, ArrayList<Card> cardListIn) { //Used for testing
		playerList = playerListIn;
		cardList = cardListIn;
		seenWeapons = new ArrayList<Card>();
		seenPeople = new ArrayList<Card>();
		seenRooms = new ArrayList<Card>();
		solution = new ArrayList<Card>();
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
		return this.cardList;
	}

	public ArrayList<Card> getSolution() {
		return solution;
	}
	
	//used only for testing. Sets the Solution to a defined situation
	public void setSolution(Card set) {
		solution.add(set);
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
		
		
		for(int i = 0; i < cardList.size(); i++) { //Iterates through the game's list of cards
			Card checkedCard = cardList.get(i); 
			
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
	
	private Boolean makeAccusation(ArrayList<Card> accuseSet) {
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
			
			
			/*     Not sure on this; look later &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
			try{  
				LinkedList<String> cards = new LinkedList<String>();
				while(files[i].hasNextLine()){
					line = files[i].nextLine();
					cards.add(line);
				}
				stacks.add(cards);
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
		//It will then remove that card from the list before it is placed in the deck
		int r = generator.nextInt(peopleList.size());
		solution.add(new Card(peopleList.get(r), Card.typeOfCard.PERSON));
		peopleList.remove(r);

		//chooses a random weapon from weaponslist and places it into the solution
		//It will then remove that card from the list before it is placed in the deck
		r = generator.nextInt(weaponsList.size());
		solution.add(new Card(weaponsList.get(r), Card.typeOfCard.WEAPON));
		weaponsList.remove(r);

		//chooses a random room from roomslist and places it into the solution
		//It will then remove that card from the list before it is placed in the deck
		r = generator.nextInt(roomsList.size());
		solution.add(new Card(roomsList.get(r), Card.typeOfCard.ROOM));
		roomsList.remove(r);
		
		//loads the people into the deck
		for(int i=0; i<peopleList.size();i++){
			Card temp = new Card(peopleList.get(i),Card.typeOfCard.PERSON);
			cardList.add(temp);
		}
		
		//loads the weapons into the deck
		for(int i=0; i<weaponsList.size();i++){
			Card temp = new Card(weaponsList.get(i),Card.typeOfCard.WEAPON);
			cardList.add(temp);
		}
		
		//loads the rooms into the deck
		for(int i=0; i<roomsList.size();i++){
			Card temp = new Card(roomsList.get(i),Card.typeOfCard.ROOM);
			cardList.add(temp);
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

		
	/*

	*/

}
