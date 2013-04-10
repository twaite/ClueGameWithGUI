package board;

public class Card {

	private String name;
	private CardType cardType;
	
	public enum CardType { PERSON, WEAPON, ROOM	}

	@Override
	public String toString() {
		return "Card [name=" + name + ", cardType=" + cardType + "]\n";
	}
	
	public Card() {
		name = "";
		cardType = CardType.PERSON;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cardType == null) ? 0 : cardType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (cardType != other.cardType)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
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
