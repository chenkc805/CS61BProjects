//package radix;

import org.junit.Test;
import static org.junit.Assert.*;


public class SortsTest {

    @Test
    public void basic() {
        int[] array = {135, 16, 3955, 193857, 129, 159812, 59};
        System.out.println(Sorts.radixSort(array));
    }
    
    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(SortsTest.class);
    }
}   