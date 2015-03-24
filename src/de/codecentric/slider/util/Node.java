package de.codecentric.slider.util;

import java.util.List;

import de.codecentric.slider.util.filter.NodeFilter;

public interface Node<T extends Node<?>> {
    public SliderBoard getBoard();

    public T getPrevious();

    /**
     * @return Values of tiles packed in one long with tile 0 as lowest and tile 15 as highest bits.
     */
    int valueAt(int row, int col);

    boolean isSolution();

    long asLong();

    int getMovesDone();

    List<T> nextNodes(NodeFilter<T> filter);
}
