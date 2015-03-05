package ngordnet;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Collection;
import java.util.HashSet;

public class YearlyRecord {

    private HashMap<String, Integer> yr;
    private TreeMap<String, Integer> yrSorted;
    private HashMap<Integer, String> yrReversed;
    private TreeMap<Integer, String> yrReversedSorted;
    private HashMap<String, Integer> yrRank;
    private HashSet<String> words;
    private HashSet<Number> counts;
    private int size;
    private boolean rankSorted;
    private boolean sorted;

    /** Creates a new empty YearlyRecord. */
    public YearlyRecord() {
        this.yr = new HashMap<String, Integer>();
        this.yrReversed = new HashMap<Integer, String>();
        this.size = 0;
        rankSorted = false;
        sorted = false;
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        this.yr = otherCountMap;
        this.yrReversed = new HashMap<Integer, String>();
        for (String key: otherCountMap.keySet()) {
            yrReversed.put(otherCountMap.get(key), key);
        }
        this.size = otherCountMap.size();
        rankSorted = false;
        sorted = false;
    }

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        return yr.get(word);
    }

    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count) {
        yr.put(word, count);
        yrReversed.put(count, word);
        size += 1;
        rankSorted = false;
        sorted = false;
    }

    /** Returns the number of words recorded this year. */
    public int size() {
        return size;
    }

    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        if (sorted) {
            return yrReversedSorted.values();
        } else {
            yrReversedSorted = new TreeMap<Integer, String>(yrReversed);
            sorted = true;
            return yrReversedSorted.values();
        }
    }

    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
        if (sorted) {
            return counts;
        } else {
            yrReversedSorted = new TreeMap<Integer, String>(yrReversed);
            for (Integer key: yrReversedSorted.keySet()) {
                counts.add(key);
            }
            sorted = true;
            return counts;
        }
    }

    /** Returns rank of WORD. Most common word is rank 1. 
      * If two words have the same rank, break ties arbitrarily. 
      * No two words should have the same rank.
      */
    public int rank(String word) {
        if (rankSorted) {
            return yrRank.get(word);
        } else {
            yrRank = new HashMap<String, Integer>();
            yrReversedSorted = new TreeMap<Integer, String>(yrReversed);
            int i = yrReversedSorted.size();
            for (Integer key: yrReversedSorted.keySet()) {
                yrRank.put(yrReversedSorted.get(key), i);
                i -= 1;
            }
            rankSorted = true;
            return yrRank.get(word);
        }
    }
} 






