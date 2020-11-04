package commands.impl;

import commands.Command;
import filesystem.FileSystem;

/**
 * @author Jorge Báez
 */
public class RmCommand implements Command {
    private String param;

    public RmCommand(String param) {
        this.param = param;
    }

    @Override
    public String execute(FileSystem fs) {
        Boolean success = fs.getCurrent().remove(this.param);
        return success ? "" : "Directory not found";
    }
}
