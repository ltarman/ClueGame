package Board;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;


import Board.Board;
import Board.BoardCell;


public class ComputerPlayer extends Player{
	
	private char visited;
	//private Random generator = new Random();

	public ComputerPlayer(String playerName, ArrayList<Card> cards, String location, String color) {
		super(playerName, cards, location, color);
	}

	
	//This needs to be worked on. %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	
	public int pickLocation(Set<Integer> possibleTargets) {
		//Selects a random cell from the calculated list of targets
		Random randomGenerator = new Random();
		Object [] arrayOfTargets;
		arrayOfTargets = possibleTargets.toArray();

		return 6;
	}

	public char getVistited() {
		return visited;
	}

	public void setVisited(char vistited) {
		this.visited = vistited;
	}

	public ArrayList<Card> makeSuggestion() {
		ArrayList<Card> returnedCardList = new ArrayList<Card>();
		return returnedCardList;
	}
	
}
