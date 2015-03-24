package de.codecentric.slider.simple;

import java.util.HashMap;
import java.util.Map;

import de.codecentric.slider.util.Node;
import de.codecentric.slider.util.SimpleNode;
import de.codecentric.slider.util.SliderBoard;
import de.codecentric.slider.util.Solver;
import de.codecentric.slider.util.filter.DontGoBackFilter;
import de.codecentric.slider.util.filter.NodeFilter;

public class SequentialIDFSSolver implements Solver {
    private NodeFilter<SimpleNode> filter;
    private Map<Long, Integer> node2movesDone;
    
    public SequentialIDFSSolver() {
	filter = new DontGoBackFilter<>();
	node2movesDone = new HashMap<>();
    }
    
    @Override
    public String toString() {
	return "IDFS";
    }

    @Override
    public Node<?> solve(SliderBoard board) {
	SimpleNode solutionNode = null;
	for (int bound = 0; solutionNode == null && bound < 80; bound++) {
	    node2movesDone.clear();
	    solutionNode = search(new SimpleNode(board), 0, bound);
	}
	return solutionNode;
    }

    private SimpleNode search(SimpleNode node, int movesDone, int bound) {
	if (node.isSolution()) {
	    return node;
	}
	if (movesDone >= bound) {
	    return null;
	}
	Long nodeAsLong = node.asLong();
	Integer knownMovesDone = node2movesDone.get(nodeAsLong);
	if (knownMovesDone == null || node.getMovesDone() < knownMovesDone) {
	    node2movesDone.put(nodeAsLong, node.getMovesDone());
	    for (SimpleNode nextNode : node.nextNodes(filter)) {
		SimpleNode found = search(nextNode, movesDone + 1, bound);
		if (found != null) {
		    return found;
		}
	    }
	}
	return null;
    }
}
