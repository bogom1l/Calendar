package bg.tu_varna.sit.calendar.command.impl.event;

import bg.tu_varna.sit.calendar.command.contract.Command;
import bg.tu_varna.sit.calendar.model.Event;
import bg.tu_varna.sit.calendar.service.impl.EventServiceImpl;
import bg.tu_varna.sit.calendar.util.InputUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Scanner;

public class UnbookCommand implements Command {
    private final EventServiceImpl eventService;
    private final Scanner scanner;

    public UnbookCommand(EventServiceImpl eventService, Scanner scanner) {
        this.eventService = eventService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        LocalDate date = InputUtils.readLocalDate("Date (YYYY-MM-DD): ");
        LocalTime timeStart = InputUtils.readLocalTime("Start Time (HH:MM): ");
        LocalTime timeEnd = InputUtils.readLocalTime("End Time (HH:MM): ");

        Optional<Event> eventToRemove = eventService.findEventByDateAndTimeStartAndTimeEnd(date, timeStart, timeEnd);

        if (eventToRemove.isPresent()) {
            eventService.unbookEvent(eventToRemove.get());
            System.out.println("Event unbooked successfully.");
        } else {
            System.out.println("Event not found.");
        }
    }
}