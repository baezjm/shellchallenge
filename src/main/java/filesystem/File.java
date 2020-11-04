package filesystem;

import java.io.Serializable;

/**
 * @author Jorge Báez
 */
public class File implements FileEntity, Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String content;
    private Directory parent;

    public File(final String name, final Directory parent, final String content) {
        this.name = name;
        this.parent = parent;
        this.content = content;
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
    public String getName() {
        return this.name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
