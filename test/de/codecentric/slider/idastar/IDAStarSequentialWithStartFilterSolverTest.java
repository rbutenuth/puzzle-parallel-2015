package de.codecentric.slider.idastar;

import org.junit.Test;

import de.codecentric.slider.AbstractSolverTest;

public class IDAStarSequentialWithStartFilterSolverTest extends AbstractSolverTest {
    
    @Test
    public void testRoot() {
	testSolver(new IDAStarSequentialWithStartFilterSolver(), 0);
    }
    
    @Test
    public void testDistance1() {
	testSolver(new IDAStarSequentialWithStartFilterSolver(), 1);
    }
    
    @Test
    public void testDistance2() {
	testSolver(new IDAStarSequentialWithStartFilterSolver(), 2);
    }
    
    @Test
    public void testDistance3() {
	testSolver(new IDAStarSequentialWithStartFilterSolver(), 3);
    }
    
    @Test
    public void testDistance10() {
	testSolver(new IDAStarSequentialWithStartFilterSolver(), 10);
    }
    
    @Test
    public void testDistance15() {
	testSolver(new IDAStarSequentialWithStartFilterSolver(), 15);
    }
    
    @Test
    public void testDistance20() {
	testSolver(new IDAStarSequentialWithStartFilterSolver(), 20);
    }
    
    @Test
    public void testDistance40() {
	testSolver(new IDAStarSequentialWithStartFilterSolver(), 20);
    }
}
