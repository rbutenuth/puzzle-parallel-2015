package de.codecentric.slider.astar;

import java.util.List;

import de.codecentric.slider.util.Node;
import de.codecentric.slider.util.SliderBoard;
import de.codecentric.slider.util.Solver;
import de.codecentric.slider.util.filter.NodeFilter;
import de.codecentric.slider.util.filter.VisitedSetFilter;
import de.codecentric.slider.util.queue.NodeQueue;
import de.codecentric.slider.util.set.SimpleHashSet;

public class AStarSolver implements Solver {
    private NodeQueue open;
    private NodeFilter<AStarNode> filter;

    public AStarSolver(NodeQueue open) {
	this.open = open;
	filter = new VisitedSetFilter<>(new SimpleHashSet());
    }

    @Override
    public String toString() {
	return "A*, queue: " + open;
    }

    @Override
    public Node<?> solve(SliderBoard board) {
	AStarNode root = new AStarNode(board);
	if (root.isSolution()) {
	    return root;
	}
	open.add(root);
	filter.markVisited(root.asLong());
	AStarNode solutionNode = null;
	while (solutionNode == null) {
	    AStarNode node = open.fetchFirst();
	    List<AStarNode> nextNodes = node.nextNodes(filter);
	    for (AStarNode nextNode : nextNodes) {
		if (nextNode.isSolution()) {
		    solutionNode = nextNode;
		    break;
		}
		open.add(nextNode);

	    }
	}
	return solutionNode;
    }
}
