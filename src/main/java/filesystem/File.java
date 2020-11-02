package filesystem;

import java.io.Serializable;

/**
 * @author Jorge Báez
 */
class File implements FileEntity, Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Directory parent;

    public File(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    @Override
    public Directory getParent() {
        return this.parent;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public FileEntity getSubDir(String dirName) {
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
