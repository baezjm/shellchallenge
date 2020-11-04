package commands.impl;

import commands.Command;
import filesystem.Directory;
import filesystem.FileSystem;

import static java.util.Objects.isNull;

/**
 * @author Jorge Báez
 */
public class MkdirCommand implements Command {
    private String argument;

    public MkdirCommand(String argument) {
        if (isNull(argument)) { throw new RuntimeException("Invalid Command"); }
        if (argument.length() > 100) { throw new RuntimeException("Invalid File or Folder Name"); }
        this.argument = argument;
    }

    /**
     * Execute a mkdir command to create a new directory in the current directory.
     * @param fs {@link FileSystem} when the command will create a directory.
     * @return
     */
    @Override
    public String execute(FileSystem fs) {
        Boolean success = fs.getCurrent().create(new Directory(argument, fs.getCurrent()));
        return (success) ? "" : "Directory already exists \n";
    }
}
