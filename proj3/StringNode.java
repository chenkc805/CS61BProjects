public class StringNode {
    private double key;
    private String value;

    public StringNode(double k, String v) {
        this.key = k;
        this.value = v;
    }

    public String getString() {
        return value;
    }

    public double getDouble() {
        return key;
    }
}