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
	private Piece[][] pieces;
	private boolean selected;
	private Piece selectedPiece;
	private int selectedXPosition;
	private int selectedYPosition;
	private boolean moved;
	private boolean exploded;

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
                board.select((int) x, (int) y);
            }
            if (StdDrawPlus.isSpacePressed() && board.canEndTurn()){
            	board.endTurn();
            }      
            StdDrawPlus.show(100);
        }
    }

	public Board(boolean shouldBeEmpty) {
		int N = 8;
		this.pieces = new Piece[N][N];
		this.turn = 0;
		this.selected = false;
		this.selectedPiece = null;
		this.moved = false;
		this.selectedXPosition = -1;
		this.selectedYPosition = -1;
		if (shouldBeEmpty) {
		}
		else {
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
			if (startPiece.hasCaptured()) {
				return false;
			}
			else {
				return true;
			}
		}
		if (deltaXAbs == 2 && finalPiece == null) {
			if (startPiece.isKing() || startPiece.isFire() || startPiece.hasCaptured()) {
				if (pieceAt(xFinal-1, yFinal-1) != null && pieceAt(xFinal-1, yFinal-1).side() != startPiece.side() && deltaX == 2 && deltaY == 2) {
					return true;
				}
				else if (pieceAt(xFinal+1, yFinal-1) != null && pieceAt(xFinal+1, yFinal-1).side() != startPiece.side() && deltaX == -2 && deltaY == 2) {
					return true;
				}
			}
			if (startPiece.isKing() || !startPiece.isFire() || startPiece.hasCaptured()) {
				if (pieceAt(xFinal+1, yFinal+1) != null && pieceAt(xFinal+1, yFinal+1).side() != startPiece.side() && deltaX == -2 && deltaY == -2) {
					return true;
				}
				else if (pieceAt(xFinal-1, yFinal+1) != null && pieceAt(xFinal-1, yFinal+1).side() != startPiece.side() && deltaX == 2 && deltaY == -2) {
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
		if (selected == true && canSelect(x,y) && selectedXPosition != x && pieceAt(x,y) == null) {
			if (!moved) { 
				selectedPiece.move(x,y);
				if (selectedPiece.isBomb() && selectedPiece.hasCaptured()) {
					bombExplosion(x,y);
				}
			}
			else if (moved && selectedPiece.hasCaptured() && Math.abs(selectedXPosition-x) == 2){
				selectedPiece.move(x,y);
			}
			moved = true;
			pieces[selectedXPosition][selectedYPosition] = null;
			pieces[x][y] = selectedPiece;
			selectedXPosition = x;
			selectedYPosition = y;
			if (exploded) {
				remove(x,y);
			}
		}
		else if (pieceAt(x,y) != null && canSelect(x,y)) {
			System.out.println("you");
			selectedPiece = pieceAt(x,y);
			selected = true;
			selectedXPosition = x;
			selectedYPosition = y;
		}
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
			exploded = false;
			selectedPiece.doneCapturing();
			selectedPiece = null;
			selectedXPosition = -1;
			selectedYPosition = -1;
			System.out.println("End turn");
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

	private boolean intervalContains(int low, int high, int n) {
    	return n >= low && n < high;
	}

	private void bombExplosion(int x,int y) {
		int left = x-1;
		int right = x+1;
		int above = y+1;
		int below = y-1;
		if (intervalContains(0,8,left) && intervalContains(0,8,above)) {
			if (pieceAt(left,above) != null && !pieceAt(left,above).isShield()) {
				remove(left, above);
			}
		}
		if (intervalContains(0,8,right) && intervalContains(0,8,above)) {
			if (pieceAt(right,above) != null && !pieceAt(right,above).isShield()) {
				remove(right, above);
			}
		}
		if (intervalContains(0,8,left) && intervalContains(0,8,below)) {
			if (pieceAt(left,below) != null && !pieceAt(left,below).isShield()) {
				remove(left, below);
			}
		}
		if (intervalContains(0,8,right) && intervalContains(0,8,below)) {
			if (pieceAt(right,below) != null && !pieceAt(right,below).isShield()) {
				remove(right, below);
			}
		}
		exploded = true;
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