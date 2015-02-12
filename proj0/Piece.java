/* Class for pieces in a Checkers game for Proj0, Spring 2015 CS61B
 * @author Kevin Chen
 */

public class Piece { 
	private int xPosition;
	private int yPosition; 
	private String type;
	private boolean isFire;
	private boolean hasCaptured;
	private Board b;
	private boolean crowned;
	
	public Piece(boolean isFire, Board b, int x, int y, String type) {
		this.xPosition = x;
		this.yPosition = y;
		this.type = type;
		this.isFire = isFire;
		this.hasCaptured = false;
		this.b = b;
		this.crowned = false;	}

	public boolean isFire() {
		return isFire;
	}

	public int side() {
		if (isFire()) {
			return 0;
		}
		else {
			return 1;
		}
	}

	public boolean isKing() {
		return crowned;
	}

	public boolean isBomb() {
		return this.type.equals("bomb");
	}

	public boolean isShield() {
		return this.type.equals("shield");
	}

	public void move(int x, int y) {
		int deltaX = x - xPosition;
		int deltaY = y - yPosition;
		if (Math.abs(deltaX) == 2 && Math.abs(deltaY) == 2) {
			if (deltaX == 2 && deltaY == 2 && x != 0 && y != 0 && b.pieceAt(x-1, y-1) != null && b.pieceAt(x-1, y-1).side() != side()) {
					b.remove(x-1,y-1);
			}
			else if (deltaX == -2 && deltaY == 2 && y != 0 &&  b.pieceAt(x+1, y-1) != null && b.pieceAt(x+1, y-1).side() != this.side()) {
					b.remove(x+1,y-1);
			}
			else if (deltaX == -2 && deltaY == -2 && b.pieceAt(x+1, y+1) != null && b.pieceAt(x+1, y+1).side() != this.side()) {
					b.remove(x+1,y+1);
			}
			else if (deltaX == 2 && deltaY == -2 && x != 0 && b.pieceAt(x-1, y+1) != null && b.pieceAt(x-1, y+1).side() != this.side()) {
					b.remove(x-1,y+1);
			}
			hasCaptured = true;
		}
		if ((isFire() && y == 7) || (!isFire() && y == 0)) {
			crowned = true;
		}
		b.place(null, xPosition, yPosition);
		b.place(this, x, y);
		xPosition = x;
		yPosition = y;
		if (isBomb() && hasCaptured()) {
			bombExplosion(x,y);
		}
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
			if (b.pieceAt(left,above) != null && !b.pieceAt(left,above).isShield()) {
				b.remove(left, above);
			}
		}
		if (intervalContains(0,8,right) && intervalContains(0,8,above)) {
			if (b.pieceAt(right,above) != null && !b.pieceAt(right,above).isShield()) {
				b.remove(right, above);
			}
		}
		if (intervalContains(0,8,left) && intervalContains(0,8,below)) {
			if (b.pieceAt(left,below) != null && !b.pieceAt(left,below).isShield()) {
				b.remove(left, below);
			}
		}
		if (intervalContains(0,8,right) && intervalContains(0,8,below)) {
			if (b.pieceAt(right,below) != null && !b.pieceAt(right,below).isShield()) {
				b.remove(right, below);
			}
		}
		b.remove(x,y);
	}

	public boolean hasCaptured() {
		return hasCaptured;
	}

	public void doneCapturing() {
		hasCaptured = false;
	}
}