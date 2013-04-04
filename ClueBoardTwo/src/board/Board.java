package board;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JPanel;

//Naomi and Brandon
public class Board extends JPanel{
	public static final int ROWS = 23;
	public static final int COLS = 23;
	public static final int ROOMS = 11;
	private ArrayList<BoardCell> cells;
	private Map<Character, String> rooms;
	private HashSet<BoardCell> targets;
	private Map<Integer, LinkedList<Integer>> adjMtx;
	private int numRooms;
	private int numRows;
	private int numColumns;
	//Defaults to Dr. Rader's config files
	private String legend;
	private String board;
	private boolean[] visited;
	ArrayList<Player> players;
	
	public Board() {
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character, String>();
		targets = new HashSet<BoardCell>();
		adjMtx = new HashMap<Integer, LinkedList<Integer>>();
		legend = "ClueLegend.txt";
		board = "ClueLayout.csv";
		visited = new boolean[ROWS * COLS];
	}
	
	public Board(String board, String legend ) {
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character, String>();
		targets = new HashSet<BoardCell>();
		adjMtx = new HashMap<Integer, LinkedList<Integer>>();
		this.board = board;
		this.legend = legend;
		visited = new boolean[ROWS * COLS];
		}
	
	public void loadConfigFiles() {
		try {
			this.loadRoomConfig();
			this.loadBoardConfig();
		}
		catch (BadConfigFormatException e) {
			System.out.println(e);
		}
		catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}
	
	//Load Legend file
	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader legendr = new FileReader(legend);
		Scanner input = new Scanner(legendr);

		int lineCount = 0;
		while (input.hasNextLine()) {
			String line = input.nextLine();
			String[] parts = line.split(",");
			if (parts.length > 2) {
				throw new BadConfigFormatException("Legend file row contains too many columns");
			}
			if (parts.length == 1 ) {
				throw new BadConfigFormatException( "Legend file row contains blank column" );
			}
			else {
				char initial = parts[0].charAt(0);
				String room = parts[1];
				room = room.substring(1, room.length());
				rooms.put(initial, room);	
				}
			lineCount++;
		}
		if (lineCount != ROOMS) {
			throw new BadConfigFormatException( "Legend File doesn't contain the correct number of rooms" );
		}
	}
	
	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {
		BoardCell newCell;
		FileReader boardr = new FileReader(board);
		Scanner input = new Scanner(boardr);
			while(input.hasNextLine()) {
				String line = input.nextLine();
				String[] parts = line.split(",");
				if(numRows == 0)
					numColumns = parts.length;
				else
					if(parts.length != numColumns) {
						throw new BadConfigFormatException("Too few columns in board file");
					}
				for(int j = 0; j < numColumns; ++j) {
					if(parts[j].charAt(0) == 'X' || parts[j].charAt(0) == 'W' || parts[j].charAt(0) == 'C' || parts[j].charAt(0) == 'K' || parts[j].charAt(0) == 'B' || parts[j].charAt(0) == 'R'
							|| parts[j].charAt(0) == 'L' || parts[j].charAt(0) == 'S' || parts[j].charAt(0) == 'D' || parts[j].charAt(0) == 'O' || parts[j].charAt(0) == 'H') {
						if(parts[j].length() == 1 && parts[j].charAt(0) == 'W') {
							newCell = new WalkwayCell(numRows,j);
							cells.add(newCell);
						}
						else if(parts[j].length() == 1) {
							newCell = new RoomCell(numRows,j,parts[j].charAt(0));
							cells.add(newCell);
						}
						else {
							newCell = new RoomCell(numRows,j,parts[j].charAt(0),parts[j].charAt(1));
							cells.add(newCell);
						}
					} else {
						throw new BadConfigFormatException( "Config File contains invalid Room Initial" );
					}
				}
				numRows++;
		}
		numRooms = cells.size();
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public int calcIndex(int row, int column){
		return (numColumns*row) + column;
	}
	
	public RoomCell getRoomCellAt(int row, int column){
		if(cells.get(calcIndex(row,column)).isRoom())
			return (RoomCell) cells.get(calcIndex(row,column));
		else
			return null;
	}
	
	public BoardCell getCellAt(int location) {
		return cells.get(location);
	}

	public BoardCell getCellAt(int row, int column) {
		return cells.get(calcIndex(row,column));
	}
	
	public ArrayList<BoardCell> getCells() {
		return cells;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}

	public int getNumRooms() {
		return numRooms;
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	//calcTargets with location
	public void calcTargets(int location, int steps) {
		LinkedList<BoardCell> adjs = new LinkedList<BoardCell>();
		visited[location] = true;
		for( Integer i : getAdjList(location) ) {
			if(!visited[i])
				adjs.add(cells.get(i));
		}
		for(BoardCell adjCell : adjs) {
			boolean exited = false;
			for(int i = 0; i < numRooms; i++) {
				if(getCellAt(i).isDoorway() && visited[i])
					exited = true;
			}
			visited[calcIndex(adjCell.getRow(), adjCell.getColumn())] = true;
			if(calcIndex(adjCell.getRow(), adjCell.getColumn()) < numRooms && calcIndex(adjCell.getRow(), adjCell.getColumn()) > 0) {
				if(steps == 1) 
					targets.add(adjCell);
				else if (getCellAt(calcIndex(adjCell.getRow(), adjCell.getColumn())).isDoorway() && !exited)
					targets.add(adjCell);
				else
					calcTargets(calcIndex(adjCell.getRow(), adjCell.getColumn()), steps - 1);
			visited[calcIndex(adjCell.getRow(), adjCell.getColumn())] = false;
			}
		}
	}
	
	//calcTargets with coordinates
	public void calcTargets(int row, int column, int steps) {
		targets = new HashSet<BoardCell>();
		int location = calcIndex(row,column);
		startTargets(location,steps);
	}
	
	public void calcAdjacencies(){
		visited = new boolean[numRooms];
		LinkedList<Integer> adjs;
		for(int row = 0; row < numRows; row++) {
			for(int column = 0; column < numColumns; column++) {
				adjs = new LinkedList<Integer>();
				visited[calcIndex(row,column)] = true;
				//room exit
				if(getCellAt(calcIndex(row,column)).isDoorway()) {
					RoomCell.DoorDirection direction = getRoomCellAt(row, column).getDoorDirection();
					switch(direction) {
					case DOWN :
						adjs.add(calcIndex(row+1,column));
						adjMtx.put(calcIndex(row,column), adjs);
						break;
					case UP :
						adjs.add(calcIndex(row-1,column));
						adjMtx.put(calcIndex(row,column), adjs);
						break;
					case LEFT :
						adjs.add(calcIndex(row,column-1));
						adjMtx.put(calcIndex(row,column), adjs);
						break;
					case RIGHT :
						adjs.add(calcIndex(row,column+1));
						adjMtx.put(calcIndex(row,column), adjs);
						break;
					default :
						adjMtx.put(calcIndex(row,column), adjs);
						break;
					}
				}	
				//room cell
				else if(getCellAt(calcIndex(row,column)).isRoom())
					adjMtx.put(calcIndex(row,column),adjs);
				//walkway
				else {
					//check down
					if(row < numRows - 2 && checkAdjacency(row + 1, column) && calcIndex(row + 1, column) < numRooms)
						adjs.add(calcIndex(row + 1,column));
					//check up
					if(row > 0 && checkAdjacency(row - 1, column))
						adjs.add(calcIndex(row - 1,column));
					//check left
					if(column > 0 && checkAdjacency(row, column - 1))
						adjs.add(calcIndex(row,column - 1));
					//check right
					if(column < numColumns - 1 && checkAdjacency(row, column + 1) && calcIndex(row, column + 1) < numRooms)
						adjs.add(calcIndex(row,column + 1));
					visited[calcIndex(row,column)] = false;
					adjMtx.put(calcIndex(row,column),adjs);
				}
			}
		}
	}
	
	//Checks if the adjacent cell is a walkway or a door you can enter
	public boolean checkAdjacency(int row, int column) {
		int location = calcIndex(row,column);
		if(location == numRooms)
			return false;
		else if(cells.get(location).isWalkway())
			return true;
		else if (cells.get(location).isDoorway()) {
			RoomCell test = (RoomCell) cells.get(location);
			RoomCell.DoorDirection direction = test.getDoorDirection();
			switch (direction) {
			case DOWN :
				if(visited[calcIndex(row + 1,column)])
					return true;
				else
					return false;
			case UP :
				if(visited[calcIndex(row - 1,column)])
					return true;
				else
					return false;
			case RIGHT :
				if(visited[calcIndex(row,column + 1)])
					return true;
				else
					return false;
			case LEFT :
				if(visited[calcIndex(row,column - 1)])
					return true;
				else
					return false;
			default :
				return false;
			}
			
		}
		else
			return false;
	}
	
	public HashSet<BoardCell> getTargets(){
		return targets;	
	}
	
	public LinkedList<Integer> getAdjList(int location){
		return adjMtx.get(location);
	}
	
	public void startTargets(int location, int steps){
		//empty targets and set visited to false just in case
		targets = new HashSet<BoardCell>();
		
		
		Arrays.fill(visited, false);
		//set start location to true
		visited[location] = true;
		calcTargets(location,steps);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for ( BoardCell cell : cells ) {
			cell.draw(g, this);
		}
		
		for ( Player p : players ) {
			p.draw(g, this);
		}
	}
}
