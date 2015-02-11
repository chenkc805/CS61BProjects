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
		System.out.println("move");
		int deltaX = x - xPosition;
		int deltaY = y - yPosition;
		if (Math.abs(deltaX) == 2 && Math.abs(deltaY) == 2) {
			if (b.pieceAt(x-1, y-1) != null && b.pieceAt(x-1, y-1).side() != side() && deltaX == 2 && deltaY == 2) {
					b.remove(x-1,y-1);
			}
			else if (b.pieceAt(x+1, y-1) != null && b.pieceAt(x+1, y-1).side() != this.side() && deltaX == -2 && deltaY == 2) {
					b.remove(x+1,y-1);
			}
			else if (b.pieceAt(x+1, y+1) != null && b.pieceAt(x+1, y+1).side() != this.side() && deltaX == -2 && deltaY == -2) {
					b.remove(x+1,y+1);
			}
			else if (b.pieceAt(x-1, y+1) != null && b.pieceAt(x-1, y+1).side() != this.side() && deltaX == 2 && deltaY == -2) {
					b.remove(x-1,y+1);
			}
			xPosition = x;
			yPosition = y;
			hasCaptured = true;
		}
		else if (Math.abs(deltaX) == 1 && Math.abs(deltaY) == 1) {
			xPosition = x;
			yPosition = y;
		}
		if ((isFire() && y == 7) || (!isFire() && y == 0)) {
			crowned = true;
		}
	}

	public boolean hasCaptured() {
		return hasCaptured;
	}

	public void doneCapturing() {
		hasCaptured = false;
	}
}