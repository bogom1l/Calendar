package bg.tu_varna.sit.calendar.command.impl;

import bg.tu_varna.sit.calendar.command.Command;
import bg.tu_varna.sit.calendar.model.Event;
import bg.tu_varna.sit.calendar.service.EventService;
import bg.tu_varna.sit.calendar.util.InputUtils;

import java.util.List;

public class FindCommand implements Command {
    private final EventService eventService;

    public FindCommand(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute() {
        String searchTerm = InputUtils.readString("Enter the search term: ");

        List<Event> matchingEvents = eventService.findEventsByTitleOrDescription(searchTerm);

        if (matchingEvents.isEmpty()) {
            System.out.println("No events found containing the search term.");
        } else {
            System.out.println("Matching events:");
            matchingEvents.forEach(System.out::println);
        }
    }
}