
public class DoubleChain {
	
	private DNode head;
	
	public DoubleChain(double val) {
		/* your code here. */
		head = new DNode(val); 
	}

	public DNode getFront() {
		return head;
	}

	/** Returns the last item in the DoubleChain. */		
	public DNode getBack() {
		DNode pointer = head;
		while (pointer.next != null) {
			pointer = pointer.next;
		}
		return pointer;
	}
	
	/** Adds D to the front of the DoubleChain. */	
	public void insertFront(double d) {
		head.prev = new DNode(null, d, head);
		head = head.prev;
	}
	
	/** Adds D to the back of the DoubleChain. */	
	public void insertBack(double d) {
		DNode pointer = head;
		while (pointer.next != null) {
			pointer = pointer.next;
		}
		pointer.next = new DNode(pointer, d, null);
	}
	
	/** Removes the last item in the DoubleChain and returns it. 
	  * This is an extra challenge problem. */
	public DNode deleteBack() {
		/* your code here */
		DNode pointer = head;
		while (pointer.next.next != null) {
			pointer = pointer.next;
		}
		DNode lastDNode = pointer.next;
		pointer.next = null;
		return lastDNode;
	}
	
	/** Returns a string representation of the DoubleChain. 
	  * This is an extra challenge problem. */
	public String toString() {
		/* your code here */		
		return null;
	}

	public static class DNode {
		public DNode prev;
		public DNode next;
		public double val;
		
		private DNode(double val) {
			this(null, val, null);
		}
		
		private DNode(DNode prev, double val, DNode next) {
			this.prev = prev;
			this.val = val;
			this.next =next;
		}
	}
	
}
