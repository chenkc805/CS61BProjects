import java.util.Set; /* java.util.Set needed only for challenge problem. */
import java.util.*;

/** A data structure that uses a linked list to store pairs of keys and values.
 *  Any key must appear at most once in the dictionary, but values may appear multiple
 *  times. Supports get(key), put(key, value), and contains(key) methods. The value
 *  associated to a key is the value in the last call to put with that key. 
 *
 *  For simplicity, you may assume that nobody ever inserts a null key or value
 *  into your map.
 */ 
public class ULLMap<S, T> implements Map61B<S, T>, Iterable<S> { //FIX ME
    /** Keys and values are stored in a linked list of Entry objects.
      * This variable stores the first pair in this linked list. You may
      * point this at a sentinel node, or use it as a the actual front item
      * of the linked list. 
      */
    private Entry front;
    private int size;

    public ULLMap() {
        front = null;
        size = 0;
    }

    public static <T,S> ULLMap<T,S> inverse(ULLMap<S,T> map) {
        if (map.front == null) {
            return null;
        } else {
            ULLMap<T,S> result = new ULLMap<T,S>();
            for (int i = 1; i <= map.size(); i++) {
                result.put(map.front.val, map.front.key);
                map.front = map.front.next;
            }
        return result;
        }
    }

    @Override
    public T get(S key) { //FIX ME
        if (front == null) {
            return null;
        }
        Entry pointer = front;
        if (!containsKey(key)) {
            return null;
        } else {
            for(int i = 1; i <= size; i++) {
                if (pointer.get(key) == null) {
                    pointer = pointer.next;
                } else {
                    return pointer.get(key).val;
                }
            }
        }
        return null;
    }

    @Override
    public void put(S key, T val) { //FIX ME
        if (front == null) {
            front = new Entry(key, val, null);
        } else {
            Entry pointer = front;
            while (pointer.next != null) {
                pointer = pointer.next;
            }
            pointer.next = new Entry(key,val,null); //FILL ME IN
        }
        size++;
    }

    @Override
    public boolean containsKey(S key) { //FIX ME
        if (front == null) {
            return false;
        }
        Entry pointer = front;
        for(int i = 1; i <= size; i++) {
            if (pointer.get(key) == null) {
                pointer = pointer.next;
            } else {
                return true;
            }
        }//FILL ME IN
        return false; //FIX ME
    }

    @Override
    public int size() {
        return size; // FIX ME (you can add extra instance variables if you want)
    }

    @Override
    public void clear() {
        front = null;
        size = 0;
    //FILL ME IN
    }

    public Iterator<S> iterator() {
        return new ULLMapIter();
    }


    /** Represents one node in the linked list that stores the key-value pairs
     *  in the dictionary. */
    private class Entry {
    
        /** Stores KEY as the key in this key-value pair, VAL as the value, and
         *  NEXT as the next node in the linked list. */
        public Entry(S k, T v, Entry n) { //FIX ME
            key = k;
            val = v;
            next = n;
        }

        /** Returns the Entry in this linked list of key-value pairs whose key
         *  is equal to KEY, or null if no such Entry exists. */
        public Entry get(S k) { //FIX ME
            if (k.equals(key)) {
                return this;
            } else { //FILL ME IN (using equals, not ==)
            return null;
            } //FIX ME
        }

        /** Stores the key of the key-value pair of this node in the list. */
        private S key; //FIX ME
        /** Stores the value of the key-value pair of this node in the list. */
        private T val; //FIX ME
        /** Stores the next Entry in the linked list. */
        private Entry next;
    
    }

    private class ULLMapIter implements Iterator<S> {

        public int i;
        public Entry pointer;

        public ULLMapIter() {
            i = 0;
            pointer = front;
        }

        public boolean hasNext() {
            return (i < size());
        }

        public S next() {
            if (hasNext()) {
                S result = pointer.key;
                i += 1;
                pointer = pointer.next;
                return result;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException("Welp."); // In java.lang
        }
    }

    // /* Methods below are all challenge problems. Will not be graded in any way. 
    //  * Autograder will not test these. */
    // @Override
    // public remove(S key) { //FIX ME SO I COMPILE
    //     throw new UnsupportedOperationException();
    // }

    // @Override
    // public remove(S key, T value) { //FIX ME SO I COMPILE
    //     throw new UnsupportedOperationException();
    // }

    // @Override
    // public Set<> keySet() { //FIX ME SO I COMPILE
    //     throw new UnsupportedOperationException();
    // }


}