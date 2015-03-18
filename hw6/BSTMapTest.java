import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;


public class BSTMapTest {

    @Test
    public void basicTest() {
        BSTMap<Integer, Integer> test = new BSTMap<Integer, Integer>();
        test.put(5, 5);
        test.put(6, 6);
        test.put(4, 4);
        test.put(10, 10);
        test.put(100, 100);
        test.put(1, 1);
        test.put(25, 25);
        test.put(36, 36);
        test.put(49, 49);
    }

    @Test
    public void testSize() {
        BSTMap<Integer, Integer> test = new BSTMap<Integer, Integer>();
        test.put(5, 5);
        test.put(6, 6);
        test.put(4, 4);
        test.put(10, 10);
        test.put(100, 100);
        test.put(5, 4);
        test.put(25, 25);
        test.put(36, 36);
        test.put(49, 49);
        assertEquals((Integer) 8,(Integer) test.size());
    }

    @Test
    public void testPrint() {
        BSTMap<Integer, Integer> test = new BSTMap<Integer, Integer>();
        test.put(5, 5);
        test.put(6, 6);
        test.put(4, 4);
        test.put(10, 10);
        test.put(100, 100);
        test.put(1, 1);
        test.put(25, 25);
        test.put(36, 36);
        test.put(49, 49);
        test.printInOrder();
    }

    @Test
    public void testGet() {
        BSTMap<Integer, Integer> test = new BSTMap<Integer, Integer>();
        test.put(5, 5);
        test.put(6, 6);
        test.put(4, 4);
        test.put(10, 10);
        test.put(100, 100);
        test.put(5, 4);
        test.put(25, 25);
        test.put(36, 36);
        test.put(49, 49);
        assertEquals((Integer) 25,(Integer) test.get(25));
        assertEquals((Integer) 4,(Integer) test.get(5));
        assertEquals(null, test.get(7));
    }
    
    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(BSTMapTest.class);
    }
}   