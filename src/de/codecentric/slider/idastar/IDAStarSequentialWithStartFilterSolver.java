package de.codecentric.slider.idastar;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.codecentric.slider.util.Node;
import de.codecentric.slider.util.SliderBoard;
import de.codecentric.slider.util.Solver;
import de.codecentric.slider.util.filter.DontGoBackFilter;
import de.codecentric.slider.util.filter.NodeFilter;

public class IDAStarSequentialWithStartFilterSolver implements Solver {
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

    private NodeFilter<IDAStarNode> hashSetFilter;
    private NodeFilter<IDAStarNode> dontGoBackFilter;
    
    public IDAStarSequentialWithStartFilterSolver() {
	dontGoBackFilter = new DontGoBackFilter<>();
    }

    @Override
    public String toString() {
	return "IDA* (mit Filter)";
    }

    @Override
    public Node<?> solve(SliderBoard board) {
//	long startTime = System.currentTimeMillis();
	IDAStarNode root = new IDAStarNode(board);
	int bound = root.getOptimisticDistanceToSolution();
	IDAStarNode solutionNode = null;
	while (solutionNode == null) {
	    // The first filter has to be recreated for every depth
	    hashSetFilter = new ShortestWinsFilter<>();
//	    System.out.println("bound: " + bound + ", time used: " + (System.currentTimeMillis() - startTime) / 1000.0 + "s");
	    SearchResult r = search(root, bound);
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

    private SearchResult search(IDAStarNode node, int bound) {
	int f = node.getMovesDone() + node.getOptimisticDistanceToSolution();
	if (f > bound) {
	    return new SearchResult(f);
	}
	if (node.isSolution()) {
	    return new SearchResult(node);
	}
	int min = Integer.MAX_VALUE;
	List<IDAStarNode> successors = node.nextNodes(node.getMovesDone() < 25 ? hashSetFilter : dontGoBackFilter);
	sortSuccessors(successors);
	for (IDAStarNode succ : successors) {
	    SearchResult r = search(succ, bound);
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
