package bg.tu_varna.sit.commands.event;

import bg.tu_varna.sit.commands.contracts.Command;
import bg.tu_varna.sit.model.Event;
import bg.tu_varna.sit.service.EventService;
import bg.tu_varna.sit.util.InputUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AgendaCommand implements Command {
    private final EventService eventService;

    public AgendaCommand(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute() {
        LocalDate date = InputUtils.readLocalDate("Enter the date (yyyy-mm-dd) to view events:");

        List<Event> events = eventService.getAgendaForDateSortedByTime(date);

        printAgenda(events);
    }

    private void printAgenda(List<Event> events) {
        if (events.isEmpty()) {
            System.out.println("No events found for this date.");
        } else {
            for (Event event : events) {
                System.out.println("Event: " + event.getTitle() + " | Start: " + event.getTimeStart() + " | End: " + event.getTimeEnd());
            }
        }
    }
}
