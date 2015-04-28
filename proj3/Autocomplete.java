import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;

/**
 * Implements autocomplete on prefixes for a given dictionary of terms and weights.
 * @author Kevin Chen
 */
public class Autocomplete {

    private WeightedTrie trie;
    private PriorityQueue<Node> pq;
    private ArrayList<String> iterableTopMatches;
    //private ArrayList<String> iterableTopMatches;
    private ArrayList<String> allMatches;
    //private ArrayList<String> allMatches;
    private static final int SIZE_OF_PQ = 11;
    private static final NodeMaxWeightComparator COMPARATOR = new NodeMaxWeightComparator();
    /**
     * Initializes required data structures from parallel arrays.
     * @param terms Array of terms.
     * @param weights Array of weights.
     */
    public Autocomplete(String[] terms, double[] weights) {
        HashSet<String> checkDuplicates = new HashSet<String>(Arrays.asList(terms));
        if (terms.length != weights.length || checkDuplicates.size() != terms.length) {
            throw new IllegalArgumentException();
        }
        trie = new WeightedTrie();
        for (int i = 0; i < terms.length; i++) {
            if (weights[i] < 0) {
                trie.clear();
                throw new IllegalArgumentException();
            }
            System.out.println(terms[i]);
            trie.insert(terms[i], weights[i]);
        }
        pq = new PriorityQueue<Node>(SIZE_OF_PQ, COMPARATOR);
        allMatches = new ArrayList<String>();
    }

    /**
     * Find the weight of a given term. If it is not in the dictionary, return 0.0
     * @param term The word to look for
     * @return weight of the given term.
     */
    public double weightOf(String term) {
        if (trie.find(term, true)) {
            return trie.get(term, true).existsWeight;
        } else {
            return 0.0;
        }
    }

    /**
     * Return the top match for given prefix, or null if there is no matching term.
     * @param prefix Input prefix to match against.
     * @return Best (highest weight) matching string in the dictionary.
     */
    public String topMatch(String prefix) {
        return topMatch(trie.root, prefix, -1);
    }

    /**
     * Helper method for finding the topMatch
     * @param n the Node we are currently on
     * @param prefix is the prefix we are searching through
     * @param max the maxWeight of the prefix
     * @return the top match string
     */
    private String topMatch(Node n, String prefix, double max) {
        Node curr = null;
        if (trie.find(prefix, false)) {
            curr = trie.get(prefix, false);
        } else if (trie.find(prefix, true)) {
            curr = trie.get(prefix, true);
        } else {
            System.out.println("No top matches for this prefix.");
            return null;
        }
        if (curr.existsWeight == curr.maxWeight) {
            return prefix;
        }
        double weight = curr.maxWeight;
        double topMatchDouble = 0.0;
        Node topNode = null;
        Node[] nodes = curr.links;
        for (int i = 0; i < trie.R; i++) {
            if (nodes[i] != null) {
                if (nodes[i].maxWeight == weight) {
                    topNode = nodes[i];
                    break;
                }  
            }
        }
        return topMatch(topNode, prefix + Character.toString((char) Arrays.asList(nodes).indexOf(topNode)), topNode.maxWeight);
    }

    /**
     * Returns the top k matching terms (in descending order of weight) as an iterable.
     * If there are less than k matches, return all the matching terms.
     * @param prefix The top k terms of this prefix
     * @param k The number of terms to return in the iterable
     * @return Iterable of the top k terms with prefix.
     */
    public Iterable<String> topMatches(String prefix, int k) {
        if (k < 0) {
            throw new IllegalArgumentException();
        }
        TreeMap<Double, String> treeTopMatches = new TreeMap<Double, String>();
        ArrayList<String> iterableTopMatches = new ArrayList<String>();
        findAll(prefix);
        for (String word : allMatches) {
            treeTopMatches.put(weightOf(word), word);
        }
        allMatches.clear();
        while (k > 0) {
            Map.Entry<Double, String> x = treeTopMatches.pollLastEntry();
            if (x == null) {
                break;
            }
            iterableTopMatches.add(x.getValue());
            k--;
        }
        return iterableTopMatches;

    }

    /**
     * Returns all words in the dictionary with the given prefix, sorted by weight.
     * @param prefix The prefix to look through
     */
    private void findAll(String prefix) {
        findAll(trie.get(prefix, false), prefix, false);
        findAll(trie.get(prefix, true), prefix, true);
    }

    /**
     * Returns all words in the dictionary with the given prefix, sorted by weight.
     * @param n the node that we are currently looking through
     * @param prefix The prefix to look through
     */
    private void findAll(Node n, String prefix, boolean isFullWord) {
        if (n == null) {
            return;
        }
        if (n.fullWord) {
            allMatches.add(prefix);
        }
        Node curr = trie.get(prefix, false);
        if (curr != null) {
            for (int i = 0; i < trie.R; i++) {
                Node x = curr.links[i];
                if (x != null) {
                    findAll(x, prefix + Character.toString((char) i), isFullWord);
                }
            }
        }
    }

    /**
     * Returns the highest weighted matches within k edit distance of the word.
     * If the word is in the dictionary, then return an empty list.
     * @param word The word to spell-check
     * @param dist Maximum edit distance to search
     * @param k    Number of results to return 
     * @return Iterable in descending weight order of the matches
     */
    public Iterable<String> spellCheck(String word, int dist, int k) {
        LinkedList<String> results = new LinkedList<String>();  
        /* YOUR CODE HERE; LEAVE BLANK IF NOT PURSUING BONUS */
        return results;
    }
    /**
     * Test client. Reads the data from the file, 
     * then repeatedly reads autocomplete queries from standard input and 
     * prints out the top k matching terms.
     * @param args takes the name of an input file and an integer k as command-line arguments
     */
    public static void main(String[] args) {
        // initialize autocomplete data structure
        In in = new In(args[0]);
        int N = in.readInt();
        String[] terms = new String[N];
        double[] weights = new double[N];
        for (int i = 0; i < N; i++) {
            weights[i] = in.readDouble();   // read the next weight
            in.readChar();                  // scan past the tab
            terms[i] = in.readLine();       // read the next term
        }

        Autocomplete autocomplete = new Autocomplete(terms, weights);

        //process queries from standard input
        int k = Integer.parseInt(args[1]);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            for (String term : autocomplete.topMatches(prefix, k)) {
                StdOut.printf("%14.1f  %s\n", autocomplete.weightOf(term), term);
           }
        }
    }
}




