import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.System;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.FileSystem;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Gitlet implements Serializable {

    /* Gitlet class */
    static Gitlet g;
    
    /* Contains all the branches with branch name as KEY 
     * and LinkedList as VAL. */
    HashMap<String, GitletNode> branches;

    /* The main commit tree. */
    GitletNode master;

    /* Contains a list of the files that are staged. */
    HashMap<String, File> staged;

    /* Gives the date and time of a commit. */
    String dateAndTime;

    /* The most recent ID */
    int iD = 0;

    /* The branch you are on */
    char branch;

    String currentDir = System.getProperty("user.dir");

    public static void main(String[] args) {
        if (args.length == 0) {
            return;
        } else if (!ReadWriteFiles.fileExists(".gitlet/savedState.ser")) {
            if (args[0].equals("init")) {
                g = new Gitlet();
                g.init();
                ReadWriteFiles.writeSerFile(g, ".gitlet/savedState.ser");
                return;
            } else {
                System.out.println("Gitlet has not been initialized yet.");
                return;
            }
        }

        Gitlet g = ReadWriteFiles.readSerFile(".gitlet/savedState.ser");

        switch (args[0]) {
        case "add":
            g.add(args[1]);
            ReadWriteFiles.writeSerFile(g, ".gitlet/savedState.ser");
            break;
        case "commit":
            g.commit(args[1]);
            ReadWriteFiles.writeSerFile(g, ".gitlet/savedState.ser");
            break;
        case "log":
            g.log();
            ReadWriteFiles.writeSerFile(g, ".gitlet/savedState.ser");
            break;
        case "checkout":
            if (args.length == 3) {
                g.checkoutID(Integer.parseInt(args[1]), args[2]);
            } else if (g.branches.containsKey(args[1])) {
                g.checkoutBranch(args[1]);
            } else {
                g.checkoutFile(args[1]);
            }
            break;
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
        staged = new HashMap<String, File>();
        branches = new HashMap<String, GitletNode>();
        branches.put("master", master);
        File directory = new File(".gitlet/");
        if (!directory.exists()) {
            master = new GitletNode(null, getTime(), "initial commit", iD, null);
            directory.mkdir();
            (new File(".gitlet/commits/")).mkdir();
            (new File(".gitlet/commits/0/")).mkdir();
            iD++;
        } else {
            System.out.println("A gitlet version control system already exists in the current directory.");
        }
    }

    private String getTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(date);
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

            File put = new File(fileName);

            /* Checks if the list is empty */
            if (master.getID() == 0) {
                staged.put(fileName, put);
                return;
            } 

            /* Checks if the headNode contains FILENAME */
            if (master._files.containsKey(fileName)) {
                byte[] fileNameData = Files.readAllBytes(Paths.get(fileName));
                byte[] headNodeData = Files.readAllBytes(master._files.get(fileName).toPath());
                if (Arrays.equals(fileNameData, headNodeData)) {
                    System.out.println("File has not been modified since the last commit.");
                    return;
                }
            }
            
            /* Stages the file */
            staged.put(fileName, put);
        
        } catch (IOException e) {
            return;
        }
    }

    private int generateByteHashCode(String fileName) throws IOException {
        byte[] fileNameData = Files.readAllBytes(Paths.get(fileName));
        return fileNameData.hashCode();
    }

    /**
     * Saves a snapshot of certain files that can be 
     * viewed or restored at a later time. 
     * @param MESSAGE: The message that is associated with the commit
     */
    public void commit(String message) {
        (new File(".gitlet/commits/" + iD + "/")).mkdir();
        HashMap<String, File> toBeCommitted = new HashMap<String, File>();
        for (String fileName : staged.keySet()) {
            ReadWriteFiles.writeCommitFile(fileName, iD);
            toBeCommitted.put(fileName, staged.get(fileName));
        }
        master = new GitletNode(toBeCommitted, getTime(), message, iD, master);
        staged.clear();
        iD++;
    }

    /** 
     * Mark the file for removal; this means it will not be 
     * inherited as an old file in the next commit. If the 
     * file had been staged, instead unstage it.
     * @param FILENAME: The file to be removed
     */
    public void remove(String fileName) {

    }

    /** 
     * Starting at the current HEAD pointer, display information 
     * about each commit backwards along the commit tree until 
     * the initial commit.
     */ 
    public void log() {
        GitletNode pointer = master;
        while (pointer != null) {
            System.out.println("====");
            System.out.println("Commit " + pointer._iD + ".");
            System.out.println(pointer._timeStamp);
            System.out.println(pointer._commitMessage);
            pointer = pointer.next();
        }
    }

    /**
     * Like log, except displays information about all commits 
     * ever made. The order of the commits does not matter.
     */ 
    public void globalLog() {

    }

    /**
     * Prints out the id of the commit that has the given 
     * commit MESSAGE. If there are multiple such commits, it 
     * prints the ids out on separate lines.
     * @param MESSAGE: The message of the commit that is to be found 
     */
    public void find(String message) {

    }

    /**
     * Displays what branches currently exist, and marks the 
     * current branch with a *. Also displays what files have 
     * been staged or marked for removal.
     */ 
    public void status() {

    }

    /**
     * Restores the given file in the working directory to 
     * its state at the commit at the head of the current branch.
     * @param FILENAME: The file to be restored
     */ 
    public void checkoutFile(String fileName) {
        try {
            System.out.println(fileName);
            File file = new File(fileName);
            Files.copy(Paths.get(".gitlet/commits/" + master.getID() + "/" + file.getName()), Paths.get(fileName), REPLACE_EXISTING); 
        } catch (NullPointerException e) {
            System.out.println("File does not exist in the most recent commit, or no such branch exists.");
            return;
        } catch (IOException e) {
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
        GitletNode pointer = master;
        while (pointer != null) {
            if (pointer._iD == commitID) {
                try {
                    Files.copy(pointer._files.get(fileName).toPath(), Paths.get(fileName), REPLACE_EXISTING); 
                    return;
                } catch (NullPointerException e) {
                    System.out.println("File does not exist in that commit.");
                } catch (IOException e) {
                    return;
                }
            } else {
                pointer = pointer.next();
            }
        }
        System.out.println("No commit with that id exists.");
    }

    /**
     * Restores all files in the working directory to their 
     * versions in the commit at the head of the given branch. 
     * Considers the given branch to now be the current branch.
     * @param BRANCHNAME: The branch to be the current branch.
     */
    public void checkoutBranch(String branchName) {

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
    public void branch(String branchName) {

    }

    /**
     * Deletes the branch with the given name. 
     * @param BRANCHNAME: The name of the branch to be removed.
     */
    public void removeBranch(String branchName) {

    }

    /**
     * Restores all files to their versions in the commit 
     * with the given id. Also moves the current branch's 
     * head to that commit node.
     * @param COMMITID: The ID of the commit node that is restored
     */ 
    public void reset(String commitID) {
    
    }

    /**
     * Merges files from the head of the given branch into 
     * the head of the current branch. 
     * @param BRANCHNAME: The branch to be merged into the 
     * current branch
     */ 
    public void merge(String branchName) {

    }

    /**
     * Finds the split point of the current branch and the 
     * given branch, then snaps off the current branch at 
     * this point, then reattaches the current branch to the 
     * head of the given branch.
     * @param BRANCHNAME: The branch to connect our current branch 
     * onto. 
     */
    public void rebase(String branchName) {

    }

    /**
     * Does essentially what REBASE does except with a twist. 
     * For each node it replays, it allows the user to change 
     * the commit’s message or skip replaying the commit.
     * @param BRANCHNAME: The branch to connect our current branch 
     * onto. 
     */
    public void interactiveRebase(String branchName) {

    }
}

