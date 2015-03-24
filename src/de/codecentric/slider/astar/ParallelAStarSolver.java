package de.codecentric.slider.astar;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;

import de.codecentric.slider.util.Node;
import de.codecentric.slider.util.SliderBoard;
import de.codecentric.slider.util.Solver;
import de.codecentric.slider.util.filter.NodeFilter;
import de.codecentric.slider.util.queue.NodeQueue;

public class ParallelAStarSolver implements Solver {
    private AtomicBoolean lock = new AtomicBoolean();
    private NodeQueue open;
    private NodeFilter<AStarNode> filter;
    private volatile AStarNode solutionNode;

    public ParallelAStarSolver(NodeQueue open, NodeFilter<AStarNode> filter) {
	this.open = open;
	this.filter = filter;
	solutionNode = null;
    }

    @Override
    public String toString() {
	return "P-A*, queue: " + open + ", filter: " + filter;
    }

    @Override
    public Node<?> solve(SliderBoard board) {
	AStarNode root = new AStarNode(board);
	if (root.isSolution()) {
	    return root;
	}
	open.add(root);
	filter.markVisited(root.asLong());
	ForkJoinPool pool = ForkJoinPool.commonPool();
	Runnable[] tasks = new Runnable[pool.getParallelism()];
	for (int i = 0; i < tasks.length; i++) {
	    tasks[i] = new Runnable() {
		@Override
		public void run() {
		    while (solutionNode == null) {
			AStarNode node = open.fetchFirst();
			List<AStarNode> nextNodes = node.nextNodes(filter);
			for (AStarNode nextNode : nextNodes) {
			    if (nextNode.isSolution()) {
				solutionNode = nextNode;
				synchronized (lock) {
				    lock.set(true);
				    lock.notify();
				}
				return;
			    }
			    open.add(nextNode);
			}
		    }
		}
	    };
	    pool.execute(tasks[i]);
	}
	synchronized (lock) {
	    try {
		if (!lock.get()) {
		    lock.wait();
		}
	    } catch (InterruptedException e) {
		System.out.println("Ups, interrupted...");
	    }
	}
	// make sure no worker thread wait forever at fetchFirst()
	for (int i = 0; i < tasks.length; i++) {
	    open.add(solutionNode);
	}

	return solutionNode;
    }
}
