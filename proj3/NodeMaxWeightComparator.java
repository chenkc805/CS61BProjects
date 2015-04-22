/**
 * Comparator for comparing maxWeight values of Nodes
 * @author Kevin Chen 
 */

public class NodeMaxWeightComparator implements Comparator<Node>{

    public double compare(Node n1, Node n2) {
        return n1.maxWeight - n2.maxWeight;
    }
}
