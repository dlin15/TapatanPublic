package tapatan.a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


/* The game of tapatan */
public class tapatanBoard {
	
	/* 
	*  0 1 2
	*  3 4 5
	*  6 7 8 
	*/
	int[] board;
	
	// true is player 1
	// false is player 2
	boolean turn;
	
	// A map from each postion to its adjacent positions
	private HashMap<Integer,int[]> adjacentPositions;
	
	// A set of values that represents winning position sums
	private HashSet<Integer> winValues;
	
	/*
	*  1  2   4
	*  8  16  32
	*  64 128 256
	*/
	private HashMap<Integer, Integer> winMap;
	
	// Position sums for each player
	private int p1,p2;
	
	// Win state of the game
	// 0 --> no win
	// 1 --> player 1 won
	// 2 --> player 2 won
	private int win;
	
	// Initialize values
	public tapatanBoard(){
		turn = true;
		board = new int[9];
		p1 = 0;
		p2 = 0;
		adjacentPositionSetUp();
		winValueSetUp();
		winMapSetUp();
		win = 0;
	}
	
	// Moves the current player's piece from the given source to destination positions.
	// Assumed destination must be valid because of adjacent positions
	// Param: source postision, destination position
	public void move(int src, int dest){
		// Remove the piece from the source postition
		board[src] = 0;
		if(turn){
			p1 -= winMap.get(src);
		}else{
			p2 -= winMap.get(src);
		}
		// Place the piece in the destination location
		place(dest);
	}

	public boolean validPlace(int position){
		if(board[position] == 0){
			return true;
		}
		return false;
	}
	
	// Places the current player's piece on the board at the given position.
	// Param: destination postision
	public boolean place(int dest){

		if(!validPlace(dest)){
			return false;
		}

		// Player 1's turn
		if(turn){
			// add winMap value at destination postision to Player 2's position sum
			p1 += winMap.get(dest);
			// Places player 2's piece in destination postision
			board[dest] = 1;
			// Check for win
			if(winValues.contains(p1)){
				win = 1; //player one wins
			}
		// Player 2's turn
		}else{
			// add winMap value at destination postision to Player 2's position sum
			p2 += winMap.get(dest);
			// Places player 2's piece in destination postision
			board[dest] = 2;
			// Check for win
			if(winValues.contains(p2)){
				win = 2; //player two wins
			}
		}
		// Switch players
		turn = !turn;
		return true;
	}
	
	// Lists the adjecent positions to the current position
	// Param: postion on the board
	// Return: List of adjacent positions on the board
	public List<Integer> adjacentPositions(int position){
		int[] adjacent = adjacentPositions.get(position);
		// create list
		List<Integer> valid = new ArrayList<Integer>();
		for(int i:adjacent){
			// only add empty postions
			if(board[i] == 0){
				valid.add(i);
			}
		}
		return valid;
	}
	
	
	// Returns the win state of the game
	public int checkWin(){
		return win;
	}
	
	// Returns which player's turn it is
	public boolean getTurn(){
		return turn;
	}
	
	// Create winMap
	private void winMapSetUp(){
		winMap = new HashMap<Integer,Integer>();
		winMap.put(0,1);
		winMap.put(1,2);
		winMap.put(2,4);
		winMap.put(3,8);
		winMap.put(4,16);
		winMap.put(5,32);
		winMap.put(6,64);
		winMap.put(7,128);
		winMap.put(8,256);
	}
	
	// Create winningSets
	// winningSets represents the values where a player wins
	private void winValueSetUp(){
		winValues = new HashSet<Integer>();
		winValues.add(7);
		winValues.add(56);
		winValues.add(73);
		winValues.add(84);
		winValues.add(146);
		winValues.add(273);
		winValues.add(292);
		winValues.add(448);
	}
	
	// Create the set of adjacent postions
	private void adjacentPositionSetUp(){
		adjacentPositions = new HashMap<Integer, int[]>();
		adjacentPositions.put(0, new int[]{1,3,4});
		adjacentPositions.put(1, new int[]{0,2,4});
		adjacentPositions.put(2, new int[]{1,4,5});
		adjacentPositions.put(3, new int[]{0,4,6});
		adjacentPositions.put(4, new int[]{0,1,2,3,5,6,7,8});
		adjacentPositions.put(5, new int[]{2,4,8});
		adjacentPositions.put(6, new int[]{3,4,7});
		adjacentPositions.put(7, new int[]{4,6,8});
		adjacentPositions.put(8, new int[]{4,5,7});
	}
	
}
