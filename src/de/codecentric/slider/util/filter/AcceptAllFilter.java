package de.codecentric.slider.util.filter;

import de.codecentric.slider.util.Node;


/**
 * A filter which accepts all moves.
 * 
 * @param <T> Node implementation.
 */
public class AcceptAllFilter<T extends Node<?>> implements NodeFilter<T> {
    
    @Override
    public void markVisited(Long nodeAsLong) {
	// Nothing to do
    }

    @Override
    public boolean accept(T from, Long nodeAsLong) {
	return true;
    }

    @Override
    public String toString() {
	return "Accept all filter";
    }
}
