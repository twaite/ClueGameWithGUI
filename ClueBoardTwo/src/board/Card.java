package board;

public class Card {

	String name;
	CardType cardType;
	
	public enum CardType { PERSON, WEAPON, ROOM	}

	public Card() {
		
	}
	
	public Card( String name, CardType cardType ) {
		this.name = name;
		this.cardType = cardType;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName( String newName ) {
		this.name = newName;
	}
	
	public void setCardType( CardType newCardType ) {
		this.cardType = newCardType;
	}
	
	public CardType getCardType() {
		return cardType;
	}
	
	///EQUALS METHOD
	
}
