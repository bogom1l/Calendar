package bg.tu_varna.sit.input;

import bg.tu_varna.sit.commands.CommandType;

import java.util.Scanner;

public class UserInputHandler {
    private final Scanner scanner;

    public UserInputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getRawInput() {
        System.out.print("Enter your command: ");
        return scanner.nextLine().trim();
    }

    public CommandType parseCommand(String input) {
        try {
            return CommandType.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            // If the input doesn't match a valid command type
            return null;
        }
    }

//    public void start() {
//        while (true) {
//            printMenu();
//
//            String choice = scanner.nextLine();
//
//            //todo?
//            if (!eventService.isCalendarOpen() && !choice.equals("open") && !choice.equals("exit")) {
//                System.out.println("No calendar file is currently open. Please open a file first.");
//                continue;
//            }
//
//            commandExecutor.executeCommand(choice);
//        }
//    }
//
//    private void printMenu() {
//        System.out.println("\nPlease select an operation:");
//        if (!eventService.isCalendarOpen()) {
//            System.out.println("open - Open calendar");
//            System.out.println("exit - Exit");
//        } else {
//            System.out.println("Type 'help' to see all available commands.");
//        }
//    }
}