import static org.junit.Assert.*;

import org.junit.Test;

/** Perform tests of the Piece class
 */

public class TestBoard {

	public Board board = new Board(true);
	public Piece tester = new Piece(true, board, 0, 0, "pawn");
    public Piece oppTester0 = new Piece(false, board, 3, 3, "pawn");
    public Piece oppTester1 = new Piece(false, board, 5, 3, "pawn");
    public Piece oppTester2 = new Piece(false, board, 0, 0, "pawn");
	public Piece oppTester = new Piece(false, board, 1, 1, "pawn");
    /** Tests the constructor of DoubleChain */

    @Test
    public void testCanSelect() {
    	Board board = new Board(true);
    	board.place(tester, 0,0);
        board.select(0,0);
    	assertEquals(true, board.canSelect(1,1));
        board.select(1,1);
    	tester.move(1, 1);
    	assertEquals(false, tester.hasCaptured());
    	assertEquals(tester, board.pieceAt(1,1));
    }

    @Test
    public void testPlace() {
        board.place(tester,1,1);
        board.place(oppTester, 0,0);
    	assertEquals(null, board.pieceAt(1,2));
        assertEquals(tester, board.pieceAt(1,1));
        assertEquals(oppTester, board.pieceAt(0,0));
    }

    @Test
    public void testRemove() {
        board.place(tester,1,1);
        board.place(oppTester, 0,0);
        board.remove(1,1);
        assertEquals(null, board.pieceAt(1,1));
        assertEquals(oppTester, board.pieceAt(0,0));
    }

    @Test
    public void testSelect() {
    	board.place(tester,1,1);
        board.place(oppTester, 0,0);
        assertEquals(true, board.canSelect(1,1));
        assertEquals(false, board.canSelect(0,0));
        board.select(1,1);
        board.select(2,2);
        assertEquals(tester, board.pieceAt(2,2));
    }

    @Test
    public void testEndTurn() {
        board.place(tester, 0,0);
        board.select(0,0);
        assertFalse(board.canEndTurn());
        board.select(1,1);
        tester.move(1, 1);
        assertTrue(board.canEndTurn());
        board.endTurn();
    }

    @Test
    public void testWinner() {
        board.place(tester,0,0);
        board.place(oppTester, 1,1);
        board.select(0,0);
        board.select(2,2);
        board.remove(1,1);
        assertEquals("Fire", board.winner());
    }

    @Test
    public void testMultiCapture() {
        board.place(tester,0,0);
        board.place(oppTester,1,1);
        board.place(oppTester0,3,3);
        board.place(oppTester1,5,3);
        board.select(0,0);
        board.select(2,2);
        board.select(4,4);
        board.select(6,2);
        assertNull(board.pieceAt(1,1));
        assertNull(board.pieceAt(3,3));
        assertNull(board.pieceAt(5,3));

    }

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestBoard.class);
    }
}