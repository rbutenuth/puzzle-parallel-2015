package de.codecentric.slider.simple;

import org.junit.Test;

import de.codecentric.slider.AbstractSolverTest;

public class SequentialIDFSSolverTest extends AbstractSolverTest {
    
    @Test
    public void testRoot() {
	testSolver(new SequentialIDFSSolver(), 0);
    }
    
    @Test
    public void testDistance1() {
	testSolver(new SequentialIDFSSolver(), 1);
    }
    
    @Test
    public void testDistance2() {
	testSolver(new SequentialIDFSSolver(), 2);
    }
    
    @Test
    public void testDistance3() {
	testSolver(new SequentialIDFSSolver(), 3);
    }
    
    @Test
    public void testDistance10() {
	testSolver(new SequentialIDFSSolver(), 10);
    }
    
    @Test
    public void testDistance15() {
	testSolver(new SequentialIDFSSolver(), 15);
    }
}
