package bg.tu_varna.sit.calendar.command.impl;

import bg.tu_varna.sit.calendar.command.Command;
import bg.tu_varna.sit.calendar.model.Event;
import bg.tu_varna.sit.calendar.service.EventService;

import java.util.List;
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
        System.out.print("Enter the search term: ");
        String searchTerm = scanner.nextLine();

        List<Event> matchingEvents = eventService.findEventsByTitleOrDescription(searchTerm);

        if (matchingEvents.isEmpty()) {
            System.out.println("No events found containing the search term.");
        } else {
            System.out.println("Matching events:");
            matchingEvents.forEach(System.out::println);
        }
    }
}