package filesystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

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

    public String printChildes(Boolean recursive) {
        StringBuilder sb = new StringBuilder();
        return this.printRecursive(recursive, sb);
    }

    private String printRecursive(Boolean recursive, StringBuilder sb) {
        List<Directory> childDirsToPrint = new ArrayList<>();

        if (recursive && !childes.isEmpty()) {
            sb.append(this.getFullPath()).append("\n");
        }

        // Print this directory
        for (FileEntity child : childes) {
            sb.append(child.getName()).append("\n");

            if (recursive && child.isDirectory()) {
                childDirsToPrint.add((Directory) child);
            }
        }

        for (Directory dir : childDirsToPrint) {
            dir.printRecursive(true, sb);
        }

        return sb.toString();
    }


    public Boolean create(boolean isDirectory, String name) {
        if (this.containsChild(name)) {
            return false;
        }

        FileEntity newFileEntity;

        if (isDirectory){
            newFileEntity = new Directory(name, this);
        }else{
            newFileEntity = new File(name, this);
        }

        return childes.add(newFileEntity);
    }

    private Boolean containsChild(String childName) {
        return childes.stream().anyMatch(child -> child.getName().equals(childName));
    }

    public String getFullPath() {
        if (isNull(this.getParent())) {
            return "/" + this.name;
        }
        List<String> dirs = new ArrayList<>();
        Directory current = this;

        while (nonNull(current)) {
            dirs.add(current.getName());
            current = current.getParent();
        }

        Collections.reverse(dirs);
        return "/" + String.join("/", dirs);
    }

    public FileEntity getSubDir(String[] dirNames) {
        FileEntity current = this;

        for (String dirName : dirNames) {

            FileEntity child = current.getSubDir(dirName);

            if (isNull(child) || !child.isDirectory()) {
                throw new RuntimeException("Directory not found");
            } else {
                current = child;
            }
        }
        return current;
    }

    @Override
    public FileEntity getSubDir(String dirName) {
        return this.childes.stream().filter(child -> child.getName().equals(dirName)).findFirst().orElse(null);
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