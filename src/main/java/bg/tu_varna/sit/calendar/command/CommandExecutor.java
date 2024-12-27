package bg.tu_varna.sit.calendar.command;

public class CommandExecutor {
    private final CommandFactory commandFactory;

    public CommandExecutor(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public void executeCommand(CommandType commandType) {
        Command command = commandFactory.getCommand(commandType);
        if (command != null) {
            command.execute();
        } else {
            System.out.println("Invalid Command");
        }
    }
}

