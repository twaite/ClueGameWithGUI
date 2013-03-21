package test;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import board.Board;
import board.BoardCell;
import board.ClueGame;
import board.ComputerPlayer;
import board.Solution;

public class GameActionTests {

	static ClueGame game;
	static Board board;
	
	
	@BeforeClass
	public static void setUp() {
		game = new ClueGame();
		board = game.getBoard();
		board.loadConfigFiles();
		board.calcAdjacencies();
		game.loadConfigFiles( "" , "" ); //INSERT FILE NAMES HERE
		game.deal();
		
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
		
		player.setLastRoomVisited("Billiard Room");
		
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
 	* Ensures that 	(1) If a player (human or computer) has a card that's suggested, that card is "shown" (i.e.,
 	* 						 returned).
    *			   	(2) If the player has multiple cards that match, the card to be returned is selected randomly.
    * 				(3) Once a player has shown a card, no other players are queried.
    * 				(4) In the board game, disproving a suggestion starts with a player to the left of the person
    * 						making the suggestion. We will modify this somewhat, so that querying the players 
    * 						will happen from a random starting point. For example, on one suggestion player 0 
    * 						might be the first to check for match, followed by players 1, 2, 3 etc. On the next 
    * 						suggestion, player 4 might be the first to check, followed by player 5, human, player
    * 						 0, etc.
    * 				(5) The player making the suggestion should not be queried.
    * 				(6) If none of the other players has any relevant cards, the error value (null) is returned.
 	************************************************************************************************************/
	@Test
	public void testDisproveSuggestion() {	
	
	}
}
