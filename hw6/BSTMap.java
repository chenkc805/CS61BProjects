import java.util.Iterator;
import java.util.Set;
import java.lang.Comparable;
import java.util.LinkedList;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K,V> {

    int size = 0;
    Node node;
    private Node temp;

    public BSTMap() {
        node = null;
    }

    /** Removes all of the mappings from this map. */
    public void clear() {
        node = null;
        size = 0;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        if (node == null) {
            return false;
        }
        return node.get(key) != null;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key. 
     */
    public V get(K key) {
        if (node == null) {
            return null;
        }
        Node lookup = node.get(key);
        if (lookup == null) {
            return null;
        }
        return lookup._val;
    }

   /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        if (node == null) {
            node = new Node(key, value, null, null);
            size += 1;
        } else {
            Node lookup = node.get(key);
            if (lookup != null) {
                lookup._val = value;
            } else {
                temp = node;
                putHelper(temp, key, value);
                size += 1;
            }
        }
    }

    /* Traverses through the tree from a starting node N 
     * and places KEY/VALUE pair in the right location */
    private void putHelper(Node n, K key, V value) {
        int compare = key.compareTo(n._key);
        if (compare < 0) {
            if (n._left == null) {
                n._left = new Node(key, value, null, null);
            } else {
                putHelper(n._left, key, value);
            }
        } else if (compare > 0) {
            if (n._right == null) {
                n._right = new Node(key, value, null, null);
            } else {
                putHelper(n._right, key, value);   
            }
        }
    }

    /* Prints all the keys in order */
    public void printInOrder() {
        if (node == null) {
            System.out.println("Nothing here");
        } else {
            System.out.println(printInOrderHelper(node));
        }
    }

    private LinkedList<K> printInOrderHelper(Node n) {
        if (n == null) {
            return new LinkedList<K>();
        }
        if (n.isLeaf()) {
            LinkedList<K> littleList = new LinkedList<K>();
            littleList.add(n._key);
            return littleList;
        } else {
            LinkedList<K> result = new LinkedList<K>();
            result.addAll(printInOrderHelper(n._left));
            result.add(n._key);
            result.addAll(printInOrderHelper(n._right));
            return result;
        }
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for HW6. */
    // public V remove(K key);

    /* Removes the Node for the specified key only if it is currently mapped to
     * the specified value. Not required for HW6a. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    /* Returns a Set view of the keys contained in this map. Not required for HW6. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    private class Node {

        /* Constructor for a node of a tree with 
         * KEY/VAL pair and LEFT and RIGHT nodes. */        
        public Node(K key, V val, Node left, Node right) {
            _key = key;
            _val = val;
            _right = right;
            _left = left;
        }

        /* Traverses a tree to find KEY */
        public Node get(K key) {
            if (key == null) {
                return null;
            }
            int compare = key.compareTo(_key);
            if (compare == 0) {
                return this;
            }
            if (compare < 0 && _left != null) {
                return _left.get(key);
            }
            if (compare > 0 && _right != null) {
                return _right.get(key);
            }
            return null;
        }

        /* Checks if a node is a leaf */
        private boolean isLeaf() {
            return _left == null && _right == null;
        }

        private K _key;
        private V _val;
        private Node _right;
        private Node _left;
        
    }
}