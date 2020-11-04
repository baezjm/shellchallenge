package commands.impl;

import commands.Command;
import filesystem.Directory;
import filesystem.FileEntity;
import filesystem.FileSystem;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author Jorge Báez
 */
public class LsCommand implements Command {
    private static final String R_PARAM = "-r";

    private String param;

    public LsCommand() {
    }

    public LsCommand(String param) {
        this.param = param;
    }

    @Override
    public String execute(FileSystem fs) {

        if (isNull(param)) return getChildNames(fs.getCurrent());

        if (R_PARAM.equals(this.param)) {
            return fs.getCurrent().printAll();
        }

        String[] dirNames = this.param.split("/");
        Directory directory = fs.getCurrent().getSubDir(dirNames);

        if (nonNull(directory)) {
            return getChildNames(directory);
        } else {
            return "Directory not found";
        }
    }

    /**
     *
     * @param directory the current directory
     * @return a String whit all child's names, with a '\n' separator
     */
    private String getChildNames(Directory directory){
        StringBuilder sb = new StringBuilder();
        directory.getChildes().forEach(c -> sb.append(c.getName()).append("\n"));
        return sb.toString();
    }
}
