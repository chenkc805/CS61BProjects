/* Radix.java */

package radix;

/**
 * Sorts is a class that contains an implementation of radix sort.
 * @author
 */
public class Sorts {

    public static void main(String[] args) {
        int[] array = {135, 16, 3955, 193857, 129, 159812, 59};
        System.out.println(radixSort(array));
    }

    /**
     *  Sorts an array of int keys according to the values of <b>one</b>
     *  of the base-16 digits of each key. Returns a <b>NEW</b> array and
     *  does not modify the input array.
     *  
     *  @param key is an array of ints.  Assume no key is negative.
     *  @param whichDigit is a number in 0...7 specifying which base-16 digit
     *    is the sort key. 0 indicates the least significant digit which
     *    7 indicates the most significant digit
     *  @return an array of type int, having the same length as "keys"
     *    and containing the same keys sorted according to the chosen digit.
     **/
    public static int[] countingSort(int[] keys, int whichDigit) {
        int[] counts = new int[16];
        int[] positions = new int[16];
        int[] result = new int[keys.length];
        for (int i = 0; i < keys.length; i++) {
            int number = keys[i];
            int index = (number >>> (4 * whichDigit)) & 15;
            counts[index]++;  
        }
        positions[0] += counts[0];
        for (int i = 1; i < keys.length; i++) {
            positions[i] = positions[i-1] + counts[i];
        }
        for (int i = 0; i < keys.length; i++) {
            int number = keys[i];
            int index = (number >>> (4 * whichDigit)) & 15;      
            int place = positions[index];
            result[place] = number;     
        }
        return result;
    }

    /**
     *  radixSort() sorts an array of int keys (using all 32 bits
     *  of each key to determine the ordering). Returns a <b>NEW</b> array
     *  and does not modify the input array
     *  @param key is an array of ints.  Assume no key is negative.
     *  @return an array of type int, having the same length as "keys"
     *    and containing the same keys in sorted order.
     **/
    public static int[] radixSort(int[] keys) {
        int[] result = keys;
        for (int i = 0; i < 8; i++) {
            result = countingSort(result, i);
        }
        return result;
    }

}
