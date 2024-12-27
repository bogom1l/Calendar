package bg.tu_varna.sit.calendar.command.impl;

import bg.tu_varna.sit.calendar.command.Command;
import bg.tu_varna.sit.calendar.model.Event;
import bg.tu_varna.sit.calendar.service.EventService;
import bg.tu_varna.sit.calendar.service.HolidayService;
import bg.tu_varna.sit.calendar.util.InputUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class BookCommand implements Command {
    private final EventService eventService;
    private final HolidayService holidayService;

    public BookCommand(EventService eventService, HolidayService holidayService) {
        this.eventService = eventService;
        this.holidayService = holidayService;
    }

    @Override
    public void execute() {
        Optional<Event> event = createEvent();

        if (event.isEmpty()) {
            System.out.println("Error creating an event.");
        }

        if (eventService.bookEvent(event.get())) {
            System.out.println("Event booked successfully.");
        } else {
            System.out.println("Failed to book event.");
        }
    }

    private Optional<Event> createEvent() {
        System.out.println("Enter event details:");

        String title = InputUtils.readString("Title: ");

        LocalDate date = InputUtils.readLocalDate("Date (YYYY-MM-DD): ");

        if (holidayService.isHoliday(date)) {
            System.out.println("Cannot schedule an event on " + date + " because it's a holiday.");
        }

        LocalTime timeStart = InputUtils.readLocalTime("Start Time (HH:MM): ");
        LocalTime timeEnd = getValidatedTimeEnd(timeStart);

        String description = InputUtils.readString("Description: ");

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

