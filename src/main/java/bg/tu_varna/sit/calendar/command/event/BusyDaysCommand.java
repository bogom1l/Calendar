package bg.tu_varna.sit.calendar.command.event;

import bg.tu_varna.sit.calendar.command.contract.Command;
import bg.tu_varna.sit.calendar.model.Event;
import bg.tu_varna.sit.calendar.service.EventService;
import bg.tu_varna.sit.calendar.command.util.InputUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class BusyDaysCommand implements Command {
    private final EventService eventService;

    public BusyDaysCommand(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute() {
        LocalDate from = InputUtils.readLocalDate("Enter the start date (yyyy-mm-dd): ");
        LocalDate to = InputUtils.readLocalDate("Enter the end date (yyyy-mm-dd): ");

        List<Map.Entry<LocalDate, Map.Entry<Long, List<Event>>>> busyDays = eventService.getBusyDaysWithEventsInRange(from, to);

        if (busyDays.isEmpty()) {
            System.out.println("No busy days in the specified range.");
        } else {
            System.out.println("Busy days sorted by booked minutes:");
            for (Map.Entry<LocalDate, Map.Entry<Long, List<Event>>> entry : busyDays) {
                LocalDate date = entry.getKey();
                long totalMinutes = entry.getValue().getKey();
                List<Event> events = entry.getValue().getValue();

                System.out.println(date + ": " + totalMinutes + " minutes booked");

                // Print event details for the day
                for (Event event : events) {
                    System.out.println("  Event: [" + event.getTitle() + "] " + event.getTimeStart() + " - " + event.getTimeEnd() + " (" +
                            Duration.between(event.getTimeStart(), event.getTimeEnd()).toMinutes() + " minutes)");
                }
            }
        }
    }
}