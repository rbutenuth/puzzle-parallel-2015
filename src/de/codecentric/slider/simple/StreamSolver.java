package de.codecentric.slider.simple;

import java.util.ArrayList;
import java.util.List;

import de.codecentric.slider.util.Node;
import de.codecentric.slider.util.SimpleNode;
import de.codecentric.slider.util.SliderBoard;
import de.codecentric.slider.util.Solver;
import de.codecentric.slider.util.filter.NodeFilter;

public class StreamSolver implements Solver {
    private NodeFilter<SimpleNode> filter;
    private volatile SimpleNode solutionNode;

    public StreamSolver(NodeFilter<SimpleNode> filter) {
	this.filter = filter;
	solutionNode = null;
    }

    @Override
    public String toString() {
	return "P-BFS, filter: " + filter;
    }

    @Override
    public Node<?> solve(SliderBoard board) {
	SimpleNode root = new SimpleNode(board);
	List<SimpleNode> front = new ArrayList<>();
	front.add(root);
	if (root.isSolution()) {
	    solutionNode = root;
	}
	while (solutionNode == null) {
	    front = buildNextFront(front);
	}
	return solutionNode;
    }

    private List<SimpleNode> buildNextFront(List<SimpleNode> front) {
	return front.parallelStream().map((node) -> {
	    List<SimpleNode> nf = new ArrayList<>();
	    for (SimpleNode nextNode : node.nextNodes(filter)) {
		if (nextNode.isSolution()) {
		    solutionNode = nextNode;
		    break;
		}
		nf.add(nextNode);
	    }
	    return nf;
	}).reduce((left, right) -> {
	    left.addAll(right);
	    return left;
	}).get();
    }
}
