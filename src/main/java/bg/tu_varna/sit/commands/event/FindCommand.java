package bg.tu_varna.sit.commands.event;

import bg.tu_varna.sit.commands.contracts.Command;
import bg.tu_varna.sit.service.EventService;

import java.util.Scanner;

public class FindCommand implements Command {
    private final EventService eventService;
    private final Scanner scanner;

    public FindCommand(EventService eventService, Scanner scanner) {
        this.eventService = eventService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Enter event title or date to search: ");
        String searchTerm = scanner.nextLine();

        eventService.findEvent(searchTerm).forEach(event ->
                System.out.println(event.toString())
        );
    }
}