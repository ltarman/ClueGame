package Board;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;


import Board.Board;
import Board.BoardCell;


public class ComputerPlayer extends Player{
	
	private char visited;
	private boolean willAccuse;
	private ArrayList<Card> solutionGuess;

	public ComputerPlayer(String playerName, ArrayList<Card> cards, String location, String color) {
		super(playerName, cards, location, color);
		willAccuse = false;
		solutionGuess = new ArrayList<Card>();
	}
	
	@Override
	public void playerTurn(int randomRollValue){
		Board board = connectGame.getBoard();
		
		//if the previous suggestion was not disproved, the player will accuse
		if(willAccuse){makeAccusation();}
		
		//dice roll and movement
		board.startTargets(index, randomRollValue);
		this.index = pickLocation(board.getTargets(),board);
		
		//if the player lands in a room, it will make a suggestion
		if(board.getCellAt(index).isRoom()){
			visited = board.getCellAt(index).getInitial();
			solutionGuess = makeSuggestion();
		}
		board.repaint();

	}

	//the computer will make a suggestion based on the see cards lists
	public ArrayList<Card> makeSuggestion() {
		ArrayList<Card> guess = new ArrayList<Card>();
		guess = connectGame.computerSuggestion(this);
		//add the three cards to the seen card lists
		for(Card i:guess){connectGame.addToSeenCards(i);}
		//test for possible accusation
		Card result = connectGame.testSuggestion(this, guess.get(0), guess.get(1), guess.get(2));
		if(result==null){willAccuse = true;}
		
		return guess;
	}
	
	private void makeAccusation(){
		connectGame.makeAccusation(solutionGuess);
		
		connectGame.getPlayerList().lastIndexOf(solutionGuess.get(1).getName());
		
		
		
		//display something + reset,game over
		willAccuse = false;
	}
	
	public int pickLocation(Set<Integer> possibleTargets, Board board) {
		//Selects a random cell from the calculated list of targets
		int r = 0;
		Random generator = new Random();
		boolean room = false;
		
		for(int i=0;i<possibleTargets.size();i++){
			if((board.getCellAt((Integer) possibleTargets.toArray()[i])).isRoom() && visited != board.getCellAt((Integer) possibleTargets.toArray()[i]).getInitial()){
				room = true;
				return (Integer) possibleTargets.toArray()[i];
			}
		}
		
		if(!room){
			r = generator.nextInt(possibleTargets.size());
			return (Integer) possibleTargets.toArray()[r];
		}else return 0;
	}

	public char getVistited() {
		return visited;
	}
	
	//for testing only
	public void setVisited(char vistited) {
		this.visited = vistited;
	}
	
	public ArrayList<Card> getCards(){
		return this.playerCardList;
	}


}
