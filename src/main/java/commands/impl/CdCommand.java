package commands.impl;

import commands.Command;
import filesystem.Directory;
import filesystem.FileSystem;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * @author Jorge Báez
 */
public class CdCommand implements Command {
    private static final String PARENT = "..";
    private static final String ROOT = "root";

    private List<String> dirNames;

    public CdCommand(String argument) {
        this.dirNames = nonNull(argument) ? Arrays.asList(argument.split("/")) : Collections.singletonList("");
    }

    /**
     * Execute a cd command,
     * @param fs a {@link FileSystem} to apply the command
     * @return a {@code String} when is ok with blank, or an error.
     */
    @Override
    public String execute(FileSystem fs) {
        if (PARENT.equals(this.dirNames.get(0))) {
            if (!ROOT.equals(fs.getCurrent().getName()) && fs.getCurrent().getParent() != null) {
                fs.setCurrent(fs.getCurrent().getParent());
            }
        } else {
            Directory dir = fs.getCurrent().getChildDirectory(dirNames);
            if (nonNull(dir)) {
                fs.setCurrent(dir);
            } else {
                return "Directory not found";
            }
        }
        return "";
    }
}
