package bg.tu_varna.sit.calendar.command.impl.event;

import bg.tu_varna.sit.calendar.command.contract.Command;
import bg.tu_varna.sit.calendar.model.Event;
import bg.tu_varna.sit.calendar.service.impl.EventServiceImpl;
import bg.tu_varna.sit.calendar.util.InputUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Scanner;

public class ChangeCommand implements Command {
    private final EventServiceImpl eventService;
    private final Scanner scanner;

    public ChangeCommand(EventServiceImpl eventService, Scanner scanner) {
        this.eventService = eventService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        // Step 1: Get user input for date and timeStart
        LocalDate date = InputUtils.readLocalDate("Enter the event date (yyyy-mm-dd): ");
        LocalTime timeStart = InputUtils.readLocalTime("Enter the event start time (hh:mm): ");

        // Step 2: Find the event by date and timeStart
        Optional<Event> eventToChange = eventService.findEventByDateAndTimeStart(date, timeStart);

        // If event is not found, exit
        if (eventToChange.isEmpty()) {
            System.out.println("Event not found by the provided date and start time.");
            return;
        }

        Event event = eventToChange.get();
        System.out.println("Event found:\n" + event);

        // Step 2: Prompt user for field to change
        String fieldToChange = getFieldToChange();

        // Step 3: Prompt for the new value
        System.out.print("Enter the new value: ");
        String newValue = scanner.nextLine();

        boolean success = false;

        if ("date".equals(fieldToChange)) {
            LocalDate newDate = LocalDate.parse(newValue);

            if (!eventService.isTimeRangeAvailableForDate(newDate, event.getTimeStart(), event.getTimeEnd())) {
                System.out.println("The selected date is already taken by another event.");
                return;
            }

            success = eventService.updateEventDate(event, newDate);
        } else if ("timeStart".equals(fieldToChange)) {

            LocalTime newTimeStart = LocalTime.parse(newValue);

            if (!eventService.isTimeStartAvailable(date, newTimeStart)) {
                System.out.println("The selected start time is already taken by another event.");
                return;
            }

            if (newTimeStart.isAfter(event.getTimeEnd())) {
                System.out.println("The new start time cannot be after the existing event's end time.");
                return;
            }

            success = eventService.updateEventStartTime(event, newTimeStart);

        } else if ("timeEnd".equals(fieldToChange)) {

            LocalTime newTimeEnd = LocalTime.parse(newValue);

            if (!eventService.isTimeEndAvailable(date, newTimeEnd)) {
                System.out.println("The selected end time is already taken by another event.");
                return;
            }

            if (newTimeEnd.isBefore(event.getTimeStart())) {
                System.out.println("The new end time cannot be before the existing event's start time.");
                return;
            }

            success = eventService.updateEventEndTime(event, newTimeEnd);

        } else if ("title".equals(fieldToChange)) {
            success = eventService.updateEventTitle(event, newValue);
        } else if ("description".equals(fieldToChange)) {
            success = eventService.updateEventDescription(event, newValue);
        }

        if (success) {
            System.out.println("Event updated successfully.");
        } else {
            System.out.println("Failed to update the event. Invalid value or field.");
        }
    }

    private boolean isValidOption(String option) {
        return option.equals("date") ||
                option.equals("timeStart") ||
                option.equals("timeEnd") ||
                option.equals("title") ||
                option.equals("description");
    }

    private String getFieldToChange() {
        while (true) {
            System.out.print("Enter the option to change (date, timeStart, timeEnd, title, description): ");
            String input = scanner.nextLine();
            if (isValidOption(input)) {
                return input;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }


}