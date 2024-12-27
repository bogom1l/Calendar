package bg.tu_varna.sit.input;

import bg.tu_varna.sit.commands.CommandExecutor;
import bg.tu_varna.sit.commands.CommandType;

public class ApplicationManager {
    private final UserInputHandler userInputHandler;
    private final CommandExecutor commandExecutor;
    private final AppState appState;

    public ApplicationManager(UserInputHandler userInputHandler, CommandExecutor commandExecutor, AppState appState) {
        this.userInputHandler = userInputHandler;
        this.commandExecutor = commandExecutor;
        this.appState = appState;
    }

    public void start() {
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

            // Check if the calendar is open or validate the command
            //if (!eventService.isCalendarOpen() && !isCommandAllowedWithoutCalendar(commandType)) {

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
        //if (!eventService.isCalendarOpen()) {
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

