package Board;

import java.util.ArrayList;


public class HumanPlayer extends Player{
	
	public HumanPlayer(String playerName, ArrayList<Card> cards, String location, String color) {
		super(playerName, cards, location, color);
	}
	
	@Override
	public void playerTurn(int randomRollValue){
		
		//System.out.println(index);
		this.connectGame.togglePlayerTurn();
		this.connectGame.getBoard().startTargets(index, randomRollValue);
		
		this.connectGame.getBoard().setShowPlayerTargets(true);
		this.connectGame.getBoard().repaint();
		//this.connectGame.validate();

	}

}
