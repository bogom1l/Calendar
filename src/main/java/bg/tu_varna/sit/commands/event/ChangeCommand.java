package bg.tu_varna.sit.commands.event;

import bg.tu_varna.sit.commands.contracts.Command;
import bg.tu_varna.sit.model.Event;
import bg.tu_varna.sit.service.EventService;
import bg.tu_varna.sit.util.LocalTimeAdapter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Scanner;

public class ChangeCommand implements Command {
    private final EventService eventService;
    private final Scanner scanner;

    public ChangeCommand(EventService eventService, Scanner scanner) {
        this.eventService = eventService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        // Step 1: Get user input for date and timeStart
        LocalDate date = promptForDate();
        LocalTime timeStart = promptForTimeStart();

        // Step 2: Find the event by date and timeStart
        Optional<Event> eventToChange = eventService.findEventByDateAndTimeStart(date, timeStart);

        // If event is not found, exit
        if (eventToChange.isEmpty()) {
            System.out.println("Event not found by the provided date and start time.");
            return;
        }

        // Step 3: Get the option (date, timeStart, timeEnd, title, description)
        String option = promptForOption();

        // Step 4: Validate the option
        if (!isValidOption(option)) {
            System.out.println("Invalid option.");
            return;
        }

        // Step 5: Get new value for the option
        String newValue = promptForNewValue();

        // Step 6: Check if the new value type matches the option
        if (!isValidValueForOption(newValue, option)) {
            System.out.println("Invalid value type.");
            return;
        }

        // Step 7: If the option is date or timeStart, check for conflicts
        //todo ; to change it to find if there is an event that is on this date and if it is between timeStart and timeEnd
//        if ("date".equals(option) || "timeStart".equals(option) || "timeEnd".equals(option)) {
//            // Check for conflicts before making the change
//            if (eventService.isConflict(newDate, newTimeStart, newTimeEnd)) {
//                System.out.println("Conflict detected! Event can't be changed.");
//                return;  // Prevent the change if there is a conflict
//            }
//        }

        // Step 8: Perform the change
        //changeEvent(eventToChange.get(), option, newValue);
        System.out.println("Event updated successfully.");

    }

    private LocalDate promptForDate() {
        System.out.print("Enter the event date (yyyy-mm-dd): ");
        return LocalDate.parse(scanner.nextLine());
    }

    private LocalTime promptForTimeStart() {
        System.out.print("Enter the event start time (hh:mm): ");
        return LocalTime.parse(scanner.nextLine());
    }

    private String promptForOption() {
        System.out.print("Enter the option to change (date, timeStart, timeEnd, title, description): ");
        return scanner.nextLine();
    }

    private String promptForNewValue() {
        System.out.print("Enter the new value: ");
        return scanner.nextLine();
    }

    private boolean isValidOption(String option) {
        return option.equals("date") || option.equals("timeStart") || option.equals("timeEnd") || option.equals("title") || option.equals("description");
    }

    private boolean isValidValueForOption(String newValue, String option) {
        return switch (option) {
            case "date" -> isValidDate(newValue);
            case "timeStart", "timeEnd" -> isValidTime(newValue);
            case "title", "description" -> true; // Any string is valid for title/description
            default -> false;
        };
    }

    private boolean isValidDate(String newValue) {
        try {
            LocalDate.parse(newValue); // Tries to parse the date
            return true;
        } catch (Exception e) {
            return false;  // Invalid date format
        }
    }

    private boolean isValidTime(String newValue) {
        try {
            LocalTime.parse(newValue); // Tries to parse the time
            return true;
        } catch (Exception e) {
            return false;  // Invalid time format
        }
    }




//    private void changeEvent(Event event, String option, String newValue) {
//        // Apply the change based on the option selected
//        switch (option) {
//            case "date":
//                event.setDate(newValue); // Change date
//                break;
//            case "timeStart":
//                event.setTimeStart(newValue); // Change start time
//                break;
//            case "timeEnd":
//                event.setTimeEnd(newValue); // Change end time
//                break;
//            case "title":
//                event.setTitle(newValue); // Change title
//                break;
//            case "description":
//                event.setDescription(newValue); // Change description
//                break;
//        }
//    }
}

/*
package bg.tu_varna.sit.commands.event;

import bg.tu_varna.sit.commands.contracts.Command;
import bg.tu_varna.sit.model.Event;
import bg.tu_varna.sit.service.EventService;

import java.util.Optional;
import java.util.Scanner;

public class ChangeCommand implements Command {
    private final EventService eventService;
    private final Scanner scanner;

    public ChangeCommand(EventService eventService, Scanner scanner) {
        this.eventService = eventService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        String date = promptForDate();
        String timeStart = promptForTimeStart();
        String option = promptForOption();
        String newValue = promptForNewValue();

        Optional<Event> eventToChange = eventService.findEventByDateAndTimeStart(date, timeStart);

        if (eventToChange.isPresent()) {
            Event event = eventToChange.get();
            if (isChangeValid(event, option, newValue)) {
                applyChange(event, option, newValue);
                System.out.println("Event updated successfully.");
            } else {
                System.out.println("The new value conflicts with another event.");
            }
        } else {
            System.out.println("Event not found.");
        }
    }

    private String promptForDate() {
        System.out.print("Enter the event date (yyyy-mm-dd): ");
        return scanner.nextLine();
    }

    private String promptForTimeStart() {
        System.out.print("Enter the event start time (hh:mm): ");
        return scanner.nextLine();
    }

    private String promptForOption() {
        System.out.print("Enter the option to change (date, timeStart, timeEnd, title, description): ");
        return scanner.nextLine();
    }

    private String promptForNewValue() {
        System.out.print("Enter the new value: ");
        return scanner.nextLine();
    }

    private boolean isChangeValid(Event event, String option, String newValue) {
        // For date, timeStart, and timeEnd, check if the new value conflicts with other events
        if (option.equals("date") || option.equals("timeStart") || option.equals("timeEnd")) {
            return !eventService.isEventAtTime(newValue, event.getDate(), event.getTimeStart());
        }
        // For title and description, no conflict check needed
        return true;
    }

    private void applyChange(Event event, String option, String newValue) {
        switch (option) {
            case "date":
                event.setDate(newValue);
                break;
            case "timeStart":
                event.setTimeStart(newValue);
                break;
            case "timeEnd":
                event.setTimeEnd(newValue);
                break;
            case "title":
                event.setTitle(newValue);
                break;
            case "description":
                event.setDescription(newValue);
                break;
        }
    }
}

 */