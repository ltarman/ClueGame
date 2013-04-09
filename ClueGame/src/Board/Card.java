package Board;

public class Card {

	public enum typeOfCard {PERSON, WEAPON, ROOM};
	
	private typeOfCard cardType; 
	private String cardName = "";
	
	public Card(String cardName, typeOfCard type) {
		super();
		this.cardType = type;
		this.cardName = cardName;
	}
	
	public void setType(typeOfCard typeIn) {
		cardType = typeIn;
	}
	
	public void setName(String nameIn) {
		cardName = nameIn;
	}
	
	public typeOfCard getType() {
		return cardType;
	}
	
	public String getName() {
		return cardName;
	}
	
	public Boolean equals(Card cardIn){
		if ((this.cardType == cardIn.cardType) && (this.cardName == cardIn.cardName)) {
			return true;
		} else {
			return false;
		}
		
	}
	
	
	
	
}
