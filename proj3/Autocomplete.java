import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Arrays;

/**
 * Implements autocomplete on prefixes for a given dictionary of terms and weights.
 * @author Kevin Chen
 */
public class Autocomplete {

    private WeightedTrie trie;
    private PriorityQueue<Node> pq;
    private PriorityQueue<String> _topMatches;
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
            trie.insert(terms[i], weights[i]);
        }
        pq = new PriorityQueue<Node>(SIZE_OF_PQ, COMPARATOR);
    }

    /**
     * Find the weight of a given term. If it is not in the dictionary, return 0.0
     * @param term The word to look for
     * @return weight of the given term.
     */
    public double weightOf(String term) {
        if (trie.find(term, true) && trie.get(term, true) != null) {
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
        if (n.existsWeight == max) {
            return prefix;
        }
        if (trie.find(prefix, false) && trie.get(prefix, false) != null) {
            Node curr = trie.get(prefix, false);
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
            return topMatch(topNode, prefix + topNode.character, max);
        } else {
            return null;
        }
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
        Node curr = trie.get(prefix, false);
        topMatches(curr, prefix, k, curr.maxWeight);
        PriorityQueue<String> result = new PriorityQueue<String>(_topMatches);
        _topMatches.clear();
        return result;

    }

    /**
     * Helper method for finding the topMatches
     * @param x the Node we are currently on
     * @param prefix is the prefix we are searching through
     * @param k Number of results to return 
     * @param weight the maxWeight of the prefix
     */
    private void topMatches(Node x, String prefix, int k, double weight) {
        if (x == null) {
            return;
        }
        if (x.fullWord) {
            _topMatches.add(prefix);
            k--;
        }
        if (k == 0) {
            return;
        }
        Node[] nodes = x.links;
        for (int i = 0; i < nodes.length; i++) {
            pq.add(nodes[i]);
        }
        Node first = pq.poll();
        Node second = pq.poll();
        Node third = pq.poll();
        topMatches(first, prefix + first.character, k, weight);
        topMatches(second, prefix + second.character, k, weight);
        topMatches(third, prefix + third.character, k, weight);
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

        // process queries from standard input
        int k = Integer.parseInt(args[1]);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            for (String term : autocomplete.topMatches(prefix, k)) {
                StdOut.printf("%14.1f  %s\n", autocomplete.weightOf(term), term);
            }
        }
    }
}




