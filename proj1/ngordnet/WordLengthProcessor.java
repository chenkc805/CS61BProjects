package ngordnet;

import java.util.Collection;

public class WordLengthProcessor implements YearlyRecordProcessor {
    
    public double process(YearlyRecord yearlyRecord)  {
        Collection<String> words = yearlyRecord.words();

        long total = 0L;
        long counts = 0L;

        for (String word: words) {
            int count = yearlyRecord.count(word);
            long multiply = (long) word.length() * count;

            counts += count;
            total += multiply;
        }
        double result = (double) total / counts;

        return result;
    }
}
