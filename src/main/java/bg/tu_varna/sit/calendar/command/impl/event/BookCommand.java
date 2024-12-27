package bg.tu_varna.sit.calendar.command.impl.event;

import bg.tu_varna.sit.calendar.command.contract.Command;
import bg.tu_varna.sit.calendar.model.Event;
import bg.tu_varna.sit.calendar.service.impl.EventServiceImpl;
import bg.tu_varna.sit.calendar.service.impl.HolidayServiceImpl;
import bg.tu_varna.sit.calendar.util.InputUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Scanner;

public class BookCommand implements Command {
    private final EventServiceImpl eventService;
    private final Scanner scanner;
    private final HolidayServiceImpl holidayService;

    public BookCommand(EventServiceImpl eventService, Scanner scanner, HolidayServiceImpl holidayService) {
        this.eventService = eventService;
        this.scanner = scanner;
        this.holidayService = holidayService;
    }

    @Override
    public void execute() {
        Optional<Event> event = createEvent();

        if (event.isEmpty()) {
            System.out.println("Error creating an event.");
            return;
        }

        if (eventService.bookEvent(event.get())) {
            System.out.println("Event booked successfully.");
        } else {
            System.out.println("Failed to book event.");
        }
    }

    private Optional<Event> createEvent() {
        System.out.println("Enter event details:");

        System.out.print("Title: ");
        String title = scanner.nextLine();

        LocalDate date = InputUtils.readLocalDate("Date (YYYY-MM-DD): ");

        if (holidayService.isHoliday(date)) {
            System.out.println("Cannot schedule an event on " + date + " because it's a holiday.");
            return Optional.empty();
        }

        LocalTime timeStart = InputUtils.readLocalTime("Start Time (HH:MM): ");
        LocalTime timeEnd = getValidatedTimeEnd(timeStart);

        System.out.print("Description: ");
        String description = scanner.nextLine();

        return Optional.of(new Event(title, date, timeStart, timeEnd, description));
    }

    private LocalTime getValidatedTimeEnd(LocalTime timeStart) {
        LocalTime timeEnd;
        while (true) {
            timeEnd = InputUtils.readLocalTime("End Time (HH:MM): ");
            if (timeEnd.isAfter(timeStart)) {
                return timeEnd;
            } else {
                System.out.println("End time must be after " + timeStart + ". Please try again.");
            }
        }
    }

}

