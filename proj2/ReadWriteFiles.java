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

public class ReadWriteFiles {

    /** 
     * Returns true if quickSave file exists. 
     * @return existence of quickSave file.
     * Credit to HugLife.java from Lab 9 
     */
    public static boolean fileExists(String filename) {
        boolean fileExists = new File(filename).exists();        
        return fileExists;
    }

    /**
     * Write a .ser file containing a world state.
     * @param  fileName to write to
     * Credit to HugLife.java from Lab 9
     */
    public static void writeSerFile(Gitlet g, String fileName) {
        if (!fileName.endsWith(".ser")) {
            throw new IllegalArgumentException("File name must end with .ser.");
        }
        try {
            FileOutputStream fout = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(g);
            oos.close();
        } catch (IOException e) {
            System.out.println("writeSerFile");
            System.out.println("Could not write world state: " + e);
        }
    }

    public static void writeCommitFile(String fileName, int iD) {
        try {
            File file = new File(fileName);
            Files.copy(Paths.get(fileName), Paths.get(".gitlet/commits/" + iD + "/" + file.getName()));
        } catch (IOException e) {
            System.out.println("writeCommitFile");
            System.out.println("Could not write world state: " + e);
        }
    }

    /**
     * Reads a .ser file containing a world state.
     * @param  wordFilename to read from
     * @return a newly initialized HugLife
     * Credit to HugLife.java from Lab 9
     */
    public static Gitlet readSerFile(String fileName) {
        if (!fileName.endsWith(".ser")) {
            throw new IllegalArgumentException("File name must end with .ser.");
        }

        try {
            FileInputStream fin = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fin);
            Object historyObject = ois.readObject();
            ois.close();
            Gitlet g = (Gitlet) historyObject; 
            return g; 
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File readGenericFile(String fileName) {
        try {
            System.out.println("where");

            FileInputStream fin = new FileInputStream(fileName);
            System.out.println("where");
            ObjectInputStream ois = new ObjectInputStream(fin);
            System.out.println("where");
            Object historyObject = ois.readObject();
            System.out.println("historyObject: " + historyObject);
            ois.close();
            File result = (File) historyObject; 
            return result; 
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Whoa Error");
            e.printStackTrace();
            return null;
        }
    }
}