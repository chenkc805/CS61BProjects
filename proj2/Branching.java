

public class Branching {

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