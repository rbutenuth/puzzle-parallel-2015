package de.codecentric.slider.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import de.codecentric.slider.astar.AStarNode;
import de.codecentric.slider.util.filter.DontGoBackFilter;

public class AStarNodeTest {

    @Test
    public void testStartIsSolution() {
	AStarNode n = new AStarNode(new SliderBoard());
	assertTrue(n.isSolution());
    }

    @Test
    public void testStartBoardHasDistanceZero() {
	AStarNode n = new AStarNode(new SliderBoard());
	assertEquals(0, n.getOptimisticDistanceToSolution());
    }

    @Test
    public void testStartBoardFreePosIs15() {
	AStarNode n = new AStarNode(new SliderBoard());
	assertEquals(15, n.computeFreePos());
    }

    @Test
    public void testBoardAfterOneMoveHasDistanceOne() {
	AStarNode n = new AStarNode(new SliderBoard());
	List<AStarNode> nn = n.nextNodes(new DontGoBackFilter<AStarNode>());
	assertEquals(2, nn.size());
	AStarNode n1 = nn.get(0);
	assertEquals(1, n1.getOptimisticDistanceToSolution());
	AStarNode n2 = nn.get(1);
	assertEquals(1, n2.getOptimisticDistanceToSolution());
    }
    
    @Test
    public void testBoardAfterOneHasCorrectFreePos() {
	int moveDown = 11;
	int moveRight = 14;
	AStarNode n = new AStarNode(new SliderBoard());
	List<AStarNode> nn = n.nextNodes(new DontGoBackFilter<AStarNode>());
	assertEquals(2, nn.size());
	AStarNode n1 = nn.get(0);
	int free1 = n1.computeFreePos();
	assertTrue(free1 == moveDown || free1 == moveRight);
	AStarNode n2 = nn.get(1);
	int free2 = n2.computeFreePos();
	assertTrue(free2 == moveDown || free2 == moveRight);
    }
}
