package Board;

import java.util.ArrayList;


public class HumanPlayer extends Player{
	
	public HumanPlayer(String playerName, ArrayList<Card> cards, String location, String color) {
		super(playerName, cards, location, color);
	}
	
	@Override
	public void playerTurn(int randomRollValue){
		
		System.out.println(index);
		
		this.connectGame.getBoard().startTargets(index, randomRollValue);
		
		this.connectGame.getBoard().setShowPlayerTargets(true);
		System.out.println("WHEEEEE");
		this.connectGame.getBoard().repaint();
		this.connectGame.validate();
		System.out.println("WHEEEEE");
		for(int i = 0; i < 100000000; i++) {
			//System.out.println("WHEEEEE");
		}
		//this.connectGame.getBoard().setShowPlayerTargets(false);

	}

}
