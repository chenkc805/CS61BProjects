/* Class for the board in a Checkers game for Proj0, Spring 2015 CS61B
 * @author Kevin Chen
 */

public class Board { 
	public int turn;
	private static Piece[][] pieces;
	public static boolean selected;
	public static Piece selectedPiece;
	public Board board;
	private int selectedXPosition;
	private int selectedYPosition;
	public static boolean moved;

    /** Draws an N x N board. Adapted from:
        http://introcs.cs.princeton.edu/java/15inout/CheckerBoard.java.html
     */

    private static void drawBoard(int N) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((i + j) % 2 == 0) StdDrawPlus.setPenColor(StdDrawPlus.GRAY);
                else                  StdDrawPlus.setPenColor(StdDrawPlus.RED);
                StdDrawPlus.filledSquare(i + .5, j + .5, .5);
                StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
                if (pieces[i][j] != null) {
                	Piece aPiece = pieces[i][j];
                	if (aPiece.isFire()) {
	                	if (aPiece.isBomb()) {
	                		StdDrawPlus.picture(i + .5, j + .5, "img/bomb-fire.png", 1, 1);	
	                	}
	                	else if (aPiece.isShield()) {
	                		StdDrawPlus.picture(i + .5, j + .5, "img/shield-fire.png", 1, 1);	
	                	}
	                	else {
	                		StdDrawPlus.picture(i + .5, j + .5, "img/pawn-fire.png", 1, 1);	
	                	}
	                }           
	                else if (!aPiece.isFire()) {
	                	if (aPiece.isBomb()) {
	                		StdDrawPlus.picture(i + .5, j + .5, "img/bomb-water.png", 1, 1);	
	                	}
	                	else if (aPiece.isShield()) {
	                		StdDrawPlus.picture(i + .5, j + .5, "img/shield-water.png", 1, 1);	
	                	}
	                	else {
	                		StdDrawPlus.picture(i + .5, j + .5, "img/pawn-water.png", 1, 1);	
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
            drawBoard(N);
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
	        	Piece pawn = new Piece(true, board, i, 0, "pawn");
	        	this.place(pawn, i, 0);
        	}
			for (int i = 1; i < N; i += 2) {
	        	Piece shield = new Piece(true, board, i, 1, "shield");
	        	this.place(shield, i, 1);
        	}
			for (int i = 0; i < N; i += 2) {
	        	Piece bomb = new Piece(true, board, i, 2, "bomb");
	        	this.place(bomb, i, 2);
        	}
			for (int i = 1; i < N; i += 2) {
	        	Piece pawn = new Piece(false, board, i, 7, "pawn");
	        	this.place(pawn, i, 7);
        	}
			for (int i = 0; i < N; i += 2) {
	        	Piece shield = new Piece(false, board, i, 6, "shield");
	        	this.place(shield, i, 6);
        	}
			for (int i = 1; i < N; i += 2) {
	        	Piece bomb = new Piece(false, board, i, 5, "bomb");
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
		if (deltaYAbs != deltaYAbs || deltaXAbs > 2 || xFinal > 7 || yFinal > 7 || xFinal < 0 || yFinal < 0) {
			return false;
		}
		if (deltaXAbs == 1 && finalPiece == null) {
			return true;
		}
		if (deltaXAbs == 2 && finalPiece == null) {
			if (startPiece.isKing() || startPiece.isFire()) {
				if (pieceAt(xFinal-1, yFinal-1).side() != startPiece.side() && deltaX == 2 && deltaY == 2) {
					return true;
				}
				else if (pieceAt(xFinal+1, yFinal-1).side() != startPiece.side() && deltaX == -2 && deltaY == 2) {
					return true;
				}
			}
			if (startPiece.isKing() || !startPiece.isFire()) {
				if (pieceAt(xFinal-1, yFinal-1).side() != startPiece.side() && deltaX == -2 && deltaY == -2) {
					return true;
				}
				else if (pieceAt(xFinal-1, yFinal-1).side() != startPiece.side() && deltaX == 2 && deltaY == -2) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean canSelect(int x, int y) {
		Piece aPiece = pieces[x][y];
		if (aPiece != null) {  									// There is a piece at x,y
			if (this.turn != aPiece.side()) {					// Check if the piece is on the player's side
				return false;									//
			}													//
			if (selected == false) {							// Check if that square is already selected
				return true;									//
			}													// 
			if (selected == true && this.moved == false) {	// Check if the player has selected a piece but has not moved it
				return true;
			}
		}
		// >>>>Return to this later when you have implemented move()<<<<
		else {
			if (selected == true) {
				if (this.moved == false) {
					return validMove(selectedXPosition, selectedYPosition, x,y);
				}
				else if (this.moved && selectedPiece.hasCaptured()) {
					return validMove(selectedXPosition, selectedYPosition, x,y);
				}	
			}
		}
		return false;
	}

	public void select(int x, int y) {
		if (selected == true) {
			if (canSelect(x,y)) {
				selectedPiece.move(x,y);
				this.moved = true;
				this.pieces[selectedXPosition][selectedYPosition] = null;
				if (selectedPiece.isBomb()) {
					bombExplosion(x,y);
				}
				else {
					this.pieces[x][y] = selectedPiece;
					selectedXPosition = x;
					selectedYPosition = y;
				}
			}
		}
		else if (pieces[x][y] != null) {
			selectedPiece = pieces[x][y];
			selected = true;
			selectedXPosition = x;
			selectedYPosition = y;
		}
	}

	public void place(Piece p, int x, int y) {
		pieces[x][y] = p;
	}

	public Piece remove(int x, int y) {
		Piece piece = pieces[x][y];
		pieces[x][y] = null;
		return piece;
	}

	public boolean canEndTurn() {
		if (this.moved = true) {
			return true;
		}
		else {
			return false;
		}
	}

	public void endTurn() {
		turn = Math.abs(this.turn-1);
		moved = false;
		selected = false;
		selectedPiece = null;
		selectedXPosition = -1;
		selectedYPosition = -1;
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
		Piece[] explosionRadius = new Piece[4];
		if (intervalContains(0,8,left) && intervalContains(0,8,above)) {
			explosionRadius[0] = pieces[left][above];
		}
		if (intervalContains(0,8,right) && intervalContains(0,8,above)) {
			explosionRadius[1] = pieces[right][above];
		}
		if (intervalContains(0,8,left) && intervalContains(0,8,below)) {
			explosionRadius[2] = pieces[left][below];
		}
		if (intervalContains(0,8,right) && intervalContains(0,8,below)) {
			explosionRadius[3] = pieces[right][below];
		}
		for (int i = 0; i < 4; i++) {
			if (explosionRadius[i] != null) {
				if (explosionRadius[i].isShield()) {
				}
				else {
					explosionRadius[i] = null;
				}
			}
		}
	}

	public String winner() {
		if (turn == 0) { 
			if (this.numberOfPieces(1) == 0) {
				if (this.numberOfPieces(0) == 0) {
					return "No one";
				}
					return "Fire";
			}
				return null;
		}
		else {
			if (this.numberOfPieces(0) == 0) {
				if (this.numberOfPieces(1) == 0) {
					return "No one";
				}
					return "Water";
			}
				return null;
		}
	}













	
}