package filesystem;

import java.io.Serializable;

/**
 * @author Jorge Báez
 */
public class FileSystem implements Serializable {

    private Directory current;

    public FileSystem(Directory current) {
        this.current = current;
    }

    public Directory getCurrent() {
        return current;
    }

    public void setCurrent(Directory dir) {
        this.current = dir;
    }
}
