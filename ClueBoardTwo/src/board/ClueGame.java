package board;

import java.awt.Color;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;


public class ClueGame {

	Solution solution = new Solution();
	Player humanPlayer = new HumanPlayer();
	ArrayList<Card> cards = new ArrayList<Card>();
	ArrayList<Player> players = new ArrayList<Player>();
	Board board = new Board();
	int turnIndicator;
	
	/************************************************************************************************************
 	* Default constructor
 	************************************************************************************************************/
	public ClueGame() {
		loadConfigFiles("people.txt", "cards.txt");
	}
	
	/************************************************************************************************************
 	* Default constructor
 	************************************************************************************************************/
	public ClueGame(String peopleFileName, String cardFileName) {
		loadConfigFiles(peopleFileName, cardFileName);
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
	 * @throws BadConfigFormatException 
 	************************************************************************************************************/
	public void loadConfigFiles( String peopleFileName, String cardFileName ) {
		try {
			loadPeopleFile( peopleFileName );
			loadCardFile( cardFileName );
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/************************************************************************************************************
 	* Parameter: String peopleFileName
 	* 
 	* 	Opens and extracts the people info from the given people file name.
 	************************************************************************************************************/
	public void loadPeopleFile( String peopleFileName ) throws FileNotFoundException {
		FileReader reader = new FileReader(peopleFileName);
		Scanner input = new Scanner(reader);
		String line, name, color;
		Point p;
		String[] parts;
		int playerCount = 0;
		
		while (input.hasNextLine()) {
			line = input.nextLine();
			parts = line.split(", ");
			name = parts[0];
			System.out.println(name);
			color = parts[1];
			convertColor( color );
			p = new Point( Integer.parseInt(parts[2]), Integer.parseInt(parts[3]) );
			if ( playerCount == 0 ) {
				humanPlayer = new HumanPlayer(name, color, p);
				players.add(humanPlayer);
			} else {
				players.add(new ComputerPlayer(name, color, p));
			}
			playerCount++;
		}

	}
	
	public Color convertColor( String strColor ) {
		Color color;
		try {
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color) field.get(null);
		} catch ( Exception e ) {
			color = null; // Not defined
		}
		return color;
	}
	
	/************************************************************************************************************
 	* Parameter: String cardFileName
 	* 
 	* 	Opens and extracts the card info from the given card file name.
 	************************************************************************************************************/
	public void loadCardFile( String cardFileName ) throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(cardFileName);
		Scanner input = new Scanner(reader);
		String line;
		String[] parts;
		Card newCard;
		int numOfPeople, numOfWeps, numOfRooms;
		
		if (input.hasNextLine()) {
			 line = input.nextLine();
			 parts = line.split(", ");
			 numOfPeople = Integer.parseInt( parts[0] );
			 numOfWeps = Integer.parseInt( parts[1] );
			 numOfRooms = Integer.parseInt( parts[2] );
		} else {
			throw new BadConfigFormatException( "Card file is empty" );
		}
		
		while (numOfPeople != 0) {
			if (input.hasNextLine()) {
				//START HERE AGAIN YOU PIECES OF POO. GOOD LUCK BTW FUTURE TEAM
			} else {
				throw new BadConfigFormatException( "Specified number of lines in Card file is invalid" );
			}
		}
		
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
	public Card handleSuggestion( String person, String weapon, String room, Player accusingPlayer ) {
		return new Card();
	}
	
	/************************************************************************************************************
 	* Parameter: Solution solution
 	* 
 	* 	
 	************************************************************************************************************/
	public boolean checkAccusation( Solution solution ) {
		if ( (solution.getPerson() != this.solution.getPerson()) || 
				(solution.getWeapon() != this.solution.getWeapon()) ||
						(solution.getRoom() != this.solution.getRoom()) ) {
			return false;
		} else {
			return true;
		}
	}
	
	
	public Player getHumanPlayer() {
		return humanPlayer;
	}
	
	public ArrayList<Card> getCardList() {
		return cards;
	}
	
	public ArrayList<Player> getPlayerList() {
		return players;
	}
	
	
	//For Test purposes
	public void setSolution( Solution newSolution ) {
		//this.solution = newSolution;
		this.solution.setPerson(newSolution.getPerson());
		this.solution.setWeapon(newSolution.getWeapon());
		this.solution.setRoom(newSolution.getRoom());
	}
	
	public Board getBoard() {
		return board;
	}
}
