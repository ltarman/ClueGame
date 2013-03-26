package boardTests;

import java.io.FileNotFoundException;

import java.io.IOException;

import org.junit.*;

import junit.framework.Assert;

import Board.*;
import Board.RoomCell.*;

public class BoardTest {
	private Board board;
	
	@Before
	public void setup() {
		board = new Board();
		board.loadConfigFiles();
	}
	
	@Test
	public void testRoomsMap() {
		Assert.assertEquals(board.getRooms().size(), 11);
		Assert.assertEquals(board.getRooms().get('K'), "Kitchen");
		Assert.assertEquals(board.getRooms().get('D'), "Dining Room");
		Assert.assertEquals(board.getRooms().get('V'), "Living Room");
		Assert.assertEquals(board.getRooms().get('B'), "Ballroom");
		Assert.assertEquals(board.getRooms().get('R'), "Library");
		Assert.assertEquals(board.getRooms().get('S'), "Study");
		Assert.assertEquals(board.getRooms().get('T'), "Bathroom");
		Assert.assertEquals(board.getRooms().get('Y'), "Lobby");
		Assert.assertEquals(board.getRooms().get('E'), "Bedroom");
		Assert.assertEquals(board.getRooms().get('X'), "Closet");
		Assert.assertEquals(board.getRooms().get('W'), "Walkway");
	}
	
	@Test
	public void testBoard() {
		Assert.assertEquals(board.getNumRows(), 25);
		Assert.assertEquals(board.getNumColumns(), 25);
	}
	
	@Test
	public void testRooms() {
		RoomCell testRoom = board.getRoomCellAt(2, 2);
		Assert.assertEquals(testRoom.getInitial(),'K');
		testRoom = board.getRoomCellAt(4, 12);
		Assert.assertEquals(testRoom.getInitial(),'D');
		testRoom = board.getRoomCellAt(12, 5);
		Assert.assertEquals(testRoom.getInitial(),'B');
		testRoom = board.getRoomCellAt(22, 3);
		Assert.assertEquals(testRoom.getInitial(),'V');
		testRoom = board.getRoomCellAt(21, 13);
		Assert.assertEquals(testRoom.getInitial(),'Y');
		testRoom = board.getRoomCellAt(1, 8);
		Assert.assertEquals(testRoom.getInitial(),'T');
		testRoom = board.getRoomCellAt(2, 22);
		Assert.assertEquals(testRoom.getInitial(),'S');
		testRoom = board.getRoomCellAt(9, 20);
		Assert.assertEquals(testRoom.getInitial(),'R');
		testRoom = board.getRoomCellAt(18, 22);
		Assert.assertEquals(testRoom.getInitial(),'E');
	}
	
	@Test
	public void testDoors() {
		RoomCell testRoom = board.getRoomCellAt(4,2);
		Assert.assertEquals(testRoom.getDoorDirection(),DoorDirection.DOWN);
		Assert.assertEquals(testRoom.isDoorway(),true);
		testRoom = board.getRoomCellAt(20,3);
		Assert.assertEquals(testRoom.getDoorDirection(),DoorDirection.UP);
		testRoom = board.getRoomCellAt(9,16);
		Assert.assertEquals(testRoom.getDoorDirection(),DoorDirection.LEFT);
		testRoom = board.getRoomCellAt(13,6);
		Assert.assertEquals(testRoom.getDoorDirection(),DoorDirection.RIGHT);
		
		final int DOORS = 14;
		int numDoors = 0;
		for(int i = 0; i < board.getCells().size(); i++) {
			if(board.getCells().get(i).isDoorway())
				numDoors++;
			}
		Assert.assertEquals(numDoors,DOORS);
	}
	
	@Test
	public void testCalcIndex() {
		Assert.assertEquals(board.calcIndex(0,0),0);
		Assert.assertEquals(board.calcIndex(0,11),11);
		Assert.assertEquals(board.calcIndex(18,0),450);
		Assert.assertEquals(board.calcIndex(14,13),363);
		Assert.assertEquals(board.calcIndex(24,24),624);
		Assert.assertEquals(board.calcIndex(26,24),-1);
		Assert.assertEquals(board.calcIndex(-2,14),-1);
	}
	
	@Test(expected = BadConfigFormatException.class)
	public void testBadConfigFormatException1() throws BadConfigFormatException, FileNotFoundException {
		board.loadLegendConfig("BadLegendTooManyItems.txt");
	}
	
	@Test(expected = BadConfigFormatException.class)
	public void testBadConfigFormatException2() throws BadConfigFormatException, FileNotFoundException {
		board.loadLegendConfig("BadLegendTwoStrings.txt");
	}
	
	@Test(expected = BadConfigFormatException.class)
	public void testBadConfigFormatException3() throws BadConfigFormatException, FileNotFoundException {
		board.loadBoardConfig("BadBoardInvalidRoom.csv");
	}
	
	@Test(expected = BadConfigFormatException.class)
	public void testBadConfigFormatException4() throws BadConfigFormatException, FileNotFoundException {
		board.loadBoardConfig("BadBoardRowsDontMatch.csv");
	}

}
