// Make sure to make this class a part of the synthesizer package
package synthesizer;

public class ArrayRingBuffer extends AbstractBoundedQueue {
  /* Index for the next dequeue or peek. */
  private int first;           
  /* Index for the next enqueue. */
  private int last;             
  /* Array for storing the buffer data. */
  private double[] rb;

  /** Create a new ArrayRingBuffer with the given capacity. */
  public ArrayRingBuffer(int capacity) {
    this.first = 0;
    this.last = 0;
    this.fillCount = 0;
    this.capacity = capacity;
    this.rb = new double[capacity];
    // TODO: Create new array with capacity elements.
    //       first, last, and fillCount should all be set to 0. 
    //       this.capacity should be set appropriately. Note that the local variable
    //       here shadows the field we inherit from AbstractBoundedQueue.
  }

  /** Adds x to the end of the ring buffer. If there is no room, then
    * throw new RuntimeException("Ring buffer overflow") 
    */
  public void enqueue(double x) {
    if (isFull()) {
      throw new RuntimeException("Ring buffer overflow");
    }
    else {
      rb[last] = x;
      if (last == capacity()-1) {
        last = 0;
      }
      else {
        last++;
      }
      fillCount++;
    }
    // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
    // is there room?
  }

  /** Dequeue oldest item in the ring buffer. If the buffer is empty, then
    * throw new RuntimeException("Ring buffer underflow");
    */
  public double dequeue() {
    if (isEmpty()) {
      throw new RuntimeException("Ring buffer underflow");
    }
    else {
      double result = rb[first];
      rb[first] = 0;
      if (first == capacity()-1) {
        first = 0;
      }
      else {
        first++;
      }
      fillCount--;
      return result;
    }
    // TODO: Dequeue the first item. Don't forget to decrease fillCount and update first.
  }

  /** Return oldest item, but don't remove it. */
  public double peek() {
    if (isEmpty()) {
      throw new RuntimeException("Ring buffer underflow");
    }
    else {
      return rb[first];
    }
    // TODO: Return the first item. None of your instance variables should change.
  }

}
