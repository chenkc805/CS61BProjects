import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Implements autocomplete on prefixes for a given dictionary of terms and weights.
 * @author Kevin Chen
 */
public class Autocomplete {

    private TST trie;
    //private ArrayList<String> iterableTopMatches;

    //private ArrayList<String> iterableTopMatches;
    private ArrayList<String> allMatches;

    //private ArrayList<String> allMatches;
    /**
     * Initializes required data structures from parallel arrays.
     * @param terms Array of terms.
     * @param weights Array of weights.
     */
    public Autocomplete(String[] terms, double[] weights) {
        if (terms.length != weights.length) {
            throw new IllegalArgumentException();
        }
        trie = new TST();
        for (int i = 0; i < terms.length; i++) {
            if (weights[i] < 0 || trie.contains(terms[i])) {
                trie.clear();
                throw new IllegalArgumentException();
            }
            trie.put(terms[i], weights[i]);
        }
        allMatches = new ArrayList<String>();
    }

    /**
     * Find the weight of a given term. If it is not in the dictionary, return 0.0
     * @param term The word to look for
     * @return weight of the given term.
     */
    public double weightOf(String term) {
        if (trie.contains(term)) {
            return trie.get(term);
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
        trie.k = 1;
        return trie.getTopMatches(prefix).poll().getString();
    }

    /**
     * Returns the top k matching terms (in descending order of weight) as an iterable.
     * If there are less than k matches, return all the matching terms.
     * @param prefix The top k terms of this prefix
     * @param k The number of terms to return in the iterable
     * @return Iterable of the top k terms with prefix.
     */
    public Iterable<String> topMatches(String prefix, int k) {
        if (k < 1) throw new IllegalArgumentException();
        ArrayList<String> iterableTopMatches = new ArrayList<String>();
        PriorityQueue<StringNode> top = trie.getTopMatches(prefix);
        while (k > 0) {
            StringNode s = top.poll();
            if (s != null) {
                iterableTopMatches.add(s.getString());
                k--;
            } else {
                break;
            }
        }
        return iterableTopMatches;
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



