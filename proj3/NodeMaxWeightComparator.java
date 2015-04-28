import java.util.Comparator;

/**
 * Comparator for comparing maxWeight values of Nodes
 * @author Kevin Chen 
 */

public class NodeMaxWeightComparator implements Comparator<Node>{

    public int compare(Node n1, Node n2) {
        if (n1.maxWeight > n2.maxWeight) {
            return -1;
        }
        if (n1.maxWeight < n2.maxWeight) {
            return 1;
        }
        return 0;
    }
}
