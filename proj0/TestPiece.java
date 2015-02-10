import static org.junit.Assert.*;

import org.junit.Test;

/** Perform tests of the Piece class
 */

public class TestPiece {

	public Board board = new Board(true);
	public Piece tester = new Piece(true, board, 0, 0, "pawn");
	public Piece oppTester = new Piece(false, board, 1, 1, "pawn");
    /** Tests the constructor of DoubleChain */

    @Test
    public void testMove() {
    	board.place(tester, 0,0);
        board.select(0,0);
    	assertEquals(null, board.pieceAt(1,1));
        board.select(1,1);
    	tester.move(1, 1);
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
    public void testIsKing() {
    	assertEquals(false, tester.isKing());
    }

    @Test
    public void testIsBomb() {
    	assertEquals(false, tester.isBomb());
    }

    @Test
    public void testIsShield() {
    	assertEquals(false, tester.isShield());
    }

    @Test
    public void testHasCaptured() {
    	assertEquals(false, tester.hasCaptured());
    }


    // @Test
    // public void testCapture() {
    // 	Board board = new Board(true);
    // 	board.place(tester, 0,0);
    // 	board.place(oppTester, 1, 1);
    // 	tester.move(2,2);
    // 	assertEquals(true, tester.hasCaptured());
    // 	assertEquals(null, board.pieceAt(1,1));
    // }

    /** Tests some basic DoubleChain operations. */
    @Test
    public void testBasicOperations() {

    }

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestPiece.class);
    }
}