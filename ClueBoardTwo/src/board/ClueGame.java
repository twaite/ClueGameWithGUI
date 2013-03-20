package board;

import java.util.ArrayList;

public class ClueGame {

	
	Player humanPlayer = new HumanPlayer();
	ArrayList<Card> cards = new ArrayList<Card>();
	ArrayList<Player> players = new ArrayList<Player>();
	int turnIndicator;
	
	/************************************************************************************************************
 	* Default constructor
 	************************************************************************************************************/
	public ClueGame() {
		
	}
	
	/************************************************************************************************************
 	* Distributes cards among the players as evenly as possible (maximum difference between the number of cards
 	* 	of two players is one.
 	************************************************************************************************************/
	public void deal() {
		
	}
	
	/************************************************************************************************************
 	* Parameters: String peopleFileName, String cardFileName
 	* 
 	* 	Calls helper functions loadPeopleFileName(String peopleFileName) and loadCardFileName(cardFileName).
 	************************************************************************************************************/
	public void loadConfigFiles( String peopleFileName, String cardFileName ) {
		loadPeopleFile( peopleFileName );
		loadCardFile( cardFileName );
	}
	
	/************************************************************************************************************
 	* Parameter: String peopleFileName
 	* 
 	* 	Opens and extracts the people info from the given people file name.
 	************************************************************************************************************/
	public void loadPeopleFile( String peopleFileName ) {
		
	}
	
	/************************************************************************************************************
 	* Parameter: String cardFileName
 	* 
 	* 	Opens and extracts the card info from the given card file name.
 	************************************************************************************************************/
	public void loadCardFile( String cardFileName ) {
		
	}
	
	/************************************************************************************************************
 	* 
 	************************************************************************************************************/
	public void selectAnswer() {
		
	}
	
	/************************************************************************************************************
 	* Parameter: String person, String room, String weapon, String accusingPlayer
 	* 
 	* 	
 	************************************************************************************************************/
	public void handleSuggestion( String person, String room, String weapon, String accusingPlayer ) {
		
	}
	
	/************************************************************************************************************
 	* Parameter: Solution solution
 	* 
 	* 	
 	************************************************************************************************************/
//	public boolean checkAccusation( Solution solution ) {
//		
//	}
	
	
	public Player getHumanPlayer() {
		return humanPlayer;
	}
	
	public ArrayList<Card> getCardList() {
		return cards;
	}
	
	public ArrayList<Player> getPlayerList() {
		return players;
	}
}
