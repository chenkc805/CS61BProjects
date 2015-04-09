public class ReadWriteFiles {

    /**
     * Write a .ser file containing a world state.
     * @param  fileName to write to
     * Credit to HugLife.java from Lab 9
     */
    public static void writeSerFile(String fileName) {
        if (!fileName.endsWith(".ser")) {
            throw new IllegalArgumentException("File name must end with .ser.");
        }
        try {
            FileOutputStream fout = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
            oos.close();
        } catch (IOException e) {
            System.out.println("writeSerFile");
            System.out.println("Could not write world state: " + e);
        }
    }

    public static void writeCommitFile(String fileName) {
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
            return null;
        }
    }

    public static File readGenericFile(String fileName) {
        try {
            FileInputStream fin = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fin);
            Object historyObject = ois.readObject();
            ois.close();
            File result = (File) historyObject; 
            return result; 
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}