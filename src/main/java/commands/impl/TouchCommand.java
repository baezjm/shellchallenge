package commands.impl;

import commands.Command;
import filesystem.FileSystem;
import filesystem.File;

import static java.util.Objects.isNull;

/**
 * @author Jorge Báez
 */
public class TouchCommand implements Command {

    private String argument;

    public TouchCommand(String argument) {
        if (isNull(argument)) { throw new RuntimeException("Invalid Command"); }
        if (argument.length() > 100) { throw new RuntimeException("Invalid File or Folder Name"); }
        this.argument = argument;
    }

    public String execute(FileSystem fs) {
        Boolean success = fs.getCurrent().create(new File(argument,fs.getCurrent()));
        return (success) ? "" : "File already exists \n";
    }
}
