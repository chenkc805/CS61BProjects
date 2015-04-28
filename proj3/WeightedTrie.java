import java.util.Arrays;
/**
 * Prefix-Trie. Supports linear time find() and insert(). 
 * Should support determining whether a word is a full word in the 
 * Trie or a prefix.
 * @author Kevin Chen
 */
public class WeightedTrie {

    final int R = 400;
    Node root = new Node();
    private int size;

    public void clear() {
        root.links = new Node[R];
        root.maxWeight = 0.0;
    }

    public boolean find(String s, boolean isFullWord) {
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        return find(root, s, isFullWord, 0);
    }

    private boolean find(Node x, String s, boolean isFullWord, int d) {
        if (x == null) {
            return false;
        }
        if (d == s.length()) {
            if (isFullWord) {
                return x.fullWord == isFullWord;
            } else {
                return x.partialWord != isFullWord;
            }
        }
        char c = s.charAt(d);
        return find(x.links[c], s, isFullWord, d + 1);
    }



    public Node get(String s, boolean isFullWord) {
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        if (find(root, s, isFullWord, 0)) {
            return get(root, s, isFullWord, 0);
        } else {
            return null;
        }
    }

    private Node get(Node x, String s, boolean isFullWord, int d) {
        if (x == null) {
            return null;
        }
        if (d == s.length()) {
            if (x.fullWord == isFullWord || x.partialWord != isFullWord) {
                return x;
            } else {
                return null;
            }
        }
        char c = s.charAt(d);
        return get(x.links[c], s, isFullWord, d + 1);
    }

    public void insert(String s, double weight) {
        if (s.equals("") || s == null) {
            throw new IllegalArgumentException();
        }
        insert(root, s, 0, weight);
        size++;
    }

    private Node insert(Node x, String s, int d, double weight) {
        if (x == null) {
            x = new Node();
        }
        if (d == s.length()) {
            x.fullWord = true;
            x.partialWord = false;
            x.existsWeight = weight;
        } else {
            char c = s.charAt(d);
            x.partialWord = true;
            c = resize(c);
            x.links[c] = insert(x.links[c], s, d + 1, weight);
        }
        if (x.maxWeight < weight) {
            x.maxWeight = weight;
        }
        return x;
    }

    public int size() {
        return size;
    }

    public char resize(char c) {
        if (c == 8217) {
            c = 256;
        } else if (c == 304) {
            c = 257;
        } else if (c == 7891) {
            c = 258;
        } else if (c == 363) {
            c = 259;
        } else if (c == 351) {
            c = 260;
        } else if (c == 332) {
            c = 261;
        } else if (c == 7779) {
            c = 262;
        } else if (c == 333) {
            c = 263;
        } else if (c == 7720) {
            c = 264;
        }
        // } else if {
            
        // } else if {
            
        // } else if {
            
        // }
        return c;
    }
}
