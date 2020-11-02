package commands;

import filesystem.FileSystem;

/**
 * @author Jorge Báez
 */
public interface Command {
    String execute(FileSystem fs);
}
