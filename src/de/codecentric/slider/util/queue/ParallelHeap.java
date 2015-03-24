package de.codecentric.slider.util.queue;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

import de.codecentric.slider.astar.AStarNode;

public class ParallelHeap implements NodeQueue {
    private PriorityBlockingQueue<AStarNode> open;
    
    public ParallelHeap() {
	open = new PriorityBlockingQueue<>(1_000_000, new Comparator<AStarNode>() {

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
	try {
	    return open.take();
        } catch (InterruptedException e) {
            return null;
        }
    }
    
    @Override
    public String toString() {
	return "java.lang.PriorityBlockingQueue";
    }
}
