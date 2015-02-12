/* Class for the board in a Checkers game for Proj0, Spring 2015 CS61B
 * @author Kevin Chen
 *
 * What to fix: 
 * King movement
 * Bomb should remove itself when it captures anoter piece
 * Multicapture
 * Check 3 different outcomes for winners
 *
 *
 *
 */

public class Board { 
	private int turn;
	private int N;
	private Piece[][] pieces;
	private boolean selected;
	private Piece selectedPiece;
	private int selectedXPosition;
	private int selectedYPosition;
	private boolean moved;
	// public Piece tester = new Piece(true, this, 0, 0, "shield");
 //    public Piece tester0 = new Piece(true, this, 1, 1, "pawn");
 //    public Piece tester1 = new Piece(true, this, 1, 1, "pawn");
 //    public Piece oppTester0 = new Piece(false, this, 3, 3, "pawn");
 //    public Piece bomb = new Piece(false, this, 2, 2, "bomb");
 //    public Piece oppTester1 = new Piece(false, this, 5, 3, "pawn");
 //    public Piece oppTester2 = new Piece(false, this, 0, 0, "pawn");
	// public Piece oppTester = new Piece(false, this, 1, 1, "pawn");

    /** Draws an N x N board. Adapted from:
        http://introcs.cs.princeton.edu/java/15inout/CheckerBoard.java.html
     */

    private void drawBoard(int N) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((i + j) % 2 == 0) StdDrawPlus.setPenColor(StdDrawPlus.GRAY);
                else                  StdDrawPlus.setPenColor(StdDrawPlus.RED);
                if (i == selectedXPosition && j == selectedYPosition) StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
                StdDrawPlus.filledSquare(i + .5, j + .5, .5);
                StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
                if (pieces[i][j] != null) {
                	Piece aPiece = pieces[i][j];
                	if (aPiece.isFire()) {
	                	if (aPiece.isBomb()) {
	                		if (aPiece.isKing()) {
	                			StdDrawPlus.picture(i + .5, j + .5, "img/bomb-fire-crowned.png", 1, 1);	
	                		}
	                		else {
	                			StdDrawPlus.picture(i + .5, j + .5, "img/bomb-fire.png", 1, 1);	
	                		}	
	                	}
	                	else if (aPiece.isShield()) {
	                		if (aPiece.isKing()) {
	                			StdDrawPlus.picture(i + .5, j + .5, "img/shield-fire-crowned.png", 1, 1);	
	                		}
	                		else {
	                			StdDrawPlus.picture(i + .5, j + .5, "img/shield-fire.png", 1, 1);	
	                		}
	                	}
	                	else {
	                		if (aPiece.isKing()) {
	                			StdDrawPlus.picture(i + .5, j + .5, "img/pawn-fire-crowned.png", 1, 1);	
	                		}
	                		else {
	                			StdDrawPlus.picture(i + .5, j + .5, "img/pawn-fire.png", 1, 1);	
	                		}
	                	}
	                }           
	                else if (!aPiece.isFire()) {
	                	if (aPiece.isBomb()) {
	                		if (aPiece.isKing()) {
	                			StdDrawPlus.picture(i + .5, j + .5, "img/bomb-water-crowned.png", 1, 1);	
	                		}	                		
	                		else {
	                			StdDrawPlus.picture(i + .5, j + .5, "img/bomb-water.png", 1, 1);	
	                		}
	                	}
	                	else if (aPiece.isShield()) {
	                		if (aPiece.isKing()) {
	                			StdDrawPlus.picture(i + .5, j + .5, "img/shield-water-crowned.png", 1, 1);	
	                		}
	                		else {
	                			StdDrawPlus.picture(i + .5, j + .5, "img/shield-water.png", 1, 1);	
	                		}
	                	}
	                	else {
	                		if (aPiece.isKing()) {
	                			StdDrawPlus.picture(i + .5, j + .5, "img/pawn-water-crowned.png", 1, 1);	
	                		}
	                		else {
	                			StdDrawPlus.picture(i + .5, j + .5, "img/pawn-water.png", 1, 1);
	                		}	
	                	}             	 	
                    }	
                }
            }
        }
    }

    public static void main(String[] args) {
        int N = 8;
        StdDrawPlus.setXscale(0, N);
        StdDrawPlus.setYscale(0, N);
        Board board = new Board(false);

        /** Monitors for mouse presses. Wherever the mouse is pressed,
            a new piece appears. */
        while(true) {
            board.drawBoard(N);
            if (StdDrawPlus.mousePressed()) {
                double x = StdDrawPlus.mouseX();
                double y = StdDrawPlus.mouseY();
                if (board.canSelect((int) x, (int) y)) {
                	board.select((int) x, (int) y);
                }
            }
            if (StdDrawPlus.isSpacePressed() && board.canEndTurn()){
            	board.endTurn();
            }
            if (board.winner() != null) {
            	System.out.println(board.winner());
            }
            StdDrawPlus.show(100);
        }
    }

	public Board(boolean shouldBeEmpty) {
		this.N = 8;
		this.pieces = new Piece[N][N];
		this.turn = 0;
		this.selected = false;
		this.selectedPiece = null;
		this.moved = false;
		this.selectedXPosition = -1;
		this.selectedYPosition = -1;
		if (!shouldBeEmpty) {
			for (int i = 0; i < N; i += 2) {
	        	Piece pawn = new Piece(true, this, i, 0, "pawn");
	        	this.place(pawn, i, 0);
        	}
			for (int i = 1; i < N; i += 2) {
	        	Piece shield = new Piece(true, this, i, 1, "shield");
	        	this.place(shield, i, 1);
        	}
			for (int i = 0; i < N; i += 2) {
	        	Piece bomb = new Piece(true, this, i, 2, "bomb");
	        	this.place(bomb, i, 2);
        	}
			for (int i = 1; i < N; i += 2) {
	        	Piece pawn = new Piece(false, this, i, 7, "pawn");
	        	this.place(pawn, i, 7);
        	}
			for (int i = 0; i < N; i += 2) {
	        	Piece shield = new Piece(false, this, i, 6, "shield");
	        	this.place(shield, i, 6);
        	}
			for (int i = 1; i < N; i += 2) {
	        	Piece bomb = new Piece(false, this, i, 5, "bomb");
	        	this.place(bomb, i, 5);
        	}            	        	        	
		}
	}
	
	public Piece pieceAt(int x, int y) {
		return pieces[x][y];
	}

	private boolean validMove(int xStart, int yStart, int xFinal, int yFinal) {
		Piece startPiece = pieceAt(xStart, yStart);
		Piece finalPiece = pieceAt(xFinal, yFinal);
		int deltaX = xFinal - xStart;
		int deltaY = yFinal - yStart;
		int deltaXAbs = Math.abs(deltaX);
		int deltaYAbs = Math.abs(deltaY);
		if (startPiece == null) {
			return false;
		}
		if (deltaYAbs != deltaXAbs || deltaXAbs > 2 || xFinal > 7 || yFinal > 7 || xFinal < 0 || yFinal < 0) {
			return false;
		}
		if (deltaXAbs == 1 && finalPiece == null) {
			if (startPiece.isFire() && !startPiece.isKing() && deltaY == -1) {
				return false;
			}
			if (!startPiece.isFire() && !startPiece.isKing() && deltaY == 1) {
				return false;
			}
			if (startPiece.hasCaptured()) {
				return false;
			}
			else {
				return true;
			}
		}
		if (deltaXAbs == 2 && finalPiece == null) {
			if (startPiece.isKing() || startPiece.isFire() || startPiece.hasCaptured()) {
				if (deltaX == 2 && deltaY == 2 && xFinal != 0 && yFinal != 0 && pieceAt(xFinal - 1, yFinal - 1) != null && pieceAt(xFinal-1, yFinal-1).side() != startPiece.side()) {
					return true;
				}
				else if (deltaX == -2 && deltaY == 2 && xFinal != N && yFinal != 0 && pieceAt(xFinal + 1, yFinal - 1) != null && pieceAt(xFinal+1, yFinal-1).side() != startPiece.side()) {
					return true;
				}
			}
			if (startPiece.isKing() || !startPiece.isFire() || startPiece.hasCaptured()) {
				if (deltaX == -2 && deltaY == -2 && xFinal != N && yFinal != N && pieceAt(xFinal + 1, yFinal + 1) != null && pieceAt(xFinal+1, yFinal+1).side() != startPiece.side()) {
					return true;
				}
				else if (deltaX == 2 && deltaY == -2 && xFinal != 0 && yFinal != N && pieceAt(xFinal - 1, yFinal + 1) != null && pieceAt(xFinal-1, yFinal+1).side() != startPiece.side()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean canSelect(int x, int y) {
		Piece aPiece = pieceAt(x,y);
		if (aPiece != null) {  									// There is a piece at x,y
			if (turn != aPiece.side()) {						// Check if the piece is on the player's side
				return false;									//
			}													//
			if (selected == false) {							// Check if that square is already selected
				return true;									//
			}													// 
			if (selected == true && moved == false) {			// Check if the player has selected a piece but has not moved it
				selected = false;
				return true;
			}
		}
		else {
			if (selected == true) {
				if (moved == false) {
					return validMove(selectedXPosition, selectedYPosition, x,y);
				}
				else if (moved && selectedPiece.hasCaptured()) {
					return validMove(selectedXPosition, selectedYPosition, x,y);
				}	
			}
		}
		return false;
	}

	public void select(int x, int y) {
		if (selected == true) {
			selectedPiece.move(x,y);
			moved = true;
		}
		else {
			selectedPiece = pieceAt(x,y);
			selected = true;
		}
		selectedXPosition = x;
		selectedYPosition = y;
	}

	public void place(Piece p, int x, int y) {
		pieces[x][y] = p;
	}

	public Piece remove(int x, int y) {
		Piece piece = pieceAt(x,y);
		pieces[x][y] = null;
		return piece;
	}

	public boolean canEndTurn() {
		return moved;
	}

	public void endTurn() {
		if (canEndTurn()) {
			turn = Math.abs(this.turn-1);
			moved = false;
			selected = false;
			selectedPiece.doneCapturing();
			selectedPiece = null;
			selectedXPosition = -1;
			selectedYPosition = -1;
		}
	}

	private int numberOfPieces(int team) {
		int count = 0;
		for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
            	if (pieces[i][j] != null) {
	            	if (pieces[i][j].side() == team) {
	            		count += 1;
	            	}
	            }
            }
		}
		return count;
	}

	public String winner() {
		if (numberOfPieces(1) == 0 && numberOfPieces(0) == 0) {
			return "No one";
		}
		else if (numberOfPieces(1) == 0) {
			return "Fire";
		}
		else if (numberOfPieces(0) == 0) {
			return "Water";
		}
		else {
			return null;
		}
	}











	
}