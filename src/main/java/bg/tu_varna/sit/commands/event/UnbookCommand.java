package bg.tu_varna.sit.commands.event;

import bg.tu_varna.sit.commands.contracts.Command;
import bg.tu_varna.sit.model.Event;
import bg.tu_varna.sit.service.EventService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Scanner;

public class UnbookCommand implements Command {
    private final EventService eventService;
    private final Scanner scanner;

    public UnbookCommand(EventService eventService, Scanner scanner) {
        this.eventService = eventService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        LocalDate date = promptForDate();
        LocalTime timeStart = promptForTime("start");
        LocalTime timeEnd = promptForTime("end");

        Optional<Event> eventToRemove = eventService.findEventByDateAndTimeStartAndTimeEnd(date, timeStart, timeEnd);

        if (eventToRemove.isPresent()) {
            eventService.unbookEvent(eventToRemove.get());
            System.out.println("Event unbooked successfully.");
        } else {
            System.out.println("Event not found.");
        }
    }

    private LocalDate promptForDate() {
        System.out.print("Enter the event date (yyyy-mm-dd): ");
        return LocalDate.parse(scanner.nextLine());
    }

    private LocalTime promptForTime(String timeType) {
        System.out.print("Enter the event " + timeType + " time (hh:mm): ");
        return LocalTime.parse(scanner.nextLine());
    }
}