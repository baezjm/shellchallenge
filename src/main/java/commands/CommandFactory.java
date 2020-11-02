package commands;

import commands.impl.CdCommand;
import commands.impl.LsCommand;
import commands.impl.MkdirCommand;
import commands.impl.PwdCommand;
import commands.impl.TouchCommand;
import exception.EmptyCommandException;

import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

/**
 * @author Jorge Báez
 */
public enum CommandFactory {
    PWD((param) -> new PwdCommand()),
    LS(LsCommand::new),
    CD(CdCommand::new),
    MKDIR(MkdirCommand::new),
    TOUCH(TouchCommand::new);

    private Function<String, Command> creator;

    CommandFactory(Function<String, Command> creator) {
        this.creator = creator;
    }

    public static Command create(String commandName, String params){

        if ("".equals(commandName) || isNull(commandName)) throw new EmptyCommandException("Command name is required");

        CommandFactory commandFactory = Stream.of(values())
                .filter(c -> commandName.equals(c.name().toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unrecognized command"));

        return commandFactory.creator.apply(params);
    }
}
