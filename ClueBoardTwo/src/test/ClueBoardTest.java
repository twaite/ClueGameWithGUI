package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import board.BadConfigFormatException;
import board.Board;
import board.BoardCell;
import board.RoomCell;
import board.WalkwayCell;


public class ClueBoardTest {
	Board board;
	RoomCell cell;

	@Before
	public void beforeTest() {
		board = new Board("Clue Board.csv", "Legend.txt");
		board.loadConfigFiles();
		board.calcAdjacencies();
		}

	// Check mapping and number of rooms
	@Test
	public void testMapping() {
		Map<Character, String> rooms = board.getRooms();
		Assert.assertEquals(11, rooms.size());
		Assert.assertEquals("Closet", rooms.get('X'));
		Assert.assertEquals("Walkway", rooms.get('W'));
		Assert.assertEquals("Conservatory", rooms.get('C'));
		Assert.assertEquals("Kitchen", rooms.get('K'));
		Assert.assertEquals("Ballroom", rooms.get('B'));
		Assert.assertEquals("Billiard Room", rooms.get('R'));
		Assert.assertEquals("Library", rooms.get('L'));
		Assert.assertEquals("Study", rooms.get('S'));
		Assert.assertEquals("Dining Room", rooms.get('D'));
		Assert.assertEquals("Lounge", rooms.get('O'));
		Assert.assertEquals("Hall", rooms.get('H'));
	}

	// Check room initials
	@Test
	public void testRoomInitial() {
		// Test Kitchen
		cell = (RoomCell) board.getRoomCellAt(0, 0);
		Assert.assertEquals('K', cell.getInitial());
		// Test Conservatory
		cell = (RoomCell) board.getRoomCellAt(2, 8);
		Assert.assertEquals('C', cell.getInitial());
		// Test Hall
		cell = (RoomCell) board.getRoomCellAt(3, 14);
		Assert.assertEquals('H', cell.getInitial());
		// Test Library
		cell = (RoomCell) board.getRoomCellAt(6, 22);
		Assert.assertEquals('L', cell.getInitial());
		// Test Ballroom
		cell = (RoomCell) board.getRoomCellAt(11, 0);
		Assert.assertEquals('B', cell.getInitial());
		// Test Lounge
		cell = (RoomCell) board.getRoomCellAt(15, 22);
		Assert.assertEquals('O', cell.getInitial());
		// Test Billiard Room
		cell = (RoomCell) board.getRoomCellAt(22, 0);
		Assert.assertEquals('R', cell.getInitial());
		// Test Dining Room
		cell = (RoomCell) board.getRoomCellAt(21, 10);
		Assert.assertEquals('D', cell.getInitial());
		// Test Study
		cell = (RoomCell) board.getRoomCellAt(22, 22);
		Assert.assertEquals('S', cell.getInitial());
	}

	// Check number of rooms in board is correct
	@Test
	public void testNumRooms() {
		Assert.assertEquals(529, board.getNumRooms());
	}

	// Check non doors
	@Test
	public void testNonDoors() {
		// test upper left corner
		cell = (RoomCell) board.getRoomCellAt(0, 0);
		Assert.assertEquals(false, cell.isDoorway());
		// test bottom right corner
		cell = (RoomCell) board.getRoomCellAt(22, 22);
		Assert.assertEquals(false, cell.isDoorway());
		// test top edge
		cell = (RoomCell) board.getRoomCellAt(0, 10);
		Assert.assertEquals(false, cell.isDoorway());
		// test left edge
		cell = (RoomCell) board.getRoomCellAt(18, 0);
		Assert.assertEquals(false, cell.isDoorway());
		// test right edge
		cell = (RoomCell) board.getRoomCellAt(7, 22);
		Assert.assertEquals(false, cell.isDoorway());
		// test bottom edge
		cell = (RoomCell) board.getRoomCellAt(22, 10);
		Assert.assertEquals(false, cell.isDoorway());
		// test center cells
		cell = (RoomCell) board.getRoomCellAt(12, 12);
		Assert.assertEquals(false, cell.isDoorway());
		cell = (RoomCell) board.getRoomCellAt(10, 3);
		Assert.assertEquals(false, cell.isDoorway());
		cell = (RoomCell) board.getRoomCellAt(3, 10);
		Assert.assertEquals(false, cell.isDoorway());
	}

	// Test door direction
	@Test
	public void testDoorDirection() {
		// Kitchen door
		cell = (RoomCell) board.getRoomCellAt(3, 4);
		Assert.assertEquals(true, cell.isDoorway());
		Assert.assertEquals(RoomCell.DoorDirection.DOWN, cell.getDoorDirection());
		// Conservatory door
		cell = (RoomCell) board.getRoomCellAt(1, 10);
		Assert.assertEquals(true, cell.isDoorway());
		Assert.assertEquals(RoomCell.DoorDirection.RIGHT, cell.getDoorDirection());
		// Hall door
		cell = (RoomCell) board.getRoomCellAt(4, 13);
		Assert.assertEquals(true, cell.isDoorway());
		Assert.assertEquals(RoomCell.DoorDirection.DOWN, cell.getDoorDirection());
		// Library doors
		cell = (RoomCell) board.getRoomCellAt(6, 19);
		Assert.assertEquals(true, cell.isDoorway());
		Assert.assertEquals(RoomCell.DoorDirection.LEFT, cell.getDoorDirection());
		cell = (RoomCell) board.getRoomCellAt(7, 19);
		Assert.assertEquals(true, cell.isDoorway());
		Assert.assertEquals(RoomCell.DoorDirection.LEFT, cell.getDoorDirection());
		// Ballroom doors
		cell = (RoomCell) board.getRoomCellAt(10, 5);
		Assert.assertEquals(true, cell.isDoorway());
		Assert.assertEquals(RoomCell.DoorDirection.RIGHT, cell.getDoorDirection());
		cell = (RoomCell) board.getRoomCellAt(11, 5);
		Assert.assertEquals(true, cell.isDoorway());
		Assert.assertEquals(RoomCell.DoorDirection.RIGHT, cell.getDoorDirection());
		cell = (RoomCell) board.getRoomCellAt(9, 5);
		Assert.assertEquals(true, cell.isDoorway());
		Assert.assertEquals(RoomCell.DoorDirection.RIGHT, cell.getDoorDirection());
		// Lounge door
		cell = (RoomCell) board.getRoomCellAt(13, 16);
		Assert.assertEquals(true, cell.isDoorway());
		Assert.assertEquals(RoomCell.DoorDirection.LEFT, cell.getDoorDirection());
		// Billiard room door
		cell = (RoomCell) board.getRoomCellAt(17, 3);
		Assert.assertEquals(true, cell.isDoorway());
		Assert.assertEquals(RoomCell.DoorDirection.UP, cell.getDoorDirection());
		// Dining room doors
		cell = (RoomCell) board.getRoomCellAt(17, 8);
		Assert.assertEquals(true, cell.isDoorway());
		Assert.assertEquals(RoomCell.DoorDirection.LEFT, cell.getDoorDirection());
		cell = (RoomCell) board.getRoomCellAt(18, 8);
		Assert.assertEquals(true, cell.isDoorway());
		Assert.assertEquals(RoomCell.DoorDirection.LEFT, cell.getDoorDirection());
		// Study door
		cell = (RoomCell) board.getRoomCellAt(18, 19);
		Assert.assertEquals(true, cell.isDoorway());
		Assert.assertEquals(RoomCell.DoorDirection.UP, cell.getDoorDirection());
	}

	// Test calcIndex
	@Test
	public void testCalcIndex() {
		Assert.assertEquals(0, board.calcIndex(0, 0));
		Assert.assertEquals(528, board.calcIndex(22, 22));
		Assert.assertEquals(396, board.calcIndex(17, 5));
		Assert.assertEquals(272, board.calcIndex(11, 19));
		Assert.assertEquals(461, board.calcIndex(20, 1));
	}

	// Test exception tossing with Legend file
	@Test(expected = BadConfigFormatException.class)
	public void testExceptionLegend() throws BadConfigFormatException, FileNotFoundException {
		board = new Board("Clue Board.csv", "BadLegend.txt");
		board.loadRoomConfig();
	}
	
	// Test exception tossing with Board file
	@Test(expected = BadConfigFormatException.class)
	public void testExceptionBoard() throws BadConfigFormatException, FileNotFoundException {
		board = new Board("BadBoard.csv", "Legend.txt");
		board.loadBoardConfig();
	}
	
	//Test walkways and edge cases
	//These tests are DARK PURPLE in planning spreadsheet
	@Test
	public void testWalkwayAdjacent(){
		//Location 145
		LinkedList<Integer> testList = board.getAdjList(145);
		Assert.assertTrue(testList.contains(144));
		Assert.assertTrue(testList.contains(146));
		Assert.assertTrue(testList.contains(122));
		Assert.assertTrue(testList.contains(168));
		Assert.assertEquals(4, testList.size());
		
		//Location 92
		testList = board.getAdjList(92);
		Assert.assertTrue(testList.contains(115));
		Assert.assertEquals(1, testList.size());
		
		//Location 129
		testList = board.getAdjList(129);
		Assert.assertTrue(testList.contains(128));
		Assert.assertTrue(testList.contains(152));
		Assert.assertEquals(2, testList.size());
		
		//Location 41
		testList = board.getAdjList(41);
		Assert.assertTrue(testList.contains(40));
		Assert.assertTrue(testList.contains(64));
		Assert.assertTrue(testList.contains(18));
		Assert.assertEquals(3, testList.size());
		
		//Location 413
		testList = board.getAdjList(413);
		Assert.assertTrue(testList.contains(412));
		Assert.assertTrue(testList.contains(390));
		Assert.assertEquals(2, testList.size());
		
		//Location 521
		testList = board.getAdjList(521);
		Assert.assertTrue(testList.contains(520));
		Assert.assertTrue(testList.contains(498));
		Assert.assertEquals(2, testList.size());
	}
	
	//Test Locations beside a room not a doorway
	//These tests are RED in planning spreadsheet
	@Test
	public void testNotDoorwayLocation(){
		//Location 328
		LinkedList<Integer> testList = board.getAdjList(328);
		Assert.assertTrue(testList.contains(329));
		Assert.assertTrue(testList.contains(305));
		Assert.assertTrue(testList.contains(351));
		Assert.assertEquals(3, testList.size());
		
		//Location 222
		testList = board.getAdjList(222);
		Assert.assertTrue(testList.contains(221));
		Assert.assertTrue(testList.contains(199));
		Assert.assertTrue(testList.contains(245));
		Assert.assertEquals(3, testList.size());
	}
	
	//Test Locations Adjacent to a doorway
	//These tests are GREEN in planning spreadsheet
	@Test
	public void testDoorwayAdjacent(){
		
		//Location 410
		LinkedList<Integer> testList = board.getAdjList(410);
		Assert.assertTrue(testList.contains(411));
		Assert.assertTrue(testList.contains(409));
		Assert.assertTrue(testList.contains(387));
		Assert.assertTrue(testList.contains(433));
		Assert.assertEquals(4, testList.size());
		
		//Location 213
		testList = board.getAdjList(213);
		Assert.assertTrue(testList.contains(212));
		Assert.assertTrue(testList.contains(214));
		Assert.assertTrue(testList.contains(190));
		Assert.assertTrue(testList.contains(236));
		Assert.assertEquals(4, testList.size());
		
		//Location 156
		testList = board.getAdjList(156);
		Assert.assertTrue(testList.contains(155));
		Assert.assertTrue(testList.contains(157));
		Assert.assertTrue(testList.contains(133));
		Assert.assertTrue(testList.contains(179));
		Assert.assertEquals(4, testList.size());
		
		//Location 128
		testList = board.getAdjList(128);
		Assert.assertTrue(testList.contains(127));
		Assert.assertTrue(testList.contains(129));
		Assert.assertTrue(testList.contains(105));
		Assert.assertTrue(testList.contains(151));
		
		//Location 96
		testList = board.getAdjList(96);
		Assert.assertTrue(testList.contains(97));
		Assert.assertTrue(testList.contains(73));
		Assert.assertTrue(testList.contains(119));
		Assert.assertEquals(3, testList.size());
	}
	
	//Test Doorways
	//These tests are YELLOW in planning spreadsheet
	public void testDoorways(){
		//Location 105
		LinkedList<Integer> testList = board.getAdjList(105);
		Assert.assertTrue(testList.contains(128));
		Assert.assertEquals(1, testList.size());
		
		//Location 180
		testList = board.getAdjList(180);
		Assert.assertTrue(testList.contains(179));
		Assert.assertEquals(1, testList.size());
		
		//Location 235
		testList = board.getAdjList(235);
		Assert.assertTrue(testList.contains(236));
		Assert.assertEquals(1, testList.size());
		
		//Location 394
		testList = board.getAdjList(394);
		Assert.assertTrue(testList.contains(371));
		Assert.assertEquals(1, testList.size());
	}
	
	//TestTargets Walkways
	//These tests are ORANGE in planning spreadsheet
	@Test
	public void testTargetWalkways(){
		//Location Row 16 Column 1
		board.startTargets(board.calcIndex(16, 1), 1);
		HashSet<BoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(370)));
		Assert.assertTrue(targets.contains(board.getCellAt(368)));
		Assert.assertTrue(targets.contains(board.getCellAt(346)));
		
		//Location Row 14 Column 13
		board.startTargets(board.calcIndex(14, 13), 3);
		targets= board.getTargets();
		Assert.assertEquals(9, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(336)));
		Assert.assertTrue(targets.contains(board.getCellAt(334)));
		Assert.assertTrue(targets.contains(board.getCellAt(332)));
		Assert.assertTrue(targets.contains(board.getCellAt(312)));
		Assert.assertTrue(targets.contains(board.getCellAt(314)));
		Assert.assertTrue(targets.contains(board.getCellAt(310)));
		Assert.assertTrue(targets.contains(board.getCellAt(290)));
		Assert.assertTrue(targets.contains(board.getCellAt(360)));
		Assert.assertTrue(targets.contains(board.getCellAt(382)));
		
		//AFTER WE FIX THIS WALKWAYS WILL WORK THINKS THERE SHOULD BE 6 PLACES I CAN ONLY FIND 4
		//Location Row 22 Column 14
		board.startTargets(board.calcIndex(22, 14), 6);
		targets= board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(452)));
		Assert.assertTrue(targets.contains(board.getCellAt(406)));
		Assert.assertTrue(targets.contains(board.getCellAt(428)));
		Assert.assertTrue(targets.contains(board.getCellAt(382)));
		Assert.assertTrue(targets.contains(board.getCellAt(474)));
		Assert.assertTrue(targets.contains(board.getCellAt(498)));
		
		//Location Row 10 Column 19
		board.startTargets(board.calcIndex(10, 19), 2);
		targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(251)));
		Assert.assertTrue(targets.contains(board.getCellAt(247)));
		Assert.assertTrue(targets.contains(board.getCellAt(273)));
		Assert.assertTrue(targets.contains(board.getCellAt(271)));
	}

	
	//TestTargets Leaving rooms
	//These tests are ORANGE in planning spreadsheet
	@Test
	public void testTargetsExit(){
		//Location Row 3 Column 4
		board.startTargets(board.calcIndex(3, 4), 1);
		HashSet<BoardCell> targets = board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(96)));
		
		//Location Row 18 Column 8
		board.startTargets(board.calcIndex(18, 8), 4);
		targets= board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(352)));
		Assert.assertTrue(targets.contains(board.getCellAt(374)));
		Assert.assertTrue(targets.contains(board.getCellAt(396)));
		Assert.assertTrue(targets.contains(board.getCellAt(398)));
		Assert.assertTrue(targets.contains(board.getCellAt(420)));
		Assert.assertTrue(targets.contains(board.getCellAt(442)));
		Assert.assertTrue(targets.contains(board.getCellAt(466)));
	}
	
	//TestTargets Entering rooms
	//These tests are ORANGE in planning spreadsheet
	@Test
	public void testTargetsEnter(){
		//AFTER WE FIX THIS FIRST ONE ALL THE TEST WILL PASS DOES NOT RECGONIZE THE DOOR AS AN OPTION
		//Location Row 1 Column 12
		board.startTargets(board.calcIndex(1, 12), 3);
		Set targets= board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(34)));
		Assert.assertTrue(targets.contains(board.getCellAt(33)));
		Assert.assertTrue(targets.contains(board.getCellAt(58)));
		Assert.assertTrue(targets.contains(board.getCellAt(80)));
		Assert.assertTrue(targets.contains(board.getCellAt(104)));
		Assert.assertTrue(targets.contains(board.getCellAt(12)));
		
		//Location Row 11 Column 6
		board.startTargets(board.calcIndex(11, 6), 1);
		targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(260)));
		Assert.assertTrue(targets.contains(board.getCellAt(258)));
		Assert.assertTrue(targets.contains(board.getCellAt(236)));
		Assert.assertTrue(targets.contains(board.getCellAt(282)));
		
	}

}
