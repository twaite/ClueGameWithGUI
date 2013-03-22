package test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
		Assert.assertEquals( game.getHumanPlayer().getName(), "Orezy" ); 
		Assert.assertEquals( game.getHumanPlayer().getColor(), "Green" ); 
		Assert.assertEquals( game.getHumanPlayer().getLocation(), location );
	
		//Check the first computer player in the file
//		player = game.getPlayerList().get(1);
//		location = new Point( 6, 0 );
//		Assert.assertEquals ( player.getName() , "OkyG");
//		Assert.assertEquals ( player.getColor() , "Blue");
//		Assert.assertEquals ( player.getLocation() , location);
		
		//Check the last computer player in the file
		player = game.getPlayerList().get(8);
		location = new Point( 0, 11 );
		Assert.assertEquals ( player.getName() , "Gator");
		Assert.assertEquals ( player.getColor() , "White");
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
 	* Ensures that (1) all cards are dealt, (2) all players have roughly the same number of cards, and (3) one 
 	* 	card is not given to two different players.
 	************************************************************************************************************/
	@Test
	public void testDeal() {
		
		game.deal(); 
		ArrayList<Player> players = new ArrayList<Player>();
		players = game.getPlayerList();
		Set<Card> cards = new HashSet<Card>();
		int totalNumCards = 0;
		int minNumCards = 1000000;
		int maxNumCards = players.get(0).getCardList().size();
		int cardListSize;
		
		for ( Player p : players ) {
			cardListSize = p.getCardList().size();
			totalNumCards += cardListSize;
			if ( cardListSize < minNumCards ) {
				minNumCards = cardListSize; 
			} else if ( cardListSize > maxNumCards ) {
				maxNumCards = cardListSize;
			}
			for ( Card c : p.getCardList() ) {
				//(3) Check that the current player does not have a card that is also in the set of cards from the previous players
				Assert.assertTrue( !(cards.contains( c )) );
			}
			cards.addAll(p.getCardList());
		}
		
		//(1) Check that the various players card counts add up to the total number of cards in the deck
		Assert.assertEquals( totalNumCards, 24);
		
		//(2) Check that the number of cards held by the player with the most cards is only at most one more card than the number of cards
		// held by the player with the least number of cards
		Assert.assertTrue((maxNumCards - minNumCards) <= 1);
		
	}
	

}
