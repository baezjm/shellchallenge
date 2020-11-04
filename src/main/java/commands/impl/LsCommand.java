package commands.impl;

import commands.Command;
import filesystem.Directory;
import filesystem.FileEntity;
import filesystem.FileSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

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

        if (R_PARAM.equals(this.param)) {
            StringBuilder sb = new StringBuilder();

            lsR(fs.getCurrent(), sb);

            return sb.toString();
        }

        final Directory directory = getDirectory(fs);

        if (isNull(directory)) return "Directory not found";
        return ls(directory);
    }

    /**
     * Retrieve a directory given a name, the name can be test or /test/test1
     * @param fs
     * @return
     */
    private Directory getDirectory(FileSystem fs) {
        Directory directory;

        if (isNull(param))
            directory = fs.getCurrent();
        else{
            List<String> dirNames = Stream.of(this.param.split("/")).filter(path -> !path.isEmpty()).collect(Collectors.toList());
            directory = fs.getCurrent().getChildDirectory(dirNames);
        }
        return directory;
    }

    /**
     * Execute the command ls -r. This command resolve the current path directory and each name of her childs.
     * @param current
     * @param sb
     */
    private void lsR(Directory current, StringBuilder sb) {
        List<Directory> childDirsToPrint = new ArrayList<>();

        if (!current.getChildes().isEmpty()) {
            sb.append(current.getPath()).append("\n");
        }

        for (FileEntity child : current.getChildes()) {
            sb.append(child.getName()).append("\n");

            if (child.isDirectory()) {
                childDirsToPrint.add((Directory) child);
            }
        }

        for (Directory dir : childDirsToPrint) {
            lsR(dir,sb);
        }
    }

    /**
     *
     * @param directory the current directory
     * @return a String whit all child's names, with a '\n' separator
     */
    private String ls(Directory directory){
        StringBuilder sb = new StringBuilder();
        directory.getChildes().forEach(c -> sb.append(c.getName()).append("\n"));
        return sb.toString();
    }
}
