package Board;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;


import Board.Board;
import Board.BoardCell;


public class ComputerPlayer extends Player{
	
	private char visited;
	public boolean willAccuse;
	private ArrayList<Card> guess = new ArrayList<Card>();
	

	public ComputerPlayer(String playerName, String location, String color) {
		super(playerName, location, color);
		willAccuse = false;
		solutionGuess = new ArrayList<Card>();
	}
	
	public ComputerPlayer(String playerName, ArrayList<Card> cards, String location, String color) {
		super(playerName, cards, location, color);
		willAccuse = false;
		solutionGuess = new ArrayList<Card>();
	}
	
	@Override
	public void playerTurn(int randomRollValue){
		
		this.connectGame.getBoard().setShowPlayerTargets(false);
		
		Board board = connectGame.getBoard();
		
		//if the previous suggestion was not disproved, the player will accuse
		if(willAccuse){makeAccusation();}
		
		//dice roll and movement
		board.startTargets(index, randomRollValue);
		this.index = pickLocation(board.getTargets(),board);
		
		showSuggestion = false;		//unless the player is in a room, the past suggestion should not be shown
		
		//if the player lands in a room, it will make a suggestion
		if(board.getCellAt(index).isRoom()){
			visited = board.getCellAt(index).getInitial();
			solutionGuess = makeSuggestion();
		}
		board.repaint();

	}

	//the computer will make a suggestion based on the see cards lists
	public ArrayList<Card> makeSuggestion() {
		
		guess = connectGame.computerSuggestion(this);

		for(Card i:guess){}
		//test for possible accusation
		result = connectGame.testSuggestion(this, guess.get(0), guess.get(1), guess.get(2));
		connectGame.addToSeenCards(result);
		if(result==null){willAccuse = true;}
		else if(result!=null){
			showSuggestion = true;
		}
		
		return guess;
	}
	
	private void makeAccusation(){
		connectGame.makeAccusation(solutionGuess);
		
		String temp = "";
		ArrayList<Card> temp2 = new ArrayList<Card>():
		
		Player accused = new Player(temp,temp2,temp,temp);
		Card accusedRoom;
		int roomIndex = 0;
		
		for(int i = 0; i<solutionGuess.size();i++){
			if(solutionGuess.get(i).getType() == Card.typeOfCard.PERSON){
					accused = connectGame.getPlayerList().get(i);
			}else if(solutionGuess.get(i).getType() == Card.typeOfCard.ROOM){
				roomIndex = connectGame.roomIndexes.get(connectGame.getPlayerList().lastIndexOf(solutionGuess.get(1).getName()));
			}
		}
		
		accused.setIndex(roomIndex);
				
		
		
		
		if(guess == connectGame.getSolution()){
			AccuseScreen gameover= new AccuseScreen(guess,willAccuse,this);
		}else{
			willAccuse = false;
			AccuseScreen gameover= new AccuseScreen(guess,willAccuse,this);
		}
		
		
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
