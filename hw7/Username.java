import java.lang.StringBuilder;
import java.util.Random;

public class Username {

    // Potentially useless note: (int) '0' == 48, (int) 'a' == 97

    // Instance Variables (remember, they should be private!)
    // YOUR CODE HERE
    // private String usrname;
    private String usrname;
    //private HashMap<String, String> database;

    public Username() {
        StringBuilder sb = new StringBuilder();
        int oneOrTwo = randInt(1,2);
        if (oneOrTwo == 1) {
            int oneOrTwoOrThree = randInt(1,3);
            for (int i = 0; i <= 2; i++) {
                if (oneOrTwoOrThree == 1) {
                    sb.append((char) randInt(65, 90));
                } else if (oneOrTwoOrThree == 2) {
                    sb.append((char) randInt(97, 122));
                } else {
                    sb.append(randInt(0, 9));
                }
            }
        } else {
            int oneOrTwoOrThree = randInt(1,3);
            for (int i = 0; i <= 3; i++) {
                if (oneOrTwoOrThree == 1) {
                    sb.append((char) randInt(65, 90));
                } else if (oneOrTwoOrThree == 2) {
                    sb.append((char) randInt(97, 122));
                } else {
                    sb.append(randInt(0, 9));
                }
            }
        }
        usrname = sb.toString();
        //database.put(usrname, null);
    }

    /* Returns a random integer from MIN to MAX, inclusive. 
     * Taken from: http://stackoverflow.com/questions/363681/generating-random-integers-in-a-range-with-java
     */
    private static int randInt(int min, int max) {
        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public Username(String reqName) {
        if (reqName == null) {
            throw new NullPointerException("Requested username is null!");
        }
        int len = reqName.length();
        if (len < 2 || len > 3) {
            throw new IllegalArgumentException("Your username must be either 2 or 3 characters long.");
        }
        for (int i = 0; i < len; i++)  {
            if (!Character.isLetterOrDigit(reqName.charAt(i))) {
                throw new IllegalArgumentException("This username contains invalid characters. All characters must be alpha-numeric.");
            }
        }
        usrname = reqName;
        //database.put(reqName, null);
    }

    public boolean validUsername(String username) {
        if (len < 2 || len > 3) {
            return false;
        }
        for (int i = 0; i < len; i++)  {
            if (!Character.isLetterOrDigit(reqName.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    // public String getUsername() {
    //     return usrname;
    // }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Username) {
            int first = this.hashCode();
            int second = o.hashCode();
            return first == second;
        }
        return false;
    }

    @Override
    public int hashCode() { 
        int result = 0;
        for (int i = 0; i < usrname.length(); i++) {
            int asciiCode = (int) usrname.charAt(i);
            if (97 <= asciiCode && asciiCode <= 122) {
                asciiCode = asciiCode - 32;
            }
            result = (result |  (usrname.charAt(i)));
            if (i != usrname.length() - 1) {
                result = result << 7;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        // You can put some simple testing here.
        Username user1 = new Username();
        Username user2 = new Username("BoB");
        Username user3 = new Username();
        Username user4 = user3;
        System.out.println(user4.equals(user3));
        System.out.println(user3.equals(user2));
        System.out.println(user1.hashCode());

    }
}