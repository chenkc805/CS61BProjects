import static org.junit.Assert.*;

import org.junit.Test;

/** Perform tests of the Piece class
 */

public class TestBoard {

	private Board board = new Board(true);
	private Piece tester = new Piece(true, board, 0, 0, "pawn");
    private Piece testerOOB = new Piece(true, board, 123, 15, "pawn");
    private Piece tester0 = new Piece(true, board, 1, 1, "pawn");
    private Piece oppTester0 = new Piece(false, board, 3, 3, "pawn");
    private Piece bomb = new Piece(false, board, 3, 3, "bomb");
    private Piece bombOOB = new Piece(false, board, -23, 4, "bomb");
    private Piece oppTester1 = new Piece(false, board, 5, 3, "pawn");
    private Piece oppTester2 = new Piece(false, board, 0, 0, "pawn");
	private Piece oppTester = new Piece(false, board, 1, 1, "pawn");
    /** Tests the constructor of DoubleChain */

    @Test
    public void testCanSelect() {
    	board.place(tester, 0,0);
        board.select(0,0);
    	assertTrue(board.canSelect(1,1));
        board.select(1,1);
    	assertFalse(tester.hasCaptured());
    	assertEquals(tester, board.pieceAt(1,1));
    }

    @Test
    public void testPlace() {
        board.place(tester0,1,1);
        board.place(testerOOB, 123,15);
        board.place(bomb,-23,4);
        assertFalse(board.canSelect(9,9));
        assertFalse(board.canSelect(-23,4));
        assertFalse(board.canSelect(10, -9));
        assertTrue(board.canSelect(1,1));
        board.place(oppTester, 0,0);
    	assertEquals(null, board.pieceAt(1,2));
        assertEquals(tester0, board.pieceAt(1,1));
        assertEquals(oppTester, board.pieceAt(0,0));
    }

    @Test
    public void testPieceAt() {
        board.place(tester0,1,1);
        board.place(testerOOB, 123,15);
        board.place(bomb,-23,4);
        assertNull(board.pieceAt(-23,5));
        assertNull(board.pieceAt(5,5));
    }

    @Test
    public void testRemove() {
        board.place(tester,0,0);
        board.place(oppTester,1,1);
        board.place(oppTester0,3,3);
        board.place(oppTester1,5,3);
        assertEquals(oppTester0, board.remove(3,3));
        assertNull(board.remove(5,5));
        assertNull(board.remove(9,9));
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
        assertNull(board.winner());
        board.select(0,0);
        board.select(2,2);
        assertEquals("Fire", board.winner());
    }

    @Test
    public void testWaterWinner() {
        board.place(tester0,1,1);
        board.place(oppTester0,3,3);
        board.select(1,1);
        board.select(2,2);
        board.endTurn();
        board.select(3,3);
        board.select(1,1);
        assertEquals(oppTester0, board.pieceAt(1,1));
        assertNull(board.pieceAt(2,2));
        assertEquals("Water", board.winner());
    }

    @Test
    public void testBombWinner() {
        board.place(tester0,1,1);
        board.place(bomb,3,3);
        board.select(1,1);
        board.select(2,2);
        board.endTurn();
        board.select(3,3);
        board.select(1,1);
        assertNull(board.pieceAt(1,1));
        assertNull(board.pieceAt(2,2));
        assertEquals("No one", board.winner());
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