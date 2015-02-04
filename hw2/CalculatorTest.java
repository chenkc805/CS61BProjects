import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
    /* Do not change this to be private. For silly testing reasons it is public. */
    public Calculator tester;

    /**
     * setUp() performs setup work for your Calculator.  In short, we 
     * initialize the appropriate Calculator for you to work with.
     * By default, we have set up the Staff Calculator for you to test your 
     * tests.  To use your unit tests for your own implementation, comment 
     * out the StaffCalculator() line and uncomment the Calculator() line.
     **/
    @Before
    public void setUp() {
        // tester = new StaffCalculator(); // Comment me out to test your Calculator
        tester = new Calculator();   // Un-comment me to test your Calculator
    }

    // TASK 1: WRITE JUNIT TESTS
    @Test
    public void testAdd() {

        assertEquals(11, tester.add(5,6));
        assertEquals(-4, tester.add(-3,-1));
        assertEquals(-7, tester.add(-10, 3));
        assertEquals(-7, tester.add(10, -17));
    }

    @Test
    public void testMultiply() {
        assertEquals(5, tester.multiply(5,1));
        assertEquals(30, tester.multiply(3, 10));
    }

    @Test
    public void testHistory() {
        int result = tester.add(5,6);
        tester.saveEquation("5 + 6", result);
        int result2 = tester.multiply(2,3);
        tester.saveEquation("2 * 3", result2);
        assertEquals(17, tester.cumulativeSum());
        assertEquals(66, tester.cumulativeProduct());
        tester.printAllHistory();
        tester.undoEquation();
        assertEquals(11, tester.cumulativeProduct());
        tester.printAllHistory();
    }

    @Test
    public void testClearHistory() {
        int result = tester.add(5,6);
        tester.saveEquation("5 + 6", result);
        int result2 = tester.multiply(2,3);
        tester.saveEquation("2 * 3", result2);
        assertEquals(17, tester.cumulativeSum());
        tester.clearHistory();
        assertEquals(1, tester.cumulativeProduct());
        assertEquals(0, tester.cumulativeSum());
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(CalculatorTest.class);
    }       
}