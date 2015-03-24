package de.codecentric.slider.simple;

import java.util.ArrayList;
import java.util.List;

import de.codecentric.slider.util.Node;
import de.codecentric.slider.util.SimpleNode;
import de.codecentric.slider.util.SliderBoard;
import de.codecentric.slider.util.Solver;
import de.codecentric.slider.util.filter.NodeFilter;

public class SequentialSolverWithVisitedSet implements Solver {
    private NodeFilter<SimpleNode> filter;

    public SequentialSolverWithVisitedSet(NodeFilter<SimpleNode> filter) {
	this.filter = filter;
    }
    
    @Override
    public String toString() {
        return "BFS, filter: " + filter; 
    }


    @Override
    public Node<?> solve(SliderBoard board) {
        SimpleNode root = new SimpleNode(board);
        if (root.isSolution()) {
            return root;
        }
        List<SimpleNode> front = new ArrayList<>();
        List<SimpleNode> nextFront;
        filter.accept(null, root.asLong());
	front.add(root);
        SimpleNode solutionNode = null;
        while (solutionNode == null) {
            nextFront = new ArrayList<>();
            solutionNode = buildNextFront(front, nextFront);
            front = nextFront;
        }
        return solutionNode;
    }

    private SimpleNode buildNextFront(List<SimpleNode> front, List<SimpleNode> nextFront) {
        for (SimpleNode node : front) {
            for (SimpleNode nextNode : node.nextNodes(filter)) {
                if (nextNode.isSolution()) {
                    return nextNode;
                }
                nextFront.add(nextNode);
            }
        }
        return null;
    }
}
