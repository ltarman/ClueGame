package clueGameTests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Board.Card;
import Board.ClueGame;
import Board.ComputerPlayer;
import Board.HumanPlayer;
import Board.Player;
import Board.Card.typeOfCard;

import Board.*;

public class GameActionTests {

	private static Board board;
	private static ArrayList<Card> testDeck;
	private ArrayList<Card> seenWeapons;
	private ArrayList<Card> seenPeople;
	private ArrayList<Card> seenRooms;

	public void addToSeenWeapons(Card cardIn) {
		seenWeapons.add(cardIn);
	}

	public void addToSeenPeople(Card cardIn) {
		seenPeople.add(cardIn);
	}

	public void addToSeenRooms(Card cardIn) {
		seenRooms.add(cardIn);
	}

	@Before
	public void test() throws FileNotFoundException {
		//Initializing the board and the game
		board = new Board();
		board.loadConfigFiles();
		testDeck = new ArrayList<Card>();

	}

	
	//Operational.
	@Test
	public void testAccusation(){
		
		ArrayList<Card> emptyDeckOfCards = new ArrayList<Card>();
		ArrayList<Player> emptyListOfPlayers = new ArrayList<Player>(); //Necessary to instantiate the clueGame
		
		ClueGame testGame = new ClueGame(emptyListOfPlayers, emptyDeckOfCards);
		//creating the three cards for the solution
		Card person = new Card("Miss Scarlet", typeOfCard.PERSON);
		Card weapon = new Card("Lead Pipe", typeOfCard.WEAPON);
		Card room = new Card("Kitchen", typeOfCard.ROOM);

		ArrayList<Card> testAccusationList = new ArrayList<Card>();
		testAccusationList.add(person); testAccusationList.add(weapon); testAccusationList.add(room);
		
		//pass the three cards into the solution held by ClueGame
		testGame.setSolution(person);
		testGame.setSolution(weapon);
		testGame.setSolution(room);

		//check if the strings match what is held by the solution in ClueGame
		assertTrue(testGame.getSolution().get(0).getName() == "Miss Scarlet");
		assertTrue(testGame.getSolution().get(1).getName() == "Lead Pipe");
		assertTrue(testGame.getSolution().get(2).getName() == "Kitchen");
		
		assertTrue(testGame.makeAccusation(testAccusationList)); //Tests the actual accusation function.
	}

	
	@Test
	public void testRandomTarget(){
		//calculate an index for a spot on the board and create a new computer player to manipulate	
		ComputerPlayer player = new ComputerPlayer("comp", testDeck, "315", "Blue");
		int test11_14 = 0;
		int test12_15 = 0;
		int test13_14 = 0;
		
		//have pickLocation choose a random location 100 times and record the number of times it picks a spot
		int index = board.calcIndex(12, 14);
		//load the adj list into the board
		board.calcAdjacencies();
		board.calcTargets(index, 1);
		
		for(int i=0; i<100; i++){
			int chosen = player.pickLocation(board.getTargets(),board);
			if (chosen == board.calcIndex(11,14))	test11_14++;
			else if (chosen == board.calcIndex(12,15))	test12_15++;
			else if (chosen == board.calcIndex(13,14))	test13_14++;
			//if the computer does not pick a designated spot, the test has failed
			else fail("testRandomTarget failure");
		}
		//test to see if it picked each spot at least once as well as it picked 100 spots
		assertEquals(100, test11_14 + test12_15, test13_14);
		assertFalse(test11_14 == 0);
		assertFalse(test12_15 == 0);
		assertFalse(test13_14 == 0);
	}
	

	@Test
	public void testRoomSelection(){
		//calculate an index for a spot on the board and create a new computer player to manipulate
		int index = board.calcIndex(9, 15);
		ComputerPlayer player = new ComputerPlayer("comp", testDeck, "315", "Blue");
		
		//load the adj list into the board
		board.calcAdjacencies();
		board.calcTargets(index, 1);

		//It must run pickLocation to put the playing in a new location. 
		//This variable is not used, however changes the location inside the player
		int chosen = player.pickLocation(board.getTargets(), board);
		assertTrue(chosen == board.calcIndex(9, 16));
	}
	
	

	@Test
	public void testVisited(){
		//calculate an index for a spot on the board and create a new computer player to manipulate
		int index = board.calcIndex(9, 15);
		ComputerPlayer player = new ComputerPlayer("comp", testDeck, "315", "Blue");
		player.setVisited('R');
		board.calcAdjacencies();
		board.calcTargets(index, 1);

		int test8_15 = 0;
		int test10_15 = 0;
		int test9_14 = 0;
		int test9_16 = 0;
		
		//have pickLocation choose a random location 100 times and record the number of times it picks a spot
		for (int i=0;i<100;i++){
			int chosen = player.pickLocation(board.getTargets(),board);
			if (chosen == board.calcIndex(8,15)) test8_15++;
			else if (chosen == board.calcIndex(10,15))	test10_15++;
			else if (chosen == board.calcIndex(9,14))	test9_14++;
			else if (chosen == board.calcIndex(9,16))	test9_16++;
		}
		assertFalse(test8_15 == 0);
		assertFalse(test10_15 == 0);
		assertFalse(test9_14 == 0);
		assertFalse(test9_16 == 0);
	}
	 
	
	
	
	//Operational.
	@Test
	public void testDisproveSuggestion(){
		ArrayList<Player> testPlayerList = new ArrayList<Player>();
		ArrayList<Card> testCardListA = new ArrayList<Card>(); 
		ArrayList<Card> testCardListB = new ArrayList<Card>();
		ArrayList<Card> testCardListC = new ArrayList<Card>();
		ArrayList<Card> testCardListG = new ArrayList<Card>(); //Cards for the deck (unused for the tests)

		Card testCardKnife = new Card("Knife", Card.typeOfCard.WEAPON); //Cards for the first player's hand
		Card testCardPipe = new Card("Lead Pipe", Card.typeOfCard.WEAPON);
		Card testCardMustard = new Card("Colonel Mustard", Card.typeOfCard.PERSON);
		Card testCardGreen = new Card("Mr. Green", Card.typeOfCard.PERSON);
		Card testCardHall = new Card("Hall", Card.typeOfCard.ROOM);
		Card testCardKitchen = new Card("Kitchen", Card.typeOfCard.ROOM);
		testCardListA.add(testCardKnife);testCardListA.add(testCardMustard); testCardListA.add(testCardPipe);
		testCardListA.add(testCardGreen); testCardListA.add(testCardHall); testCardListA.add(testCardKitchen); 

		Player testPlayerA = new Player("TestPlayerAlpha", testCardListA, "341", "Clear"); //Creates first player, gives appropriate cards

		Card testCardCandle = new Card("Candlestick", Card.typeOfCard.WEAPON);
		Card testCardWhite = new Card("Mrs. White", Card.typeOfCard.PERSON);
		Card testCardLibrary = new Card("Library", Card.typeOfCard.ROOM);
		testCardListB.add(testCardCandle); testCardListB.add(testCardWhite); testCardListB.add(testCardLibrary); 
		Player testPlayerB = new Player("TestPlayerBeta", testCardListB, "341", "Undefined"); //Same, but now for a second player

		Card testCardWrench = new Card("Wrench", Card.typeOfCard.WEAPON);
		Card testCardPlum = new Card("Professor Plum", Card.typeOfCard.PERSON);
		Card testCardLounge = new Card("Lounge", Card.typeOfCard.ROOM);
		testCardListC.add(testCardWrench); testCardListC.add(testCardPlum); testCardListC.add(testCardLounge);
		Player testPlayerH = new HumanPlayer("TestPlayerEta", testCardListC, "341", "NotApplicable"); //Same, but now for a human player

		Card testCardRope = new Card("Rope", Card.typeOfCard.WEAPON);
		Card testCardScarlet = new Card("Miss Scarlet", Card.typeOfCard.PERSON);
		Card testCardStudy = new Card("Study", Card.typeOfCard.ROOM); //Extra cards for tests, so we can get 'void' for some of the tests

		testPlayerList.add(testPlayerA); testPlayerList.add(testPlayerB);testPlayerList.add(testPlayerH); //Adds players A,B, and the Human Player to the player list.

		ClueGame testGame = new ClueGame(testPlayerList, testCardListG);

		//TEST FOR ONE PLAYER, ONE POSSIBLE MATCH
		//Checks that player A holds the knife, Mustard, and Hall cards, respectively
		Card test = testPlayerA.disproveSuggestion(testCardKnife, testCardScarlet, testCardStudy);
		assertTrue(test==testCardKnife); 
		assertTrue(testPlayerA.disproveSuggestion(testCardRope, testCardMustard, testCardStudy)==testCardMustard);
		assertTrue(testPlayerA.disproveSuggestion(testCardRope, testCardScarlet, testCardHall)==testCardHall);
		//Checks that the player does not hold any of the extra cards
		assertTrue(testPlayerA.disproveSuggestion(testCardRope, testCardScarlet, testCardStudy)==null);

		//TEST FOR ONE PLAYER, MULTIPLE MATCHES
		int pipeCounts = 0; //used to track number of times a card was selected to disprove
		int greenCounts = 0;
		int kitchenCounts = 0;
		Boolean noOtherCards = true; //Used to track if un-suggested cards are returned
		//Runs the function numerous times, and counts every time a specific card is selected. Also changes a boolean
		//if a card that was not suggested is returns (or null, for that matter)
		Card testCard;
		for(int i = 0; i < 50; i++) { 
			testCard = testPlayerA.disproveSuggestion(testCardPipe, testCardGreen, testCardKitchen);
			if(testCard==testCardPipe) {
				pipeCounts++;
			} else if(testCard==testCardGreen) {
				greenCounts++;
			} else if(testCard==testCardKitchen) {
				kitchenCounts++;
			} else {
				System.out.println("fail");
				noOtherCards = false;
			}
		}
		//tests that if multiple suggested cards exist in a player's hand, the one taken is chosen at random.
		assertTrue(pipeCounts > 0); 
		assertTrue(greenCounts > 0);
		assertTrue(kitchenCounts > 0);
		//Tests that only the cards that were suggested could be returned.
		assertTrue(noOtherCards);


		//TEST FOR MULTIPLE PLAYERS
		pipeCounts = 0;
		int whiteCounts = 0;
		int nullCounts = 0; //Tracks number of times null' has been returned by the function
		noOtherCards = true;

		//Runs through the function 50 times, counting the number of times each result is returned, or changing a boolean if 
		//the un-held card is returned
		for(int i = 0; i < 50; i++) { 
			testCard = testGame.testSuggestion(testPlayerH,testCardPipe, testCardWhite, testCardStudy);
			if(testCard==testCardPipe) {
				pipeCounts++;
			} else if(testCard==testCardWhite) {
				whiteCounts++;
			} else if(testCard==testCardStudy) {
				noOtherCards = false;
			} else {
				nullCounts++;
			}
		}

		assertTrue(pipeCounts > 0); //Ensures that player A showed its card at least once.
		assertTrue(whiteCounts > 0); //Ensures that player B showed its card at least once.
		assertTrue(noOtherCards); //Ensures that the un-held card was never returned
		assertTrue(nullCounts == 0); //Ensures that at least one of the players returned a correct card every time

		//And again, but now with a suggestion that cannot be disproved
		for(int i = 0; i < 50; i++) { 
			testCard = testGame.testSuggestion(testPlayerH,testCardRope, testCardScarlet, testCardStudy);
			if(testCard==testCardPipe) {
				pipeCounts++;
			} else if(testCard==testCardWhite) {
				whiteCounts++;
			} else if(testCard==testCardStudy) {
				noOtherCards = false;
			} else {
				nullCounts++;
			}
		}

		assertTrue(nullCounts == 50); //'null' should be the result all 50 times.


		//TEST THAT A PLAYER MAKING A SUGGESTION ISN'T QUERIED
		nullCounts = 0;
		for(int i = 0; i < 50; i++) { //Tests a suggestion by player B that only player B can disprove
			testCard = testGame.testSuggestion(testPlayerB,testCardCandle, testCardWhite, testCardLibrary);
			if(testCard==testCardCandle) {
			} else if(testCard==testCardWhite) {
			} else if(testCard==testCardLibrary) {
			} else {
				nullCounts++;
			}
		}

		assertTrue(nullCounts == 50); //This should result in 'null' being returned every time.


		//TEST A SUGGESTION ONLY A HUMAN PLAYER CAN DISPROVE
		//The wrench is held by the human, the others don't exist in anyone's hand, so the wrench should be returned.
		assertTrue(testPlayerH.disproveSuggestion(testCardWrench, testCardScarlet, testCardStudy)==testCardWrench);
	};
	
	
	
	
	//Operational.
	@Test
	public void testComputerMakeSuggestion() { //Tests the compter's ability to make reasonable suggestions

		//The 'makeSuggestion' function returns an arrayList of 3 cards, in the order of weapon, person, then location
		//It will utilize the arrayLists for seen cards above, as well as the arrayList for the deck to make a (potentially random) decision


		ArrayList<Card> testCardList = new ArrayList<Card>();
		ComputerPlayer testPlayerA = new ComputerPlayer("TestPlayerAlpha", testCardList, "341", "Clear");
		ArrayList<Player> testPlayerList = new ArrayList<Player>();
		testPlayerList.add(testPlayerA);
		
		//We will use a smaller deck of cards, for simplicity
		Card testCardKnife = new Card("Knife", Card.typeOfCard.WEAPON); //Cards in the deck
		Card testCardPipe = new Card("Lead Pipe", Card.typeOfCard.WEAPON);
		Card testCardMustard = new Card("Colonel Mustard", Card.typeOfCard.PERSON);
		Card testCardGreen = new Card("Mr. Green", Card.typeOfCard.PERSON);
		
		//may lead to problems, instead of a name Hallway, have to use index?
		Card testCardHall = new Card("315", Card.typeOfCard.ROOM);
		Card testCardKitchen = new Card("Kitchen", Card.typeOfCard.ROOM);
		
		testCardList.add(testCardKnife); testCardList.add(testCardPipe); testCardList.add(testCardMustard);
		testCardList.add(testCardGreen); testCardList.add(testCardHall); testCardList.add(testCardKitchen);
		
		ClueGame testGame = new ClueGame(testPlayerList, testCardList);
		
		//Initially, no cards in the deck have been seen, so the computer has to guess.

		int knifeCounts = 0; //Keeps track of the number of occurrence for each card
		int pipeCounts = 0;
		int mustardCounts = 0;
		int greenCounts = 0;
		int hallCounts = 0;

		ComputerPlayer testComputer = new ComputerPlayer("TesterComp", null, "315", "Invisible");
		
		Boolean noProblems = true; //Will switch to false if the suggestion is not reasonable

		for(int i = 0; i < 50; i++) { //Makes 50 suggestions, tallies the results of which cards were drawn
			ArrayList<Card> computerGuess = testGame.computerSuggestion(testComputer);

			if(computerGuess.get(0)==testCardKnife){
				knifeCounts++;
	
			} else if (computerGuess.get(0)==testCardPipe){
				pipeCounts++;

			} else {
				noProblems=false;

			}
			if(computerGuess.get(1)==testCardMustard){
				mustardCounts++;

			} else if (computerGuess.get(1)==testCardGreen){
				greenCounts++;

			} else {
				noProblems=false;

			}
			if(computerGuess.get(2).equals(testCardHall)){
				hallCounts++;
			} else {
				noProblems=false;

			}
		}

		assertTrue(knifeCounts > 0);//These should be occurring at least once, or there is a problem with random selection
		assertTrue(pipeCounts > 0);
		assertTrue(mustardCounts > 0);
		assertTrue(greenCounts > 0);
		assertTrue(hallCounts == 50); //We assume that the computer is in the hall, so only the hall can be returned for the location card

		assertTrue(noProblems == true); //Any random cards returned?


		//Now we test if the computer will make the only possible suggestion if all but one card is possible for the weapon, person, or room

		testGame.addToSeenCards(testCardKnife);
		testGame.addToSeenCards(testCardMustard);
		testGame.addToSeenCards(testCardHall); //Now only the pipe, green, and kitchen cards are available.

		//We will still assume that the computer is in the Hall, however

		assertTrue(testGame.computerSuggestion(testComputer).get(0) == testCardPipe); //The only remaining weapon card is the pipe
		assertTrue(testGame.computerSuggestion(testComputer).get(1) == testCardGreen); //Green is the only remaining person
		assertTrue(testGame.computerSuggestion(testComputer).get(2).equals(testCardHall));  //The computer, however, is still in the hall.
	}
	
}

