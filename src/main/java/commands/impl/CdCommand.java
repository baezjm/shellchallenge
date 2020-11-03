package commands.impl;

import commands.Command;
import filesystem.Directory;
import filesystem.FileSystem;

import static java.util.Objects.nonNull;

/**
 * @author Jorge Báez
 */
public class CdCommand implements Command {
    private static final String PARENT = "..";
    private static final String ROOT = "root";

    private String[] dirNames;

    public CdCommand(String argument) {
        this.dirNames = nonNull(argument) ? argument.split("/") : new String[]{""};
    }


    @Override
    public String execute(FileSystem fs) {
        if (PARENT.equals(this.dirNames[0])) {
            if (!ROOT.equals(fs.getCurrent().getName()) && fs.getCurrent().getParent() != null) {
                fs.setCurrent(fs.getCurrent().getParent());
            }
        } else {
            Directory dir = fs.getCurrent().getSubDir(dirNames);
            if (nonNull(dir)) {
                fs.setCurrent(dir);
            } else {
                return "Directory not found";
            }
        }
        return "";
    }
}
