package test;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import board.ClueGame;
import board.Solution;

public class GameActionTests {

	static ClueGame game;
	
	@BeforeClass
	public static void setUp() {
		game = new ClueGame();
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
 	* 
 	************************************************************************************************************/
	@Test
	public void testSelectingATargetLocation() {
		
	}
	
}
