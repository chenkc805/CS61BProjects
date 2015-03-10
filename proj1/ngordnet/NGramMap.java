package ngordnet;
import edu.princeton.cs.introcs.In;
import java.util.HashMap;
import java.util.Collection;

public class NGramMap {

    /* The key is the YEAR and the value is TOTAL NUMBER OF WORDS from that year. */
    private TimeSeries<Long> countsMap;
    /* The key is the YEAR and the value is a YEARLYRECORD with all the WORDS and their COUNTS. */
    private HashMap<Integer, YearlyRecord> yearMap;
    private HashMap<String, TimeSeries> wordsMap;
    private In words;
    private In counts;

    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    public NGramMap(String wordsFilename, String countsFilename) {
        counts = new In(countsFilename);
        words = new In(wordsFilename);
        

        countsMap = new TimeSeries<Long>();
        yearMap = new HashMap<Integer, YearlyRecord>();
        wordsMap = new HashMap<String, TimeSeries>();

        while (counts.hasNextLine()) {
            String row = counts.readLine();
            String[] tokens = row.split(",");
            countsMap.put(Integer.parseInt(tokens[0]), Long.parseLong(tokens[1]));
        }

        while (words.hasNextLine()) {
            String word = words.readString();
            Integer year = words.readInt();
            Integer count = words.readInt();
            Integer forget = words.readInt();
            YearlyRecord yearMapValue = yearMap.get(year);
            if (yearMapValue != null) {
                yearMapValue.put(word, count);
            } else {
                YearlyRecord putThis = new YearlyRecord();
                putThis.put(word, count);
                yearMap.put(year, putThis);
            }
            TimeSeries wordsMapValue = wordsMap.get(word);
            if (wordsMapValue != null) {
                wordsMapValue.put(year, count);
            } else {
                TimeSeries putThisTimeSeries = new TimeSeries();
                putThisTimeSeries.put(year, count);
                wordsMap.put(word, putThisTimeSeries);
            }
        }
    }
    
    /** Returns the absolute count of WORD in the given YEAR. If the word
      * did not appear in the given year, return 0. */
    public int countInYear(String word, int year) {
        try {
            if (yearMap.get(year).words().contains(word)) {
                return yearMap.get(year).count(word);
            } else {
                return 0;
            }
        } catch (NullPointerException e) {
            System.err.println("This year is not in our records.");
            return 0;
        }
    }

    /** Returns a defensive copy of the YearlyRecord of YEAR. */
    public YearlyRecord getRecord(int year) {
        try {
            YearlyRecord result = new YearlyRecord();
            for (String key: yearMap.get(year).words()) {
                result.put(key, yearMap.get(year).count(key));
            }
            return result;
        } catch (NullPointerException e) {
            System.err.println("This year is not in our records.");
            return null;         
        }
    }

    /** Returns the total number of words recorded in all volumes. */
    public TimeSeries<Long> totalCountHistory() {
        TimeSeries<Long> totalCountHistory = new TimeSeries<Long>(countsMap);
        return totalCountHistory;
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Integer> countHistory(String word, int startYear, int endYear) {
        TimeSeries<Long> countsInInterval = new TimeSeries<Long>(countsMap, startYear, endYear);
        TimeSeries<Integer> countHistory = new TimeSeries<Integer>();
        for (Integer key: countsInInterval.keySet()) {
            countHistory.put(key, countInYear(word, key));
        }
        return countHistory;
    }

    /** Provides a defensive copy of the history of WORD. */
    public TimeSeries<Integer> countHistory(String word) {
        TimeSeries<Integer> result = new TimeSeries<Integer>(wordsMap.get(word));
        return result;
    }

    /** Provides the relative frequency of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Double> weightHistory(String word, int startYear, int endYear) {
        TimeSeries<Double> numerator = new TimeSeries(wordsMap.get(word), startYear, endYear);
        TimeSeries<Double> denominator = new TimeSeries(countsMap, startYear, endYear);
        TimeSeries<Double> result = numerator.dividedBy(denominator);
        return result;
    }

    /** Provides the relative frequency of WORD. */
    public TimeSeries<Double> weightHistory(String word) {
        TimeSeries<Double> numerator = new TimeSeries(wordsMap.get(word));
        TimeSeries<Double> denominator = new TimeSeries(countsMap);
        TimeSeries<Double> result = numerator.dividedBy(denominator);
        return result; 
    }

    /** Provides the summed relative frequency of all WORDS between
      * STARTYEAR and ENDYEAR. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        TimeSeries<Double> sum = new TimeSeries<Double>();
        TimeSeries<Double> denominator = new TimeSeries(countsMap, startYear, endYear);
        for (String word: words) {
            TimeSeries<Double> numerator = new TimeSeries(wordsMap.get(word), startYear, endYear);
            sum = sum.plus(numerator);
        }
        TimeSeries<Double> result = sum.dividedBy(denominator);
        return result; 
    }

    /** Returns the summed relative frequency of all WORDS. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words) {
        TimeSeries<Double> result = new TimeSeries<Double>();
        for (String word: words) {
            result = result.plus(weightHistory(word));
        }
        return result;
    }

    // /** Provides processed history of all words between STARTYEAR and ENDYEAR as processed
    //   * by YRP. */
    // public TimeSeries<Double> processedHistory(int startYear, int endYear,
    //                                            YearlyRecordProcessor yrp)

    // /** Provides processed history of all words ever as processed by YRP. */
    // public TimeSeries<Double> processedHistory(YearlyRecordProcessor yrp) 
}