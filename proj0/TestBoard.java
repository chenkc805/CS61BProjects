import static org.junit.Assert.*;

import org.junit.Test;

/** Perform tests of the Piece class
 */

public class TestBoard {

	public Board board = new Board(true);
	public Piece tester = new Piece(true, board, 0, 0, "pawn");
	public Piece oppTester = new Piece(false, board, 1, 1, "pawn");
    /** Tests the constructor of DoubleChain */

    @Test
    public void testCanSelect() {
    	Board board = new Board(true);
    	board.place(tester, 0,0);
        assertEquals(false, board.selected);
        board.select(0,0);
        assertEquals(true, board.selected);
        assertEquals(tester, board.selectedPiece);
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
        board.turn = 0;
    	board.place(tester,1,1);
        board.place(oppTester, 0,0);
        assertEquals(true, board.canSelect(1,1));
        assertEquals(false, board.canSelect(0,0));
    }

    @Test
    public void testEndTurn() {
        board.place(tester, 0,0);
        board.select(0,0);
        board.select(1,1);
        tester.move(1, 1);
        assertEquals(true, board.moved);
        board.endTurn();
        assertEquals(false, board.moved);
        assertEquals(null, board.selectedPiece);
        assertEquals(1, board.turn);
    }

    @Test
    public void testWinner() {
        board.turn = 0;
        board.place(tester,0,0);
        board.place(oppTester, 1,1);
        board.select(0,0);
        board.select(2,2);
        board.remove(1,1);
        assertEquals("Fire", board.winner());
    }



    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestBoard.class);
    }
}