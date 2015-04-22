import static org.junit.Assert.*;

import org.junit.Test;

/** Class that demonstrates basic Trie functionality.
 *  @author Josh Hug
 */
public class TrieTest {

    @Test
    public void testFullWord() {
        Trie t = new Trie();
        t.insert("hello");
        t.insert("hey");
        t.insert("goodbye");
        assertTrue(t.find("hell", false));
        assertTrue(t.find("hello", true));
        assertTrue(t.find("good", false));
        assertFalse(t.find("bye", false));
        assertFalse(t.find("heyy", false));
        assertFalse(t.find("hell", true));   
    }

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TrieTest.class);
    }                 
} 
