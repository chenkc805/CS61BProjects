import static org.junit.Assert.*;

import org.junit.Test;

/** Class that demonstrates basic Trie functionality.
 *  @author Josh Hug
 */
public class AutocompleteTest {

    @Test
    public void testBasic() {
        In in = new In("wiktionary.txt");
        int N = in.readInt();
        String[] terms = new String[N];
        double[] weights = new double[N];
        for (int i = 0; i < N; i++) {
            weights[i] = in.readDouble();   // read the next weight
            in.readChar();                  // scan past the tab
            terms[i] = in.readLine();       // read the next term
        }

        Autocomplete autocomplete = new Autocomplete(terms, weights);

        assertEquals("Mr", autocomplete.topMatch("Mr"));
        assertEquals("the", autocomplete.topMatch("t"));
        assertEquals("of", autocomplete.topMatch("o"));
        assertEquals("and", autocomplete.topMatch("a"));
        assertEquals("another", autocomplete.topMatch("anot"));
        assertEquals("Gutenberg", autocomplete.topMatch("Gu"));
        assertEquals("mother", autocomplete.topMatch("mot"));
        assertEquals("Gutenberg", autocomplete.topMatch("Gu"));
        assertEquals("together", autocomplete.topMatch("tog"));

    }


    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(AutocompleteTest.class);
    }                 
} 
