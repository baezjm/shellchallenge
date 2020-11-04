package commands.impl;

import commands.Command;
import filesystem.File;
import filesystem.FileEntity;
import filesystem.FileSystem;

/**
 * @author Jorge Báez
 */
public class CatCommand implements Command {

    private String name;

    public CatCommand(String name) {
        this.name = name;
    }

    @Override
    public String execute(FileSystem fs) {
        File fileEntity = fs.getCurrent().getFile(this.name);
        return fileEntity.getContent();
    }
}
