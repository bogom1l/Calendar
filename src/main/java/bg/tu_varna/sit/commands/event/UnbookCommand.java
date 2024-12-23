package bg.tu_varna.sit.commands.event;

import bg.tu_varna.sit.commands.contracts.Command;
import bg.tu_varna.sit.service.EventService;

import java.util.Scanner;

public class UnbookCommand implements Command {
    private final EventService eventService;
    private final Scanner scanner;

    public UnbookCommand(EventService eventService, Scanner scanner) {
        this.eventService = eventService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Enter the title of the event to unbook: ");
        String title = scanner.nextLine();

        if (eventService.unbookEvent(title)) {
            System.out.println("Event unbooked successfully.");
        } else {
            System.out.println("Event not found.");
        }
    }
}