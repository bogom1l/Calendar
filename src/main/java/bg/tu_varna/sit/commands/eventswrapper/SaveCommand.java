package bg.tu_varna.sit.commands.eventswrapper;

import bg.tu_varna.sit.commands.contracts.Command;
import bg.tu_varna.sit.service.EventService;

public class SaveCommand implements Command {
    private EventService eventService;

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