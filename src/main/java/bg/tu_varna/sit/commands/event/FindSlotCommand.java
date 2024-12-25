package bg.tu_varna.sit.commands.event;

import bg.tu_varna.sit.commands.contracts.Command;
import bg.tu_varna.sit.model.Event;
import bg.tu_varna.sit.service.EventService;
import bg.tu_varna.sit.util.InputUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FindSlotCommand implements Command {
    private final EventService eventService;

    public FindSlotCommand(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute() {
        LocalDate fromDate = InputUtils.readLocalDate("Enter the starting date (yyyy-mm-dd): ");
        int hoursToFind = InputUtils.readInt("Enter the number of hours to find: ");

        LocalDate currentDate = fromDate;

        while (true) {
            // Get events for the current date
            List<Event> events = new ArrayList<>(eventService.getEventsByDate(currentDate)); // Ensure list is mutable
            events.sort(Comparator.comparing(Event::getTimeStart));

            // Check available slots from 08:00 to 17:00
            LocalTime availableStart = LocalTime.of(8, 0);
            LocalTime availableEnd = LocalTime.of(17, 0);

            for (Event event : events) {
                if (Duration.between(availableStart, event.getTimeStart()).toHours() >= hoursToFind) {
                    System.out.println("Available slot found: " + currentDate + " from " + availableStart);
                    return;
                }
                availableStart = event.getTimeEnd(); // Move the start to the end of the current event
            }

            // Check after the last event of the day
            if (Duration.between(availableStart, availableEnd).toHours() >= hoursToFind) {
                System.out.println("Available slot found: " + currentDate + " from " + availableStart);
                return;
            }

            // Move to the next day
            currentDate = currentDate.plusDays(1);
        }
    }
}