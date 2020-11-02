package commands.impl;

import commands.Command;
import filesystem.FileSystem;

/**
 * @author Jorge Báez
 */
public class PwdCommand implements Command {

    @Override
    public String execute(FileSystem fs) {
        return fs.getCurrent().getFullPath().concat("\n");
    }
}
