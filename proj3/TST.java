import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;

/**
 *  The <tt>TST</tt> class represents an symbol table of key-value
 *  pairs, with string keys and generic values.
 *  It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>,
 *  <em>delete</em>, <em>size</em>, and <em>is-empty</em> methods.
 *  It also provides character-based methods for finding the string
 *  in the symbol table that is the <em>longest prefix</em> of a given prefix,
 *  finding all strings in the symbol table that <em>start with</em> a given prefix,
 *  and finding all strings in the symbol table that <em>match</em> a given pattern.
 *  A symbol table implements the <em>associative array</em> abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be <tt>null</tt>&mdash;setting the
 *  value associated with a key to <tt>null</tt> is equivalent to deleting the key
 *  from the symbol table.
 *  <p>
 *  This implementation uses a ternary search trie.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/52trie">Section 5.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 * 
 *  This implementation has been modified to satisfy the AutocompleteTST.java needs. 
 *  The author of this modification is:
 *  @author Kevin Chen 
 */
public class TST {
    private int N;              // size
    private TSTNode root;          // root of TST
    private static final int SIZE_OF_PQ = 11;
    private final StringNodeComparator STRING_NODE_COMPARATOR = new StringNodeComparator();
    private final NodeComparator NODE_COMPARATOR = new NodeComparator();
    private PriorityQueue<TSTNode> pqNodes;
    public int k;

    /**
     * Initializes an empty string symbol table.
     */
    public TST() {
        pqNodes = new PriorityQueue<TSTNode>(SIZE_OF_PQ, NODE_COMPARATOR);
    }

    public void clear() {
        root.left = null;
        root.right = null;
        root.mid = null;
        root.c = '\u0000';
        root.val = -1;
        root.maxVal = -1;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return N;
    }

    /**
     * Does this symbol table contain the given key?
     * @param key the key
     * @return <tt>true</tt> if this symbol table contains <tt>key</tt> and
     *     <tt>false</tt> otherwise
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
     */
    public boolean contains(String key) {
        if (key == null || key.length() == 0) throw new IllegalArgumentException();
        return get(key) != -1;
    }

    /**
     * Returns the value associated with the given key.
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     *     and <tt>null</tt> if the key is not in the symbol table
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
     */
    public double get(String key) {
        TSTNode x = get(root, key, 0);
        if (x == null) return -1;
        return x.val;
    }

    // return subtrie corresponding to given key
    private TSTNode get(TSTNode x, String key, int d) {
        if (x == null) return null;
        char c = key.charAt(d);
        if      (c < x.c)              return get(x.left,  key, d);
        else if (c > x.c)              return get(x.right, key, d);
        else if (d < key.length() - 1) return get(x.mid,   key, d+1);
        else                           return x;
    }

    /**
     * Inserts the key-value pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table.
     * If the value is <tt>null</tt>, this effectively deletes the key from the symbol table.
     * @param key the key
     * @param val the value
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
     */
    public void put(String key, double val) {
        if (key == null || key.length() == 0) throw new IllegalArgumentException();
        if (!contains(key)) N++;
        root = put(root, key, val, 0);
    }

    private TSTNode put(TSTNode x, String key, double val, int d) {
        if (key == null || key.length() == 0) throw new IllegalArgumentException();
        char c = key.charAt(d);
        if (x == null) {
            x = new TSTNode();
            x.c = c;
            x.maxVal = val;
        }
        if (x.maxVal < val) {
            x.maxVal = val;
        }
        if      (c < x.c)               x.left  = put(x.left,  key, val, d);
        else if (c > x.c)               x.right = put(x.right, key, val, d);
        else if (d < key.length() - 1)  x.mid   = put(x.mid,   key, val, d+1);
        else                            x.val   = val; 
        return x;
    }

    /**
     * Returns the string in the symbol table that is the longest prefix of <tt>query</tt>,
     * or <tt>null</tt>, if no such string.
     * @param query the query string
     * @throws NullPointerException if <tt>query</tt> is <tt>null</tt>
     * @return the string in the symbol table that is the longest prefix of <tt>query</tt>,
     *     or <tt>null</tt> if no such string
     */
    public String longestPrefixOf(String query) {
        if (query == null || query.length() == 0) return null;
        int length = 0;
        TSTNode x = root;
        int i = 0;
        while (x != null && i < query.length()) {
            char c = query.charAt(i);
            if      (c < x.c) x = x.left;
            else if (c > x.c) x = x.right;
            else {
                i++;
                if (x.val != -1) length = i;
                x = x.mid;
            }
        }
        return query.substring(0, length);
    }

    public PriorityQueue<StringNode> getTopMatches(String prefix) {
        PriorityQueue<StringNode> pq = 
            new PriorityQueue<StringNode>(SIZE_OF_PQ, STRING_NODE_COMPARATOR);
        if (prefix.length() == 0 || prefix == null) {
            for (String s : keys()) {
                double key = get(s);
                if (key > 0) {
                    StringNode insert = new StringNode(key, s);
                    pq.add(insert);
                }
            }
        } else {
            for (String s : keysWithPrefix(prefix)) {
                double key = get(s);
                if (key > 0) {
                    StringNode insert = new StringNode(key, s);
                    pq.add(insert);
                }
            }
        }
        return pq;
    }

    /**
     * Returns all keys in the symbol table as an <tt>Iterable</tt>.
     * To iterate over all of the keys in the symbol table named <tt>st</tt>,
     * use the foreach notation: <tt>for (Key key : st.keys())</tt>.
     * @return all keys in the sybol table as an <tt>Iterable</tt>
     */
    public Iterable<String> keys() {
        Queue<String> queue = new Queue<String>();
        collect(root, new StringBuilder(), queue);
        return queue;
    }

    /**
     * Returns all of the keys in the set that start with <tt>prefix</tt>.
     * @param prefix the prefix
     * @return all of the keys in the set that start with <tt>prefix</tt>,
     *     as an iterable
     */
    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> queue = new Queue<String>();
        TSTNode x = get(root, prefix, 0);
        if (x == null) return queue;
        if (x.val != -1) queue.enqueue(prefix);
        collect(x.mid, new StringBuilder(prefix), queue);
        return queue;
    }

    // all keys in subtrie rooted at x with given prefix
    private void collect(TSTNode x, StringBuilder prefix, Queue<String> queue) {
        if (x == null) return;
        collect(x.left,  prefix, queue);
        if (x.val != -1) queue.enqueue(prefix.toString() + x.c);
        collect(x.mid,   prefix.append(x.c), queue);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, queue);
    }

    /**
     * Returns all of the keys in the set that start with <tt>prefix</tt>.
     * @param prefix the prefix
     * @return all of the keys in the set that start with <tt>prefix</tt>,
     *     as an iterable
     */
    public Iterable<String> keysWithPrefixPQ(String prefix) {
        Queue<String> queue = new Queue<String>();
        TSTNode x = null;
        if (prefix == null || prefix.length() == 0) x = root;
        else                                        x = get(root, prefix, 0);
        if (x == null) return queue;
        if (x.val != -1) queue.enqueue(prefix);
        collectPQ(x.mid, new StringBuilder(prefix), queue);
        return queue;
    }

    // all keys in subtrie rooted at x with given prefix
    private void collectPQ(TSTNode x, StringBuilder prefix, Queue<String> queue) {
        if (x == null || k == 0) return;
        StringBuilder middle = new StringBuilder(prefix.append(x.c));
        prefix.deleteCharAt(prefix.length() - 1);
        TSTNode node = pqNodes.peek();
        if (node != null) { 
            if (x.val > node.maxVal) {
                System.out.println("Insert: " + prefix.toString() + x.c);
                queue.enqueue(prefix.toString() + x.c);
                k--;
            }
        }
        if (k == 0) return;
        if (x.val == x.maxVal) { 
            queue.enqueue(prefix.toString());
            k--;
        }
        if (x.left != null)     pqNodes.add(x.left);
        if (x.right != null)    pqNodes.add(x.right);
        if (x.mid != null)      pqNodes.add(x.mid);
        TSTNode pollNode = pqNodes.poll();
        if (pollNode == x.mid)  collectPQ(pollNode, middle, queue);
        else                    collectPQ(pollNode, prefix, queue); 
    }
}
