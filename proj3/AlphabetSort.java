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
    private static int maxLength;

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
        temp = new String[a.length];
        sortMSD(a, 0, a.length - 1, 0);
    }

    /**
     *  Sorts an array of strings recursively using MSD Radix Sort. 
     *  @param a The array of strings to be sorted
     *  @param low The lower index of array to begin at
     *  @param high The higher index of array to end at
     *  @param d The character that we are currently at as we interate through strings.
     */
    private static void sortMSD(String[] a, int low, int high, int d) { 
        if (high < low + 1) {
            insertion(a, low, high, d);
            return;
        }
        int[] count = new int[R + 2];       
        for (int i = low; i <= high; i++) {
            count[charAt(a[i], d) + 2]++;
        }
        for (int i = 0; i <= R; i++) {     
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
     *  Insertion sort algorithm 
     *  @param a The array of strings to be sorted
     *  @param low The lower index of array to begin at
     *  @param high The higher index of array to end at
     *  @param d The character that we are currently at as we interate through strings.
     */
    private static void insertion(String[] a, int low, int high, int d) {
        for (int i = low; i <= high; i++) {
            for (int j = i; j > low; j--) {
                if  (more(a[j], a[j - 1], d)) {
                    exch(a, j - 1, j);
                }
            }
        }
    }

    /**
     *  Exchange a[i] and a[j]
     *  @param a The array of strings to be exchanged
     *  @param i the index of first string to be exchanged
     *  @param j the index of second string to be exchanged with i
     */
    private static void exch(String[] a, int i, int j) {
        String tempString = a[i];
        a[i] = a[j];
        a[j] = tempString;
    }

    /**
     *  Checks if a character is less than or greater than another character.
     *  @param s the first string to compare
     *  @param t the second string to compare
     *  @param d in the index of the character being checked
     *  @return bool true depending on if the dth character in v is less than or equal to
     *          the dth character in w. 
     */
    private static boolean more(String s, String t, int d) {
        for (int i = d; i < Math.max(s.length(), t.length()); i++) {
            if (charAt(s, i) > charAt(t, i)) {
                return true;
            } else if (charAt(s, i) <= charAt(t, i)) {
                return false;
            }
        }
        return s.length() > t.length();
    }

    /**
     *  Removes strings from an array that do not fit the specifications of the alphabet 
     *  @param a The array to remove strings from
     *  @param d The index of the character of each string in A that we are checking
     */
    private static void remove(String[] a, int d) {
        if (d > maxLength) {
            return;
        }
        ArrayList<String> n = new ArrayList<String>();
        for (int i = 0; i < a.length; i++) {
            if (charAt(a[i], d) != -1 || d >= a[i].length()) {
                n.add(a[i]);
            } 
        }
        String[] newArray = new String[n.size()];
        toSort = n.toArray(newArray);
        remove(toSort, d + 1);
    }

    /**
     *  Sets the longest length of a string in String[] A to maxLength
     *  @param a The array to scan through
     */
    private static void getLengthLongestString(String[] a) {
        maxLength = 0;
        for (String s : a) {
            if (s.length() > maxLength) {
                maxLength = s.length();
            }
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
            if (input == null || input.equals("") || alphabetSet.size() < alphabet.size()) {
                throw new IllegalArgumentException();
            }
            ArrayList<String> toSortArrayList = new ArrayList<String>();
            while (input != null) {
                toSortArrayList.add(input);
                input = in.readLine();
            }
            Object[] toSortObject = toSortArrayList.toArray();
            toSort = Arrays.copyOf(toSortObject, toSortObject.length, String[].class);

            getLengthLongestString(toSort);
            remove(toSort, 0);
            sortMSD(toSort);
            for (int i = 0; i < toSort.length; i++) {
                System.out.println(toSort[i]);
            }

        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}

