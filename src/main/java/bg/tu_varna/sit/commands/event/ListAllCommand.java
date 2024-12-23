package bg.tu_varna.sit.commands.event;

import bg.tu_varna.sit.commands.contracts.Command;
import bg.tu_varna.sit.service.EventService;

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