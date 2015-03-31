package de.codecentric.slider.idastar;

import org.junit.Test;

import de.codecentric.slider.AbstractSolverTest;

public class IDAStarSequentialStartStartWithFilterForkJoinSolverTest extends AbstractSolverTest {
    
    @Test
    public void testRoot() {
	testSolver(new IDAStarSequentialStartWithFilterForkJoinSolver(6), 0);
    }
    
    @Test
    public void testDistance1() {
	testSolver(new IDAStarSequentialStartWithFilterForkJoinSolver(6), 1);
    }
    
    @Test
    public void testDistance2() {
	testSolver(new IDAStarSequentialStartWithFilterForkJoinSolver(6), 2);
    }
    
    @Test
    public void testDistance3() {
	testSolver(new IDAStarSequentialStartWithFilterForkJoinSolver(6), 3);
    }
    
    @Test
    public void testDistance10() {
	testSolver(new IDAStarSequentialStartWithFilterForkJoinSolver(6), 10);
    }
    
    @Test
    public void testDistance15() {
	testSolver(new IDAStarSequentialStartWithFilterForkJoinSolver(6), 15);
    }
    
    @Test
    public void testDistance20() {
	testSolver(new IDAStarSequentialStartWithFilterForkJoinSolver(6), 20);
    }
    
    @Test
    public void testDistance40() {
	testSolver(new IDAStarSequentialStartWithFilterForkJoinSolver(6), 20);
    }
}
