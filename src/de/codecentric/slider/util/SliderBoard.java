package de.codecentric.slider.util;

import java.util.Arrays;

public class SliderBoard {
    public static final long SOLUTION_BOARD = 0xfedcba987654321L;
    private static final int SIZE = 4;
    private final byte[] tiles = new byte[SIZE * SIZE];

    /**
     * Create a board in start/solved setting.
     */
    public SliderBoard() {
	for (int i = 0; i < tiles.length - 1; i++) {
	    tiles[i] = (byte) (i + 1);
	}
	// automatically: tiles[tiles.length - 1] = 0;
    }

    /**
     * Create a board from a set of tiles (starting in upper left corner, row by row).
     * 
     * @param tiles
     *            Values from 1..15 and 0 for the free position.
     * @throws IllegalArgumentException
     *             If size of array is wrong or values are duplicate or out of range 0..15.
     */
    public SliderBoard(byte[] tiles) {
	if (tiles.length != SIZE * SIZE) {
	    throw new IllegalArgumentException("Only 4*4 is supported");
	}
	checkTileValues(tiles);
	System.arraycopy(tiles, 0, this.tiles, 0, SIZE * SIZE);
    }

    public SliderBoard(long value) {
	long shift = value;
	for (int i = 0; i < tiles.length; i++) {
	    tiles[i] = (byte)(shift & 0xf);
	    shift >>= 4;
	}
    }

    /**
     * @return Values of tiles packed in one long with tile 0 as lowest and tile 15 as highest bits.
     */
    public long asLong() {
	long value = 0;
	for (int i = tiles.length - 1; i >= 0; i--) {
	    value = (value << 4) | tiles[i];
	}
	return value;
    }

    private void checkTileValues(byte[] tiles) {
	boolean[] check = new boolean[SIZE * SIZE];
	for (int i = 0; i < tiles.length; i++) {
	    if (tiles[i] < 0 || tiles[i] > 15) {
		throw new IllegalArgumentException("Value at index " + i + " out of range: " + tiles[i]);
	    }
	    if (check[tiles[i]]) {
		throw new IllegalArgumentException("Duplicate value: " + tiles[i]);
	    }
	    check[tiles[i]] = true;
	}
    }
    
    public boolean isSolution() {
	boolean correct = tiles[tiles.length - 1] == 0;

	for (int i = 0; correct && i < tiles.length - 1; i++) {
	    correct &= tiles[i] == i + 1;
	}
	return correct;
    }

    /**
     * @param row
     *            Row, 0..size-1
     * @param col
     *            , Column, 0..size-1
     * @return Value at position, 1..size*size-1 and 0 for free
     */
    public int valueAt(int row, int col) {
	if (row < 0 || row >= SIZE) {
	    throw new IllegalArgumentException("illegal row value: " + row);
	}
	if (col < 0 || col >= SIZE) {
	    throw new IllegalArgumentException("illegal col value: " + col);
	}
	return tiles[position(row, col)];
    }

    private int position(int row, int column) {
	return row * SIZE + column;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder(System.lineSeparator());
	for (int r = 0; r < SIZE; r++) {
	    for (int c = 0; c < SIZE; c++) {
		byte value = tiles[position(r, c)];
		if (value == 0) {
		    sb.append("    ");
		} else {
		    sb.append(String.format("%3d ", value));
		}
	    }
	    sb.append(System.lineSeparator());
	}
	return sb.toString();
    }

    @Override
    public int hashCode() {
	return Arrays.hashCode(tiles);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	SliderBoard other = (SliderBoard) obj;
	if (!Arrays.equals(tiles, other.tiles))
	    return false;
	return true;
    }
}
