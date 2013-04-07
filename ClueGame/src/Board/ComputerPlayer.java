package Board;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;


import Board.Board;
import Board.BoardCell;


public class ComputerPlayer extends Player{
	
	private char visited;

	public ComputerPlayer(String playerName, ArrayList<Card> cards, String location, String color) {
		super(playerName, cards, location, color);
	}
	
	@Override
	public void playerTurn(int randomRollValue){
		Board board = connectGame.getBoard();
		board.calcTargets(index, randomRollValue);
		this.index = pickLocation(board.getTargets(),board);
		board.repaint();
		
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

	public void setVisited(char vistited) {
		this.visited = vistited;
	}

	public ArrayList<Card> makeSuggestion() {
		ArrayList<Card> returnedCardList = new ArrayList<Card>();
		return returnedCardList;
	}
	
}
