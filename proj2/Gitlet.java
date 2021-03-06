import java.io.Serializable;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Iterator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Gitlet implements Serializable {

    /* Gitlet class */
    static Gitlet g;

    /* The main commit tree. */
    GitletNode current;
    
    /* Contains all the branches.
     * KEY: Branch Name 
     * VAL: First GitletNode of KEY */
    HashMap<String, GitletNode> branches;

    /* All commits in the entire commit tree 
     * KEY: ID
     * VAL: GitletNode
     */
    HashMap<Integer, GitletNode> allCommits;

    /* Contains a list of the files that are staged. 
     * KEY: Filename
     * VAL: File
     */
    HashMap<String, File> staged;

    /* Contains a map of split points and the branches of those points. 
     * KEY: Splitpoint ID
     * VAL: ArrayList of branch names
     */
    HashMap<Integer, ArrayList<String>> splitPoints;

    /* Contains files that will be moved to the next node 
     * KEY: Filename
     * VAL: File
     */
    HashMap<String, File> toBeCommitted;

    /* Contains ID mapping for files in each node
     * KEY: Filename
     * VAL: ID
     */
    HashMap<String, Integer> toBeCommittedIDs;

    /* Gives the date and time of a commit. */
    String dateAndTime;

    /* List of items to be removed */
    ArrayList<String> removed;

    /* The most recent ID */
    int iD;

    /* The branch you are on */
    String branchName;

    /* Complete file paths with branch and ID for each commit */
    String path;

    /* The current directory */
    String currentDir;

    /* Main method that determines the command that wants to be run with Gitlet. 
     * It will either initialize a new .ser file or read an existing .ser file. 
     */
    public static void main(String[] args) {
        try {
            if (!ReadWriteFiles.fileExists(".gitlet/savedState.ser")) {
                if (args[0].equals("init")) {
                    g = new Gitlet();
                    g.init();
                } else {
                    System.out.println("Gitlet has not been initialized yet.");
                }
                return;
            }
            g = ReadWriteFiles.readSerFile(".gitlet/savedState.ser");
            if (g.dangerousMethods(args[0])) {
                if (!g.warning()) {
                    return;
                }
            }
            switch (args[0]) {
                case "init":
                    System.out.print("A gitlet version control system already exists in the");
                    System.out.println(" current directory.");
                    return;
                case "add": 
                    g.add(args[1]);
                    break;
                case "commit":
                    g.commit(args);
                    break;
                case "log":
                    g.log();
                    break;
                case "rm":
                    g.remove(args[1]);
                    break;
                case "rm-branch":
                    g.removeBranch(args[1]);
                    break;
                case "find":
                    g.find(args[1]);
                    break;
                case "global-log":
                    g.globalLog();
                    break;
                case "status":
                    g.status();
                    break;
                case "branch":
                    g.branch(args[1]);
                    break;
                case "reset":
                    g.reset(args[1]);
                    break; 
                case "merge":
                    g.merge(args[1]);
                    break;
                case "rebase":
                    g.rebase(args[1]);
                    break;
                case "i-rebase":
                    g.interactiveRebase(args[1]);
                    break;
                case "checkout":
                    g.checkoutParser(args);
                    break;
                default: 
                    System.out.println("Please enter a valid command.");
                    break;
            }
            ReadWriteFiles.writeSerFile(g, ".gitlet/savedState.ser");
        } catch (IndexOutOfBoundsException e) {
            System.out.print("Please specify which ID, branch, and/or file you wish to act");
            System.out.println(" upon.");
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes gitlet. Creates a new gitlet version 
     * control system in the current directory. This 
     * system will automatically start with one commit: 
     * a commit that contains no files and has the commit 
     * message "initial commit". 
     */
    public void init() {
        iD = 0;
        staged = new HashMap<String, File>();
        branches = new HashMap<String, GitletNode>();
        allCommits = new HashMap<Integer, GitletNode>();
        splitPoints = new HashMap<Integer, ArrayList<String>>();
        branchName = "master";
        removed = new ArrayList<String>();
        branches.put("master", current);
        File directory = new File(".gitlet/");
        if (!directory.exists()) {
            current = new GitletNode(null, null, getTime(), "initial commit", 0, branchName, null);
            branches.put(branchName, current);
            directory.mkdir();
            (new File(".gitlet/commits/master/0/")).mkdirs();
            iD++;
            changePath();
        } else {
            System.out.print("A gitlet version control system already");
            System.out.println(" exists in the current directory.");
        }
        allCommits.put(current.getID(), current);
        ReadWriteFiles.writeSerFile(g, ".gitlet/savedState.ser");
    }

    /**
     * Indicates you want the file to be included in 
     * the upcoming commit as having been changed. 
     * @param FILENAME: The file that you want to stage.
     */
    public void add(String fileName) {
        try {
            if (!Files.exists(Paths.get(fileName))) {
                System.out.println("File does not exist.");
                return;
            }
            File file = new File(fileName);
            /* Checks if the list is empty */
            if (current.getID() == 0) {
                staged.put(fileName, file);
                return;
            } 
            /* Checks if the headNode contains FILENAME */
            if (current.getFiles().containsKey(fileName)) {
                byte[] fileNameData = Files.readAllBytes(Paths.get(fileName));
                byte[] headNodeData = Files.readAllBytes(current.getFiles().get(fileName).toPath());
                if (Arrays.equals(fileNameData, headNodeData)) {
                    System.out.println("File has not been modified since the last commit.");
                    return;
                }
            }
            /* Stages the file */
            staged.put(fileName, file);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * Saves a snapshot of certain files that can be 
     * viewed or restored at a later time. 
     * @param MESSAGE: The message that is associated with the commit
     */
    public void commit(String[] args) {
        String message;
        try {
            message = args[1];
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Please enter a commit message.");
            return;
        }
        if (staged.isEmpty()) {
            System.out.println("No changes added to the commit.");
            return;
        }
        (new File(path)).mkdir();
        if (current.getFiles() == null) {
            toBeCommitted = new HashMap<String, File>();
        } else {
            toBeCommitted = current.getFiles();
        }
        if (current.getIDMap() == null) {
            toBeCommittedIDs = new HashMap<String, Integer>();
        } else {
            toBeCommittedIDs = current.getIDMap();
        }
        if (!removed.isEmpty()) {
            for (String rm : removed) {
                toBeCommittedIDs.remove(rm);
                toBeCommitted.remove(rm);
            }
        }
        HashMap<String, File> commitFiles = new HashMap<String, File>(toBeCommitted);
        HashMap<String, Integer> commitIDs = new HashMap<String, Integer>(toBeCommittedIDs);
        for (String fileName : staged.keySet()) {
            ReadWriteFiles.writeCommitFile(fileName, branchName, iD);
            File commitThis = ReadWriteFiles.readGenericFile(".gitlet/commits/" 
                + branchName + "/" + iD + "/" + fileName);
            commitFiles.put(fileName, commitThis);
            commitIDs.put(fileName, iD);
        }
        current = new GitletNode(commitFiles, commitIDs, getTime(), message, iD, 
            branchName, current);
        branches.put(branchName, current);
        allCommits.put(current.getID(), current);
        staged.clear();
        removed.clear();
        iD++;
        changePath();
    }

    /** 
     * Mark the file for removal; this means it will not be 
     * inherited as an old file in the next commit. If the 
     * file had been staged, instead unstage it.
     * @param FILENAME: The file to be removed
     */
    public void remove(String fileName) {
        if (staged.get(fileName) != null) {
            staged.remove(fileName);
            removed.add(fileName);
        } else {
            System.out.println("No reason to remove the file.");
            return;
        }
        if (current.getFiles() == null) {
            return;
        }
        if (current.getFiles().get(fileName) == null) {
            System.out.println("No reason to remove the file.");
            return;
        }
    }

    /** 
     * Starting at the current HEAD pointer, display information 
     * about each commit backwards along the commit tree until 
     * the initial commit.
     */ 
    public void log() {
        GitletNode pointer = current;
        while (pointer != null) {
            oneLog(pointer);
            pointer = pointer.next();
        }
    }

    /** 
     * Prints the log of one node 
     * @param POINTER: The node to write the log for
     */
    private void oneLog(GitletNode pointer) {
        System.out.println("====");
        System.out.println("Commit " + pointer.getID() + ".");
        System.out.println(pointer.getTimeStamp());
        System.out.println(pointer.getCommitMessage());
    }

    /**
     * Like log, except displays information about all commits 
     * ever made. The order of the commits does not matter.
     */ 
    public void globalLog() {
        ArrayList<String> logged = new ArrayList<String>();
        for (String branch : branches.keySet()) {
            GitletNode pointer = branches.get(branch);
            while (pointer != null) {
                if (logged.contains(pointer.getBranchName() + pointer.getID())) {
                    break;
                } else {
                    oneLog(pointer);
                    logged.add(pointer.getBranchName() + pointer.getID());
                    pointer = pointer.next();
                }
            }
        }
    }

    /**
     * Prints out the id of the commit that has the given 
     * commit MESSAGE. If there are multiple such commits, it 
     * prints the ids out on separate lines.
     * @param MESSAGE: The message of the commit that is to be found 
     */
    public void find(String message) {
        GitletNode pointer = current;
        while (pointer != null) {
            if (pointer.getCommitMessage().equals(message)) {
                System.out.println(pointer.getID());
                return;
            }
            pointer = pointer.next();
        }
        System.out.println("Found no commit with that message.");
    }

    /**
     * Displays what branches currently exist, and marks the 
     * current branch with a *. Also displays what files have 
     * been staged or marked for removal.
     */ 
    public void status() {
        System.out.println("=== Branches ===");
        for (String branch : branches.keySet()) {
            if (branch.equals(branchName)) {
                System.out.print("*");
            }
            System.out.println(branch);
        }
        System.out.println(" ");
        System.out.println("=== Staged Files ===");
        for (String stagedFile : staged.keySet()) {
            System.out.println(stagedFile);
        }
        System.out.println(" ");
        System.out.println("=== Files Marked for Removal ===");
        for (String remove : removed) {
            System.out.println(remove);
        }
    }

    public void checkoutParser(String[] args) {
        if (args.length == 3) {
            checkoutID(Integer.parseInt(args[1]), args[2]);
        } else if (g.branches.containsKey(args[1])) {
            checkoutBranch(args[1]);
        } else {
            checkoutFile(args[1]);
        }
    }

    /**
     * Restores the given file in the working directory to 
     * its state at the commit at the head of the current branch.
     * @param FILENAME: The file to be restored
     */ 
    public void checkoutFile(String fileName) {
        try {
            int id = current.getIDMap().get(fileName);
            if (id < current.getID()) {
                checkoutID(id, fileName);
                return;
            } 
            File file = new File(fileName);
            Path sourcePath = Paths.get(".gitlet/commits/" + current.getFullName() 
                + "/" + file.getPath());
            if (Files.exists(sourcePath)) {
                Files.copy(sourcePath, Paths.get(fileName), REPLACE_EXISTING); 
            }
        } catch (NullPointerException e) {
            System.out.print("File does not exist in the most recent ");
            System.out.println("commit or no such branch exists.");
            return;
        } catch (IOException e) {
            System.out.println(e);
            return;
        } 
    }

    /**
     * Restores the given file in the working directory to 
     * its state at the given commit.
     * @param COMMITID: The given commit
     * @param FILENAME: The file to be restored in the commit of COMMITID
     */
    public void checkoutID(int commitID, String fileName) {
        try {
            GitletNode pointer = allCommits.get(commitID);
            while (pointer != null) {
                int id = pointer.getIDMap().get(fileName);
                if (id < commitID) {
                    checkoutID(id, fileName);
                } else {
                    File file = new File(fileName);
                    Path sourcePath = Paths.get(".gitlet/commits/" 
                        + pointer.getFullName() + "/" + file.getPath());
                    Files.copy(sourcePath, Paths.get(fileName), REPLACE_EXISTING); 
                }
                return;
            }
            System.out.println("No commit with that id exists.");
        } catch (NullPointerException e) {
            System.out.println("File does not exist in that commit.");
            return;
        } catch (IOException e) {
            return;
        }
    }

    /**
     * Restores all files in the working directory to their 
     * versions in the commit at the head of the given branch. 
     * Considers the given branch to now be the current branch.
     * @param BRANCHNAME: The branch to be the current branch.
     */
    public void checkoutBranch(String branch) {
        try {
            if (!branches.containsKey(branch)) {
                System.out.println("File does not exist in the most recent commit,");
                System.out.println(" or no such branch exists.");
                return;
            } 
            if (branch.equals(branchName)) {
                System.out.println("No need to checkout the current branch.");
                return;
            }
            GitletNode pointer = current;
            current = branches.get(branch);
            branchName = branch;
            if (current.getFiles() == null) {
                return;
            }
            for (String fileName : pointer.getFiles().keySet()) {
                checkoutFile(fileName);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * Creates a new branch with the given name. A branch 
     * is nothing more than the name of a head pointer in 
     * the commit graph. Before you ever call branch, your 
     * code should be running with a default branch called 
     * "master". Note: Does NOT immediately switch to the 
     * newly created branch.
     * @param BRANCHNAME: The name of the new branch
     */ 
    public void branch(String branch) {
        if (branches.containsKey(branch)) {
            System.out.println("A branch with that name already exists.");
            return;
        }
        GitletNode newBranch = current;
        branches.put(branch, newBranch);
    }

    /**
     * Deletes the branch with the given name. 
     * @param BRANCHNAME: The name of the branch to be removed.
     */
    public void removeBranch(String branch) {
        if (branch.equals(branchName)) {
            System.out.println("Cannot remove the current branch.");
        } else if (branches.containsKey(branch)) {
            branches.remove(branch);
        } else {
            System.out.println("A branch with that name does not exist.");
        }
    }

    /**
     * Restores all files to their versions in the commit 
     * with the given id. Also moves the current branch's 
     * head to that commit node.
     * @param COMMITID: The ID of the commit node that is restored
     */ 
    public void reset(String commitID) {
        int id = Integer.parseInt(commitID);
        GitletNode node = allCommits.get(id);
        if (node == null) {
            System.out.println("No commit with that id exists.");
        } else {
            for (String fileName : node.getFiles().keySet()) {
                checkoutID(id, fileName);
            }
            current = node;
        }

    }

    /**
     * Merges files from the head of the given branch into 
     * the head of the current branch. 
     * @param BRANCHNAME: The branch to be merged into the 
     * current branch
     */ 
    public void merge(String branch) {
        try {
            GitletNode otherHead = branches.get(branch);
            System.out.println(branches);
            System.out.println(otherHead);
            System.out.println(current);
            if (otherHead == null) {
                System.out.println("A branch with that name does not exist.");
                return;
            }
            if (current == otherHead) {
                System.out.println("Cannot merge a branch with itself.");
                return;
            }

            /* Find the split node */
            SplitNode s = new SplitNode(current, otherHead);
            int splitID = s.getSplitNodeID();

            HashMap<String, File> currentFiles = current.getFiles();
            HashMap<String, Integer> currentIDs = current.getIDMap();
            HashMap<String, File> otherFiles = otherHead.getFiles();
            HashMap<String, Integer> otherIDs = otherHead.getIDMap();
            for (String fileName : otherFiles.keySet()) {
                int otherid = otherIDs.get(fileName);
                int id = -1;
                if (currentIDs.containsKey(fileName)) {
                    id = currentIDs.get(fileName);
                    if (otherid > splitID) {
                        if (id <= splitID) {
                            checkoutID(otherid, fileName);
                        } else {
                            File file = new File(".gitlet/commits/" + otherHead.getFullName()
                                + "/" + fileName);
                            if (file.exists()) {
                                System.out.println(4);
                                Files.copy(file.toPath(), Paths.get(fileName + ".conflicted"));
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds the split point of the current branch and the 
     * given branch, then snaps off the current branch at 
     * this point, then reattaches the current branch to the 
     * head of the given branch.
     * @param BRANCHNAME: The branch to connect our current branch 
     * onto. 
     */
    public void rebase(String branch) {
        GitletNode otherHead = branches.get(branch);
        if (otherHead == null) {
            System.out.println("A branch with that name does not exist.");
            return;
        } else if (otherHead == current) {
            System.out.println("Cannot rebase a branch onto itself.");
            return;
        }
        GitletNode pointer = current;
        GitletNode otherNode = otherHead;
        SplitNode s = new SplitNode(pointer, otherNode);
        int splitID = s.getSplitNodeID();
        LinkedList<Integer> pointerIDs = s.getPointerIDs();
        LinkedList<Integer> otherNodeIDs = s.getOtherNodeIDs();
        if (otherNodeIDs.contains(current.getID())) {
            current = otherHead;
            return;
        } else if (pointerIDs.contains(otherHead.getID())) {
            System.out.println("Already up-to-date.");
            return;
        }
        int thisCommitID = pointerIDs.removeLast();
        while (splitID != thisCommitID) {
            thisCommitID = pointerIDs.removeLast();
        }
        while (!pointerIDs.isEmpty()) {
            GitletNode movingNode = allCommits.get(pointerIDs.pollLast());
            GitletNode newNode = new GitletNode(movingNode.getFiles(), movingNode.getIDMap(), getTime(),
                movingNode.getCommitMessage(), iD, branch, otherHead);
            otherHead = newNode;
            iD++;
        }
        branches.put(otherHead.getBranchName(), otherHead);
    }

    /**
     * Does essentially what REBASE does except with a twist. 
     * For each node it replays, it allows the user to change 
     * the commit’s message or skip replaying the commit.
     * @param BRANCHNAME: The branch to connect our current branch 
     * onto. 
     */
    public void interactiveRebase(String branch) throws IOException {
        GitletNode otherHead = branches.get(branch);
        if (otherHead == null) {
            System.out.println("A branch with that name does not exist.");
            return;
        } else if (otherHead == current) {
            System.out.println("Cannot rebase a branch onto itself.");
            return;
        }
        GitletNode pointer = current;
        GitletNode otherNode = branches.get(branch);
        SplitNode s = new SplitNode(pointer, otherNode);
        int splitID = s.getSplitNodeID();
        LinkedList<Integer> pointerIDs = s.getPointerIDs();
        LinkedList<Integer> otherNodeIDs = s.getOtherNodeIDs();
        if (otherNodeIDs.contains(current.getID())) {
            current = otherHead;
            return;
        } else if (pointerIDs.contains(otherHead.getID())) {
            System.out.println("Already up-to-date.");
            return;
        }
        int thisCommitID = -1;
        while (splitID != thisCommitID) {
            System.out.println(pointerIDs);
            thisCommitID = pointerIDs.removeLast();
        }
        Iterator<Integer> iter = pointerIDs.descendingIterator();
        while (iter.hasNext()) {
            int id = iter.next();
            System.out.println("Currently replaying: ");
            GitletNode movingNode = allCommits.get(id);
            oneLog(movingNode);
            System.out.print("Would you like to (c)ontinue, (s)kip this "); 
            System.out.println("commit, or change this commit's (m)essage?");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String answer;
            answer = br.readLine();
            while ((answer.equals("s") && (id == pointerIDs.getFirst() 
                || id == pointerIDs.getLast())) || (!answer.equals("c") 
                && !answer.equals("s") && !answer.equals("m")))  {
                System.out.println("Please enter a valid command");
                System.out.print("Would you like to (c)ontinue, (s)kip this "); 
                System.out.println("commit, or change this commit's (m)essage?");
                br = new BufferedReader(new InputStreamReader(System.in));
                answer = br.readLine();
            }
            if (answer.equals("c")) {
                GitletNode newNode = new GitletNode(movingNode.getFiles(), movingNode.getIDMap(), getTime(),
                    movingNode.getCommitMessage(), iD, branch, otherHead);
                otherHead = newNode;
                iD++;
            } else if (answer.equals("m")) {
                System.out.println("Please enter a new message for this commit.");
                br = new BufferedReader(new InputStreamReader(System.in));
                String message = br.readLine();
                GitletNode newNode = new GitletNode(movingNode.getFiles(), movingNode.getIDMap(), getTime(),
                    message, iD, branch, otherHead);
                otherHead = newNode;
                iD++;                
            }
        }
        branches.put(otherHead.getBranchName(), otherHead);
    }
    
    /* Changes the file path to where files are saved, depending on what
     * branch and commit ID the "commit" is being run.
     */
    private void changePath() {
        path = ".gitlet/commits/" + branchName + "/" + iD + "/";
    }

    /* Gets the current director where Gitlet is run. 
     */
    private void getCurrentDirectory() {
        currentDir = System.getProperty("user.dir");
    }

    /** 
     * Gets the current time, formatted as "yyyy-MM-dd hh:mm:ss" for commits.
     * @return: The time.
     */
    private String getTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(date);
    }

    /** 
     * Throws a warning if any of the dangerous methods are being run.
     * @return: True if answer is "yes".
     */
    private boolean warning() throws IOException {
        System.out.print("Warning: The command you entered may alter the files in your "); 
        System.out.print("working directory. Uncommitted changes may be lost. Are you ");
        System.out.println("sure you want to continue? (yes/no)"); 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String answer = br.readLine();
        return answer.equals("yes");
    }

    /**
     * Checks whether the command being run is a dangerous method.
     * @param The method to run
     * @return True if the method is dangerous.
     */
    private boolean dangerousMethods(String input) {
        return input.equals("reset") || input.equals("merge") || input.equals("rebase")
                || input.equals("i-rebase") || input.equals("checkout");
    }

    /**
     * A nested class that has a constructor giving LinkedLists of IDs 
     * and the ID of the split point.
     * For use for merge, rebase, and interactiveRebase. 
     */
    private class SplitNode implements Serializable {

        /* Split point ID */
        private int sid;

        /* LinkedList of IDs of current branch */
        private LinkedList<Integer> pid;

        /* LinkedList of IDs of given branch */
        private LinkedList<Integer> oid;

        /**
         * A constructor that gives the LinkedLists of IDs as we traverse
         * down the current branch and given branch and the ID of the split point.
         * @param POINTER: Head node of current branch
         * @param otherNode: Head node of given branch
         */
        public SplitNode(GitletNode pointer, GitletNode otherNode) {
            int splitNodeID = -1;
            LinkedList<Integer> pointerIDs = new LinkedList<Integer>();
            LinkedList<Integer> otherNodeIDs = new LinkedList<Integer>();
            while (pointer != null && otherNode != null) {
                if (otherNode.getID() == pointer.getID()) {
                    splitNodeID = pointer.getID();
                    pointerIDs.add(pointer.getID());
                    otherNodeIDs.add(otherNode.getID());
                    break;                
                }
                if (otherNodeIDs.contains(pointer.getID())) {
                    splitNodeID = pointer.getID();
                    break;
                } else if (pointerIDs.contains(otherNode.getID())) {
                    splitNodeID = otherNode.getID();
                    break;
                } else {
                    pointerIDs.add(pointer.getID());
                    otherNodeIDs.add(otherNode.getID());
                    pointer = pointer.next();
                    otherNode = otherNode.next();
                }
            }
            if (pointer == null) {
                while (otherNode != null) {
                    if (pointerIDs.contains(otherNode.getID())) {
                        splitNodeID = otherNode.getID();
                        otherNodeIDs.add(otherNode.getID());
                        break;
                    }
                    otherNode = otherNode.next();
                    otherNodeIDs.add(otherNode.getID());
                }
            } else if (otherNode == null) {
                while (pointer != null) {
                    if (otherNodeIDs.contains(pointer.getID())) {
                        splitNodeID = pointer.getID();
                        pointerIDs.add(pointer.getID());
                        break;
                    }
                    pointer = pointer.next();
                    pointerIDs.add(pointer.getID());
                }
            }
            this.sid = splitNodeID;
            this.pid = pointerIDs;
            this.oid = otherNodeIDs;
        }

        /* Retrieves the split node ID. */
        public int getSplitNodeID() {
            return sid;
        }

        /* Retrieves the LinkedList of IDs of the current branch. */
        public LinkedList<Integer> getPointerIDs() {
            return pid;
        }

        /* Retrieves the LinkedList of IDs of the given branch. */
        public LinkedList<Integer> getOtherNodeIDs() {
            return oid;
        }
    }
}

