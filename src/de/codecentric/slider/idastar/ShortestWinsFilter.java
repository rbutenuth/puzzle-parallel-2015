package de.codecentric.slider.idastar;

import java.util.HashMap;
import java.util.Map;

import de.codecentric.slider.util.Node;
import de.codecentric.slider.util.filter.NodeFilter;

public class ShortestWinsFilter<T extends Node<?>> implements NodeFilter<T> {
    private Map<Long, Integer> nodeToCostMap = new HashMap<>(1_000_000);

    @Override
    public void markVisited(Long nodeAsLong) {
	; // do nothing
    }

    @Override
    public boolean accept(T from, Long nodeAsLong) {
	if (from == null) {
	    return true;
	}
	int newCost = from.getMovesDone() + 1;
	Integer oldCost = nodeToCostMap.get(nodeAsLong);
	boolean result;
	if (oldCost == null) {
	    nodeToCostMap.put(nodeAsLong, newCost);
	    result = true;
	} else {
	    if (newCost < oldCost) {
		nodeToCostMap.put(nodeAsLong, newCost);
		result = true;
	    } else {
		result = false;
	    }
	}
	return result;
    }

    @Override
    public String toString() {
	return "ShortestWinsFilter";
    }
}
