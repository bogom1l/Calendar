package bg.tu_varna.sit.calendar.ui;

import bg.tu_varna.sit.calendar.command.CommandType;

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
            return null;
        }
    }
}