/**
 * Prefix-Trie. Supports linear time find() and insert(). 
 * Should support determining whether a word is a full word in the 
 * Trie or a prefix.
 * @author Kevin Chen
 */
public class Trie {

    private static final int R = 255;
    private Node root = new Node();

    /**
     * A private class for each node in the Trie 
     */
    private class Node {
        boolean fullWord;
        boolean partialWord;
        boolean exists;
        Node[] links;

        /**
         * Constructor for the Node class 
         * Initilizes an empty Node array and sets the 
         * exists bool to false. 
         */
        public Node() {
            links = new Node[R];
            partialWord = false;
            fullWord = false;
        }
    }

    /**
     * Sees if a word exists in this Trie
     * @param s is the string we are looking for
     * @param isFullWord is true if we are looking for a full word, not just part of a word
     * @return bool depending on if the word is found and if that word matches s and isFullWord
     */ 
    public boolean find(String s, boolean isFullWord) {
        if (s == null || s.equals("")) {
            return false;
        }
        return find(root, s, isFullWord, 0);
    }

    /**
     * Helper function for find
     * @param x is the Node that we are currently at
     * @param s is the string we are looking for
     * @param isFullWord is true if we are looking for a full word, not just part of a word
     * @param d is the index of the character in s that we are searching for
     * @return bool depending on if the word is found and if that word matches s and isFullWord
     */ 
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

    /**
     * Sees if a word exists in this Trie
     * @param s is the string we are inserting
     */ 
    public void insert(String s) {
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        insert(root, s, 0);
    }

    /**
     * Helper function for find
     * @param x is the Node that we are currently at
     * @param s is the string we are looking for
     * @param d is the index of the character in s that we are searching for
     * @return the final Node of the inserted word s
     */ 
    private Node insert(Node x, String s, int d) {
        if (x == null) {
            x = new Node();
        }
        if (d == s.length()) {
            x.fullWord = true;
        } else {
            char c = s.charAt(d);
            x.links[c] = insert(x.links[c], s, d + 1);
            x.partialWord = true;
        }
        return x;
    }
}
