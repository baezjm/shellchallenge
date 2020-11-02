package app;

import commands.CommandFactory;
import exception.EmptyCommandException;
import filesystem.Directory;
import filesystem.FileSystem;
import utils.FileUtils;

import java.util.Optional;
import java.util.Scanner;

/**
 * @author Jorge Báez
 */
public class DirectoriesApplication {

    private static final String INITIAL_STATE_FILENAME = "state.txt";

    private FileSystem fs;

    public DirectoriesApplication() {
        this.restoreOrCreateFileSystem();
    }

    private void restoreOrCreateFileSystem() {
        Optional<Object> maybeFile = FileUtils.readObjectFromDisk(INITIAL_STATE_FILENAME);

        if (maybeFile.isPresent()) {
            this.fs = (FileSystem) maybeFile.get();
        } else {
            Directory root = new Directory("root", null);
            this.fs = new FileSystem(root);
        }
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        String commandAsText = sc.nextLine();

        while (!commandAsText.equalsIgnoreCase("quit")) {
            try {

                final String[] splittedCommand = commandAsText.split(" ");
                final String commandName = splittedCommand[0];
                final String argument = (splittedCommand.length > 1) ? splittedCommand[1] : null;

                final String output = CommandFactory.create(commandName, argument)
                        .execute(this.fs);

                System.out.print(output);

            } catch (EmptyCommandException e) {
                System.out.print("");
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

            commandAsText = sc.nextLine();
        }

        try {
            FileUtils.saveObjectToDisk(INITIAL_STATE_FILENAME, this.fs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
