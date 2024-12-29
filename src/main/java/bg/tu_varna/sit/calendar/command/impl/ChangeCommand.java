package bg.tu_varna.sit.calendar.command.impl;

import bg.tu_varna.sit.calendar.command.Command;
import bg.tu_varna.sit.calendar.model.Event;
import bg.tu_varna.sit.calendar.service.EventService;
import bg.tu_varna.sit.calendar.util.InputUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class ChangeCommand implements Command {
    private final EventService eventService;

    public ChangeCommand(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute() {
        LocalDate date = InputUtils.readLocalDate("Enter the event date (yyyy-mm-dd): ");
        LocalTime timeStart = InputUtils.readLocalTime("Enter the event start time (hh:mm): ");

        Optional<Event> eventToChange = eventService.findEventByDateAndTimeStart(date, timeStart);

        if (eventToChange.isEmpty()) {
            System.out.println("Event not found by the provided date and start time.");
            return;
        }

        Event event = eventToChange.get();
        System.out.println("Event found:\n" + event);

        String fieldToChange = getFieldToChange();
        String newValue = InputUtils.readString("Enter the new value: ");

        boolean success = updateField(event, fieldToChange, newValue, date);
        if (!success) {
            System.out.println("Failed to update the event. Invalid value or conflict detected.");
            return;
        }

        System.out.println("Event updated successfully.");
    }

    private String getFieldToChange() {
        while (true) {
            String input = InputUtils.readString("Enter the option to change (date, timeStart, timeEnd, title, description): ");
            if (isValidOption(input)) {
                return input;
            }
            System.out.println("Invalid option. Please try again.");
        }
    }

    private boolean isValidOption(String option) {
        return switch (option) {
            case "date", "timeStart", "timeEnd", "title", "description" -> true;
            default -> false;
        };
    }

    private boolean updateField(Event event, String field, String newValue, LocalDate originalDate) {
        return switch (field) {
            case "date" -> updateDate(event, newValue);
            case "timeStart" -> updateStartTime(event, newValue, originalDate);
            case "timeEnd" -> updateEndTime(event, newValue, originalDate);
            case "title" -> eventService.updateEventTitle(event, newValue);
            case "description" -> eventService.updateEventDescription(event, newValue);
            default -> false;
        };
    }

    private boolean updateDate(Event event, String newValue) {
        try {
            LocalDate newDate = LocalDate.parse(newValue);
            if (!eventService.isTimeRangeAvailableForDate(newDate, event.getTimeStart(), event.getTimeEnd())) {
                System.out.println("The selected date is already taken by another event.");
                return false;
            }

            return eventService.updateEventDate(event, newDate);
        } catch (Exception e) {
            System.out.println("Invalid date format.");
            return false;
        }
    }

    private boolean updateStartTime(Event event, String newValue, LocalDate date) {
        try {
            LocalTime newTimeStart = LocalTime.parse(newValue);
            if (newTimeStart.isAfter(event.getTimeEnd())) {
                System.out.println("The new start time cannot be after the existing event's end time.");
                return false;
            }
            if (!eventService.isTimeStartAvailable(date, newTimeStart)) {
                System.out.println("The selected start time is already taken by another event.");
                return false;
            }

            return eventService.updateEventStartTime(event, newTimeStart);
        } catch (Exception e) {
            System.out.println("Invalid time format.");
            return false;
        }
    }

    private boolean updateEndTime(Event event, String newValue, LocalDate date) {
        try {
            LocalTime newTimeEnd = LocalTime.parse(newValue);
            if (newTimeEnd.isBefore(event.getTimeStart())) {
                System.out.println("The new end time cannot be before the existing event's start time.");
                return false;
            }
            if (!eventService.isTimeEndAvailable(date, newTimeEnd)) {
                System.out.println("The selected end time is already taken by another event.");
                return false;
            }

            return eventService.updateEventEndTime(event, newTimeEnd);
        } catch (Exception e) {
            System.out.println("Invalid time format.");
            return false;
        }
    }
}