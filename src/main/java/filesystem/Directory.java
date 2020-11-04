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

    public String getPath() {
        if(null == parent) return "/" + name;

        return parent.getPath() + "/" + name;
    }

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

    private Directory getChildDirectory(String dirName) {
        FileEntity directory = this.childes.stream()
                .filter(child -> child.getName().equals(dirName) && child.isDirectory())
                .findFirst().orElse(null);
        return (Directory) directory;
    }

    public File getFile(String dirName) {
        FileEntity file = this.childes.stream()
                .filter(child -> child.getName().equals(dirName) && !child.isDirectory())
                .findFirst().orElse(null);
        return (File) file;
    }

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
