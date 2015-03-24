package de.codecentric.slider.idastar;

import java.util.ArrayList;
import java.util.List;

import de.codecentric.slider.util.Node;
import de.codecentric.slider.util.SliderBoard;
import de.codecentric.slider.util.filter.NodeFilter;

public class IDAStarNode implements Node<IDAStarNode> {
    private static final int SIZE = 4;
    private final long value;
    private final IDAStarNode previous;
    private final int movesDone;
    private final int optimisticDistanceToSolution;

    public IDAStarNode(SliderBoard board) {
	if (board == null) {
	    throw new IllegalArgumentException("board is null");
	}
	value = board.asLong();
	this.previous = null;
	movesDone = 0;
	optimisticDistanceToSolution = computeOptimisticDistanceToSolution();
    }

    private IDAStarNode(long value, IDAStarNode previous, int optimisticDistanceToSolution) {
	this.value = value;
	this.previous = previous;
	movesDone = previous.movesDone + 1;
	this.optimisticDistanceToSolution = optimisticDistanceToSolution;
    }
    
    public SliderBoard getBoard() {
	return new SliderBoard(value);
    }

    public IDAStarNode getPrevious() {
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
     * @return An optimistic estimate of the number of moves from this node to the
     * solution. May be smaller than real value, but never bigger. 
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
    public List<IDAStarNode> nextNodes(NodeFilter<IDAStarNode> filter) {
	List<IDAStarNode> nextNodes = new ArrayList<>(4);
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

    private void addNode(List<IDAStarNode> boards, int freeRow, int freeCol, int fromRow, int fromCol, NodeFilter<IDAStarNode> filter) {
	int freePos = freeRow * SIZE + freeCol;
	int nextFreePos = fromRow * SIZE + fromCol;
	long movedTileValue = (value >> 4 * nextFreePos) & 0xf;
	long nextValue = value & ~(0xfL << 4 * nextFreePos);
	nextValue &= ~(0xfL << 4 * freePos);
	nextValue |= movedTileValue << 4 * freePos;
	if (filter.accept(this, nextValue)) {
	    int newOptimisticDistanceToSolution = optimisticDistanceToSolution;
	    newOptimisticDistanceToSolution += computeTileDist(freeRow, freeCol, (int)movedTileValue);
	    newOptimisticDistanceToSolution -= computeTileDist(fromRow, fromCol, (int)movedTileValue);
	    IDAStarNode newNode = new IDAStarNode(nextValue, this, newOptimisticDistanceToSolution);
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
	int rowBits = (int)(value >> row * 16) & 0xffff;
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
	IDAStarNode other = (IDAStarNode) obj;
	return value == other.value;
    }
}
