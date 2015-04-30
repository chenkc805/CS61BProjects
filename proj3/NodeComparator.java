import java.util.Comparator;

/**
 * Comparator for comparing values of StringNodes
 * @author Kevin Chen 
 */

public class NodeComparator implements Comparator<TSTNode>{

    public int compare(TSTNode n1, TSTNode n2) {
    	return -Double.compare(n1.maxVal, n2.maxVal);
    }
}
