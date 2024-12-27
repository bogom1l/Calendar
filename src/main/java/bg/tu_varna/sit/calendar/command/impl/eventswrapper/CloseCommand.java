package bg.tu_varna.sit.calendar.command.impl.eventswrapper;

import bg.tu_varna.sit.calendar.command.contract.Command;
import bg.tu_varna.sit.calendar.service.impl.EventServiceImpl;

public class CloseCommand implements Command {
    private final EventServiceImpl eventService;

    public CloseCommand(EventServiceImpl eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute() {
        eventService.close();
        System.out.println("Calendar closed.");
    }
}