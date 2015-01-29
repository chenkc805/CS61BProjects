/*
 * JUnit tests for the Triangle class
 */
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author melaniecebula
 */
public class TriangleTest {
  /**  We've already created a testScalene method.  Please fill in testEquilateral, and additionally
   *   create tests for Isosceles, Negative Sides, and Invalid sides
   **/

    @Test
    public void testScalene() {
        Triangle t = new Triangle(30, 40, 50);
        String result = t.triangleType();
        assertEquals("Scalene", result);
    }

    @Test
    public void testEquilateral() {
        Triangle t = new Triangle(40,40,40);
        Triangle u = new Triangle(50,50,50);
        Triangle v = new Triangle(60,60,60);
        assertEquals("Equilateral", t.triangleType());
        assertEquals("Equilateral", u.triangleType());
        assertEquals("Equilateral", v.triangleType());
    }

    @Test
    public void testIsosceles() {
        Triangle t = new Triangle(40, 50, 40);
        assertEquals("Isosceles", t.triangleType());
    }

    @Test
    public void testInvalid() {
        Triangle t = new Triangle(4, 50, 5);
        assertEquals("The lengths of the triangles do not form a valid triangle!", t.triangleType());
    }
    @Test
    public void testNegative() {
        Triangle t = new Triangle(-40, 50, 40);
        assertEquals("At least one length is less than 0!", t.triangleType());
    }
    public static void main(String[] args) {
      jh61b.junit.textui.runClasses(TriangleTest.class);
    }
}
