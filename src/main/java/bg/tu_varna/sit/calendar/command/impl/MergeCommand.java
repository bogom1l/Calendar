package bg.tu_varna.sit.calendar.command.impl;

import bg.tu_varna.sit.calendar.command.Command;
import bg.tu_varna.sit.calendar.model.Event;
import bg.tu_varna.sit.calendar.model.EventsWrapper;
import bg.tu_varna.sit.calendar.service.EventService;
import bg.tu_varna.sit.calendar.util.InputUtils;
import bg.tu_varna.sit.calendar.util.JAXBParser;

import java.util.ArrayList;
import java.util.List;

public class MergeCommand implements Command {
    private final EventService eventService;

    public MergeCommand(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute() {
        String calendarFileName = InputUtils.readString("Enter the name of the calendar to merge (e.g., calendar.xml): ");
        EventsWrapper otherEventsWrapper = loadEventsWrapper(calendarFileName);
        if (otherEventsWrapper == null) return;

        List<Event> otherEvents = otherEventsWrapper.getEvents();
        List<Event> currentEvents = eventService.getEventsWrapper().getEvents();
        List<Event> eventsToAdd = resolveConflicts(currentEvents, otherEvents);

        currentEvents.addAll(eventsToAdd);
        System.out.println("Merge completed.");
    }

    private EventsWrapper loadEventsWrapper(String filename) {
        try {
            return JAXBParser.loadEventsFromXMLByFilename(filename);
        } catch (Exception e) {
            System.out.println("Error loading the specified calendar file: " + e.getMessage());
            return null;
        }
    }

    private List<Event> resolveConflicts(List<Event> currentEvents, List<Event> otherEvents) {
        List<Event> eventsToAdd = new ArrayList<>();

        for (Event otherEvent : otherEvents) {
            boolean conflictFound = false;

            for (Event currentEvent : currentEvents) {
                if (eventsConflict(currentEvent, otherEvent)) {
                    conflictFound = true;
                    handleConflict(currentEvent, otherEvent, currentEvents, eventsToAdd);
                    break;
                }
            }

            if (!conflictFound) {
                eventsToAdd.add(otherEvent);
            }
        }

        return eventsToAdd;
    }

    private void handleConflict(Event currentEvent, Event otherEvent, List<Event> currentEvents, List<Event> eventsToAdd) {
        System.out.println("Conflict detected:");
        System.out.println("Current Event: " + currentEvent);
        System.out.println("Other Event: " + otherEvent);

        int choice = promptUserForConflictResolution();
        switch (choice) {
            case 1 -> System.out.println("Kept the current event.");
            case 2 -> {
                System.out.println("Replaced with the other event.");
                currentEvents.remove(currentEvent); // Remove current event if replacing
                eventsToAdd.add(otherEvent);
            }
            case 3 -> System.out.println("Skipped both events.");
            default -> System.out.println("Invalid choice. Skipping...");
        }
    }

    private int promptUserForConflictResolution() {
        int choice = 0;
        while (choice < 1 || choice > 3) {
            System.out.println("Choose which event to keep:");
            System.out.println("1. Current Event");
            System.out.println("2. Other Event");
            System.out.println("3. Skip Both");
            choice = InputUtils.readInt("Enter your choice (1-3): ");
        }
        return choice;
    }

    private boolean eventsConflict(Event event1, Event event2) {
        return event1.getDate().equals(event2.getDate()) &&
                !(event1.getTimeEnd().isBefore(event2.getTimeStart()) ||
                        event2.getTimeEnd().isBefore(event1.getTimeStart()));
    }
}