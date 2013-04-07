package boardTests;

import org.junit.*;

import java.io.*;
import junit.framework.Assert;
import Board.*;
import java.util.*;

public class PathTest {
	private Board board;
	
	@Before
	public void setup() {
		board = new Board();
		board.loadConfigFiles("legend.txt","board.csv");
		board.calcAdjacencies();
	}
	
	// Walkway Adjacency Test
	@Test
	public void testWalkwayAdjacencies() {
		LinkedList<Integer> list = board.getAdjList(391);
		Assert.assertTrue(list.contains(366));
		Assert.assertTrue(list.contains(390));
		Assert.assertTrue(list.contains(416));
		Assert.assertTrue(list.contains(392));
		Assert.assertEquals(list.size(), 4);
	}

	// Edge Adjacency Tests + Non-doorway Room Tests
	@Test
	public void testEdgeAdjacencies1() {
		LinkedList<Integer> list = board.getAdjList(17);
		Assert.assertTrue(list.contains(18));
		Assert.assertTrue(list.contains(42));
		Assert.assertEquals(list.size(), 2);
	}
	
	@Test
	public void testEdgeAdjacencies2() {
		LinkedList<Integer> list = board.getAdjList(174);
		Assert.assertTrue(list.contains(149));
		Assert.assertTrue(list.contains(173));
		Assert.assertEquals(list.size(), 2);
	}
	
	@Test
	public void testEdgeAdjacencies3() {
		LinkedList<Integer> list = board.getAdjList(618);
		Assert.assertTrue(list.contains(593));
		Assert.assertTrue(list.contains(617));
		Assert.assertEquals(list.size(), 2);
	}
	
	@Test
	public void testEdgeAdjacencies4() {
		LinkedList<Integer> list = board.getAdjList(150);
		Assert.assertTrue(list.contains(125));
		Assert.assertTrue(list.contains(151));
		Assert.assertEquals(list.size(), 2);
	}
	
	// Test Next-To-Door Adjacencies
	public void testNextDoorAdjacencies1() {
		LinkedList<Integer> list = board.getAdjList(307);
		Assert.assertTrue(list.contains(282));
		Assert.assertTrue(list.contains(306));
		Assert.assertTrue(list.contains(308));
		Assert.assertTrue(list.contains(332));
		Assert.assertEquals(list.size(), 4);
	}
	
	public void testNextDoorAdjacencies2() {
		LinkedList<Integer> list = board.getAdjList(438);
		Assert.assertTrue(list.contains(413));
		Assert.assertTrue(list.contains(439));
		Assert.assertTrue(list.contains(437));
		Assert.assertTrue(list.contains(463));
		Assert.assertEquals(list.size(), 4);
	}
	
	// Test On-Doorway Adjacencies
	@Test
	public void testOnDoorAdjacencies1() {
		LinkedList<Integer> list = board.getAdjList(114);
		Assert.assertTrue(list.contains(139));
		Assert.assertEquals(list.size(), 1);
	}
	
	@Test
	public void testOnDoorAdjacencies2() {
		LinkedList<Integer> list = board.getAdjList(331);
		Assert.assertTrue(list.contains(332));
		Assert.assertEquals(list.size(), 1);
	}
	
	// Test Walkway-only Target Paths
	@Test
	public void testWalkwayTargets1() {
		board.startTargets(391,3);
		Set<Integer> targets = board.getTargets();
		Assert.assertTrue(targets.contains(316));
		Assert.assertTrue(targets.contains(342));
		Assert.assertTrue(targets.contains(366));
		Assert.assertTrue(targets.contains(340));
		Assert.assertTrue(targets.contains(388));
		Assert.assertTrue(targets.contains(390));
		Assert.assertTrue(targets.contains(364));
		Assert.assertTrue(targets.contains(414));
		Assert.assertTrue(targets.contains(440));
		Assert.assertTrue(targets.contains(416));
		Assert.assertTrue(targets.contains(442));
		Assert.assertTrue(targets.contains(368));
		Assert.assertTrue(targets.contains(392));
		Assert.assertTrue(targets.contains(418));
		Assert.assertEquals(targets.size(), 14);
	}
	
	@Test
	public void testWalkwayTargets2() {
		board.startTargets(154,2);
		Set<Integer> targets = board.getTargets();
		Assert.assertTrue(targets.contains(152));
		Assert.assertTrue(targets.contains(128));
		Assert.assertTrue(targets.contains(130));
		Assert.assertTrue(targets.contains(156));
		Assert.assertEquals(targets.size(), 4);
	}
	
	@Test
	public void testWalkwayTargets3() {
		board.startTargets(183,4);
		Set<Integer> targets = board.getTargets();
		Assert.assertTrue(targets.contains(83));
		Assert.assertTrue(targets.contains(107));
		Assert.assertTrue(targets.contains(109));
		Assert.assertTrue(targets.contains(131));
		Assert.assertTrue(targets.contains(133));
		Assert.assertTrue(targets.contains(135));
		Assert.assertTrue(targets.contains(155));
		Assert.assertTrue(targets.contains(157));
		Assert.assertTrue(targets.contains(159));
		Assert.assertTrue(targets.contains(161));
		Assert.assertTrue(targets.contains(207));
		Assert.assertTrue(targets.contains(233));
		Assert.assertTrue(targets.contains(257));
		Assert.assertTrue(targets.contains(283));
		Assert.assertEquals(targets.size(), 14);
	}
	
	@Test
	public void testWalkwayTargets4() {
		board.startTargets(142,2);
		Set<Integer> targets = board.getTargets();
		Assert.assertTrue(targets.contains(140));
		Assert.assertTrue(targets.contains(166));
		Assert.assertTrue(targets.contains(168));
		Assert.assertTrue(targets.contains(144));
		Assert.assertTrue(targets.contains(118));
		Assert.assertTrue(targets.contains(92));
		Assert.assertEquals(targets.size(), 6);
	}
	
	// Test targets while entering rooms
	@Test
	public void testEnterRoomTargets1() {
		board.startTargets(139,2);
		Set<Integer> targets = board.getTargets();
		Assert.assertTrue(targets.contains(114));
		Assert.assertTrue(targets.contains(137));
		Assert.assertTrue(targets.contains(163));
		Assert.assertTrue(targets.contains(189));
		Assert.assertTrue(targets.contains(165));
		Assert.assertTrue(targets.contains(141));
		Assert.assertEquals(targets.size(), 6);
	}
	
	public void testEnterRoomTargets2() {
		board.startTargets(346,3);
		Set<Integer> targets = board.getTargets();
		Assert.assertTrue(targets.contains(296));
		Assert.assertTrue(targets.contains(321));
		Assert.assertTrue(targets.contains(345));
		Assert.assertTrue(targets.contains(343));
		Assert.assertTrue(targets.contains(347));
		Assert.assertTrue(targets.contains(349));
		Assert.assertTrue(targets.contains(319));
		Assert.assertTrue(targets.contains(323));
		Assert.assertTrue(targets.contains(372));
		Assert.assertEquals(targets.size(), 9);
	}
	
	// Test targets while leaving rooms
	public void testLeaveRoomTargets1() {
		board.startTargets(463,3);
		Set<Integer> targets = board.getTargets();
		Assert.assertTrue(targets.contains(388));
		Assert.assertTrue(targets.contains(412));
		Assert.assertTrue(targets.contains(436));
		Assert.assertTrue(targets.contains(414));
		Assert.assertTrue(targets.contains(440));
		Assert.assertEquals(targets.size(), 5);
	}
	
	public void testLeaveRoomTargets2() {
		board.startTargets(503,5);
		Set<Integer> targets = board.getTargets();
		Assert.assertTrue(targets.contains(450));
		Assert.assertTrue(targets.contains(452));
		Assert.assertTrue(targets.contains(476));
		Assert.assertTrue(targets.contains(454));
		Assert.assertTrue(targets.contains(456));
		Assert.assertTrue(targets.contains(480));
		Assert.assertEquals(targets.size(), 6);
	}
}
