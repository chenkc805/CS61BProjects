import edu.princeton.cs.algs4.Digraph;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WordNet {

  private ArrayList<String> synsetNouns;
  private ArrayList<Integer> synsetIDs;
  private Digraph hyponym;
  private Set<String> nouns;

  /** Creates a WordNet using files form SYNSETFILENAME and HYPONYMFILENAME */
  public WordNet(String synsetFilename, String hyponymFilename) {
    In synsetScanner = new In(synsetFilename);

    synsetNouns = new ArrayList();
    nouns = new HashSet<String>();

    while (synsetScanner.hasNextLine()) {
      String row = synsetScanner.readLine();
      String[] tokens = row.split(",");
      synsetNouns.add(Integer.parseInt(tokens[0]),tokens[1]);
      synsetIDs.add(Integer.parseInt(tokens[0]));
      nouns.add(tokens[1]);
    }

    In hyponymScanner = new In(hyponymFilename);
    hyponym = new Digraph(synsetNouns.size());
    while (hyponymScanner.hasNextLine()) {
      String row = hyponymScanner.readLine();
      String[] tokens = row.split(",");
      for (int i = 1; i < tokens.length; i++) {
        hyponym.addEdge(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[i]));
      }
    }
  }

  /* Returns true if NOUN is a word in some synset. */
  public boolean isNoun(String noun) {
    return synsetNouns.contains(noun);
  }

  /* Returns the set of all nouns. */
  public Set<String> nouns() {
    return nouns;
  }

  /** Returns the set of all hyponyms of WORD as well as all synonyms of
    * WORD. If WORD belongs to multiple synsets, return all hyponyms of
    * all of these synsets. See http://goo.gl/EGLoys for an example.
    * Do not include hyponyms of synonyms.
    */
  public Set<String> hyponyms(String word) {
    Set<String> hyponyms = new HashSet<String>();
    Set<Integer> ids = new HashSet<Integer>();
    int i = 0;
    for (String x : synsetNouns) {
      if (x.contains(word)) {
        ids.add(i);
      }
      i += 1; 
    }
    Set<Integer> descendants = GraphHelper.descendants(hyponym, ids);
    for (Integer j: descendants) {
      hyponyms.add(synsetNouns.get(j));
    }
    return hyponyms;
  }
}










