package bg.tu_varna.sit.commands.eventswrapper;

import bg.tu_varna.sit.commands.contracts.Command;
import bg.tu_varna.sit.service.EventService;

public class CloseCommand implements Command {
    private EventService eventService;

    public CloseCommand(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute() {
        eventService.close();
        System.out.println("Calendar closed.");
    }
}