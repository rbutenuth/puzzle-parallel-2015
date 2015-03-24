package de.codecentric.slider.util.filter;

import de.codecentric.slider.util.Node;

public interface NodeFilter<T extends Node<?>> {
    public void markVisited(Long nodeAsLong);
    
    public boolean accept(T from, Long nodeAsLong);
}
