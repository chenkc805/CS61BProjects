import static org.junit.Assert.*;

import org.junit.Test;

/** Class that demonstrates basic Trie functionality.
 *  @author Josh Hug
 */
public class TrieTest {

    @Test
    public void testBasic() {
        Trie t = new Trie();
        t.insert("hello");
        t.insert("hey");
        t.insert("goodbye");
        t.insert("goodbyes");
        t.insert("chicken");
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

    @Test
    public void testPartial() {
        Trie t = new Trie();
        t.insert("hello");
        t.insert("hey");
        t.insert("goodbye");
        t.insert("goodbyes");
        t.insert("chicken");
        t.insert("breakfast");
        assertFalse(t.find("ello", false));
        assertFalse(t.find("fast", true));
        assertFalse(t.find("hick", false));
    }

    @Test
    public void testFindEmpty() {
        Trie t = new Trie();
        t.insert("hello");
        t.insert("hey");
        t.insert("goodbye");
        t.insert("goodbyes");
        t.insert("chicken");
        t.insert("breakfast");
        assertFalse(t.find("", false));
        assertFalse(t.find(null, false));
    }

    @Test
    public void testMultipleWords() {
        Trie t = new Trie();
        t.insert("two words");
        assertFalse(t.find("two", true));
        assertTrue(t.find("two", false));
        assertTrue(t.find("two ", false));
        assertFalse(t.find("words", true));
        assertTrue(t.find("two words", true));
        t.insert("two ");
        assertTrue(t.find("two", false));
        assertTrue(t.find("two ", true));
        assertTrue(t.find("two words", true));
    }

    @Test
    public void testCaseSensitive() {
        Trie t = new Trie();
        t.insert("two words");
        assertFalse(t.find("two", true));
        assertTrue(t.find("two", false));
        assertTrue(t.find("two ", false));
        assertFalse(t.find("words", true));
        assertTrue(t.find("two words", true));
        t.insert("two ");
        assertTrue(t.find("two", false));
        assertTrue(t.find("two ", true));
        assertTrue(t.find("two words", true));
        t.insert("TWO");
        assertTrue(t.find("TWO", true));
        assertTrue(t.find("TWO", false));
        assertFalse(t.find("two", true));
        assertFalse(t.find("twO", true));
    }

    @Test
    public void testPunctuation() {
        Trie t = new Trie();
        t.insert("two words");
        assertFalse(t.find("two", true));
        assertTrue(t.find("two", false));
        assertTrue(t.find("two ", false));
        assertFalse(t.find("words", true));
        assertTrue(t.find("two words", true));
        t.insert("two ");
        assertTrue(t.find("two", false));
        assertTrue(t.find("two ", true));
        assertTrue(t.find("two words", true));
        t.insert("TWO!!");
        assertTrue(t.find("TWO", false));
        assertTrue(t.find("TWO!!", true));
    }

    @Test
    public void testEmptyString() {
        Trie t = new Trie();
        // t.insert(null);

    }

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TrieTest.class);
    }                 
} 
