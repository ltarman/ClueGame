package Board;

import java.util.ArrayList;
import java.util.Random;


public class Player {

	private String name;
	private String color;
	private String location; //The name of the room the player is in. Used to determine which room card must be suggested for a suggestion
	              //NOTE: the room cells have a char for their room identification, we must convert the char to a string to update this!
	private ArrayList<Card> playerCardList;
	
	public Player(String playerName, ArrayList<Card> cards, String location, String color) {
		super();
		this.name = playerName;
		this.location = location;
		this.color = color;
		playerCardList = cards;
	}
	
	public Card disproveSuggestion(Card A, Card B, Card C) {
		Random randomGenerator = new Random();
		ArrayList<Card> returnCard = new ArrayList<Card>();
		if(playerCardList.contains(A)) {
			returnCard.add(A);
		}
		if(playerCardList.contains(B)) {
			returnCard.add(B);
		}
		if(playerCardList.contains(C)) {
			returnCard.add(C);
		}
		if(returnCard.size() > 0) {		
			return returnCard.get(randomGenerator.nextInt(returnCard.size()));
		} else {
			return null;
		}
	}

	public void giveCard(Card A) {
		playerCardList.add(A);
	}

	public String getName(){
		return name;
	}

	public String getColor(){
		return color;
	}
	
	public String getLocation(){
		return location;
	}
	
	public void setLocation(String newLocation){ //FOR TESTING ONLY!!! DELETE THIS
		location = newLocation;
	}

	public ArrayList<Card> getPlayerCardList() {
		return playerCardList;
	}
	
}

