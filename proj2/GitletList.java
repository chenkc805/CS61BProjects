package gitlet;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.ListIterator;

public class GitletList implements Serializable {

    /* The main list. */
    LinkedList<Node> list;
    ListIterator<Node> listIterator;

    /* The Node containing all the files and commitMessage */
    CommitNode node;

    /** 
     * Constructor for a new list.
     */
    public GitletList() {
        list = new LinkedList<Node>();
        node = new CommitNode(null, "initial commit")
        list.add(node);
    }

    public void initIterator() {
        listIterator = new list.listIterator();
    }

    private class Node implements Serializable {
        /** 
         * Constructor for a node of a LinkedList with an
         * ArrayList of the files in that Node, a pointer to the next Node,
         * and a pointer to a branch.
         * @param FILES: ArrayList containing pointers to files in this commit
         * @param NEXT: Pointer to the next part of the list
         * @param BRANCH: Pointer to the branch from this node. */        
        public CommitNode(ArrayList<Path> files, String commitMessage, int iD) {
            _files = files;
            _commitMessage = commitMessage;
            _iD = iD;
        }

        /* Retrieves the commit message */
        public String commitMessage() {
            return _commitMessage;
        }

        private ArrayList<Path> _files;
        private String _commitMessage;
        private int _iD;
        
    }
}




