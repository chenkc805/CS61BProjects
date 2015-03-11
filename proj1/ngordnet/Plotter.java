package ngordnet;

import com.xeiam.xchart.Chart;
import com.xeiam.xchart.QuickChart;
import com.xeiam.xchart.SwingWrapper;
// import com.xeiam.xchart.StyleManager.ChartTheme;
import com.xeiam.xchart.ChartBuilder;
// import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Set;

/** Utility class for generating plots. */
public class Plotter {
    /** Creates a plot of the TimeSeries TS. Labels the graph with the
      * given TITLE, XLABEL, YLABEL, and LEGEND. */
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public static void plotTS(TimeSeries<? extends Number> ts, 
        String title, String xlabel, String ylabel, String legend) {
        Collection years = ts.years();
        Collection data = ts.data();

        Chart plot = QuickChart.getChart(title, xlabel, ylabel, legend, years, data);

        new SwingWrapper(plot).displayChart();
    }

    /** Creates a plot of the absolute word counts for WORD from STARTYEAR
      * to ENDYEAR, using NGM as a data source. */
    public static void plotCountHistory(NGramMap ngm, String word, int startYear, int endYear) {
        TimeSeries<Integer> ts = ngm.countHistory(word, startYear, endYear);

        plotTS(ts, "Plot Count History", "Years", "Counts", "Count History");
    }

    /** Creates a plot of the normalized weight counts for WORD from STARTYEAR
      * to ENDYEAR, using NGM as a data source. */
    public static void plotWeightHistory(NGramMap ngm, String word, int startYear, int endYear) {
        TimeSeries<Double> ts = ngm.weightHistory(word, startYear, endYear);

        plotTS(ts, "Plot Weight History", "Years", "Normalized Counts", "Weight History");
    }

    /** Creates a plot of the processed history from STARTYEAR to ENDYEAR, using
      * NGM as a data source, and the YRP as a yearly record processor. */
    public static void plotProcessedHistory(NGramMap ngm, int startYear,
        int endYear, YearlyRecordProcessor yrp) {
        TimeSeries wordWeights = ngm.processedHistory(startYear, endYear, yrp);
        plotTS(wordWeights, "Word Length", "year", "avg. length", "word length");
    }

    /** Creates a plot of the total normalized count of every word that is a hyponym
      * of CATEGORYLABEL from STARTYEAR to ENDYEAR using NGM and WN as data sources. */
    public static void plotCategoryWeights(NGramMap ngm, WordNet wn, 
        String categoryLabel, int startYear, int endYear) {
        Set words = wn.hyponyms(categoryLabel);        
        TimeSeries summedWeightHistory = ngm.summedWeightHistory(words, startYear, endYear);
        plotTS(summedWeightHistory, "Popularity", "year", "weight", categoryLabel);
    }

    /** Creates overlaid category weight plots for each category label in CATEGORYLABELS
      * from STARTYEAR to ENDYEAR using NGM and WN as data sources. */
    public static void plotCategoryWeights(NGramMap ngm, WordNet wn, 
        String[] categoryLabels, int startYear, int endYear) {
        Chart chart = new ChartBuilder().width(WIDTH).height(HEIGHT).
            xAxisTitle("years").yAxisTitle("data").build();

        for (String categoryLabel : categoryLabels) {
            Set words = wn.hyponyms(categoryLabel);        
            TimeSeries bundle = ngm.summedWeightHistory(words, startYear, endYear);
            chart.addSeries(categoryLabel, bundle.years(), bundle.data());
        }

        new SwingWrapper(chart).displayChart();
    }

    /** Makes a plot showing overlaid individual normalized count for every word in WORDS
      * from STARTYEAR to ENDYEAR using NGM as a data source. */
    public static void plotAllWords(NGramMap ngm, String[] words, 
        int startYear, int endYear) {
        Chart chart = new ChartBuilder().width(WIDTH).height(HEIGHT).
            xAxisTitle("years").yAxisTitle("data").build();

        for (String word : words) {
            TimeSeries bundle = ngm.weightHistory(word, startYear, endYear);
            chart.addSeries(word, bundle.years(), bundle.data());
        }
        new SwingWrapper(chart).displayChart();
    }

    /** Returns the numbers from max to 1, inclusive in decreasing order. 
      * Private, so you don't have to implement if you don't want to. */
    private static Collection<Number> downRange(int max) {
        ArrayList ranks = new ArrayList();
        for (int i = max; i >= 1; i -= 1) {
            ranks.add(i);
        }
        return ranks;
    }

    /** Plots the normalized count of every word against the rank of every word on a
      * log-log plot. Uses data from YEAR, using NGM as a data source. */
    public static void plotZipfsLaw(NGramMap ngm, int year) {
        YearlyRecord yr = ngm.getRecord(year);
        Collection counts = yr.counts();
        Collection ranks = downRange(counts.size()); 

        Chart chart = new ChartBuilder().width(WIDTH).height(HEIGHT).
            xAxisTitle("rank").yAxisTitle("count").build();
        chart.getStyleManager().setYAxisLogarithmic(true);
        chart.getStyleManager().setXAxisLogarithmic(true);
        
        chart.addSeries("zipf", ranks, counts);
        new SwingWrapper(chart).displayChart();
    }
} 








