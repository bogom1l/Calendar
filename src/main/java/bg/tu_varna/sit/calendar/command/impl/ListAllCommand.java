package bg.tu_varna.sit.calendar.command.impl;

import bg.tu_varna.sit.calendar.command.Command;
import bg.tu_varna.sit.calendar.service.EventService;

public class ListAllCommand implements Command {
    private final EventService eventService;

    public ListAllCommand(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute() {
        System.out.println("Listing all events:");
        eventService.listAllEvents().forEach(event ->
                System.out.println(event.toString())
        );
    }
}