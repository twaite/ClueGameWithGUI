package test;

import static org.junit.Assert.*;

import java.awt.Point;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import board.Board;
import board.BoardCell;
import board.Card;
import board.ClueGame;
import board.ComputerPlayer;
import board.HumanPlayer;
import board.Player;
import board.Solution;

public class GameActionTests {

	static ClueGame game;
	static Board board;
	static Card orezyCard;
	static Card okygCard;
	static Card katanaCard;
	static Card jackhammerCard;
	static Card conservatoryCard;
	static Card billiardRoomCard;
	
	@BeforeClass
	public static void setUp() {
		game = new ClueGame();
		board = game.getBoard();
		board.loadConfigFiles();
		board.calcAdjacencies();
		game.loadConfigFiles( "" , "" ); //INSERT FILE NAMES HERE
		game.deal();
		
		//Cards used in later tests
		orezyCard = new Card("Orezy", Card.CardType.PERSON);
		okygCard = new Card("OkyG", Card.CardType.PERSON);
		katanaCard = new Card("Katana", Card.CardType.WEAPON);
		jackhammerCard = new Card("Jackhammer", Card.CardType.WEAPON);
		conservatoryCard = new Card("Conservatory", Card.CardType.ROOM);
		billiardRoomCard = new Card("Billiard Room", Card.CardType.ROOM);
	}
	
	
	/************************************************************************************************************
 	* Ensures that the accusation is correct if it contains the correct person, weapon, and room, and that the 
 	* 	accusation is not correct if the room, person, or weapon, or any two or three of those, are incorrect.
 	************************************************************************************************************/
	@Test
	public void testMakeAnAccusation() {
		
		//Check that a correct answer is recognized
		Solution solution = new Solution( "OkyG", "Katana", "Ballroom");
		game.setSolution(solution);
		Assert.assertTrue( game.checkAccusation( solution ) );
		
		//Check for Incorrect Room
		solution = new Solution( "OkyG", "Katana", "Library" );
		Assert.assertFalse( game.checkAccusation( solution ) );
		
		//Check for Incorrect Weapon
		solution = new Solution( "OkyG", "Boomerang", "Ballroom" );
		Assert.assertFalse( game.checkAccusation( solution ) );
		
		//Check for Incorrect Name
		solution = new Solution( "Orezy", "Katana", "Ballroom" );
		Assert.assertFalse( game.checkAccusation( solution ) );
		
		//Check for Incorrect Name, Weapon, and Room
		solution = new Solution( "Orezy", "Boomerang", "Library" );
		Assert.assertFalse( game.checkAccusation( solution ) );
		
	}

	
	
	/************************************************************************************************************
 	* Ensures that if a room is a possible target, it is always selected as the target location.
 	************************************************************************************************************/
	@Test
	public void testRoomPreferenceSelection() {
		ComputerPlayer player = new ComputerPlayer();
		board.calcTargets(16, 20, 3);
		BoardCell selected = new BoardCell();
		
		for ( int i = 0; i < 100; i ++ ) {
			selected = player.pickLocation( board.getTargets() );
			Assert.assertTrue( selected.getColumn() == 19 );
			Assert.assertTrue( selected.getRow() == 18 );
		}
		
	}

	
	/************************************************************************************************************
 	* Ensures that the selection of a target is evenly distributed between the available locations.
 	************************************************************************************************************/
	@Test
	public void testRandomTargetSelection() {
		ComputerPlayer player = new ComputerPlayer();
		board.calcTargets(19, 14, 2);
		BoardCell selected = new BoardCell();
		int location_17_14_Total = 0;
		int location_18_15_Total = 0;
		int location_20_15_Total = 0;
		int location_21_14_Total = 0;
		
		for ( int i = 0; i < 100; i ++ ) {
			selected = player.pickLocation( board.getTargets() );
			
			if ( selected == board.getCellAt( 17, 14 ) ) {
				location_17_14_Total++;
			} else if ( selected == board.getCellAt( 18, 15 ) ) {
				location_18_15_Total++;
			} else if ( selected == board.getCellAt( 20, 15 ) ) {
				location_20_15_Total++;
			} else if ( selected == board.getCellAt( 21, 14 ) ) {
				location_21_14_Total++;
			} else {
				fail("Invalid target selected");
			}
		}
		
		//Check that there were 100 total selections (fail should also ensure)
		Assert.assertEquals( 100, location_17_14_Total + location_18_15_Total + location_20_15_Total + location_21_14_Total );
		
		//Ensure Each Target was selected more than once
		Assert.assertTrue( location_17_14_Total > 1 );
		Assert.assertTrue( location_18_15_Total > 1 );
		Assert.assertTrue( location_20_15_Total > 1 );
		Assert.assertTrue( location_21_14_Total > 1 );
	}
	
	
	/************************************************************************************************************
 	* Ensures that if a room that was last visited is in the selection of targets, that the choice is made 
 	* 	randomly from all available targets.
 	************************************************************************************************************/
	@Test
	public void testRandomTargetSelectionIfRoomPreviouslyVisited() {
		ComputerPlayer player = new ComputerPlayer();
		board.calcTargets(16, 2, 2);
		BoardCell selected = new BoardCell();
		
		player.setLastRoomVisited('R');
		
		int location_16_0_Total = 0;
		int location_15_1_Total = 0;
		int location_15_3_Total = 0;
		int location_16_4_Total = 0;
		int location_17_3_Total = 0;
		
		for ( int i = 0; i < 100; i ++ ) {
			selected = player.pickLocation( board.getTargets() );
			
			if ( selected == board.getCellAt( 16, 0 ) ) {
				location_16_0_Total++;
			} else if ( selected == board.getCellAt( 15, 1 ) ) {
				location_15_1_Total++;
			} else if ( selected == board.getCellAt( 15, 3 ) ) {
				location_15_3_Total++;
			} else if ( selected == board.getCellAt( 16, 4 ) ) {
				location_16_4_Total++;
			} else if (selected == board.getCellAt( 17, 3 ) ) {
				location_17_3_Total++;
			} else {
				fail("Invalid target selected");
			}
		}
		
		//Check that there were 100 total selections (fail should also ensure)
		Assert.assertEquals( 100, location_16_0_Total + location_15_1_Total + location_15_3_Total + location_16_4_Total + location_17_3_Total);
		//Ensure Each Target was selected more than once
		Assert.assertTrue( location_16_0_Total > 1 );
		Assert.assertTrue( location_15_1_Total > 1 );
		Assert.assertTrue( location_15_3_Total > 1 );
		Assert.assertTrue( location_16_4_Total > 1 );
		Assert.assertTrue( location_17_3_Total > 1 );
		
	}

	
	
	/************************************************************************************************************
 	* Ensures that a player returns the correct card (or null if they hold none of the cards) when disproving a 
 	* 	suggestion.
 	************************************************************************************************************/
	@Test
	public void testDisproveSuggestionOnePlayerOneCorrectMatch() {
		orezyCard = new Card("Orezy", Card.CardType.PERSON);
		okygCard = new Card("OkyG", Card.CardType.PERSON);
		katanaCard = new Card("Katana", Card.CardType.WEAPON);
		jackhammerCard = new Card("Jackhammer", Card.CardType.WEAPON);
		conservatoryCard = new Card("Conservatory", Card.CardType.ROOM);
		billiardRoomCard = new Card("Billiard Room", Card.CardType.ROOM);
		
		
		Player player = new Player();
		player.getCardList().add(orezyCard);
		player.getCardList().add(okygCard);
		player.getCardList().add(katanaCard);
		player.getCardList().add(jackhammerCard);
		player.getCardList().add(conservatoryCard);
		player.getCardList().add(billiardRoomCard);
		
		//Check correct person
		Assert.assertEquals(player.disproveSuggestion( "Orezy", "Cat", "Hall"), orezyCard );
		//Check correct weapon
		Assert.assertEquals(player.disproveSuggestion( "V3R", "Katana", "Hall"), katanaCard );
		//Check correct room
		Assert.assertEquals(player.disproveSuggestion( "V3R", "Cat", "Conservatory"), conservatoryCard );
		//Check nothing
		Assert.assertEquals(player.disproveSuggestion( "V3R", "Cat", "Hall"), null );
	}
	
	/************************************************************************************************************
 	* Ensures that a player has the possibility of returning any of the three cards in a suggestion when 
 	* 	disproving the suggestion if they have all three cards from the suggestion.
 	************************************************************************************************************/
	@Test
	public void testDisproveSuggestionOnePlayerMultipleCorrectMatches() {
		orezyCard = new Card("Orezy", Card.CardType.PERSON);
		okygCard = new Card("OkyG", Card.CardType.PERSON);
		katanaCard = new Card("Katana", Card.CardType.WEAPON);
		jackhammerCard = new Card("Jackhammer", Card.CardType.WEAPON);
		conservatoryCard = new Card("Conservatory", Card.CardType.ROOM);
		billiardRoomCard = new Card("Billiard Room", Card.CardType.ROOM);
		
		Card tempCard = new Card();
		
		Player player = new Player();
		player.getCardList().add( orezyCard );
		player.getCardList().add( okygCard );
		player.getCardList().add( katanaCard );
		player.getCardList().add( jackhammerCard );
		player.getCardList().add( conservatoryCard );
		player.getCardList().add( billiardRoomCard );
		
		int peopleCounter = 0;
		int weaponCounter = 0;
		int roomCounter = 0;
		
		for ( int i = 0; i < 100; i ++ ) {
			tempCard = player.disproveSuggestion( "Orezy", "Jackhammer",  "Conservatory" );
			if ( tempCard == orezyCard ) {
				peopleCounter++;
			} else if (tempCard == jackhammerCard ) {
				weaponCounter++;
			} else if (tempCard == conservatoryCard ) {
				roomCounter++;
			} else {
				fail( "Invalid card returned from disproveSuggestion()" );
			}
		}
		
		
		//Check that cards are returned all 100 times
		Assert.assertTrue( peopleCounter + weaponCounter + roomCounter == 100 );
		
		//Check that each card is returned at least once
		Assert.assertTrue( peopleCounter > 0 );
		Assert.assertTrue( weaponCounter > 0 );
		Assert.assertTrue( roomCounter > 0 );

	}
	
	
	/************************************************************************************************************
 	* Ensures that if multiple players have a card in the suggestion, that the player order is chosen randomly.
 	************************************************************************************************************/
	@Test
	public void testDisproveSuggestionTwoPlayerOneCorrectMatchEachRandomSelection() {
		ClueGame clueGame = new ClueGame();
		
		orezyCard = new Card("Orezy", Card.CardType.PERSON);
		okygCard = new Card("OkyG", Card.CardType.PERSON);
		katanaCard = new Card("Katana", Card.CardType.WEAPON);
		jackhammerCard = new Card("Jackhammer", Card.CardType.WEAPON);
		conservatoryCard = new Card("Conservatory", Card.CardType.ROOM);
		billiardRoomCard = new Card("Billiard Room", Card.CardType.ROOM);
		
		Card tempCard = new Card();
		
		Player player1 = new Player();
		player1.getCardList().add( orezyCard );
		player1.getCardList().add( okygCard );
		player1.getCardList().add( katanaCard );
		Player player2 = new Player();
		player2.getCardList().add( jackhammerCard );
		player2.getCardList().add( conservatoryCard );
		player2.getCardList().add( billiardRoomCard );

		Player accusingPlayer = new Player();
		
		clueGame.getPlayerList().add(player1);
		clueGame.getPlayerList().add(player2);

		clueGame.getPlayerList().add(accusingPlayer);

		
		int player1Counter = 0;
		int player2Counter = 0;
		
		for ( int i = 0; i < 100; i ++ ) {
			tempCard = clueGame.handleSuggestion( "Orezy", "Chainsaw", "Conservatory", accusingPlayer);
			if ( tempCard == orezyCard ) {
				player1Counter++;
			} else if (tempCard == conservatoryCard ) {
				player2Counter++;
			} else {
				fail( "Invalid card returned from disproveSuggestion()" );
			}
		}
		
		
		//Check that cards are returned all 100 times
		Assert.assertTrue( player1Counter + player2Counter == 100 );
		
		//Check that each card is returned at least once
		Assert.assertTrue( player1Counter > 1 );
		Assert.assertTrue( player2Counter > 1 );

	}
	
	
	/************************************************************************************************************
 	* Ensures that the player who is making the suggestion does not show one of their own card if they hold the
 	*  cards that are part of the suggestion.
 	************************************************************************************************************/
	@Test
	public void testDisproveSuggestionAccusingPlayerDoesntShowCard() {
		ClueGame clueGame = new ClueGame();
		
		orezyCard = new Card("Orezy", Card.CardType.PERSON);
		okygCard = new Card("OkyG", Card.CardType.PERSON);
		katanaCard = new Card("Katana", Card.CardType.WEAPON);
		jackhammerCard = new Card("Jackhammer", Card.CardType.WEAPON);
		conservatoryCard = new Card("Conservatory", Card.CardType.ROOM);
		billiardRoomCard = new Card("Billiard Room", Card.CardType.ROOM);
		
		
		Player player = new Player();
		player.getCardList().add(orezyCard);
		player.getCardList().add(okygCard);
		player.getCardList().add(katanaCard);
		player.getCardList().add(jackhammerCard);
		player.getCardList().add(conservatoryCard);
		player.getCardList().add(billiardRoomCard);
		
		//Check that no card is returned
		Assert.assertEquals(clueGame.handleSuggestion("Orezy", "Katana", "Conservatory", player), null);
	}
	
	
	/************************************************************************************************************
 	* Ensures that a human player returns the correct card (or null if they hold none of the cards) when 
 	* 	disproving a suggestion. 
 	************************************************************************************************************/
	@Test
	public void testDisproveSuggestionHumanPlayer() {
		orezyCard = new Card("Orezy", Card.CardType.PERSON);
		okygCard = new Card("OkyG", Card.CardType.PERSON);
		katanaCard = new Card("Katana", Card.CardType.WEAPON);
		jackhammerCard = new Card("Jackhammer", Card.CardType.WEAPON);
		conservatoryCard = new Card("Conservatory", Card.CardType.ROOM);
		billiardRoomCard = new Card("Billiard Room", Card.CardType.ROOM);
		
		
		HumanPlayer player = new HumanPlayer();
		player.getCardList().add(orezyCard);
		player.getCardList().add(okygCard);
		player.getCardList().add(katanaCard);
		player.getCardList().add(jackhammerCard);
		player.getCardList().add(conservatoryCard);
		player.getCardList().add(billiardRoomCard);
		
		//Check correct person
		Assert.assertEquals(player.disproveSuggestion( "Orezy", "Cat", "Hall"), orezyCard );
		//Check correct weapon
		Assert.assertEquals(player.disproveSuggestion( "V3R", "Katana", "Hall"), katanaCard );
		//Check correct room
		Assert.assertEquals(player.disproveSuggestion( "V3R", "Cat", "Conservatory"), conservatoryCard );
		//Check nothing
		Assert.assertEquals(player.disproveSuggestion( "V3R", "Cat", "Hall"), null );
	}
	
	
	/************************************************************************************************************
 	* Ensures that if only a human player can disprove a suggestion, then the human does so.
 	************************************************************************************************************/
	@Test
	public void testDisproveSuggestionOnlyHumanPlayerCanDisprove() {
		ClueGame clueGame = new ClueGame();
		
		HumanPlayer humanPlayer = new HumanPlayer();
		humanPlayer.getCardList().add( orezyCard );
		humanPlayer.getCardList().add( katanaCard );
		humanPlayer.getCardList().add( billiardRoomCard );
		Player player2 = new Player();
		player2.getCardList().add( okygCard );
		player2.getCardList().add( jackhammerCard );
		player2.getCardList().add( conservatoryCard );
		

		Player accusingPlayer = new Player();
		
		clueGame.getPlayerList().add(humanPlayer);
		clueGame.getPlayerList().add(player2);

		clueGame.getPlayerList().add(accusingPlayer);


		Assert.assertEquals(clueGame.handleSuggestion( "Orezy", "Cat", "Hall", accusingPlayer), orezyCard);
		
	}
	

	/************************************************************************************************************
 	* Ensures that a Computer Player will make a suggestion based off of cards it has not seen and the room it
 	* is in.
 	************************************************************************************************************/
	@Test
	public void testComputerPlayerMakeSuggestion() {
	ClueGame clueGame = new ClueGame();
	
	Solution correctSuggestion = new Solution( "Orezy", "Cat", "Hall" );
	ComputerPlayer player = new ComputerPlayer();
	Point point = new Point(4, 13);
	player.setLocation(point);
	player.updateSeen( new Card( "OkyG", Card.CardType.PERSON) );
	player.updateSeen( new Card( "V3R", Card.CardType.PERSON) );
	player.updateSeen( new Card( "D0m1n4t0r", Card.CardType.PERSON) );
	player.updateSeen( new Card( "roxguy", Card.CardType.PERSON) );
	player.updateSeen( new Card( "elbeast", Card.CardType.PERSON) );
	player.updateSeen( new Card( "Hook", Card.CardType.PERSON) );
	player.updateSeen( new Card( "Wilson", Card.CardType.PERSON) );
	player.updateSeen( new Card( "Gator", Card.CardType.PERSON) );
	player.updateSeen( new Card( "Boomerang", Card.CardType.WEAPON) );
	player.updateSeen( new Card( "Katana", Card.CardType.WEAPON) );
	player.updateSeen( new Card( "Jackhammer", Card.CardType.WEAPON) );
	player.updateSeen( new Card( "Pen", Card.CardType.WEAPON) );
	player.updateSeen( new Card( "Hammer", Card.CardType.WEAPON) );
	player.updateSeen( new Card( "Razor", Card.CardType.WEAPON) );
	player.updateSeen( new Card( "Chainsaw", Card.CardType.WEAPON) );
	player.updateSeen( new Card( "Lasso", Card.CardType.WEAPON) );
	player.updateSeen( new Card( "Conservatory", Card.CardType.ROOM) );
	player.updateSeen( new Card( "Kitchen", Card.CardType.ROOM) );
	player.updateSeen( new Card( "Ballroom", Card.CardType.ROOM) );
	player.updateSeen( new Card( "Billiard Room", Card.CardType.ROOM) );
	player.updateSeen( new Card( "Library", Card.CardType.ROOM) );
	player.updateSeen( new Card( "Study", Card.CardType.ROOM) );
	player.updateSeen( new Card( "Dining Room", Card.CardType.ROOM) );
	player.updateSeen( new Card( "Lounge", Card.CardType.ROOM) );
	
	Solution suggestion = player.createSuggestion();
	
	//Asserts that the Computers Suggestion contains the correct room, weapon, and person
	Assert.assertEquals(suggestion.getRoom(), correctSuggestion.getRoom());
	Assert.assertFalse(player.getCardsSeenList().contains( new Card("Cat",Card.CardType.WEAPON)) );
	Assert.assertFalse(player.getCardsSeenList().contains( new Card("Orezy",Card.CardType.PERSON)) );
	}
	
	/************************************************************************************************************
 	* Ensures that a Computer Player will make a suggestion based off of cards it has not seen randomly and the
 	* 	room it is in. This test makes sure there are multiple cards the computer has not seen.
 	************************************************************************************************************/
	@Test
	public void testComputerPlayerMakeSuggestionRandom() {
	ClueGame clueGame = new ClueGame();
	
	ComputerPlayer player = new ComputerPlayer();
	Point point = new Point(4, 13);
	player.setLocation(point);
	player.updateSeen( new Card( "V3R", Card.CardType.PERSON) );
	player.updateSeen( new Card( "D0m1n4t0r", Card.CardType.PERSON) );
	player.updateSeen( new Card( "roxguy", Card.CardType.PERSON) );
	player.updateSeen( new Card( "elbeast", Card.CardType.PERSON) );
	player.updateSeen( new Card( "Hook", Card.CardType.PERSON) );
	player.updateSeen( new Card( "Wilson", Card.CardType.PERSON) );
	player.updateSeen( new Card( "Gator", Card.CardType.PERSON) );
	player.updateSeen( new Card( "Boomerang", Card.CardType.WEAPON) );
	player.updateSeen( new Card( "Katana", Card.CardType.WEAPON) );
	player.updateSeen( new Card( "Pen", Card.CardType.WEAPON) );
	player.updateSeen( new Card( "Hammer", Card.CardType.WEAPON) );
	player.updateSeen( new Card( "Razor", Card.CardType.WEAPON) );
	player.updateSeen( new Card( "Chainsaw", Card.CardType.WEAPON) );
	player.updateSeen( new Card( "Lasso", Card.CardType.WEAPON) );
	player.updateSeen( new Card( "Conservatory", Card.CardType.ROOM) );
	player.updateSeen( new Card( "Kitchen", Card.CardType.ROOM) );
	player.updateSeen( new Card( "Ballroom", Card.CardType.ROOM) );
	player.updateSeen( new Card( "Billiard Room", Card.CardType.ROOM) );
	player.updateSeen( new Card( "Library", Card.CardType.ROOM) );
	player.updateSeen( new Card( "Study", Card.CardType.ROOM) );
	player.updateSeen( new Card( "Dining Room", Card.CardType.ROOM) );
	player.updateSeen( new Card( "Lounge", Card.CardType.ROOM) );
	
	int jackhammerCounter = 0;
	int okygCounter = 0;
	int catCounter = 0;
	int orezyCounter = 0;
	for ( int i = 0; i < 100; i++ ) {
		Solution suggestion = player.createSuggestion();
		if ( suggestion.getWeapon() == "Jackhammer" ) {
			jackhammerCounter++;
		}
		if ( suggestion.getWeapon() == "Cat" ) {
			catCounter++;
		}
		if ( suggestion.getPerson() == "Orezy" ) {
			orezyCounter++;
		}
		if ( suggestion.getPerson() == "OkyG" ) {
			okygCounter++;
		}
	}
	//Asserts that the Computers Suggestion contains the correct room, weapon, and person
	Assert.assertTrue(jackhammerCounter > 0);
	Assert.assertTrue(catCounter > 0);
	Assert.assertTrue(okygCounter > 0);
	Assert.assertTrue(orezyCounter > 0);
	
	//Asserts that the total of the suggestions of either weapon or either person not seen is 100
	Assert.assertEquals(jackhammerCounter + catCounter, 100);
	Assert.assertEquals(orezyCounter + okygCounter, 100);
	
	}
	
}