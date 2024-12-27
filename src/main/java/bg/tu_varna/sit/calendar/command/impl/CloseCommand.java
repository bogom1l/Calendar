package bg.tu_varna.sit.calendar.command.impl;

import bg.tu_varna.sit.calendar.command.Command;
import bg.tu_varna.sit.calendar.service.EventService;

public class CloseCommand implements Command {
    private final EventService eventService;

    public CloseCommand(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute() {
        eventService.close();
        System.out.println("Calendar closed.");
    }
}