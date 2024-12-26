package bg.tu_varna.sit.commands.event;

import bg.tu_varna.sit.commands.contracts.Command;

import java.util.Scanner;

public class ExitCommand implements Command {
    private final Scanner scanner;

    public ExitCommand(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Exiting...");
        scanner.close();
    }
}
