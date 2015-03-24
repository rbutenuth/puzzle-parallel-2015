package de.codecentric.slider.astar;

import java.util.ArrayList;
import java.util.List;

import de.codecentric.slider.util.Node;
import de.codecentric.slider.util.SliderBoard;
import de.codecentric.slider.util.filter.NodeFilter;

public class AStarNode implements Node<AStarNode> {
    private static final int SIZE = 4;
    private final long value;
    private final AStarNode previous;
    private final short movesDone;
    private short optimisticDistanceToSolution;

    public AStarNode(SliderBoard board) {
	value = board.asLong();
	this.previous = null;
	movesDone = 0;
	optimisticDistanceToSolution = (short) computeOptimisticDistanceToSolution();
    }

    private AStarNode(long value, AStarNode previous) {
	this.value = value;
	this.previous = previous;
	movesDone = (short) (previous.movesDone + 1);
	// optimisticDistanceToSolution set in addNode
    }

    public SliderBoard getBoard() {
	return new SliderBoard(value);
    }

    public AStarNode getPrevious() {
	return previous;
    }

    public int getMovesDone() {
	return movesDone;
    }

    /**
     * @return Values of tiles packed in one long with tile 0 as lowest and tile 15 as highest bits.
     */
    public long asLong() {
	return value;
    }

    /**
     * @return An optimistic estimate of the number of moves from this node to the solution. May be smaller than real
     *         value, but never bigger.
     */
    public int getOptimisticDistanceToSolution() {
	return optimisticDistanceToSolution;
    }

    private int computeOptimisticDistanceToSolution() {
	int dist = 0;
	for (int row = 0; row < SIZE; row++) {
	    for (int col = 0; col < SIZE; col++) {
		dist += computeTileDist(row, col, unsafeValueAt(row, col));
	    }
	}
	return dist;
    }

    private int computeTileDist(int row, int col, int tile) {
	if (tile == 0) {
	    return 0;
	} else {
	    int destCol = (tile - 1) % SIZE;
	    int destRow = (tile - 1) / SIZE;
	    return Math.abs(col - destCol) + Math.abs(row - destRow);
	}
    }

    public boolean isSolution() {
	return value == SliderBoard.SOLUTION_BOARD;
    }

    @Override
    public List<AStarNode> nextNodes(NodeFilter<AStarNode> filter) {
	List<AStarNode> nextNodes = new ArrayList<>(4);
	int freePos = computeFreePos();
	int row = freePos / SIZE;
	int col = freePos % SIZE;
	if (row > 0) {
	    addNode(nextNodes, row, col, row - 1, col, filter);
	}
	if (row < SIZE - 1) {
	    addNode(nextNodes, row, col, row + 1, col, filter);
	}
	if (col > 0) {
	    addNode(nextNodes, row, col, row, col - 1, filter);
	}
	if (col < SIZE - 1) {
	    addNode(nextNodes, row, col, row, col + 1, filter);
	}
	return nextNodes;
    }

    private void addNode(List<AStarNode> boards, int freeRow, int freeCol, int fromRow, int fromCol, NodeFilter<AStarNode> filter) {
	int freePos = freeRow * SIZE + freeCol;
	int nextFreePos = fromRow * SIZE + fromCol;
	long movedTileValue = (value >> 4 * nextFreePos) & 0xf;
	long nextValue = value & ~(0xfL << 4 * nextFreePos);
	nextValue &= ~(0xfL << 4 * freePos);
	nextValue |= movedTileValue << 4 * freePos;
	if (filter.accept(this, nextValue)) {
	    AStarNode newNode = new AStarNode(nextValue, this);
	    int newOptimisticDistanceToSolution = optimisticDistanceToSolution;
	    newOptimisticDistanceToSolution += computeTileDist(freeRow, freeCol, (int) movedTileValue);
	    newOptimisticDistanceToSolution -= computeTileDist(fromRow, fromCol, (int) movedTileValue);
	    newNode.optimisticDistanceToSolution = (short) newOptimisticDistanceToSolution;
	    boards.add(newNode);
	}
    }

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
	return System.lineSeparator() + new SliderBoard(value);
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
	AStarNode other = (AStarNode) obj;
	return value == other.value;
    }
}
