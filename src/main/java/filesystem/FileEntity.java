package filesystem;

/**
 * @author Jorge Báez
 */
public interface FileEntity {
    String getName();

    Directory getParent();

    boolean isDirectory();

    FileEntity getSubDir(String dirName);
}
