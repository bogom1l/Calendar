package bg.tu_varna.sit.calendar.command.impl.eventswrapper;

import bg.tu_varna.sit.calendar.command.contract.Command;
import bg.tu_varna.sit.calendar.service.impl.EventServiceImpl;

public class SaveCommand implements Command {
    private final EventServiceImpl eventService;

    public SaveCommand(EventServiceImpl eventService) {
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