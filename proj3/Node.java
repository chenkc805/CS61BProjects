public class Node {
    private final int R = 128;
    String character;
    boolean exists;
    Node[] links;
    double maxWeight;
    double existsWeight;

    public Node() {
        this(null, 0.0, 0.0);
    }

    public Node(String c, double maxWeight) {
        this(c, maxWeight, 0.0);
    }

    public Node(String c, double maxWeight, double existsWeight) {
        this.character = c;
        this.links = new Node[R];
        this.exists = false;
        this.maxWeight = maxWeight;
        this.existsWeight = existsWeight;
    }
}