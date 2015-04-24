/**
 * Prefix-Trie. Supports linear time find() and insert(). 
 * Should support determining whether a word is a full word in the 
 * Trie or a prefix.
 * @author Kevin Chen
 */
public class WeightedTrie {

    final int R = 255;
    Node root = new Node();
    private int size;

    public void clear() {
        root.links = new Node[R];
        root.maxWeight = 0.0;
    }

    public boolean find(String s, boolean isFullWord) {
        if (s.equals("") || s == null) {
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
        return find(x.links[c], s, isFullWord, d+1);
    }



    public Node get(String s, boolean isFullWord) {
        if (s.equals("") || s == null) {
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
        return get(x.links[c], s, isFullWord, d+1);
    }

    public void insert(String s, double weight) {
        if (s.equals("") || s == null) {
            throw new IllegalArgumentException();
        }
        insert(root, s, 0, weight);
        size++;
    }

    private Node insert(Node x, String s, int d, double weight) {
        if (x == null && d != s.length()) {
            String c = String.valueOf(s.charAt(d));
            x = new Node(c, weight);
        }
        if (d == s.length()) {
            x.fullWord = true;
            x.maxWeight = weight;
            x.existsWeight = weight;
        } else {
            if (x.maxWeight < weight) {
                x.maxWeight = weight;
            }
            char c = s.charAt(d);
            x.links[c] = insert(x.links[c], s, d+1, weight);
            x.partialWord = true;
        }
        return x;
    }

    public int size() {
        return size;
    }
}
