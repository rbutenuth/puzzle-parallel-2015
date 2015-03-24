package de.codecentric.slider.util.queue;

import java.util.Comparator;
import java.util.PriorityQueue;

import de.codecentric.slider.astar.AStarNode;

public class Heap implements NodeQueue {
    private PriorityQueue<AStarNode> open;
    
    public Heap() {
	open = new PriorityQueue<>(1_000_000, new Comparator<AStarNode>() {

	    @Override
	    public int compare(AStarNode n1, AStarNode n2) {
		int v1 = n1.getMovesDone() + n1.getOptimisticDistanceToSolution();
		int v2 = n2.getMovesDone() + n2.getOptimisticDistanceToSolution();
		if (v1 > v2) {
		    return 1;
		} else if (v1 < v2) {
		    return -1;
		} else {
		    return 0;
		}
	    }
	});
    }
    
    @Override
    public void add(AStarNode node) {
	open.add(node);
    }

    @Override
    public AStarNode fetchFirst() {
	return open.poll();
    }
    
    @Override
    public String toString() {
	return "java.lang.PriorityQueue";
    }
}
