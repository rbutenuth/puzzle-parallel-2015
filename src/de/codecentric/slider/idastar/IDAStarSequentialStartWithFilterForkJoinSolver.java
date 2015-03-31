package de.codecentric.slider.idastar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicBoolean;

import de.codecentric.slider.util.Node;
import de.codecentric.slider.util.SliderBoard;
import de.codecentric.slider.util.Solver;
import de.codecentric.slider.util.filter.DontGoBackFilter;
import de.codecentric.slider.util.filter.NodeFilter;

public class IDAStarSequentialStartWithFilterForkJoinSolver implements Solver {
    private static final int SEQUENTIAL_END = 3;
    private final NodeFilter<IDAStarNode> dontGoBackFilter;
    private final ForkJoinPool pool;
    private final int sequential;
    private NodeFilter<IDAStarNode> hashSetFilter;
    private AtomicBoolean found;

    public IDAStarSequentialStartWithFilterForkJoinSolver(int sequential) {
	dontGoBackFilter = new DontGoBackFilter<>();
	pool = ForkJoinPool.commonPool();
	this.sequential = sequential;
	found = new AtomicBoolean(false);
    }

    @Override
    public String toString() {
	return "S-P-IDA*, erste " + sequential + " seq. (mit Filter)";
    }

    @Override
    public Node<?> solve(SliderBoard board) {
	// long startTime = System.currentTimeMillis();
	IDAStarNode root = new IDAStarNode(board);
	int bound = root.getOptimisticDistanceToSolution();
	IDAStarNode solutionNode = null;
	while (solutionNode == null) {
	    hashSetFilter = new ShortestWinsFilter<>();
	    // System.out.println("bound: " + bound + ", time used: " + (System.currentTimeMillis() - startTime) /
	    // 1000.0 + "s");
	    SearchResult r = sequentialStartSearch(root, bound);
	    if (r.solutionNode != null) {
		solutionNode = r.solutionNode;
	    }
	    if (r.t == Integer.MAX_VALUE) {
		return null;
	    }
	    bound = r.t;
	}
	return solutionNode;
    }

    private static class SearchResult {
	public IDAStarNode solutionNode;
	public int t;

	public SearchResult(int t) {
	    this.t = t;
	}

	public SearchResult(IDAStarNode solutionNode) {
	    this.solutionNode = solutionNode;
	}
    }

    private SearchResult sequentialStartSearch(IDAStarNode node, int bound) {
	if (node.getMovesDone() >= sequential) {
	    return pool.invoke(new IDAStarTask(node, bound));
	}
	int f = node.getMovesDone() + node.getOptimisticDistanceToSolution();
	if (f > bound) {
	    return new SearchResult(f);
	}
	if (node.isSolution()) {
	    return new SearchResult(node);
	}
	int min = Integer.MAX_VALUE;
	List<IDAStarNode> successors = node.nextNodes(hashSetFilter);
	sortSuccessors(successors);
	for (IDAStarNode succ : successors) {
	    SearchResult r = sequentialStartSearch(succ, bound);
	    if (r.solutionNode != null) {
		return r;
	    }
	    if (r.t < min) {
		min = r.t;
	    }
	}
	return new SearchResult(min);
    }

    private class IDAStarTask extends RecursiveTask<SearchResult> {
	private static final long serialVersionUID = -8447124188170851712L;
	private final int bound;
	private final IDAStarNode node;

	IDAStarTask(IDAStarNode node, int bound) {
	    this.node = node;
	    this.bound = bound;
	}

	@Override
	protected SearchResult compute() {
	    int f = node.getMovesDone() + node.getOptimisticDistanceToSolution();
	    if (f > bound || found.get()) {
		return new SearchResult(f);
	    }
	    if (f > bound - SEQUENTIAL_END) {
		return sequentialEndSearch(node, bound);
	    }
	    if (node.isSolution()) {
		return new SearchResult(node);
	    }
	    int min = Integer.MAX_VALUE;
	    List<IDAStarNode> successors = node.nextNodes(dontGoBackFilter);
	    sortSuccessors(successors);
	    List<IDAStarTask> tasks = new ArrayList<>(successors.size());
	    for (IDAStarNode succ : successors) {
		tasks.add(new IDAStarTask(succ, bound));
	    }
	    invokeAll(tasks);
	    SearchResult result = null;
	    for (IDAStarTask task : tasks) {
		try {
		    SearchResult r = task.get();
		    if (r.solutionNode != null) {
			found.set(true);
			return result = r;
		    }
		    if (r.t < min) {
			min = r.t;
		    }
		} catch (InterruptedException | ExecutionException e) {
		    throw new RuntimeException(e);
		}
	    }
	    return result == null ? new SearchResult(min) : result;
	}
    }

    private SearchResult sequentialEndSearch(IDAStarNode node, int bound) {
	int f = node.getMovesDone() + node.getOptimisticDistanceToSolution();
	if (f > bound || found.get()) {
	    return new SearchResult(f);
	}
	if (node.isSolution()) {
	    return new SearchResult(node);
	}
	int min = Integer.MAX_VALUE;
	List<IDAStarNode> successors = node.nextNodes(dontGoBackFilter);
	sortSuccessors(successors);
	for (IDAStarNode succ : successors) {
	    SearchResult r = sequentialEndSearch(succ, bound);
	    if (r.solutionNode != null) {
		return r;
	    }
	    if (r.t < min) {
		min = r.t;
	    }
	}
	return new SearchResult(min);
    }

    private void sortSuccessors(List<IDAStarNode> successors) {
	Collections.sort(successors, new Comparator<IDAStarNode>() {
	    @Override
	    public int compare(IDAStarNode n1, IDAStarNode n2) {
		return n1.getOptimisticDistanceToSolution() - n2.getOptimisticDistanceToSolution();
	    }
	});
    }
}
