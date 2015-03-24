package de.codecentric.slider.astar;

import org.junit.Test;

import de.codecentric.slider.AbstractSolverTest;
import de.codecentric.slider.astar.AStarSolver;
import de.codecentric.slider.util.queue.Heap;
import de.codecentric.slider.util.queue.ListOfLists;

public class AStarSolverTest extends AbstractSolverTest {
    
    @Test
    public void testRootHeap() {
	testSolver(new AStarSolver(new Heap()), 0);
    }
    
    @Test
    public void testDistance1Heap() {
	testSolver(new AStarSolver(new Heap()), 1);
    }
    
    @Test
    public void testDistance20Heap() {
	testSolver(new AStarSolver(new Heap()), 20);
    }
    
    @Test
    public void testRootListOfLists() {
	testSolver(new AStarSolver(new ListOfLists()), 0);
    }
    
    @Test
    public void testDistance1ListOfLists() {
	testSolver(new AStarSolver(new ListOfLists()), 1);
    }
    
    @Test
    public void testDistance20ListOfLists() {
	testSolver(new AStarSolver(new ListOfLists()), 20);
    }
}
