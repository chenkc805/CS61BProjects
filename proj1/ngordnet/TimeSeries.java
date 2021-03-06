package ngordnet;
import java.util.TreeMap;
import java.util.Collection;
import java.util.LinkedList;

public class TimeSeries<T extends Number> extends TreeMap<Integer, T> {    

    private Collection<Number> yearsResult;
    private Collection<Number> dataResult;

    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        super();
    }

    /** Returns the years in which tSeries time series is valid. Doesn't really
      * need to be a NavigableSet. This is a private method and you don't have 
      * to implement it if you don't want to. */
    // private NavigableSet<Integer> validYears(int startYear, int endYear) {

    // }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR. 
     * inclusive of both end points. */
    public TimeSeries(TimeSeries<T> ts, int startYear, int endYear) {
        super(ts.subMap(startYear, true, endYear, true));
    }

    /** Creates a copy of TS. */
    public TimeSeries(TimeSeries<T> ts) {
        super(ts);
    }

    /** Returns the quotient of this time series divided by the relevant value in ts.
      * If ts is missing a key in this time series, return an IllegalArgumentException. */
    public TimeSeries<Double> dividedBy(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> result = new TimeSeries();
        for (Integer key: this.keySet()) {
            if (!ts.containsKey(key)) {
                throw new IllegalArgumentException();
            } else {
                result.put(key, this.get(key).doubleValue() / ts.get(key).doubleValue());
            }
        }
        return result;
    }

    /** Returns the sum of this time series with the given ts. The result is a 
      * a Double time series (for simplicity). */
    public TimeSeries<Double> plus(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> result = new TimeSeries();
        LinkedList<Integer> yearsAccountedFor = new LinkedList<Integer>();
        for (Integer key: this.keySet()) {
            if (!ts.containsKey(key)) {
                result.put(key, this.get(key).doubleValue());
            } else {
                result.put(key, this.get(key).doubleValue() + ts.get(key).doubleValue());
            }
            yearsAccountedFor.add(key);
        }
        for (Integer key: ts.keySet()) {
            if (!yearsAccountedFor.contains(key)) {
                result.put(key, ts.get(key).doubleValue());
                yearsAccountedFor.add(key);
            }
        }
        return result;
    }

    /** Returns all years for this time series (in any order). */
    public Collection<Number> years() {
        yearsResult = new LinkedList<Number>();
        for (Integer key: this.keySet()) {
            yearsResult.add(key);
        }
        return yearsResult;
    }

    /** Returns all data for this time series (in any order). */
    public Collection<Number> data() {
        dataResult = new LinkedList<Number>();
        for (Integer key: this.keySet()) {
            dataResult.add(this.get(key));
        }
        return dataResult;
    }
}




