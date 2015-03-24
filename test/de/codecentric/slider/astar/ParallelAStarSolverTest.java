package de.codecentric.slider.astar;

import org.junit.Test;

import de.codecentric.slider.AbstractSolverTest;
import de.codecentric.slider.util.Solver;
import de.codecentric.slider.util.filter.VisitedSetFilter;
import de.codecentric.slider.util.queue.ParallelHeap;
import de.codecentric.slider.util.set.ArrayOfSynchronizedHashSet;

public class ParallelAStarSolverTest extends AbstractSolverTest {
    private Solver createSolver() {
	return new ParallelAStarSolver(new ParallelHeap(), new VisitedSetFilter<>(new ArrayOfSynchronizedHashSet()));
    }

    @Test
    public void testRoot() {
	testSolver(createSolver(), 0);
    }
    
    @Test
    public void testDistance1() {
	testSolver(createSolver(), 1);
    }
    
    @Test
    public void testDistance5() {
	testSolver(createSolver(), 5);
    }
    
    @Test
    public void testDistance20() {
	testSolver(createSolver(), 20);
    }
}
