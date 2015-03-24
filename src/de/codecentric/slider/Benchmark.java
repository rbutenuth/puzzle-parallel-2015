package de.codecentric.slider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.codecentric.slider.astar.AStarSolver;
import de.codecentric.slider.astar.ParallelAStarSolver;
import de.codecentric.slider.idastar.IDAStarForkJoinSolver;
import de.codecentric.slider.idastar.IDAStarSequentialSolver;
import de.codecentric.slider.idastar.IDAStarSequentialStartForkJoinSolver;
import de.codecentric.slider.idastar.IDAStarSequentialStartWithFilterForkJoinSolver;
import de.codecentric.slider.idastar.IDAStarSequentialWithStartFilterSolver;
import de.codecentric.slider.simple.SequentialIDFSSolver;
import de.codecentric.slider.simple.SequentialSolverWithVisitedSet;
import de.codecentric.slider.simple.StreamSolver;
import de.codecentric.slider.util.BoardFactory;
import de.codecentric.slider.util.Node;
import de.codecentric.slider.util.SimpleNode;
import de.codecentric.slider.util.SliderBoard;
import de.codecentric.slider.util.Solver;
import de.codecentric.slider.util.filter.AcceptAllFilter;
import de.codecentric.slider.util.filter.DontGoBackFilter;
import de.codecentric.slider.util.filter.VisitedSetFilter;
import de.codecentric.slider.util.queue.Heap;
import de.codecentric.slider.util.queue.ListOfLists;
import de.codecentric.slider.util.queue.ParallelHeap;
import de.codecentric.slider.util.queue.SynchronizedListOfLists;
import de.codecentric.slider.util.set.ArrayOfSynchronizedHashSet;
import de.codecentric.slider.util.set.ConcurrentHashMapBasedSet;
import de.codecentric.slider.util.set.ReadWriteHashSet;
import de.codecentric.slider.util.set.SimpleHashSet;
import de.codecentric.slider.util.set.SynchronizedHashSet;

// -server -d64 -Xmx5000m
public class Benchmark {
    private static int MAX_SOLVER_INDEX = 32;

    private static class Work {
	final Solver solver;
	final double maxDuration;
	
	Work(Solver solver, double maxDuration) {
	    this.solver = solver;
	    this.maxDuration = maxDuration;
	}
    }
    public static void main(String[] args) {
	warmUpCommonPool();
	printHeader();
	measure();
    }

    private static Work createWork(int index) {
	Work work = null;
	switch (index) {
	case 0:
	    work = new Work(new SequentialIDFSSolver(), 60);
	    break;
	case 1:
	    work = new Work(new SequentialSolverWithVisitedSet(new AcceptAllFilter<SimpleNode>()), 5);
	    break;
	case 2:
	    work = new Work(new SequentialSolverWithVisitedSet(new DontGoBackFilter<SimpleNode>()), 10);
	    break;
	case 3:
	    work = new Work(new SequentialSolverWithVisitedSet(new VisitedSetFilter<SimpleNode>(new SimpleHashSet())), 10);
	    break;
	case 4:
	    work = new Work(new StreamSolver(new DontGoBackFilter<>()), 10);
	    break;
	case 5:
	    work = new Work(new StreamSolver(new VisitedSetFilter<>(new SynchronizedHashSet())), 10);
	    break;
	case 6:
	    work = new Work(new StreamSolver(new VisitedSetFilter<>(new ConcurrentHashMapBasedSet())), 10);
	    break;
	case 7:
	    work = new Work(new StreamSolver(new VisitedSetFilter<>(new ReadWriteHashSet())), 10);
	    break;
	case 8:
	    work = new Work(new StreamSolver(new VisitedSetFilter<>(new ArrayOfSynchronizedHashSet())), 10);
	    break;
	case 9:
	    work = new Work(new AStarSolver(new Heap()), 10);
	    break;
	case 10:
	    work = new Work(new AStarSolver(new ParallelHeap()), 10);
	    break;
	case 11:
	    work = new Work(new AStarSolver(new ListOfLists()), 10);
	    break;
	case 12:
	    work = new Work(new ParallelAStarSolver(new ParallelHeap(), //
		    new VisitedSetFilter<>(new ArrayOfSynchronizedHashSet())), 60);
	    break;
	case 13:
	    work = new Work(new ParallelAStarSolver(new SynchronizedListOfLists(), //
		    new VisitedSetFilter<>(new ArrayOfSynchronizedHashSet())), 60);
	    break;
	case 14:
	    work = new Work(new IDAStarSequentialSolver(), 60);
	    break;
	case 15:
	    work = new Work(new IDAStarSequentialWithStartFilterSolver(), 2400);
	    break;
	case 16:
	    work = new Work(new IDAStarForkJoinSolver(0), 60);
	    break;
	case 17:
	    work = new Work(new IDAStarForkJoinSolver(3), 60);
	    break;
	case 18:
	    work = new Work(new IDAStarForkJoinSolver(6), 60);
	    break;
	case 19:
	    work = new Work(new IDAStarSequentialStartForkJoinSolver(0, 6), 60);
	    break;
	case 20:
	    work = new Work(new IDAStarSequentialStartForkJoinSolver(1, 6), 60);
	    break;
	case 21:
	    work = new Work(new IDAStarSequentialStartForkJoinSolver(2, 6), 60);
	    break;
	case 22:
	    work = new Work(new IDAStarSequentialStartForkJoinSolver(3, 6), 60);
	    break;
	case 23:
	    work = new Work(new IDAStarSequentialStartForkJoinSolver(4, 6), 60);
	    break;
	case 24:
	    work = new Work(new IDAStarSequentialStartForkJoinSolver(5, 6), 60);
	    break;
	case 25:
	    work = new Work(new IDAStarSequentialStartForkJoinSolver(10, 6), 60);
	    break;
	case 26:
	    work = new Work(new IDAStarSequentialStartForkJoinSolver(15, 6), 60);
	    break;
	case 27:
	    work = new Work(new IDAStarSequentialStartForkJoinSolver(20, 6), 60);
	    break;
	case 28:
	    work = new Work(new IDAStarSequentialStartWithFilterForkJoinSolver(5), 60);
	    break;
	case 29:
	    work = new Work(new IDAStarSequentialStartWithFilterForkJoinSolver(10), 60);
	    break;
	case 30:
	    work = new Work(new IDAStarSequentialStartWithFilterForkJoinSolver(15), 2400);
	    break;
	case 31:
	    work = new Work(new IDAStarSequentialStartWithFilterForkJoinSolver(20), 2400);
	    break;
	case 32:
	    work = new Work(new IDAStarSequentialStartWithFilterForkJoinSolver(25), 2400);
	    break;
	default:
	    work = null;
	}
	return work;
    }

    private static void printHeader() {
	System.out.print("Solver");
	for (int moves = 0; moves <= BoardFactory.maxAvailableDistanceToSolution(); moves++) {
	    System.out.print(";" + moves);
	}
	System.out.println();
    }

    private static void measure() {
	for (int i = 12; i <= MAX_SOLVER_INDEX; i++) {
	    Work work = createWork(i);
	    System.out.print(work.solver.toString());
	    boolean goOn = true;
	    for (int moves = 0; goOn && moves <= BoardFactory.maxAvailableDistanceToSolution(); moves++) {
		SliderBoard start = BoardFactory.createSliderBoard(moves);
		try {
		    double duration = measure(work.solver, start, false);
		    System.out.printf(";%.3f", duration);
		    if (duration > work.maxDuration) {
			goOn = false;
		    }
		} catch (OutOfMemoryError e) {
		    work = null;
		    goOn = false;
		    System.out.printf(";OOME");
		}

		// long gcStart = System.currentTimeMillis();
		work = null;
		System.gc();
		// long gcEnd = System.currentTimeMillis();
		// System.out.println("\r\ntime for gc: " + (gcEnd - gcStart) / 1000.0 + " s");

		work = createWork(i);
	    }
	    System.out.println();
	}
    }

    private static double measure(Solver solver, SliderBoard start, boolean debug) {
	if (debug) {
	    System.out.println("Measurement for " + solver);
	}
	long startTime = System.currentTimeMillis();
	Node<?> solution = solver.solve(start);
	long endTime = System.currentTimeMillis();
	double duration = (endTime - startTime) / 1000.0;
	if (debug) {
	    System.out.println("Used time: " + duration + " seconds");
	    if (solution == null) {
		System.out.println("No solution found.");
	    } else {
		LinkedList<Node<?>> moves = new LinkedList<>();
		Node<?> n = solution;
		while (n != null) {
		    moves.addFirst(n);
		    n = n.getPrevious();
		}
		System.out.println("Moves: " + (moves.size() - 1));
		showSolution(moves);
	    }
	}
	return duration;
    }

    private static void showSolution(List<Node<?>> solution) {
	Map<Long, Integer> dupCheckMap = new HashMap<>();
	int i = 0;
	for (Node<?> s : solution) {
	    if (dupCheckMap.put(s.asLong(), i) != null) {
		System.out.println("duplicate, seen with i = " + dupCheckMap.get(s.asLong()));
	    }
	    System.out.print(i);
	    System.out.println(s);
	    i++;
	}
    }

    // Den ThreadPool aufbauen...
    private static void warmUpCommonPool() {
	List<Integer> l = new ArrayList<>(1000000);
	for (int i = 0; i < 1000000; i++) {
	    l.add(Integer.valueOf(i));
	}
	l.parallelStream().reduce((a, b) -> a + b).get();
    }
}
