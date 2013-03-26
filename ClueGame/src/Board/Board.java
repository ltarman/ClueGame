package Board;

import java.util.*;
import java.io.*;

import Board.BadConfigFormatException.errorType;
import Board.RoomCell.DoorDirection;

public class Board {
	private ArrayList<BoardCell> cells;
	private Map<Character,String> rooms;
	private int numRows;
	private int numColumns;

	private Map<Integer,LinkedList<Integer>> adjMap;
	private Set<Integer> targets;
	private boolean[] visited;

	public Board() {
		this.cells = new ArrayList<BoardCell>();
		this.rooms = new HashMap<Character,String>();
		this.numRows = 0;
		this.numColumns = 0;
		this.targets = new HashSet<Integer>();

		this.adjMap = new HashMap<Integer,LinkedList<Integer>>();
		this.targets = new HashSet<Integer>();
	}

	public void loadConfigFiles() {
		// Given test config filenames
		//String legend = "ClueLegend.txt";
		//String board = "ClueLayout.csv";
		// Default config filenames
		String legend = "Legend.txt";
		String board = "Board.csv";
		
		// Run helper functions to load legend and board separately
		try{
			this.loadLegendConfig(legend);			
			this.loadBoardConfig(board);
		}
		
		// Write to error log if exception is thrown
		catch(FileNotFoundException e) {
			System.out.println(e);
			FileWriter writer = null;
			try {
				writer = new FileWriter("Error Log.txt");
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				writer.write(e.toString());
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		}
		catch(BadConfigFormatException e) {
			System.out.println(e);
			FileWriter writer = null;
			try {
				writer = new FileWriter("Error Log.txt");
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				writer.write(e.toString());
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		}
		catch(IOException e) {
			System.out.println(e);
			FileWriter writer = null;
			try {
				writer = new FileWriter("Error Log.txt");
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				writer.write(e.toString());
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		}
	}

	// Loads legend config file
	public void loadLegendConfig(String legend) throws FileNotFoundException, BadConfigFormatException {
		File infile = new File(legend);
		Scanner scanner = new Scanner(infile);
		while(scanner.hasNextLine()){
			String[] item = scanner.nextLine().split(",");						// Converts row to string array
			if(item.length != 2 || item[0].length() != 1)						// Throw exception is string array does not have exactly 2 items
				throw new BadConfigFormatException(errorType.INVALID_LEGEND);
			item[1] = item[1].trim();											// Remove any leading/trailing whitespace
			rooms.put(item[0].charAt(0),item[1]);								// Add legend item to map
		}
	}

	// Loads board config file
	public void loadBoardConfig(String board) throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(board);
		Scanner scanner = new Scanner(reader);
		this.numRows = 0;
		
		// Create cells array
		for(int i = 0; scanner.hasNextLine(); i++) {
			this.numRows++;															// Find number of rows
			String[] row = scanner.nextLine().split(",");							// Convert current row to array of strings
			if(i == 0)																// Take number of elements in first row as number of columns
				this.numColumns = row.length;
			else
				if(row.length != this.numColumns)									// If a row has the wrong number of columns, throw exception
					throw new BadConfigFormatException(errorType.INVALID_ROWS);
			
			// Convert current row to BoardCells
			for(int j = 0; j < row.length; j++) {
				if(this.rooms.containsKey(row[j].charAt(0))) {											// Check to see if current tile has valid notation (throw an exception if not)
					if(row[j].equalsIgnoreCase("W"))													// Create walkway cell if 'W'
						cells.add(new WalkwayCell(i,j));
					else if(row[j].length() == 2) {														// Create doorway if string has 2 characters
						switch(row[j].charAt(1))
						{
						case 'U': cells.add(new RoomCell(i,j,row[j].charAt(0),DoorDirection.UP));
						break;
						case 'D': cells.add(new RoomCell(i,j,row[j].charAt(0),DoorDirection.DOWN));
						break;
						case 'L': cells.add(new RoomCell(i,j,row[j].charAt(0),DoorDirection.LEFT));
						break;
						case 'R': cells.add(new RoomCell(i,j,row[j].charAt(0),DoorDirection.RIGHT));
						break;
						case 'N': cells.add(new RoomCell(i,j,row[j].charAt(0),DoorDirection.NONE));
						break;
						}
					}
					else
						cells.add(new RoomCell(i,j,row[j].charAt(0),DoorDirection.NONE));				// Create room if not a walkway or a doorway	
				}
				else
					throw new BadConfigFormatException(errorType.INVALID_ROOM);
			}
		}
		// Set 'visited' array
		this.visited = new boolean[numRows*numColumns];
		for(int i = 0; i < visited.length; i++)
			visited[i] = false;
	}

	// Return index if cell is valid
	public int calcIndex(int row, int column) {
		if(row < 0 || column < 0 || row >= numRows || column >= numColumns)
			return -1;
		return (numColumns*row + column);
	}

	// Returns RoomCell at given row/column (ASSUMES CELL HAS ALREADY BEEN CONFIRMED AS A ROOM)
	public RoomCell getRoomCellAt(int row, int column) {
		int index = calcIndex(row, column);
		if(index < 0 || !cells.get(index).isRoom())
			return null;
		return (RoomCell) cells.get(index);
	}

	public ArrayList<BoardCell> getCells() {
		return cells;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public BoardCell getCellAt(int index) {
		return cells.get(index);
	}

	// Part II Methods
	public void calcAdjacencies() {
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				// adj[] holds the four board spaces around the current cell
				int adj[] = new int[4];
				LinkedList<Integer> adjList = new LinkedList<Integer>();
				int index = calcIndex(i,j);
				adj[0] = calcIndex(i+1,j);	// One down
				adj[1] = calcIndex(i-1,j);	// One up
				adj[2] = calcIndex(i,j+1);	// One right
				adj[3] = calcIndex(i,j-1);	// One left
				
				// Adds all valid cells in adj[] to the adjacency list
				// If this cell is a walkway
				if(cells.get(index).isWalkway()) {
					for(int k = 0; k < 4; k++) {
						// If adjacent cell is valid index and a walkway, add
						if(adj[k] >= 0 && adj[k] < numRows*numColumns &&
								(cells.get(adj[k]).isWalkway()))
							adjList.add(adj[k]);
						
						// If adjacent cell is doorway, check to see if direction is correct
						else if(adj[k] >= 0 && adj[k] < numRows*numColumns &&
								(cells.get(adj[k]).isDoorway())) {
							RoomCell door = (RoomCell) cells.get(adj[k]);
							if((door.getDoorDirection() == DoorDirection.UP && k == 0) ||
							   (door.getDoorDirection() == DoorDirection.DOWN && k == 1) ||
							   (door.getDoorDirection() == DoorDirection.LEFT && k == 2 ||
							   (door.getDoorDirection() == DoorDirection.RIGHT && k == 3))) {
								adjList.add(adj[k]);
							}
						}
					}
				}
				// If this cell is a doorway
				if(cells.get(index).isDoorway()) {
					RoomCell door = (RoomCell) cells.get(index);
					// Add only the cell the door opens to
					switch(door.getDoorDirection())
					{
					case UP: adjList.add(adj[1]);
					break;
					case DOWN: adjList.add(adj[0]);
					break;
					case LEFT: adjList.add(adj[3]);
					break;
					case RIGHT: adjList.add(adj[2]);
					break;
					}
				}
				
				adjMap.put(index, adjList);
			}
		}
	}

	public void startTargets(int index, int steps) {
		// Set visited array to false
		for(int i = 0; i < this.visited.length; i++)
			this.visited[i] = false;
		targets.clear();				// Clear target array
		this.visited[index] = true;		// Set starting tile to true
		calcTargets(index, steps);		// Calculate targets
	}
	
	public void calcTargets(int index, int steps) {
		// Gets the adjacency list for the current cell
		// Copies the list and puts it in a new one so we don't modify the original
		LinkedList<Integer> adjCells = new LinkedList<Integer>();
		for (int i = 0; i < getAdjList(index).size(); i++)
			adjCells.add(getAdjList(index).get(i));
		
		// Uses an iterator to remove all visited adjacent cells
		for(ListIterator<Integer> litr = adjCells.listIterator(0); litr.hasNext();) {
			if(visited[litr.next()])
				litr.remove();
		}
		
		// Traversal loop for each unvisited adjacent cell
		for(int i = 0; i < adjCells.size(); i++) {
			
			// Sets the visited flag for target adjacent cell to true
			this.visited[adjCells.get(i)] = true;
			
			// If we are at the end of the n-length path, add target adjacent cell to targets
			if(steps == 1 || cells.get(adjCells.get(i)).isDoorway())
				targets.add(adjCells.get(i));
			
			// Otherwise, recursively call this function for the target adjacent cell
			else
				calcTargets(adjCells.get(i),(steps-1));
			
			// On the way back, set the visited flag back to false so we can use this cell in future paths
			this.visited[adjCells.get(i)] = false;
		}
	}

	public Set<Integer> getTargets() {
		return targets;
	}

	public LinkedList<Integer> getAdjList(int index) {
		return adjMap.get(index);
	}
}
