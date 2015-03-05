package ngordnet;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Collection;

public class TimeSeries<T extends Number> extends TreeMap<Integer, T> {    

    private TreeMap<Integer, T> tSeries;

    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
      this.tSeries = new TreeMap<Integer, T>();
    }

    /** Returns the years in which tSeries time series is valid. Doesn't really
      * need to be a NavigableSet. This is a private method and you don't have 
      * to implement it if you don't want to. */
    // private NavigableSet<Integer> validYears(int startYear, int endYear) {

    // }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR. 
     * inclusive of both end points. */
    public TimeSeries(TimeSeries<T> ts, int startYear, int endYear) {
      this.tSeries = new TreeMap<Integer, T>(ts.subMap(startYear, endYear));
    }

    /** Creates a copy of TS. */
    public TimeSeries(TimeSeries<T> ts) {
      this.tSeries = new TreeMap<Integer, T>(ts.subMap(ts.firstKey(), ts.lastKey()));
    }

    /** Returns the quotient of this time series divided by the relevant value in ts.
      * If ts is missing a key in this time series, return an IllegalArgumentException. */
    public TimeSeries<Double> dividedBy(TimeSeries<? extends Number> ts) {
      TimeSeries<Double> result = new TimeSeries();
      for (Integer key: tSeries.keySet()) {
        if (!ts.containsKey(key)) {
          throw new IllegalArgumentException();
        } else {
          result.put(key, tSeries.get(key).doubleValue() / ts.get(key).doubleValue());
        }
      }
      return result;
    }

    /** Returns the sum of this time series with the given ts. The result is a 
      * a Double time series (for simplicity). */
    public TimeSeries<Double> plus(TimeSeries<? extends Number> ts) {
      TimeSeries<Double> result = new TimeSeries();
      for (Integer key: tSeries.keySet()) {
        if (!ts.containsKey(key)) {
          throw new IllegalArgumentException();
        } else {
          result.put(key, tSeries.get(key).doubleValue() + ts.get(key).doubleValue());
        }
      }
      return result;
    }

    /** Returns all years for this time series (in any order). */
    public Collection<Number> years() {
      Collection<Number> result = new HashSet<Number>();
      for (Integer key: tSeries.keySet()) {
        result.add(key);
      }
      return result;
    }

    /** Returns all data for this time series (in any order). */
    public Collection<Number> data() {
      Collection<Number> result = new HashSet<Number>();
      for (Integer key: tSeries.keySet()) {
        result.add(tSeries.get(key));
      }
      return result;
    }
}




