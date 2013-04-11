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

import Board.*;


public class GameSetupTests {

	private static Board board;
	private static ClueGame testGame;

	@Before
	public void setUp() throws FileNotFoundException{
		board = new Board();
		//testGame = new ClueGame();
		board.loadConfigFiles("legend.txt","board.csv");
	}

	@Test
	public void testPeople() throws FileNotFoundException{
		ClueGame testGame = new ClueGame();
		//get the list of players from the getter and get the three for testing
		
		
		ArrayList<Player> players = new ArrayList<Player>(testGame.getPlayerList());
		HumanPlayer human = (HumanPlayer) players.get(0);		//Assuming the human player is first in the list followed by the computer players
		ComputerPlayer p1 = (ComputerPlayer) players.get(1);
		ComputerPlayer p2 = (ComputerPlayer) players.get(5);
		
		//Testing for the correct names
		assertEquals(human.getName(), "Miss Scarlet");
		assertEquals(p1.getName(), "Colonel Mustard");
		assertEquals(p2.getName(), "Mrs White");
		
		//Testing for the correct colors
		
		System.out.println(human.getColor());
		
		assertEquals("Red" , "Red");
		
		assertEquals(human.getColor(), "Red");
		assertEquals(p1.getColor(), "Yellow");
		assertEquals(p2.getColor(), "White");

// !! Change location to whatever type it is and assertion statement !!
		//Testing for the correct starting location
		assertEquals(human.getLocation(), "150");
		assertEquals(p1.getLocation(), "18");
		assertEquals(p2.getLocation(), "349");
	}
	
	@Test
	public void TestCardLoading() throws FileNotFoundException { //Test the loading of cards
		ClueGame testGame = new ClueGame();
		assertEquals(testGame.getFullCardList().size(), 22); //Test that the game has the correct number of cards

		int weaponCardCount = 0;
		int personCardCount = 0;
		int roomCardCount = 0;
		//Counts the number of each type of card. All three types has a condition (no 'else' statement) to ensure it is correctly set.
		for(int i = 0; i < testGame.getFullCardList().size(); i++ ) {  
			if(testGame.getFullCardList().get(i).getType() == Card.typeOfCard.WEAPON) {
				weaponCardCount++;
			}  else if (testGame.getFullCardList().get(i).getType() == Card.typeOfCard.PERSON) {
				personCardCount++;
			} else if(testGame.getFullCardList().get(i).getType() == Card.typeOfCard.ROOM) {
				roomCardCount++;
			}
		}

		assertEquals(weaponCardCount, 6); //Test the deck has the appropriate number of cards of each type
		assertEquals(personCardCount, 6);
		assertEquals(roomCardCount, 10);
		Boolean weaponCheck = false;
		Boolean personCheck = false;
		Boolean roomCheck = false;

		//Checks the deck of cards for certain cards; one weapon, room, and person.
		for(int i = 0; i < testGame.getCardList().size(); i++ ) {  
			if(testGame.getCardList().get(i).getName() == "Wrench") {
				weaponCheck = true;
			} else if(testGame.getCardList().get(i).getName() == "Colonel Mustard") {
				personCheck = true;
			} else if(testGame.getCardList().get(i).getName() == "Hall") {
				roomCheck = true;
			}
		}

		assert(weaponCheck); //Test the deck to see if it does contain those cards.
		assert(personCheck);
		assert(roomCheck);
	};


	
	//Operational.
	@Test
	public void testCardDealing() { //Test that cards have been properly dealt to the players. 
		
		//I will use a smaller and player list for this.
		Card testCardKnife = new Card("Knife", Card.typeOfCard.WEAPON); //Cards.
		Card testCardPipe = new Card("Lead Pipe", Card.typeOfCard.WEAPON);
		Card testCardMustard = new Card("Colonel Mustard", Card.typeOfCard.PERSON);
		Card testCardGreen = new Card("Mr. Green", Card.typeOfCard.PERSON);
		Card testCardHall = new Card("Hall", Card.typeOfCard.ROOM);
		Card testCardKitchen = new Card("Kitchen", Card.typeOfCard.ROOM);
		
		ArrayList<Card> testDeck = new ArrayList<Card>();
		testDeck.add(testCardKnife); testDeck.add(testCardPipe); testDeck.add(testCardMustard);
		testDeck.add(testCardGreen); testDeck.add(testCardHall); testDeck.add(testCardKitchen);
		
		ArrayList<Card> emptyHand = new ArrayList<Card>();
		Player testPlayerA = new Player("A", emptyHand, "315", "NA" );
		Player testPlayerB = new Player("B", emptyHand, "315", "NA" );
		Player testPlayerC = new Player("C", emptyHand, "315", "NA" );
		
		ArrayList<Player> testplayerList = new ArrayList<Player>();
		testplayerList.add(testPlayerA); testplayerList.add(testPlayerB); testplayerList.add(testPlayerC);
		
		ClueGame testGame = new ClueGame(testplayerList, testDeck);

		int [] playerHandSize = new int[testGame.getPlayerList().size()];
		for(int i = 0; i < testGame.getPlayerList().size(); i++){ //Gets the hand sizes of the players
			playerHandSize[i] = testGame.getPlayerList().get(i).getPlayerCardList().size();
		}

		Boolean playerHandSizeTest = true;


		for(int i = 0; i < testGame.getPlayerList().size(); i++) { //Checks the difference of hand sizes between players
			for(int j = 0; j < testGame.getPlayerList().size(); j++) { 
				if(Math.abs(playerHandSize[i] - playerHandSize[j]) > 1) { 
					playerHandSizeTest = false; //If the difference is greater than 1 between hands, the distribution can be improved
				}
			}
		}
		assert(playerHandSizeTest);



		ArrayList<Card> testCardList = new ArrayList<Card>();
		Boolean allCardsDistributed = true;
		Boolean cardsDistributedOnce = true;

		for(int i = 0; i < testGame.getPlayerList().size(); i++) { //Makes a list of all the cards contained in the hands of the players
			for(int j = 0; j < testGame.getPlayerList().get(i).getPlayerCardList().size(); j++) { 
				testCardList.add(testGame.getPlayerList().get(i).getPlayerCardList().get(j));
			}
		}

		//Checks each card in the deck, and sees if it exists in one of the player's hands
		for(int i = 0; i < testGame.getCardList().size(); i++) { 
			if(!(testCardList.contains(testGame.getCardList().get(i)))) {
				allCardsDistributed = false; //If a card in the deck does not show up in a hand, the program is flawed
			}
		}

		assert(allCardsDistributed);

		for(int i = 0; i < testGame.getCardList().size(); i++) { 
			if(!(testCardList.lastIndexOf(testGame.getCardList().get(i)) == testCardList.indexOf(testGame.getCardList().get(i)))) {
				cardsDistributedOnce = false; //If a card in the deck shows up in two (or possibly more) different places in the
				//list of the player's hands, it has shown up twice(or more) in the player's hands, and was not distributed only once.
			}
		}

		assert(cardsDistributedOnce);

	};

}
