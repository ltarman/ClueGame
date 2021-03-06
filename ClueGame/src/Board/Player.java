package Board;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;


public class Player {

	private String name;
	private String color;
	protected String location; //The name of the room the player is in. Used to determine which room card must be suggested for a suggestion
	              //NOTE: the room cells have a char for their room identification, we must convert the char to a string to update this!
	protected ArrayList<Card> playerCardList;
	protected int index;
	protected ArrayList<Card> solutionGuess = new ArrayList<Card>();
	protected Card result = new Card("",Card.typeOfCard.PERSON);
	
	Boolean canMakeGuess; //Refreshes when a player moves. Prevents more than one accusation in a around
	
	private CustomMessage customMessage;
	
	protected ClueGame connectGame;
	
	public boolean showSuggestion;
	
	public Player(String playerName, String location, String color) {
		super();
		this.name = playerName;
		this.location = location;
		this.index = Integer.parseInt(location);
		this.color = color;
		playerCardList = new ArrayList<Card>();
		canMakeGuess = true;
	}
	
	public Player(String playerName, ArrayList<Card> cards, String location, String color) {
		super();
		this.name = playerName;
		this.location = location;
		this.index = Integer.parseInt(location);
		this.color = color;
		playerCardList = cards;
	}
	
	public void connectToGame(ClueGame gameIn) {
		connectGame = gameIn;
	}
	
	public void playerTurn(int randomRollValue) {
		
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
	
	//draws the player as a circle of their color and black outline
	public void draw(Graphics g,Board board){
		int row = (int) Math.floor(index/board.getNumColumns());
		int col = index-row*board.getNumColumns();
		
		
		if(color.equals("Red")) {
			g.setColor(Color.RED);
		}
		if(color.equals("Blue")) {
			g.setColor(Color.BLUE);
		}
		if(color.equals("Yellow")) {
			g.setColor(Color.ORANGE);
		}
		if(color.equals("Green")) {
			g.setColor(Color.GREEN);
		}
		if(color.equals("White")) {
			g.setColor(Color.WHITE);
		}
		if(color.equals("Purple")) {
			g.setColor(Color.MAGENTA);
		}
		
		
		//System.out.println(convertColor(color));
		//g.setColor(convertColor(color));
		
		g.fillOval(col*20+2, row*20+2, 16, 16);
		g.setColor(Color.BLACK);
		g.drawOval(col*20+2, row*20+2, 16, 16);
		
		
	}
	
	//converts a string into color for use in the draw function
	private Color convertColor(String strColor) {
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null;
		}
		return color;
	}
	
	


	//prints out the guess for the cards
	public String printGuess(){
		String guess = "";
		if(!solutionGuess.isEmpty()){
		guess = solutionGuess.get(0).getName() + ", " + solutionGuess.get(1).getName() + ", " + solutionGuess.get(2).getName();
		return guess;
		}
		else{return guess;}
	}
	
	//will return a string of the name of the current location
	public String loactionName(){
		String roomName = "";
		char key = connectGame.getBoard().getCellAt(index).getInitial();
		roomName = connectGame.getBoard().getRooms().get(key);
		return roomName;
	}
	
	public void humanSuggestion(String Player, String Weapon, Card C) {
		Card selectedPlayer = new Card("BLAH", Card.typeOfCard.PERSON);
		Card selectedWeapon = new Card("BLAH", Card.typeOfCard.WEAPON);
		Card selectedRoom = new Card("BLAH", Card.typeOfCard.ROOM);
		
		System.out.println("Room card name is: " + C.getName());
		
		for(int i = 0; i < 21; i++) {
			if(connectGame.getFullCardList().get(i).getName().equals(Player)) {
				selectedPlayer = connectGame.getFullCardList().get(i);
				//System.out.println("YAYAYAY " + Player);
			}
			if(connectGame.getFullCardList().get(i).getName().equals(Weapon)) {
				selectedWeapon = connectGame.getFullCardList().get(i);
				//System.out.println("YAYAYAY " + Weapon);
			}
			if(connectGame.getFullCardList().get(i).getName().equals(C.getName())) {
				selectedRoom = connectGame.getFullCardList().get(i);
				//System.out.println("YAYAYAY " + selectedRoom.getName());
			}
		}
		
		solutionGuess.clear();
		solutionGuess.add(selectedPlayer);
		solutionGuess.add(selectedWeapon);
		solutionGuess.add(selectedRoom);
		
		result = connectGame.testSuggestion(connectGame.getPlayerList().get(0), selectedPlayer,selectedWeapon, selectedRoom);
		showSuggestion=true;
		connectGame.displayGuessInfo(0);
	}
	
	public void humanAccusation(String personName, String weaponName, String roomName) {
		if(canMakeGuess == true) {
			ArrayList<Card> accuseList = new ArrayList<Card>();
			accuseList.ensureCapacity(6);
			//System.out.println(connectGame.getFullCardList().size() + "SIZE");
			
			for(int i = 0; i < 21; i++) {
				if(connectGame.getFullCardList().get(i).getName().equals(personName)) {
					accuseList.add(0, connectGame.getFullCardList().get(i));
					//System.out.println("YAYAYAY " + personName);
				}
				if(connectGame.getFullCardList().get(i).getName().equals(weaponName)) {
					accuseList.add(1, connectGame.getFullCardList().get(i));
					//System.out.println("YAYAYAY " + weaponName);
				}
				if(connectGame.getFullCardList().get(i).getName().equals(roomName)) {
					accuseList.add( 2, connectGame.getFullCardList().get(i));
					//System.out.println("YAYAYAY " + roomName);
				}
			}
			
			//System.out.println(accuseList.get(2));
			//System.out.println(connectGame.roomIndexes.size());
			//System.out.println(connectGame.roomIndexes.get(accuseList.get(2).getName())+"YEARGB");
			int targetLocation = connectGame.roomIndexes.get(accuseList.get(2).getName());


			


			Boolean accuseResult = connectGame.makeAccusation(accuseList);

			System.out.println("Your accuation is: " + accuseResult + "!!!");
			
			AccuseScreen gameover= new AccuseScreen(accuseList,accuseResult,this);
			
			for(int i = 0; i < connectGame.getPlayerList().size(); i++) {
				//System.out.println("SENDINGPEOPLE");
				if(accuseList.get(0).getName().equals(connectGame.getPlayerList().get(i).getName())) {
					//System.out.println("SENDING " + connectGame.getPlayerList().get(i).getName() + " TO " + targetLocation);
					connectGame.getPlayerList().get(i).setIndex(targetLocation);
					connectGame.playerTurnOff();
				}
			}
			
			canMakeGuess = false;
		} else {
			System.out.println("You have already made a guess! Wait until next turn.");
			customMessage = new CustomMessage("You have already made a guess! Wait until next turn.");
		}


	}

	
	
//getters and setters
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
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int intIn) {
		index = intIn;
		System.out.println("Moved player");
		canMakeGuess = true;
	}
	
	public void setLocation(String newLocation){ //FOR TESTING ONLY!!!
		location = newLocation;
	}

	public ArrayList<Card> getPlayerCardList() {
		return playerCardList;
	}
	

}

