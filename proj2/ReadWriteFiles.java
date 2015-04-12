import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;

import java.io.IOException;

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

    public static void writeCommitFile(String fileName, String branchName, int iD) {
        try {
            File file = new File(fileName);
            (new File(".gitlet/commits/" + branchName + "/" + iD 
                + "/" + file.getParent())).mkdirs();
            Files.copy(Paths.get(fileName), Paths.get(".gitlet/commits/" 
                + branchName + "/" + iD + "/" + fileName));
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
    public static Gitlet readSerFile(String pathName) {
        if (!pathName.endsWith(".ser")) {
            throw new IllegalArgumentException("File name must end with .ser.");
        }

        try {
            FileInputStream fin = new FileInputStream(pathName);
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

    public static File readGenericFile(String pathName) {
        try {
            File result = Paths.get(pathName).toFile();
            return result; 
        } catch (NullPointerException e) {
            return null;
        }
    }
}
