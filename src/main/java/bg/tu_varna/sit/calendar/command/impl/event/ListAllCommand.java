package bg.tu_varna.sit.calendar.command.impl.event;

import bg.tu_varna.sit.calendar.command.contract.Command;
import bg.tu_varna.sit.calendar.service.impl.EventServiceImpl;

public class ListAllCommand implements Command {
    private final EventServiceImpl eventService;

    public ListAllCommand(EventServiceImpl eventService) {
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