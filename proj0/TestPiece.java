import static org.junit.Assert.*;

import org.junit.Test;

/** Perform tests of the Piece class
 */

public class TestPiece {

	public Board board = new Board(true);
	public Piece tester = new Piece(true, board, 0, 0, "pawn");
	public Piece oppTester = new Piece(false, board, 1, 1, "pawn");
    public Piece bombTester = new Piece(true, board, 3, 3, "bomb");
    public Piece shieldTester = new Piece(true, board, 4, 4, "shield");
    /** Tests the constructor of DoubleChain */

    @Test
    public void testMove() {
    	board.place(tester, 0,0);
        board.select(0,0);
    	assertEquals(null, board.pieceAt(1,1));
        board.select(1,1);
    	assertEquals(false, tester.hasCaptured());
    	assertEquals(tester, board.pieceAt(1,1));
    }

    @Test
    public void testIsFire() {
    	assertEquals(true, tester.isFire());
    }

    @Test
    public void testSide() {
    	assertEquals(0, tester.side());
    	assertEquals(1, oppTester.side());
    }

    @Test
    public void testIsBomb() {
    	assertFalse(tester.isBomb());
        assertTrue(bombTester.isBomb());
    }

    @Test
    public void testIsShield() {
        assertFalse(oppTester.isShield());
        assertTrue(shieldTester.isShield());
    }

    @Test
    public void testHasCaptured() {
    	assertEquals(false, tester.hasCaptured());
    }

    @Test
    public void testIsKing() {
        board.place(tester, 4, 6);
        assertFalse(tester.isKing());
        board.select(4,6);
        board.select(5,7);
        assertTrue(tester.isKing());
    }

    @Test
    public void testCapture() {
    	board.place(tester, 0,0);
        assertEquals(tester, board.pieceAt(0,0));
    	board.place(oppTester, 1, 1);
        assertEquals(oppTester, board.pieceAt(1,1));
    	board.select(0,0);
        board.select(2,2);
    	assertEquals(true, tester.hasCaptured());
        assertEquals(tester, board.pieceAt(2,2));
    	assertEquals(null, board.pieceAt(1,1));
    }

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestPiece.class);
    }
}