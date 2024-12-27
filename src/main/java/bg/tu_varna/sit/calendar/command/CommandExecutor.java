package bg.tu_varna.sit.calendar.command;

import bg.tu_varna.sit.calendar.exception.EventException;

public class CommandExecutor {
    private final CommandFactory commandFactory;

    public CommandExecutor(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public void executeCommand(CommandType commandType) throws EventException {
        Command command = commandFactory.getCommand(commandType);
        if (command != null) {
            command.execute();
        } else {
            System.out.println("Invalid Command");
        }
    }
}

