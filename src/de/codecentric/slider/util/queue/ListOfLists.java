package de.codecentric.slider.util.queue;

import java.util.ArrayList;
import java.util.List;

import de.codecentric.slider.astar.AStarNode;

public class ListOfLists implements NodeQueue {
    private ArrayList<List<AStarNode>> outerList;
    private int minValue;

    public ListOfLists() {
	outerList = new ArrayList<>();
	minValue = Integer.MAX_VALUE;
    }

    @Override
    public void add(AStarNode node) {
	int value = node.getMovesDone() + node.getOptimisticDistanceToSolution();
	if (value < minValue) {
	    minValue = value;
	}
	while (outerList.size() < value + 1) {
	    outerList.add(new ArrayList<>());
	}
	List<AStarNode> list = outerList.get(value);
	list.add(node);
    }

    @Override
    public AStarNode fetchFirst() {
	if (minValue == Integer.MAX_VALUE) {
	    return null;
	}
	List<AStarNode> list = outerList.get(minValue);
	AStarNode node = list.remove(list.size() - 1);
	if (list.size() == 0) {
	    while (minValue < outerList.size() && outerList.get(minValue).size() == 0) {
		minValue++;
	    }
	    if (minValue == outerList.size()) {
		minValue = Integer.MAX_VALUE;
	    }
	}
	return node;
    }

    @Override
    public String toString() {
	return "ListOfLists";
    }
}
