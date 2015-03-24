package de.codecentric.slider;

import de.codecentric.slider.util.BoardFactory;
import de.codecentric.slider.util.Node;
import de.codecentric.slider.util.Solver;

import static org.junit.Assert.*;

public abstract class AbstractSolverTest {

    protected void testSolver(Solver s, int distanceToSolution) {
	Node<?> solution = s.solve(BoardFactory.createSliderBoard(distanceToSolution));
	assertNotNull("Solution found", solution);
	assertTrue("Is solution", solution.isSolution());
	assertEquals("Check distance", distanceToSolution, solution.getMovesDone());
	// The following check is a little bit too strict, there may be sereral different shortest paths to the solution.
//	for (int i = 0; i <= distanceToSolution; i++) {
//	    System.out.println(solution);
//	    assertEquals("Node on path with distance " + i + " is correct",
//		    BoardFactory.createSliderBoard(i), solution.getBoard());
//	    solution = solution.getPrevious();
//	}
//	assertNull("Predecessor of root must be null", solution);
    }
}
