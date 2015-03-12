package ngordnet;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Collection;
import java.util.LinkedList;
import java.util.ArrayList;

public class YearlyRecord {

    private HashMap<String, Integer> yr;
    private TreeMap<String, Integer> yrSorted;
    private HashMap<Integer, ArrayList<String>> yrReversed;
    private TreeMap<Integer, ArrayList<String>> yrReversedSorted;
    private HashMap<String, Integer> yrRank;
    private LinkedList<String> words;
    private LinkedList<Number> counts;
    private int size;
    private int numberOfWords;
    private boolean rankSorted;
    private boolean wordSorted;
    private boolean countSorted;

    /** Creates a new empty YearlyRecord. */
    public YearlyRecord() {
        this.yr = new HashMap<String, Integer>();
        this.yrReversed = new HashMap<Integer, ArrayList<String>>();
        this.size = 0;
        rankSorted = false;
        countSorted = false;
        wordSorted = false;
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        this.yr = otherCountMap;
        this.yrReversed = new HashMap<Integer, ArrayList<String>>();
        for (String key: otherCountMap.keySet()) {
            if (yrReversed.containsKey(otherCountMap.get(key))) {
                yrReversed.get(otherCountMap.get(key)).add(key);
            } else {
                ArrayList<String> putThis = new ArrayList<String>();
                putThis.add(key);
                yrReversed.put(otherCountMap.get(key), putThis);
            }
        }
        this.size = otherCountMap.size();
        rankSorted = false;
        countSorted = false;
        wordSorted = false;
    }

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        return yr.get(word);
    }

    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count) {
        if (yrReversed.containsKey(count)) {
            yrReversed.get(count).add(word);
        } else {
            ArrayList<String> putThis = new ArrayList<String>();
            putThis.add(word);
            yrReversed.put(count, putThis);
        }
        yr.put(word, count);
        size += 1;
        rankSorted = false;
        countSorted = false;
        wordSorted = false;
    }
    /** Returns the number of words recorded this year. */
    public int size() {
        return size;
    }

    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        if (wordSorted) {
            return words;
        } else {
            yrReversed = new HashMap<Integer, ArrayList<String>>();
            for (String key: yr.keySet()) {
                if (yrReversed.containsKey(yr.get(key))) {
                    yrReversed.get(yr.get(key)).add(key);
                } else {
                    ArrayList<String> putThis = new ArrayList<String>();
                    putThis.add(key);
                    yrReversed.put(yr.get(key), putThis);
                }
            }
            words = new LinkedList<String>();
            yrReversedSorted = new TreeMap<Integer, ArrayList<String>>(yrReversed);
            for (Integer key: yrReversedSorted.keySet()) {
                for (String word: yrReversedSorted.get(key)) {
                    words.add(word);
                }
            }
            wordSorted = true;
            return words;
        }
    }

    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
        if (countSorted) {
            return counts;
        } else {
            yrReversed = new HashMap<Integer, ArrayList<String>>();
            for (String key: yr.keySet()) {
                if (yrReversed.containsKey(yr.get(key))) {
                    yrReversed.get(yr.get(key)).add(key);
                } else {
                    ArrayList<String> putThis = new ArrayList<String>();
                    putThis.add(key);
                    yrReversed.put(yr.get(key), putThis);
                }
            }
            counts = new LinkedList<Number>();
            yrReversedSorted = new TreeMap<Integer, ArrayList<String>>(yrReversed);
            for (Integer key: yrReversedSorted.keySet()) {
                counts.add(key);
            }
            countSorted = true;
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
            yrReversed = new HashMap<Integer, ArrayList<String>>();
            for (String key: yr.keySet()) {
                if (yrReversed.containsKey(yr.get(key))) {
                    yrReversed.get(yr.get(key)).add(key);
                } else {
                    ArrayList<String> putThis = new ArrayList<String>();
                    putThis.add(key);
                    yrReversed.put(yr.get(key), putThis);
                }
            }
            yrRank = new HashMap<String, Integer>();
            yrReversedSorted = new TreeMap<Integer, ArrayList<String>>(yrReversed);
            int i = 1;
            for (Integer key: yrReversedSorted.descendingMap().navigableKeySet()) {
                for (String value: yrReversedSorted.get(key)) {
                    yrRank.put(value, i);
                    i += 1;
                }
            }
            rankSorted = true;
            return yrRank.get(word);
        }
    }
} 






