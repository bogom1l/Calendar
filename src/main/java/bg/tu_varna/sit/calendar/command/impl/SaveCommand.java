package bg.tu_varna.sit.calendar.command.impl;

import bg.tu_varna.sit.calendar.command.Command;
import bg.tu_varna.sit.calendar.service.EventService;

public class SaveCommand implements Command {
    private final EventService eventService;

    public SaveCommand(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute() {
        if (eventService.save()) {
            System.out.println("Calendar saved successfully.");
        } else {
            System.out.println("No calendar open to save.");
        }
    }
}