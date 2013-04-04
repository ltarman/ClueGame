package Board;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;


public class Player {

	private String name;
	private String color;
	private String location; //The name of the room the player is in. Used to determine which room card must be suggested for a suggestion
	              //NOTE: the room cells have a char for their room identification, we must convert the char to a string to update this!
	private ArrayList<Card> playerCardList;
	private int index;
	
	public Player(String playerName, ArrayList<Card> cards, String location, String color) {
		super();
		this.name = playerName;
		this.location = location;
		this.index = Integer.parseInt(location);
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
	
	public void setLocation(String newLocation){ //FOR TESTING ONLY!!!
		location = newLocation;
	}

	public ArrayList<Card> getPlayerCardList() {
		return playerCardList;
	}
}

