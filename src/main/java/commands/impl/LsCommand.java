package commands.impl;

import commands.Command;
import filesystem.Directory;
import filesystem.FileSystem;

import static java.util.Objects.nonNull;

/**
 * @author Jorge Báez
 */
public class LsCommand implements Command {
    private String param;

    public LsCommand(String param) {
        this.param = param;
    }

    @Override
    public String execute(FileSystem fs) {
        if (nonNull(this.param)) {
            if ("-r".equals(this.param)) {
                return fs.getCurrent().printChildes(true);
            }

            String[] dirNames = this.param.split("/");
            Directory dir = (Directory) fs.getCurrent().getSubDir(dirNames);

            if (nonNull(dir)) {
                return dir.printChildes(false);
            } else {
                return "Directory not found";
            }

        } else {
            return fs.getCurrent().printChildes(false);
        }
    }
}
