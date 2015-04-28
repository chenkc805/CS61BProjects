public class Node {
    private final int R = 400;
    boolean fullWord;
    boolean partialWord;
    Node[] links;
    double maxWeight;
    double existsWeight;

    public Node() {
        this(0.0, 0.0);
    }

    public Node(double maxWeight) {
        this(maxWeight, 0.0);
    }


    public Node(double maxWeight, double existsWeight) {
        this.links = new Node[R];
        this.fullWord = false;
        this.partialWord = false;
        this.maxWeight = maxWeight;
        this.existsWeight = existsWeight;
    }
}