package bg.tu_varna.sit.calendar.command.impl;

import bg.tu_varna.sit.calendar.command.Command;
import bg.tu_varna.sit.calendar.model.BusyDay;
import bg.tu_varna.sit.calendar.model.Event;
import bg.tu_varna.sit.calendar.service.EventService;
import bg.tu_varna.sit.calendar.util.InputUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class BusyDaysCommand implements Command {
    private final EventService eventService;

    public BusyDaysCommand(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute() {
        LocalDate from = InputUtils.readLocalDate("Enter the start date (yyyy-mm-dd): ");
        LocalDate to = InputUtils.readLocalDate("Enter the end date (yyyy-mm-dd): ");

        List<BusyDay> busyDays = eventService.getBusyDaysWithEventsInRange(from, to);

        if (busyDays.isEmpty()) {
            System.out.println("No busy days in the specified range.");
            return;
        }

        System.out.println("Busy days sorted by booked minutes:");
        for (BusyDay busyDay : busyDays) {
            System.out.println(busyDay.getDate() + ": " + busyDay.getTotalMinutesBooked() + " minutes booked");

            for (Event event : busyDay.getEvents()) {
                long durationMinutes = Duration.between(event.getTimeStart(), event.getTimeEnd()).toMinutes();
                System.out.println("  Event: [" + event.getTitle() + "] " + event.getTimeStart() + " - " + event.getTimeEnd() +
                        " (" + durationMinutes + " minutes)");
            }
        }
    }
}
