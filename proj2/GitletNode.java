import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

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

import java.io.IOException;
import java.io.FileNotFoundException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GitletNode implements Serializable {
    /** 
     * Constructor for a node of a LinkedList with an
     * ArrayList of the files in that Node, a pointer to the next Node,
     * and a pointer to a branch.
     * @param FILES: ArrayList containing pointers to files in this commit
     * @param NEXT: Pointer to the next part of the list
     * @param BRANCH: Pointer to the branch from this node. */        
    public GitletNode(HashMap<String, File> files, 
        String timeStamp, String commitMessage, 
        int iD, GitletNode next) {
        _files = files;
        _commitMessage = commitMessage;
        _iD = iD;
        _next = next;
        _timeStamp = timeStamp;
    }

    /* Retrieves the commit message */
    public String commitMessage() {
        return _commitMessage;
    }

    public boolean hasNext() {
        return _next != null;
    }

    public int getID() {
        return _iD;
    }

    public GitletNode next() {
    	return _next;
    }

    HashMap<String, File> _files;
    String _commitMessage;
    String _timeStamp;
    GitletNode _next;
    int _iD;
        
}