package de.codecentric.slider.util.filter;

import de.codecentric.slider.util.Node;
import de.codecentric.slider.util.set.SimpleSet;

public class VisitedSetFilter<T extends Node<?>> implements NodeFilter<T> {
    private SimpleSet set;

    @Override
    public void markVisited(Long nodeAsLong) {
	set.add(nodeAsLong);
    }
    
    public VisitedSetFilter(SimpleSet set) {
	this.set = set;
    }
    
    @Override
    public boolean accept(T from, Long nodeAsLong) {
	return set.add(nodeAsLong);
    }

    @Override
    public String toString() {
	return "VisitedSetFilter, set: " + set;
    }
}
