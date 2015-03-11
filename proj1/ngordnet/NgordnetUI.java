package ngordnet;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.In;
import java.util.Arrays;

public class NgordnetUI {

    private static String[] help = {"quit", "help", "range [start] [end]",
        "count [word] [year]", "hyponyms [word]", "history [words...] ",
        "hypohist [words...]"};

    private static int startDate = Integer.MIN_VALUE;
    private static int endDate = Integer.MAX_VALUE;
    private static NGramMap ngm;
    private static WordNet wn;
    private static Plotter plotter = new Plotter();
    private static YearlyRecordProcessor wlp = new WordLengthProcessor();

    public static void main(String[] args) {
        In files = new In("./ngordnet/ngordnetui.config");
        System.out.println("Reading ngordnetui.config...");

        String wordFile = files.readString();
        String countFile = files.readString();
        String synsetFile = files.readString();
        String hyponymFile = files.readString();

        ngm = new NGramMap(wordFile, countFile);
        wn = new WordNet(synsetFile, hyponymFile);

        while (true) {
            try {
                System.out.print("> ");
                String line = StdIn.readLine();
                String[] rawTokens = line.split(" ");
                String command = rawTokens[0];
                String[] tokens = new String[rawTokens.length - 1];
                System.arraycopy(rawTokens, 1, tokens, 0, rawTokens.length - 1);
                switch (command) {
                    case "quit": 
                        return;
                    case "help":
                        System.out.println(Arrays.toString(help));
                        break;  
                    case "range": 
                        if (endDate <= startDate) {
                            System.out.println("Start Date must be before End Date.");
                        } else {
                            startDate = Integer.parseInt(tokens[0]); 
                            endDate = Integer.parseInt(tokens[1]);
                            System.out.println("Start date: " + startDate);
                            System.out.println("End date: " + endDate);
                        }
                        break;
                    case "count":
                        count(tokens[0], tokens[1]);
                        break;
                    case "hyponyms":
                        hyponyms(tokens[0]);
                        break;
                    case "history":
                        history(tokens, startDate, endDate);
                        break;
                    case "hypohist":
                        hypohist(tokens);
                        break;          
                    case "wordlength":
                        wordlength();
                        break;
                    case "zipf":
                        zipf(Integer.parseInt(tokens[0]));
                        break;                        
                    default:
                        System.out.println("Invalid command.");  
                        break;
                }
            } catch (NullPointerException e) {
                System.out.println("Null Pointer Exception");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Out of bounds of an Array");
            }
        }
    }

    private static void count(String word, String year) { 
        int yearInt = Integer.parseInt(year);
        System.out.println(ngm.countInYear(word, yearInt));
    }

    private static void history(String[] words, int start, int end) {
        plotter.plotAllWords(ngm, words, startDate, endDate);
    }

    private static void hyponyms(String word) {
        System.out.println(wn.hyponyms(word));
    }

    private static void hypohist(String[] words) {
        plotter.plotCategoryWeights(ngm, wn, words, startDate, endDate);
    }

    private static void wordlength() {
        plotter.plotProcessedHistory(ngm, startDate, endDate, wlp);
    }

    private static void zipf(int year) {
        plotter.plotZipfsLaw(ngm, year);
    }
}







