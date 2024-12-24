package bg.tu_varna.sit.commands.event;

import bg.tu_varna.sit.commands.contracts.Command;
import bg.tu_varna.sit.model.Event;
import bg.tu_varna.sit.service.EventService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class BookCommand implements Command {
    private final EventService eventService;
    private final Scanner scanner;

    public BookCommand(EventService eventService, Scanner scanner) {
        this.eventService = eventService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        Event event = createEvent();

        if (eventService.bookEvent(event)) {
            System.out.println("Event booked successfully.");
        } else {
            System.out.println("Failed to book event.");
        }
    }

    private Event createEvent() {
        System.out.println("Enter event details:");

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        System.out.print("Start Time (HH:MM): ");
        LocalTime timeStart = LocalTime.parse(scanner.nextLine());

        System.out.print("End Time (HH:MM): ");
        LocalTime timeEnd = LocalTime.parse(scanner.nextLine());

        System.out.print("Description: ");
        String description = scanner.nextLine();

        return new Event(title, date, timeStart, timeEnd, description);
    }

}

