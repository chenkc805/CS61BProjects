import java.util.*;

public final class Test {

  public static void main(String... aArguments) {
    LinkedList<String> flavours = new LinkedList<String>();
    flavours.add("chocolate");
    flavours.add("strawberry");
    flavours.add("vanilla");

    useWhileLoop(flavours);

    useForLoop(flavours);
  }

  private static void useWhileLoop(LinkedList<String> aFlavours) {
    Iterator<String> flavoursIter = aFlavours.descendingIterator();
    while (flavoursIter.hasNext()){
      System.out.println(flavoursIter.next());
    }
  }

  /**
  * Note that this for-loop does not use an integer index.
  */
  private static void useForLoop(LinkedList<String> aFlavours) {
    for (Iterator<String> flavoursIter = aFlavours.iterator(); flavoursIter.hasNext();){
      System.out.println(flavoursIter.next());
    }
  }
} 