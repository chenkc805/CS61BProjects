import static org.junit.Assert.*;

import org.junit.Test;

/** Class that demonstrates basic WeightedTrie functionality.
 *  @author Kevin Chen
 */
public class WeightedTrieTest {

    @Test
    public void testBasic() {
        WeightedTrie t = new WeightedTrie();
        t.insert("hello", 100);
        t.insert("hey", 95);
        t.insert("goodbye", 88);
        t.insert("goodbyes", 78);
        t.insert("chicken", 12);
        assertTrue(t.find("hell", false));
        assertTrue(t.find("hello", true));
        assertTrue(t.find("good", false));
        assertTrue(t.find("goodbyes", true));
        assertTrue(t.find("goodbye", true));
        assertTrue(t.find("chicken", true));
        assertFalse(t.find("chio", false));
        assertFalse(t.find("chick", true));
        assertFalse(t.find("bye", false));
        assertFalse(t.find("heyy", false));
        assertFalse(t.find("hell", true));   
    }

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(WeightedTrieTest.class);
    }                 
} 
