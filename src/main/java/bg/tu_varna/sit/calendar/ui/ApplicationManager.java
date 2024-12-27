package bg.tu_varna.sit.calendar.ui;

import bg.tu_varna.sit.calendar.command.CommandExecutor;
import bg.tu_varna.sit.calendar.command.CommandType;
import bg.tu_varna.sit.calendar.exception.EventException;
import bg.tu_varna.sit.calendar.model.AppState;

public class ApplicationManager {
    private final UserInputHandler userInputHandler;
    private final CommandExecutor commandExecutor;
    private final AppState appState;

    public ApplicationManager(UserInputHandler userInputHandler, CommandExecutor commandExecutor, AppState appState) {
        this.userInputHandler = userInputHandler;
        this.commandExecutor = commandExecutor;
        this.appState = appState;
    }

    public void start() throws EventException {
        System.out.println("Welcome to the Calendar Application!");
        boolean running = true;

        while (running) {
            printMenu();

            String choice = userInputHandler.getRawInput();
            CommandType commandType = userInputHandler.parseCommand(choice);

            if (commandType == null) {
                System.out.println("Invalid command. Please try again.");
                continue;
            }

            if (!appState.isCalendarOpen() && !isCommandAllowedWithoutCalendar(commandType)) {
                System.out.println("No calendar file is currently open. Please open a file first.");
                continue;
            }

            if (commandType == CommandType.EXIT) {
                System.out.println("Exiting the application..");
                running = false;
            } else {
                commandExecutor.executeCommand(commandType);
            }
        }
    }

    private void printMenu() {
        System.out.println("\nPlease select an operation:");
        if (!appState.isCalendarOpen()) {
            System.out.println("open - Open calendar");
            System.out.println("exit - Exit");
        } else {
            System.out.println("Type 'help' to see all available commands.");
        }
    }

    private boolean isCommandAllowedWithoutCalendar(CommandType commandType) {
        return commandType == CommandType.OPEN || commandType == CommandType.EXIT;
    }
}

