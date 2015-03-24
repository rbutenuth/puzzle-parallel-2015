package de.codecentric.slider.util.filter;

import de.codecentric.slider.util.Node;


/**
 * A filter which avoids to move back the last moved tile to its original position.
 * 
 * @param <T> Node implementation.
 */
public class DontGoBackFilter<T extends Node<?>> implements NodeFilter<T> {
    
    @Override
    public void markVisited(Long nodeAsLong) {
	// Nothing to do
    }

    @Override
    public boolean accept(T from, Long nodeAsLong) {
	Node<?> previous = from == null ? null : from.getPrevious();
	return previous == null || previous.asLong() != nodeAsLong;
    }

    @Override
    public String toString() {
	return "Don't go back filter";
    }
}
