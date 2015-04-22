import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Sorts a list of words with a given alphabet 
 * @author Kevin Chen
 */
public class AlphabetSort {

    private static final int R = 256;
    private static String[] toSort;
    private static String[] temp; 
    private static List<Character> alphabet;

    /**
     *  Determines the order of where a certain character should fall given a certain alphabet. 
     *  @param s is the string
     *  @param d the index of the character being analyzed
     *  @return the rank of the d. The lower the number, earlier in the sort the character falls.
     */
    private static int charAt(String s, int d) {  
        if (d < s.length()) {
            return alphabet.indexOf(s.charAt(d));
        } else {
            return -1;
        }
    }

    /**
     *  Sorts an array of strings recursively using MSD Radix Sort. 
     *  @param a The array of strings to be sorted
     */
    public static void sortMSD(String[] a) {
        int N = a.length;
        temp = new String[N];
        sortMSD(a, 0, N - 1, 0);
    }

    /**
     *  Sorts an array of strings recursively using MSD Radix Sort. 
     *  @param a The array of strings to be sorted
     *  @param low The lower index of array to begin at
     *  @param high The higher index of array to end at
     *  @param d The character that we are currently at as we interate through strings.
     */
    private static void sortMSD(String[] a, int low, int high, int d) { 
        if (high <= low + 1) {
            return;
        } 
        int[] count = new int[R + 2];       
        for (int i = low; i <= high; i++) {
            count[charAt(a[i], d) + 2]++;
        }
        for (int i = 0; i < R + 1; i++) {     
            count[i + 1] += count[i];
        }
        for (int i = low; i <= high; i++) {   
            temp[count[charAt(a[i], d) + 1]] = a[i];
            count[charAt(a[i], d) + 1]++;
        }
        for (int i = low; i <= high; i++) {  
            a[i] = temp[i - low];
        }
        for (int i = 0; i < R; i++) {
            sortMSD(a, low + count[i], low + count[i + 1] - 1, d + 1);
        }
    }

    /**
     * Main method that initializes the input file
     * @param args are the arguments when initializing this java file
     */ 
    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String input = in.readLine();
            char[] charArray = input.toCharArray();
            alphabet = new ArrayList<Character>();
            for (int i = 0; i < charArray.length; i++) {
                alphabet.add(charArray[i]);
            }
            HashSet<Character> alphabetSet = new HashSet<Character>(alphabet);
            input = in.readLine();
            if (input == null || alphabetSet.size() < alphabet.size()) {
                throw new IllegalArgumentException();
            }
            ArrayList<String> toSortArrayList = new ArrayList<String>();
            while (input != null) {
                toSortArrayList.add(input);
                input = in.readLine();
            }
            Object[] toSortObject = toSortArrayList.toArray();
            toSort = Arrays.copyOf(toSortObject, toSortObject.length, String[].class);

            System.out.println(Arrays.toString(toSort));
            sortMSD(toSort);
            System.out.println(Arrays.toString(toSort));

        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}

