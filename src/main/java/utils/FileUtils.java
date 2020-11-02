package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Optional;

/**
 * @author Jorge Báez
 */
public class FileUtils {

    private static final String HOME_USER_DIR = System.getProperty("user.home");

    public static boolean saveObjectToDisk(String fileName, Serializable object) {
        try(FileOutputStream fileOut = new FileOutputStream(getFilePath(fileName));
            ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(object);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static Optional<Object> readObjectFromDisk(String fileName) {
        Optional<Object> maybeFile = Optional.empty();
        java.io.File f = new java.io.File(getFilePath(fileName));

        if (f.isFile() && f.canRead()) {
            try(FileInputStream fileIn = new FileInputStream(f);
                ObjectInputStream in = new ObjectInputStream(fileIn)) {
                maybeFile = Optional.of(in.readObject());
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }

        return maybeFile;
    }

    private static String getFilePath(String fileName) {
        return HOME_USER_DIR + java.io.File.separator + fileName;
    }
}
