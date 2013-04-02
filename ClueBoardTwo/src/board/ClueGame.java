package board;

import java.awt.Color;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;


public class ClueGame {

	private Solution solution = new Solution();
	private Player humanPlayer = new HumanPlayer();
	private ArrayList<Card> cards = new ArrayList<Card>();
	private ArrayList<Card> cardsDealt = new ArrayList<Card>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private Board board;
	private int turnIndicator;
	
	/************************************************************************************************************
 	* Default constructor
 	************************************************************************************************************/
	public ClueGame() {
		loadConfigFiles("people.txt", "cards.txt");
		board = new Board("Clue Board.csv", "Legend.txt");
		board.loadConfigFiles();
		board.calcAdjacencies();
		board.setPlayers(players);
	}
	
	/************************************************************************************************************
 	* Parameterized constructor
 	************************************************************************************************************/
	public ClueGame(String peopleFileName, String cardFileName) {
		loadConfigFiles(peopleFileName, cardFileName);
		board = new Board("Clue Board.csv", "Legend.txt");
		board.loadConfigFiles();
		board.calcAdjacencies();
		board.setPlayers(players);
	}
	
	/************************************************************************************************************
 	* Distributes cards among the players as evenly as possible (maximum difference between the number of cards
 	* 	of two players is one.
 	************************************************************************************************************/
	public void deal() {
		
		int numCards = cards.size() - 3;
		int rand;
		ArrayList<Card> cardArray = new ArrayList<Card>(cards);
		boolean foundPlayer = false;
		boolean foundWeapon = false;
		boolean foundRoom = false;
		
		String[] solutionFields = new String[3];
		Card randCard = new Card();
		
		while (!foundPlayer || !foundWeapon || !foundRoom) {
			if (!foundPlayer) {
				rand = (int) ( Math.random() * cardArray.size() );
				randCard = cardArray.get(rand);
				if (randCard.getCardType() == Card.CardType.PERSON) {
					foundPlayer = true;
					solutionFields[0] = randCard.getName();
					cardArray.remove(rand);
				}
			}
			
			if (!foundWeapon) {
				rand = (int) ( Math.random() * cardArray.size() );
				randCard = cardArray.get(rand);
				if (randCard.getCardType() == Card.CardType.WEAPON) {
					foundWeapon = true;
					solutionFields[1] = randCard.getName();
					cardArray.remove(rand);
				}
			}
			
			if (!foundRoom) {
				rand = (int) ( Math.random() * cardArray.size() );
				randCard = cardArray.get(rand);
				if (randCard.getCardType() == Card.CardType.ROOM) {
					foundRoom = true;
					solutionFields[2] = randCard.getName();
					cardArray.remove(rand);
				}
			}
		}
		
		solution = new Solution(solutionFields[0],solutionFields[1],solutionFields[2]);
		
		int numPlayers = players.size() - 1;
	
														
		while ( numCards != 0 ) {
			if (numPlayers == -1) {
				numPlayers = players.size() - 1;
			}
			//Generate rand number to access index
			rand = (int) ( Math.random() * cardArray.size() );
			
			players.get(numPlayers).getCardList().add(cardArray.get(rand));
			
			cardsDealt.add(cardArray.get(rand));
			cardArray.remove(rand);
			
			numPlayers--;
			numCards--;
		}
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
			color = parts[1];
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
				line = input.nextLine();
				newCard = new Card( line, Card.CardType.PERSON);
				cards.add( newCard );
			} else {
				throw new BadConfigFormatException( "Specified number of lines in Card file is invalid" );
			}
			numOfPeople--;
		}
		
		while (numOfWeps != 0) {
			if (input.hasNextLine()) {
				line = input.nextLine();
				newCard = new Card( line, Card.CardType.WEAPON);
				cards.add( newCard );
			} else {
				throw new BadConfigFormatException( "Specified number of lines in Card file is invalid" );
			}
			numOfWeps--;
		}
		
		while (numOfRooms != 0) {
			if (input.hasNextLine()) {
				line = input.nextLine();
				newCard = new Card( line, Card.CardType.ROOM);
				cards.add( newCard );
			} else {
				throw new BadConfigFormatException( "Specified number of lines in Card file is invalid" );
			}
			numOfRooms--;
		}
				
		for (Player p : players) {
			p.setCardsOfGameList(cards);
		}
		//System.out.println( cards );
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
		ArrayList<Player> players2 = new ArrayList<Player>(players);
		players2.remove(accusingPlayer);
		long seed = System.nanoTime();
		Collections.shuffle(players2, new Random(seed));
		//int randIndex;
		//boolean matchFound = false;
		Card matchedCard = new Card();
		
		for ( Player p : players2 ) {
			//randIndex = (int) (Math.random() * players2.size());
			matchedCard = p.disproveSuggestion(person, weapon, room);
			
			if ( matchedCard != null) {
				if (accusingPlayer instanceof ComputerPlayer) {
					((ComputerPlayer) accusingPlayer).updateSeen(matchedCard);
				}
				/////////////////////////////////////////////////////////////////////////
				//MAY NEED TO UPDATE THE HUMAN PLAYER'S SEEN CARDS LIST HERE AS WELL
				//
				/////////////////////////////////////////////////////////////////////////
				return matchedCard;
			} 
//			else {
//				players2.remove(randIndex);
//			}
		}

		
		return null;
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
