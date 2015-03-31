import java.util.HashMap;
import java.util.Map;
import java.lang.StringBuilder;
import java.util.Random;

public class UsernameBank {

    // Instance variables (remember, they should be private!)
    // YOUR CODE HERE
    private HashMap<String, String> usernameBank;
    private HashMap<String, String> emailBank;
    private HashMap<String, Integer> invalidUsernames;
    private HashMap<String, Integer> invalidEmails;

    public UsernameBank() {
        usernameBank = new HashMap<String, String>();
        emailBank = new HashMap<String, String>();
        invalidUsernames = new HashMap<String, Integer>();
        invalidEmails = new HashMap<String, Integer>();
    }

    public void generateUsername(String username, String email) {
        if (username == null || email == null) {
            throw new NullPointerException("Both inputs have to be valid strings.");
        }
        int len = username.length();
        if (len < 2 || len > 3) {
            throw new IllegalArgumentException("Your username must be either 2 or 3 characters long.");
        }
        for (int i = 0; i < len; i++)  {
            if (!Character.isLetterOrDigit(username.charAt(i))) {
                throw new IllegalArgumentException("This username contains invalid characters. All characters must be alpha-numeric.");
            }
        }
        if (usernameBank.containsKey(username)) {
            throw new IllegalArgumentException("This username is already in our database.");
        }
        usernameBank.put(username, email);
        emailBank.put(email, username);
    }

    private static int randInt(int min, int max) {
        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    // private boolean validUsername(String username) {
    //     int len = username.length();
    //     if (len < 2 || len > 3) {
    //         return false;
    //     }
    //     for (int i = 0; i < len; i++)  {
    //         if (!Character.isLetterOrDigit(reqName.charAt(i))) {
    //             return false;
    //         }
    //     }
    //     return true;
    // }

    public String getEmail(String username) {
        if (username == null) {
            throw new NullPointerException("Username is null.");
        }
        if (!checkUsername(username) || usernameBank.get(username) == null) {
            if (invalidUsernames.containsKey(username)) {
                invalidUsernames.put(username, invalidUsernames.get(username) + 1);
            } else {
                invalidUsernames.put(username, 1);
            }
            return null;
        }
        return usernameBank.get(username);
    }

    private boolean checkUsername(String reqName) {
        if (reqName == null) {
            return false;
        }
        int len = reqName.length();
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

    public String getUsername(String userEmail)  {
        if (userEmail == null) {
            throw new NullPointerException("Email is null.");
        }
        String result = emailBank.get(userEmail);
        if (result == null) {
            if (invalidEmails.containsKey(userEmail)) {
                invalidEmails.put(userEmail, invalidEmails.get(userEmail) + 1);
            } else {
                invalidEmails.put(userEmail, 1);
            }
            return null;
        }
        return result;
    }

    private boolean checkEmail(String userEmail) {
        int count = 0;
        for (int i = 0; i < userEmail.length(); i++) {
            if (i == 0 && Character.toString(userEmail.charAt(i)).equals("@")) {
                return false;
            }
            if (i != 0 && Character.toString(userEmail.charAt(i)).equals("@")) {
                count++;
            }
        }
        if (count != 1) {
            return false;
        }
        return true;
    }

    public Map<String, Integer> getBadEmails() {
        return invalidEmails;
    }

    public Map<String, Integer> getBadUsernames() {
        return invalidUsernames;
    }

    public String suggestUsername() {
        String result = suggestUsernameHelper();
        int i = 0;
        while (usernameBank.containsKey(result)) {
            result = suggestUsernameHelper();
            if (i > 100) {
                return null;
            }
            i++;
        }
        return result;
    }

    private String suggestUsernameHelper() {
        String result = null;
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
        return sb.toString();
    }

    // The answer is somewhere in between 3 and 15.
    public static final int followUp() {
        return 5;
    }

    // Optional, suggested method. Use or delete as you prefer.
    private void recordBadUsername(String username) {
        // YOUR CODE HERE
    }

    // Optional, suggested method. Use or delete as you prefer.
    private void recordBadEmail(String email) {
        // YOUR CODE HERE
    }
}