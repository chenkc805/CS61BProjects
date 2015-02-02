/**
 * @author melaniecebula
 */
public class Triangle {

  int s1;
  int s2;
  int s3;

  public Triangle(int side1, int side2, int side3) {
    s1 = side1;
    s2 = side2;
    s3 = side3;
  }

  /** Returns a String describing the type of triangle. */
  public String triangleType() {

<<<<<<< HEAD
    // Check that nothing is negative
=======
    // Chceck that nothing is negative
>>>>>>> 97ebda5e930db6b1fe3a92f8cf7e43430c0550d0
    if (s1 <= 0 || s2 <= 0 || s3 <= 0) {
      return "At least one length is less than 0!";
    }

    // Check for side length
    if ((s1 + s2 <= s3) || (s1 + s3 <= s2) || (s2 + s3 <= s1)) {
      return "The lengths of the triangles do not form a valid triangle!";
    }  

    // Return triangle type
    //TODO:  return either "Equilateral", "Isosceles", or "Scalene"
<<<<<<< HEAD
    if (s1 == s2 && s2 == s3) {
      return "Equilateral";
    }

    if (s1 == s2 || s2 == s3 || s3 == s1) {
      return "Isosceles";
    }
    else {
      return "Scalene";
    }
=======
    return null; //REPLACE THIS LINE
>>>>>>> 97ebda5e930db6b1fe3a92f8cf7e43430c0550d0
  }
}
