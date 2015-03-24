package de.codecentric.slider.simple;

import org.junit.Test;

import de.codecentric.slider.AbstractSolverTest;
import de.codecentric.slider.simple.SequentialSolverWithVisitedSet;
import de.codecentric.slider.util.SimpleNode;
import de.codecentric.slider.util.Solver;
import de.codecentric.slider.util.filter.DontGoBackFilter;

public class SequentialSolverWithDontGoBackFilterTest extends AbstractSolverTest {
    private Solver solver = new SequentialSolverWithVisitedSet(new DontGoBackFilter<SimpleNode>());
    
    @Test
    public void testRoot() {
	testSolver(solver, 0);
    }
    
    @Test
    public void testDistance1() {
	testSolver(solver, 1);
    }
    
    @Test
    public void testDistance2() {
	testSolver(solver, 2);
    }
    
    @Test
    public void testDistance3() {
	testSolver(solver, 3);
    }
    
    @Test
    public void testDistance10() {
	testSolver(solver, 10);
    }
    
    @Test
    public void testDistance15() {
	testSolver(solver, 15);
    }
}
