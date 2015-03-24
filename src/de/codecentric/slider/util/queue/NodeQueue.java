package de.codecentric.slider.util.queue;

import de.codecentric.slider.astar.AStarNode;


public interface NodeQueue {
    
    public void add(AStarNode node);
    
    public AStarNode fetchFirst();
}
