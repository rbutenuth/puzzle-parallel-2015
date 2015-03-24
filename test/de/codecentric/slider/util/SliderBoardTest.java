package de.codecentric.slider.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SliderBoardTest {

    @Test
    public void startBoardAsLong() {
	long value = new SliderBoard().asLong();
	assertEquals(SliderBoard.SOLUTION_BOARD, value);
    }

    @Test
    public void longToBoard() {
	long value = new SliderBoard().asLong();
	SliderBoard board = new SliderBoard(value);
	assertTrue(board.isSolution());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void fromBadTiles() {
	byte[] tiles = new byte[16];
	new SliderBoard(tiles);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void fromBadTilesSize() {
	byte[] tiles = new byte[17];
	new SliderBoard(tiles);
    }
    
    @Test
    public void fromTiles() {
	byte[] tiles = new byte[16];
	for (int i = 0; i < 15; i++) {
	    tiles[i] = (byte) (i + 1);
	}
	SliderBoard board = new SliderBoard(tiles);
	assertTrue(board.isSolution());
	for (int i = 0; i < 15; i++) {
	    assertEquals(i + 1, board.valueAt(i / 4, i % 4));
	}
	assertEquals(0, board.valueAt(3, 3));
    }
    
    @Test
    public void testEquals() {
	SliderBoard b1 = new SliderBoard();
	SliderBoard b2 = new SliderBoard();
	assertEquals(b1, b1);
	assertEquals(b1, b2);
	assertNotEquals(b1, "");
	assertNotEquals(b1, null);
	assertNotEquals(b1, BoardFactory.createSliderBoard(1));
	assertEquals(b1.hashCode(), b2.hashCode());
    }
}
