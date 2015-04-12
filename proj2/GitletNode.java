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

    public GitletNode(GitletNode g) {
        _files = g._files;
        _commitMessage = g._commitMessage;
        _iD = g._iD;
        _next = g._next;
        _timeStamp = g._timeStamp;
        _idMap = g._idMap;
        _branch = g._branch;
    }

    /* Retrieves the commit message */
    public String getCommitMessage() {
        return _commitMessage;
    }

    public boolean hasNext() {
        return _next != null;
    }

    public int getID() {
        return _iD;
    }

    public String getBranchName() {
        return _branch;
    }

    public String getFullName() {
        return _branch + "/" + _iD;
    }

    public GitletNode next() {
        return _next;
    }

    HashMap<String, File> _files;
    String _commitMessage;
    String _timeStamp;
    String _branch;
    GitletNode _next;
    int _iD;
    HashMap<String, Integer> _idMap;
}
