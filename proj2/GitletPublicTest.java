import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

/**
 * Class that provides JUnit tests for Gitlet, as well as a couple of utility
 * methods.
 * 
 * @author Joseph Moghadam
 * 
 *         Some code adapted from StackOverflow:
 * 
 *         http://stackoverflow.com/questions
 *         /779519/delete-files-recursively-in-java
 * 
 *         http://stackoverflow.com/questions/326390/how-to-create-a-java-string
 *         -from-the-contents-of-a-file
 * 
 *         http://stackoverflow.com/questions/1119385/junit-test-for-system-out-
 *         println
 * 
 */
public class GitletPublicTest {
    private static final String GITLET_DIR = ".gitlet/";
    private static final String TESTING_DIR = "test_files/";

    /* matches either unix/mac or windows line separators */
    private static final String LINE_SEPARATOR = "\r\n|[\r\n]";

    /**
     * Deletes existing gitlet system, resets the folder that stores files used
     * in testing.
     * 
     * This method runs before every @Test method. This is important to enforce
     * that all tests are independent and do not interact with one another.
     */
    @Before
    public void setUp() {
        File f = new File(GITLET_DIR);
        if (f.exists()) {
            recursiveDelete(f);
        }
        f = new File(TESTING_DIR);
        if (f.exists()) {
            recursiveDelete(f);
        }
        f.mkdirs();
    }

    /**
     * Tests that init creates a .gitlet directory. Does NOT test that init
     * creates an initial commit, which is the other functionality of init.
     */
    @Test
    public void testBasicInitialize() {
        gitlet("init");
        File f = new File(GITLET_DIR);
        assertTrue(f.exists());
    }

    @Test
    public void testBasicInitialize2() {
        gitlet("init");
        File f = new File(GITLET_DIR);
        assertTrue(f.exists());
        String printedOutput = gitlet("init");
        // assertTrue(printedOutput.contains("A gitlet version control system already exists in the current directory."));
        assertEquals("A gitlet version control system already exists in the current directory.\n", printedOutput);
    }

    @Test 
    public void testAdd1() {
        String dog1FileName = TESTING_DIR + "dog1.txt";
        String dog1Text = "This is a dog.";
        createFile(dog1FileName, dog1Text);
        gitlet("init");
        gitlet("add", dog1FileName);
        gitlet("commit", "first commit");
        String printedOutput = gitlet("add", dog1FileName);
        assertEquals("File has not been modified since the last commit.\n", printedOutput);
    }

    @Test 
    public void testAdd2() {
        String dog1FileName = TESTING_DIR + "dog1.txt";
        gitlet("init");
        gitlet("add", dog1FileName);
        String printedOutput = gitlet("add", dog1FileName);
        assertEquals("File does not exist.\n", printedOutput);
    }

    @Test
    public void testCommit1() {
        String dog1FileName = TESTING_DIR + "dog1.txt";
        String dog1Text = "Hi there!";
        createFile(dog1FileName, dog1Text);
        gitlet("init");
        gitlet("add", dog1FileName);
        gitlet("commit", "first commit");
        String logContent = gitlet("log");
        assertArrayEquals(new String[] { "first commit", "initial commit" },
                extractCommitMessages(logContent));
    }

    @Test
    public void testCommit2() {
        String dog1FileName = TESTING_DIR + "dog.txt";
        String dog1Text = "Hi there!";
        createFile(dog1FileName, dog1Text);
        gitlet("init");
        gitlet("add", dog1FileName);
        gitlet("commit", "first commit");
        String output = gitlet("commit", "second commit");
        assertEquals("No changes added to the commit.\n", output);
    }

    @Test
    public void testCommit3() {
        String dog1FileName = TESTING_DIR + "dog1.txt";
        String dog1Text = "Hi there!";
        createFile(dog1FileName, dog1Text);
        gitlet("init");
        gitlet("add", dog1FileName);
        String output = gitlet("commit");
        assertEquals("Please enter a commit message.\n", output);
    }

    /**
     * Tests that checking out a file name will restore the version of the file
     * from the previous commit. Involves init, add, commit, and checkout.
     */
    @Test
    public void testBasicCheckout() {
        String wugFileName = TESTING_DIR + "wug.txt";
        String wugText = "This is a wug.";
        createFile(wugFileName, wugText);
        gitlet("init");
        gitlet("add", wugFileName);
        gitlet("commit", "added wug");
        writeFile(wugFileName, "This is not a wug.");
        gitlet("checkout", wugFileName);
        assertEquals(wugText, getText(wugFileName));
    }

    @Test
    public void testBasicCheckout2() {
        String yingFileName = TESTING_DIR + "ying.txt";
        String yingText = "Ying says hi.";
        createFile(yingFileName, yingText);
        gitlet("init");
        gitlet("add", yingFileName);
        gitlet("commit", "added Ying");
        writeFile(yingFileName, "Ying says bye.");
        gitlet("checkout", yingFileName);
        assertEquals(yingText, getText(yingFileName));
    }

    /**
     * Tests that log prints out commit messages in the right order. Involves
     * init, add, commit, and log.
     */
    @Test
    public void testBasicLog() {
        gitlet("init");
        String commitMessage1 = "initial commit";

        String wugFileName = TESTING_DIR + "wug.txt";
        String wugText = "This is a wug.";
        createFile(wugFileName, wugText);
        gitlet("add", wugFileName);
        String commitMessage2 = "added wug";
        gitlet("commit", commitMessage2);

        String logContent = gitlet("log");
        assertArrayEquals(new String[] { commitMessage2, commitMessage1 },
                extractCommitMessages(logContent));
    }

    @Test
    public void testCheckoutID() {
        String wugFileName = TESTING_DIR + "wug.txt";
        String wugText = "This is a wug.";
        createFile(wugFileName, wugText);
        gitlet("init");
        gitlet("add", wugFileName);
        gitlet("commit", "added wug1");
        writeFile(wugFileName, "This is not a wug.");
        gitlet("add", wugFileName);
        gitlet("commit", "added wug2");
        writeFile(wugFileName, "This is DEFINITELY not a wug.");
        gitlet("add", wugFileName);
        gitlet("commit", "added wug3");
        writeFile(wugFileName, "poop");

        gitlet("checkout", "2", wugFileName);
        assertEquals("This is not a wug.", getText(wugFileName));

        gitlet("checkout", "1", wugFileName);
        assertEquals("This is a wug.", getText(wugFileName));

        gitlet("checkout", "3", wugFileName);
        assertEquals("This is DEFINITELY not a wug.", getText(wugFileName));
    }

    @Test
    public void testRemove() {
        String wugFileName = TESTING_DIR + "wug.txt";
        String testFileName = TESTING_DIR + "test.txt";
        String wugText = "This is a wug.";
        String testText = "This is a test.";
        createFile(testFileName, testText);
        createFile(wugFileName, wugText);
        gitlet("init");
        gitlet("add", wugFileName);
        gitlet("commit", "added wug1");
        writeFile(wugFileName, "This is not a wug.");
        gitlet("add", wugFileName);
        gitlet("rm", wugFileName);
        String noContent = gitlet("commit", "added wug2");
        gitlet("add", wugFileName);
        gitlet("add", testFileName);
        gitlet("commit", "added wug3");
        assertEquals("No changes added to the commit.\n", noContent);
    }

    @Test
    public void testRemove3() {
        String wugFileName = TESTING_DIR + "wug.txt";
        String testFileName = TESTING_DIR + "test.txt";
        String wugText = "This is a wug.";
        String testText = "This is a test.";
        createFile(testFileName, testText);
        createFile(wugFileName, wugText);
        gitlet("init");
        gitlet("add", wugFileName);
        gitlet("rm", wugFileName);
        gitlet("commit", "added wug1");
        gitlet("add", wugFileName);
    }

    @Test
    public void testFind() {
        String wugFileName = TESTING_DIR + "wug.txt";
        String wugText = "This is a wug.";
        createFile(wugFileName, wugText);
        gitlet("init");
        gitlet("add", wugFileName);
        gitlet("commit", "added wug1");
        writeFile(wugFileName, "This is not a wug.");
        gitlet("add", wugFileName);
        gitlet("commit", "added wug2");
        writeFile(wugFileName, "This is DEFINITELY not a wug.");
        gitlet("add", wugFileName);
        String content = gitlet("find", "initial commit");
        assertEquals("0\n", content);

        content = gitlet("find", "added wug1");
        assertEquals("1\n", content);

        content = gitlet("find", "WOOOW");
        assertEquals("Found no commit with that message.\n", content);
    }

    @Test
    public void testBranchAndGlobalLog() {
        String wugFileName = TESTING_DIR + "wug.txt";
        String testFileName = TESTING_DIR + "test.txt";
        String wugText = "This is a wug.";
        String testText = "This is a test.";
        createFile(testFileName, testText);
        createFile(wugFileName, wugText);
        gitlet("init");
        gitlet("add", wugFileName);
        gitlet("commit", "First commit");
        gitlet("branch", "branch1");
        gitlet("checkout", "branch1");
        gitlet("add", testFileName);
        gitlet("commit", "Second commit");

        gitlet("checkout", "master");
        writeFile(wugFileName, "This is not a wug.");
        writeFile(testFileName, "This is not a test.");
        gitlet("add", wugFileName);
        gitlet("add", testFileName);
        gitlet("commit", "Fourth commit");

        gitlet("checkout", "branch1");
        assertEquals("This is a test.", getText(testFileName));
        System.out.println("TEST GLOBAL LOG:\n " + gitlet("global-log"));
    }

    @Test
    public void testStatus() {
        String wugFileName = TESTING_DIR + "wug.txt";
        String testFileName = TESTING_DIR + "test.txt";
        String wugText = "This is a wug.";
        String testText = "This is a test.";
        createFile(testFileName, testText);
        createFile(wugFileName, wugText);
        gitlet("init");
        gitlet("add", wugFileName);
        gitlet("commit", "First commit");
        gitlet("branch", "branch1");
        gitlet("checkout", "branch1");
        gitlet("add", testFileName);
        gitlet("commit", "Second commit");

        gitlet("checkout", "master");
        writeFile(wugFileName, "This is not a wug.");
        writeFile(testFileName, "This is not a test.");
        gitlet("add", wugFileName);
        gitlet("add", testFileName);
        gitlet("rm", testFileName);
        
        System.out.println("TEST STATUS:\n " + gitlet("status"));
    }

    @Test
    public void testRemoveBranch() {
        gitlet("init");
        gitlet("branch", "branch1");
        gitlet("checkout", "branch1");
        gitlet("branch", "branch2");
        gitlet("checkout", "master");

        assertEquals("Cannot remove the current branch.\n", gitlet("rm-branch", "master"));
        gitlet("rm-branch", "branch2");
        
        System.out.println("TEST STATUS:\n " + gitlet("status"));
    }

    @Test
    public void addFiveFilesAndVerifyStatus() {
        gitlet("init");
        for (int i = 0; i < 5; i++) {
            String yingFileName = TESTING_DIR + "ying" + i + ".txt";
            String yingText = "hi";
            createFile(yingFileName, yingText);
            gitlet("add", yingFileName);
        }
        String statusOutput = gitlet("status");
        System.out.println(statusOutput);
        for (int i = 0; i < 5; i++){
            assertTrue(statusOutput.contains("ying" + i + ".txt"));
        }
    }

    @Test
    public void testRemove1() {
        String wugFileName = TESTING_DIR + "wug.txt";
        String wugText = "This is a wug.";
        createFile(wugFileName, wugText);
        gitlet("init");
        String output = gitlet("rm", wugFileName);
        assertEquals("No reason to remove the file.\n", output);
    }

    @Test 
    public void testRemove2() {
        String omgFileName = TESTING_DIR + "omg.txt";
        String omgText = "like no way";
        createFile(omgFileName, omgText);
        gitlet("init");
        gitlet("add", omgFileName);
        gitlet("rm", omgFileName);
        String output = gitlet("commit", "gurrrrrrl");
        assertEquals("No changes added to the commit.\n", output);
    }

    @Test
    public void testMerge() {
        String wugFileName = TESTING_DIR + "wug.txt";
        String testFileName = TESTING_DIR + "test.txt";
        String test2FileName = TESTING_DIR + "test2.txt";
        String wugText = "This is a wug.";
        String testText = "This is a test.";
        String test2Text = "This is a TESTUH.";
        createFile(testFileName, testText);
        createFile(wugFileName, wugText);
        createFile(test2FileName, test2Text);
        gitlet("init");
        gitlet("add", wugFileName);
        System.out.println("FIRST COMMIT: " + gitlet("commit", "First")); 
        gitlet("branch", "branch1");
        gitlet("checkout", "branch1");
        gitlet("add", testFileName);
        System.out.println(gitlet("commit", "Second commit"));
        writeFile(wugFileName, "This is not a wug.");
        writeFile(testFileName, "This is not a test.");
        gitlet("add", wugFileName);
        gitlet("add", testFileName);
        gitlet("commit", "branch1 commit");
        gitlet("checkout", "master");
        System.out.println(gitlet("merge", "branch1"));
        
        assertEquals("This is not a test.", getText(testFileName));
        assertEquals("This is not a wug.", getText(wugFileName));
    }

    @Test
    public void testRebase() {
        String wugFileName = TESTING_DIR + "wug.txt";
        String wugText = "This is a wg";
        createFile(wugFileName, wugText);
        gitlet("init");
        gitlet("add", wugFileName);
        gitlet("commit", "First");

        writeFile(wugFileName, "This is a wug");
        gitlet("add", wugFileName);
        gitlet("commit", "Second");
        gitlet("branch", "branch1");

        writeFile(wugFileName, "This is a wug.");
        gitlet("add", wugFileName);
        gitlet("commit", "Third");

        writeFile(wugFileName, "This is a wug...");
        gitlet("add", wugFileName);
        gitlet("commit", "Fourth");
        gitlet("checkout", "branch1");

        writeFile(wugFileName, "This is a wug!");
        gitlet("add", wugFileName);
        gitlet("commit", "Fifth");
        gitlet("branch", "branch2");

        writeFile(wugFileName, "This is a wug!!!");
        gitlet("add", wugFileName);
        gitlet("commit", "Sixth");
        gitlet("checkout", "branch2");

        writeFile(wugFileName, "This is a wug??!");
        gitlet("add", wugFileName);
        gitlet("commit", "Seventh");
        gitlet("checkout", "branch1");
        
        // System.out.println(gitlet("rebase", "master"));
        // writeFile(wugFileName, "This is a wug??!");
        // gitlet("checkout", wugFileName);
        
        assertEquals("This is a wug!!!", getText(wugFileName));
    }



    /**
     * Convenience method for calling Gitlet's main. Anything that is printed
     * out during this call to main will NOT actually be printed out, but will
     * instead be returned as a string from this method.
     * 
     * Prepares a 'yes' answer on System.in so as to automatically pass through
     * dangerous commands.
     * 
     * The '...' syntax allows you to pass in an arbitrary number of String
     * arguments, which are packaged into a String[].
     */
    private static String gitlet(String... args) {
        PrintStream originalOut = System.out;
        InputStream originalIn = System.in;
        ByteArrayOutputStream printingResults = new ByteArrayOutputStream();
        try {
            /*
             * Below we change System.out, so that when you call
             * System.out.println(), it won't print to the screen, but will
             * instead be added to the printingResults object.
             */
            System.setOut(new PrintStream(printingResults));

            /*
             * Prepares the answer "yes" on System.In, to pretend as if a user
             * will type "yes". You won't be able to take user input during this
             * time.
             */
            String answer = "yes";
            InputStream is = new ByteArrayInputStream(answer.getBytes());
            System.setIn(is);

            /* Calls the main method using the input arguments. */
            Gitlet.main(args);

        } finally {
            /*
             * Restores System.out and System.in (So you can print normally and
             * take user input normally again).
             */
            System.setOut(originalOut);
            System.setIn(originalIn);
        }
        return printingResults.toString();
    }

    /**
     * Returns the text from a standard text file (won't work with special
     * characters).
     */
    private static String getText(String fileName) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(fileName));
            return new String(encoded, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * Creates a new file with the given fileName and gives it the text
     * fileText.
     */
    private static void createFile(String fileName, String fileText) {
        File f = new File(fileName);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writeFile(fileName, fileText);
    }

    /**
     * Replaces all text in the existing file with the given text.
     */
    private static void writeFile(String fileName, String fileText) {
        FileWriter fw = null;
        try {
            File f = new File(fileName);
            fw = new FileWriter(f, false);
            fw.write(fileText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deletes the file and all files inside it, if it is a directory.
     */
    private static void recursiveDelete(File d) {
        if (d.isDirectory()) {
            for (File f : d.listFiles()) {
                recursiveDelete(f);
            }
        }
        d.delete();
    }

    /**
     * Returns an array of commit messages associated with what log has printed
     * out.
     */
    private static String[] extractCommitMessages(String logOutput) {
        String[] logChunks = logOutput.split("====");
        int numMessages = logChunks.length - 1;
        String[] messages = new String[numMessages];
        for (int i = 0; i < numMessages; i++) {
            System.out.println(logChunks[i + 1]);
            String[] logLines = logChunks[i + 1].split(LINE_SEPARATOR);
            messages[i] = logLines[3];
        }
        return messages;
    }

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(GitletPublicTest.class);
    }
}
