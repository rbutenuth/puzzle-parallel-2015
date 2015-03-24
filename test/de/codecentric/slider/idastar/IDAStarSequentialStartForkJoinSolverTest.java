package de.codecentric.slider.idastar;

import org.junit.Test;

import de.codecentric.slider.AbstractSolverTest;

public class IDAStarSequentialStartForkJoinSolverTest extends AbstractSolverTest {
    
    @Test
    public void testRoot() {
	testSolver(new IDAStarSequentialStartForkJoinSolver(3, 6), 0);
    }
    
    @Test
    public void testDistance1() {
	testSolver(new IDAStarSequentialStartForkJoinSolver(3, 6), 1);
    }
    
    @Test
    public void testDistance2() {
	testSolver(new IDAStarSequentialStartForkJoinSolver(3, 6), 2);
    }
    
    @Test
    public void testDistance3() {
	testSolver(new IDAStarSequentialStartForkJoinSolver(3, 6), 3);
    }
    
    @Test
    public void testDistance10() {
	testSolver(new IDAStarSequentialStartForkJoinSolver(3, 6), 10);
    }
    
    @Test
    public void testDistance15() {
	testSolver(new IDAStarSequentialStartForkJoinSolver(3, 6), 15);
    }
    
    @Test
    public void testDistance20_0() {
	testSolver(new IDAStarSequentialStartForkJoinSolver(0, 6), 20);
    }
    @Test
    public void testDistance20_1() {
	testSolver(new IDAStarSequentialStartForkJoinSolver(1, 6), 20);
    }
    @Test
    public void testDistance20_2() {
	testSolver(new IDAStarSequentialStartForkJoinSolver(2, 6), 20);
    }
    @Test
    public void testDistance20_3() {
	testSolver(new IDAStarSequentialStartForkJoinSolver(3, 6), 20);
    }
    @Test
    public void testDistance20_4() {
	testSolver(new IDAStarSequentialStartForkJoinSolver(4, 6), 20);
    }
}
