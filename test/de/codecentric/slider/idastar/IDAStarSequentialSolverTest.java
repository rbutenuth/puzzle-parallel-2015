package de.codecentric.slider.idastar;

import org.junit.Test;

import de.codecentric.slider.AbstractSolverTest;

public class IDAStarSequentialSolverTest extends AbstractSolverTest {
    
    @Test
    public void testRoot() {
	testSolver(new IDAStarSequentialSolver(), 0);
    }
    
    @Test
    public void testDistance1() {
	testSolver(new IDAStarSequentialSolver(), 1);
    }
    
    @Test
    public void testDistance2() {
	testSolver(new IDAStarSequentialSolver(), 2);
    }
    
    @Test
    public void testDistance3() {
	testSolver(new IDAStarSequentialSolver(), 3);
    }
    
    @Test
    public void testDistance10() {
	testSolver(new IDAStarSequentialSolver(), 10);
    }
    
    @Test
    public void testDistance15() {
	testSolver(new IDAStarSequentialSolver(), 15);
    }
    
    @Test
    public void testDistance20() {
	testSolver(new IDAStarSequentialSolver(), 20);
    }
}
