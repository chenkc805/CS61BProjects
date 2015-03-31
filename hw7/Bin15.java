import java.lang.Math;
// Don't forget to answer the follow-up question!
public class Bin15 {

    // A string of exactly 15 characters, each a 0 or 1.
    private String myBinStr;

    // A constantly-whining constructor for your testing purposes.
    public Bin15(String input) {

        // Check for null input
        if (input == null) {
            String msg = "Your binary string is null";
            throw new NullPointerException(msg);
        }

        // Check for length
        if (input.length() != 15) {
            String msg = "Your binary string isn't of length 15";
            throw new IllegalArgumentException(msg);
        }

        // Check for illegal characters
        for (int count = 0; count < 15; count++) {
            char c = input.charAt(count);
            // Careful with comparing vs 0 and comparing vs '0'
            if (c != '0' && c != '1') {
                String msg = "Your binary string contains a non-binary character";
                throw new IllegalArgumentException(msg);
            }
        }

        // The input is good. Let's roll.
        this.myBinStr = input;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Bin15) {
            int first = this.hashCode();
            int second = o.hashCode();
            return first == second;
        }
        return false; // YOUR CODE HERE
    }
    
    @Override
    public int hashCode() {
        int index = 0;
        int result = 0;
        while (index < 15) {
            int bit = Character.getNumericValue(myBinStr.charAt(index));
            if (bit == 1) {
                result += Math.pow(2, 14-index);
            }
            index += 1;
        }
        return result; // YOUR CODE HERE
    }

    /* DO THIS LAST, AFTER IMPLEMENTING EVERYTHING
    Follow-up question: The current length of our myBinStr is 15. What is the
    longest length possible for this String such that we still can produce a
    perfect hash (assuming we can rewrite the hash function)? Write your answer
    in the method followUpAnswer(). 
    */
    public static final int followUpAnswer() {
        return 31; // YOUR CODE HERE. THIS MAY OR MAY NOT BE CORRECT.
    }
    
    public static void main(String[] args) {
        // Optional testing here. Potentially useless information:
        int c = 0x9 - 1 - 0b01;
        // 0x9 means 9 in hexadecimal
        // 1 means 1 in decimal
        // 0b01 means 01 or 1 in binary
        Bin15 bin1 = new Bin15("010101000000000");
        Bin15 bin2 = new Bin15("010101000000000");
        Bin15 bin3 = new Bin15("010101000000001");
        System.out.println(bin1.equals(bin2));
        System.out.println(bin1.equals(bin3));
        // System.out.println((char) 80);
        System.out.println("Note to self: Answer follow-up question!");
    }
}

