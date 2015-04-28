import java.util.Comparator;

/**
 * Comparator for comparing values of StringNodes
 * @author Kevin Chen 
 */

public class TSTNodeMaxWeightComparator implements Comparator<StringNode>{

    public int compare(StringNode n1, StringNode n2) {
    	return Double.compare(n2.getDouble(), n1.getDouble());
    }
}
