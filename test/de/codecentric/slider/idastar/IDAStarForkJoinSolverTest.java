package de.codecentric.slider.idastar;

import org.junit.Test;

import de.codecentric.slider.AbstractSolverTest;

public class IDAStarForkJoinSolverTest extends AbstractSolverTest {
    
    @Test
    public void testRoot() {
	testSolver(new IDAStarForkJoinSolver(3), 0);
    }
    
    @Test
    public void testDistance1() {
	testSolver(new IDAStarForkJoinSolver(3), 1);
    }
    
    @Test
    public void testDistance2() {
	testSolver(new IDAStarForkJoinSolver(3), 2);
    }
    
    @Test
    public void testDistance3() {
	testSolver(new IDAStarForkJoinSolver(3), 3);
    }
    
    @Test
    public void testDistance10() {
	testSolver(new IDAStarForkJoinSolver(3), 10);
    }
    
    @Test
    public void testDistance15() {
	testSolver(new IDAStarForkJoinSolver(3), 15);
    }
    
    @Test
    public void testDistance20() {
	testSolver(new IDAStarForkJoinSolver(3), 20);
    }
}
