package filesystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * @author Jorge Báez
 */
public class Directory implements FileEntity, Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private Directory parent;
    private List<FileEntity> childes = new ArrayList<>();

    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    public Boolean create(FileEntity f) {
        if (this.containsChild(name)) {
            return false;
        }
        return childes.add(f);
    }

    private Boolean containsChild(String childName) {
        return childes.stream().anyMatch(child -> child.getName().equals(childName));
    }

    /**
     * Retrieve a full path to this Directory
     * @return a {@code String} representing a full path to this directory
     */
    public String getPath() {
        if(null == parent) return "/" + name;

        return parent.getPath() + "/" + name;
    }

    /**
     * Retrieve a directory if exists. It's possible search a directory by name or path.
     * @param dirNames path for the target directory
     * @return a {@link Directory}
     */
    public Directory getChildDirectory(List<String> dirNames) {
        Directory current = this;

        for (String dirName : dirNames) {

            Directory child = current.getChildDirectory(dirName);

            if (isNull(child)) {
                throw new RuntimeException("Directory not found");
            } else {
                current = child;
            }
        }
        return current;
    }

    /**
     * Retrieve a directory if exists. Search by name.
     * @param dirName name of the directory
     * @return a {@link Directory}
     */
    private Directory getChildDirectory(String dirName) {
        FileEntity directory = this.childes.stream()
                .filter(child -> child.getName().equals(dirName) && child.isDirectory())
                .findFirst().orElse(null);
        return (Directory) directory;
    }

    /**
     * Retrieve a File if exists. Search by name.
     * @param dirName name of the file
     * @return a {@link File}
     */
    public File getFile(String dirName) {
        FileEntity file = this.childes.stream()
                .filter(child -> child.getName().equals(dirName) && !child.isDirectory())
                .findFirst().orElse(null);
        return (File) file;
    }

    /**
     * Remove a directory or file, by name
     * @param name of the target directory or file
     * @return a {@code Boolean} indicating if the operation was successful
     */
    public Boolean remove (String name){
        return this.childes.removeIf(child -> child.getName().equals(name));
    }

    public List<FileEntity> getChildes() {
        return childes;
    }

    @Override
    public Directory getParent() {
        return this.parent;
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
