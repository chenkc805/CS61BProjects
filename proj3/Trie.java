/**
 * Prefix-Trie. Supports linear time find() and insert(). 
 * Should support determining whether a word is a full word in the 
 * Trie or a prefix.
 * @author Kevin Chen
 */
public class Trie {

    private static final int R = 128;
    private Node root = new Node();

    private class Node {
        boolean exists;
        Node[] links;

        public Node() {
            links = new Node[R];
            exists = false;
        }
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
            return x.exists == isFullWord;
        }
        char c = s.charAt(d);
        return find(x.links[c], s, isFullWord, d+1);
    }

    public void insert(String s) {
        if (s.equals("") || s == null) {
            throw new IllegalArgumentException();
        }
        insert(root, s, 0);
    }

    private Node insert(Node x, String s, int d) {
        if (x == null) {
            x = new Node();
        }
        if (d == s.length()) {
            x.exists = true;
        } else {
            char c = s.charAt(d);
            x.links[c] = insert(x.links[c], s, d+1);
        }
        return x;
    }
}
