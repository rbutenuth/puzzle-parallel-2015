package de.codecentric.slider.util;

import java.util.ArrayList;
import java.util.List;

import de.codecentric.slider.util.filter.NodeFilter;

public class SimpleNode implements Node<SimpleNode> {
    private static final int SIZE = 4;
    private final long value;
    private final SimpleNode previous;
    private final int movesDone;

    public SimpleNode(SliderBoard board) {
	value = board.asLong();
	this.previous = null;
	movesDone = 0;
    }

    private SimpleNode(long value, SimpleNode previous) {
	this.value = value;
	this.previous = previous;
	movesDone = (short) (previous.movesDone + 1);
	// optimisticDistanceToSolution set in addNode
    }

    @Override
    public SliderBoard getBoard() {
	return new SliderBoard(value);
    }

    @Override
    public SimpleNode getPrevious() {
	return previous;
    }

    @Override
    public int getMovesDone() {
	return movesDone;
    }

    @Override
    public long asLong() {
	return value;
    }

    @Override
    public boolean isSolution() {
	return value == SliderBoard.SOLUTION_BOARD;
    }

    @Override
    public List<SimpleNode> nextNodes(NodeFilter<SimpleNode> filter) {
	List<SimpleNode> nodes = new ArrayList<>();
	int freePos = computeFreePos();
	int row = freePos / SIZE;
	int col = freePos % SIZE;
	if (row > 0) {
	    addNode(nodes, row, col, row - 1, col, filter);
	}
	if (row < SIZE - 1) {
	    addNode(nodes, row, col, row + 1, col, filter);
	}
	if (col > 0) {
	    addNode(nodes, row, col, row, col - 1, filter);
	}
	if (col < SIZE - 1) {
	    addNode(nodes, row, col, row, col + 1, filter);
	}
	return nodes;
    }

    private void addNode(List<SimpleNode> boards, int freeRow, int freeCol, int fromRow, int fromCol, NodeFilter<SimpleNode> filter) {
	int freePos = freeRow * SIZE + freeCol;
	int nextFreePos = fromRow * SIZE + fromCol;
	long movedTileValue = (value >> 4 * nextFreePos) & 0xf;
	long nextValue = value & ~(0xfL << 4 * nextFreePos);
	nextValue &= ~(0xfL << 4 * freePos);
	nextValue |= movedTileValue << 4 * freePos;
	if (filter.accept(this, nextValue)) {
	    SimpleNode newNode = new SimpleNode(nextValue, this);
	    boards.add(newNode);
	}
    }

    @Override
    public int valueAt(int row, int col) {
	if (row < 0 || row >= SIZE) {
	    throw new IllegalArgumentException("illegal row value: " + row);
	}
	if (col < 0 || col >= SIZE) {
	    throw new IllegalArgumentException("illegal col value: " + col);
	}
	return unsafeValueAt(row, col);
    }

    private int unsafeValueAt(int row, int col) {
	int rowBits = (int) (value >> row * 16) & 0xffff;
	return (rowBits >> 4 * col) & 0xf;
    }

    public int computeFreePos() {
	int freePos = -1;
	long shift = value;
	for (int i = 0; i < 16 && freePos == -1; i++) {
	    if ((shift & 0xf) == 0) {
		freePos = i;
	    }
	    shift >>= 4;
	}
	return freePos;
    }

    @Override
    public String toString() {
	return new SliderBoard(value).toString();
    }

    @Override
    public int hashCode() {
	return Long.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	SimpleNode other = (SimpleNode) obj;
	return value == other.value;
    }
}
