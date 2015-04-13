import java.io.Serializable;
import java.util.HashMap;
import java.io.File;

public class GitletNode implements Serializable {
    /** 
     * Constructor for a node of a LinkedList with an
     * ArrayList of the files in that Node, a pointer to the next Node,
     * and a pointer to a branch.
     * @param FILES: ArrayList containing pointers to files in this commit
     * @param NEXT: Pointer to the next part of the list
     * @param BRANCH: Pointer to the branch from this node. */        
    public GitletNode(HashMap<String, File> files, HashMap<String, Integer> iDs,
        String timeStamp, String commitMessage, 
        int iD, String branch, GitletNode next) {
        _files = files;
        _commitMessage = commitMessage;
        _iD = iD;
        _next = next;
        _timeStamp = timeStamp;
        _idMap = iDs;
        _branch = branch;
    }

    /* Retrieves the files in this node. */
    public HashMap<String, File> getFiles() {
        return _files;
    }

    /* Retrieves the IDs of files in this node. */
    public HashMap<String, Integer> getIDMap() {
        return _idMap;
    }

    /* Retrieves the commit message. */
    public String getCommitMessage() {
        return _commitMessage;
    }

    /* Retrieves the ID of this node. */
    public int getID() {
        return _iD;
    }

    /* Retrieves branch that this node is in. */
    public String getBranchName() {
        return _branch;
    }

    /* Retrieves the next node attached to this node. */
    public GitletNode next() {
        return _next;
    }

    /* Retrieves the timestamp. */
    public String getTimeStamp() {
        return _timeStamp;
    }

    /* Checks if this node has a NEXT node. */
    public boolean hasNext() {
        return _next != null;
    }

    /* Retrieves the full filepath for this commit. */
    public String getFullName() {
        return _branch + "/" + _iD;
    }

    /* Retrieves the next node attached to this node. */
    public void setCommitMessage(String message) {
        _commitMessage = message;
    }

    /* Retrieves the next node attached to this node. */
    public void setID(int id) {
        _iD = id;
    }

    /* Changes the next node attached to this node. */
    public void setNext(GitletNode next) {
        _next = next;
    }

    private HashMap<String, File> _files;
    private HashMap<String, Integer> _idMap;
    private String _timeStamp;
    private String _commitMessage;
    private int _iD;
    private String _branch;
    private GitletNode _next;
}
