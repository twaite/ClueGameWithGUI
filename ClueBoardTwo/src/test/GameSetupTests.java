package test;

import java.awt.Point;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import board.Card;
import board.Card.CardType;
import board.ClueGame;
import board.Player;

public class GameSetupTests {

	static ClueGame game;
	
	@BeforeClass
	public static void setUp() {
		game = new ClueGame();
		game.loadConfigFiles( "" , "" ); //INSERT FILE NAMES HERE
		game.deal();
		
	}
	
	
	/************************************************************************************************************
 	* Ensures the human player and the first and last computer player in the file have the correct Name, Color,
 	* 	and Starting Location
 	************************************************************************************************************/
	@Test
	public void testLoadPeopleFile() {
		
		Player player = new Player();
		Point location = new Point();
		
		//Check the human player
		location = new Point( 0, 6 );
		Assert.assertEquals( game.getHumanPlayer().getName(), "Orez" ); 
		Assert.assertEquals( game.getHumanPlayer().getColor(), "Green" ); 
		Assert.assertEquals( game.getHumanPlayer().getLocation(), location );
	
		//Check the first computer player in the file
		player = game.getPlayerList().get(0);
		location = new Point( 12, 0 );
		Assert.assertEquals ( player.getName() , "OkyG");
		Assert.assertEquals ( player.getColor() , "Blue");
		Assert.assertEquals ( player.getLocation() , location);
		
		//Check the last computer player in the file
		player = game.getPlayerList().get(8);
		location = new Point( 6, 0 );
		Assert.assertEquals ( player.getName() , "V3R");
		Assert.assertEquals ( player.getColor() , "Red");
		Assert.assertEquals ( player.getLocation() , location);
	}
	
	
	/************************************************************************************************************
 	* Ensures that the deck contains the correct total number of cards and the correct number of cards of each 
 	* 	type. Also selects one room, one weapon, and one person, then ensures the deck contains each of those.
 	************************************************************************************************************/
	@Test
	public void testLoadCardsFile() {
		
		ArrayList<Card> cards = new ArrayList<Card>();
		Card tempCard = new Card();
		
		int peopleCounter= 0;
		int weaponCounter = 0;
		int roomCounter = 0;
		
		//Check that the deck contains the correct total number of cards
		Assert.assertEquals(game.getCardList().size(), 27);
		
		
		
		cards = game.getCardList();
		CardType type;
		for (Card c : cards) {
			type = c.getCardType();
			switch( type ) {
				case PERSON :
					peopleCounter++;
					break;
				case WEAPON :
					weaponCounter++;
					break;
				case ROOM :
					roomCounter++;
					break;
			}
		}
		
		//Check that the deck correct number of cards of each type
		Assert.assertEquals(peopleCounter, 9);
		Assert.assertEquals(weaponCounter, 9);
		Assert.assertEquals(roomCounter, 9);
		
		//Check that a specific person is in the deck
		tempCard = new Card( "OkyG", CardType.PERSON);
		Assert.assertTrue(cards.contains(tempCard));
		
		//Check that a specific weapon is in the deck
		tempCard = new Card( "Boomerang", CardType.WEAPON);
		Assert.assertTrue(cards.contains(tempCard));
		
		//Check that a specific room is in the deck
		tempCard = new Card( "Conservatory", CardType.ROOM);
		Assert.assertTrue(cards.contains(tempCard));
	}
	
	/************************************************************************************************************
 	* Ensures that all cards are dealt, all players have roughly the same number of cards, and one card is not 
 	* 	given to two different players.
 	************************************************************************************************************/
	@Test
	public void testDeal() {
		
		ArrayList<Player> players = new ArrayList<Player>();
		int totalNumCards = 0;
		
		players = game.getPlayerList();
		
		for (Player p : players) {
			totalNumCards += p.getCardList().size();
		
		
		}
		
		//Check that the various players card counts add up to the total number of cards in the deck
		Assert.assertEquals( totalNumCards, 27);
		
		
		//STILL IMPLEMENT ASSERT FOR PART 2 AND 3
		
		
	}
	
	/************************************************************************************************************
 	* Ensures that the accusation is correct if it contains the correct person, weapon, and room, and that the 
 	* 	accusation is not correct if the room, person, or weapon, or any two or three of those, are incorrect.
 	************************************************************************************************************/
	@Test
	public void testMakeAnAccusation() {
		
	}

	/************************************************************************************************************
 	* 
 	************************************************************************************************************/
	@Test
	public void testSelectingATargetLocation() {
		
	}
}
