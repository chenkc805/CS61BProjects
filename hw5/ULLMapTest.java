import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Iterator;

/** ULLMapTest. You should write additional tests.
 *  @author Josh Hug
 */

public class ULLMapTest {

    @Test
    public void testBasic() {
        ULLMap<String, String> um = new ULLMap<String, String>();
        um.put("Gracias", "Dios Basado");
        assertEquals(um.get("Gracias"), "Dios Basado");
    }

    
    @Test
    public void testIterator() {
        ULLMap<Integer, String> um = new ULLMap<Integer, String>();
        um.put(0, "zero");
        um.put(1, "one");
        um.put(2, "two");
        Iterator<Integer> umi = um.iterator();
        System.out.println(umi.next());
        System.out.println(umi.next());
        System.out.println(umi.next());
    }

    @Test
    public void testInverse() {
        ULLMap<Integer, String> um = new ULLMap<Integer, String>();
        um.put(0, "zero");
        um.put(1, "one");
        um.put(2, "two");
        Iterator<Integer> umi = um.iterator();
        System.out.println(umi.next());
        System.out.println(umi.next());
        System.out.println(umi.next());
        assertEquals("zero", um.get(0));
        ULLMap<String, Integer> mu = ULLMap.inverse(um);
        Iterator<String> mui = mu.iterator();
        System.out.println(mui.next());
        System.out.println(mui.next());
        System.out.println(mui.next());
        assertEquals((int) 0,(int) mu.get("zero"));
    }

    @Test
    public void testPut() {
        ULLMap<Integer, String> um = new ULLMap<Integer, String>();
        um.put(0, "zero");
        um.put(1, "one");
        um.put(2, "two");
        assertEquals("one", um.get(1));
        assertEquals(null, um.get(4));
    }

    @Test
    public void testSize() {
        ULLMap<Integer, String> um = new ULLMap<Integer, String>();
        um.put(0, "zero");
        assertEquals(1, um.size());
        um.put(1, "one");
        um.put(2, "two");
        assertEquals(3, um.size());
    }

    @Test
    public void testClear() {
        ULLMap<Integer, String> um = new ULLMap<Integer, String>();
        um.put(0, "zero");
        um.put(1, "one");
        um.put(2, "two");
        assertEquals(3, um.size());
        um.clear();
        assertEquals(0, um.size());
    }
    

    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(ULLMapTest.class);
    }
} 