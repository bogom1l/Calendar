package bg.tu_varna.sit.commands.event;

import bg.tu_varna.sit.commands.contracts.Command;
import bg.tu_varna.sit.model.Event;
import bg.tu_varna.sit.service.EventService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AgendaCommand implements Command {
    private final EventService eventService;
    private final Scanner scanner;

    public AgendaCommand(EventService eventService, Scanner scanner) {
        this.eventService = eventService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        LocalDate date = promptForDate();

        List<Event> events = eventService.getAgendaForDateSortedByTime(date);

        printAgenda(events);
    }

    private LocalDate promptForDate() {
        System.out.print("Enter the date (yyyy-mm-dd) to view events: ");
        return LocalDate.parse(scanner.nextLine());
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
