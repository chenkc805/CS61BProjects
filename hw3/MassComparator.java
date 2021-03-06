import java.util.Comparator;

/**
 * MassComparator.java
 */

public class MassComparator implements Comparator<Planet> {

    public MassComparator() {
    }

    /** Returns the difference in mass as an int.
     *  Round after calculating the difference. */
    public int compare(Planet planet1, Planet planet2) {
        return (int) (planet1.mass() - planet2.mass());
    }

}