import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/** ArrayList61BTest. You should write additional tests.
 *  @author Josh Hug
 */

public class ArrayList61BTest {
    @Test
    public void basicTest() {
        List<Integer> L = new ArrayList61B<Integer>();
        L.add(5);
        L.add(10);
        assertTrue(L.contains(5));        
        assertFalse(L.contains(0));
    }

    @Test
    public void testSize() {
        List<Integer> L = new ArrayList61B<Integer>();
        L.add(5);
        L.add(10);
        assertEquals(2, L.size());
        L.add(15);
        assertEquals(4, L.size());
    }
    
    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(ArrayList61BTest.class);
    }
}   