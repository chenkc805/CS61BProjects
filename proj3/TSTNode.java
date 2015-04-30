/**
 * Node for TST.java
 * @author Kevin Chen
 */

public class TSTNode {
    public char c;                    
    public TSTNode left, mid, right;  
    public double val;
    public double maxVal;  

    public TSTNode() {
        this.val = -1;
        this.maxVal = -1;
    }                  
}