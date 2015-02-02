import static org.junit.Assert.*;
import org.junit.Test;

public class IntListTest {

    /** Example test that verifies correctness of the IntList.list static 
     *  method. The main point of this is to convince you that 
     *  assertEquals knows how to handle IntLists just fine.
     */

    @Test 
    public void testList() {
        IntList one = new IntList(1, null);
        IntList twoOne = new IntList(2, one);
        IntList threeTwoOne = new IntList(3, twoOne);

        IntList x = IntList.list(3, 2, 1);
        assertEquals(threeTwoOne, x);
    }

    @Test
    public void testdSquareList() {
      IntList L = IntList.list(1, 2, 3);
      IntList.dSquareList(L);
      assertEquals(IntList.list(1, 4, 9), L);
    }

    /** Do not use the new keyword in your tests. You can create
     *  lists using the handy IntList.list method.  
     * 
     *  Make sure to include test cases involving lists of various sizes
     *  on both sides of the operation. That includes the empty list, which
     *  can be instantiated, for example, with 
     *  IntList empty = IntList.list(). 
     *
     *  Keep in mind that dcatenate(A, B) is NOT required to leave A untouched.
     *  Anything can happen to A. 
     */

    //TODO:  Create testSquareListRecursive()
<<<<<<< HEAD
    @Test
    public void testSquareListRecursive() {
      IntList L = IntList.list(1, 2, 3);
      IntList M = IntList.squareListRecursive(L);
      IntList x = IntList.list(1, 2, 3);
      IntList y = IntList.list(1, 4, 9);
      assertEquals(L, x);
      assertEquals(M, y);
    }
    //TODO:  Create testDcatenate and testCatenate

    @Test
    public void testDCatenate() {
      IntList L = IntList.list(1, 2, 3);
      IntList M = IntList.list(4, 5, 6);
      IntList.dcatenate(L,M);
      IntList x = IntList.list(1, 2, 3, 4, 5, 6);
      assertEquals(x, L);
    }

    @Test
    public void testCatenate() {
      IntList L = IntList.list(1, 2, 3);
      IntList M = IntList.list(4, 5, 6);
      IntList x = IntList.list(1, 2, 3, 4, 5, 6);
      assertEquals(IntList.catenate(L, M), x);
    }

    /* Run the unit tests in this file. */
    public static void main(String[] args) {
=======
    //TODO:  Create testDcatenate and testCatenate

    /* Run the unit tests in this file. */
    public static void main(String... args) {
>>>>>>> 97ebda5e930db6b1fe3a92f8cf7e43430c0550d0
        jh61b.junit.textui.runClasses(IntListTest.class);
    }       
}   
