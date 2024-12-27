package bg.tu_varna.sit.calendar.command.impl.event;

import bg.tu_varna.sit.calendar.command.contract.Command;
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
        EventsWrapper otherEventsWrapper;

        try {
            otherEventsWrapper = JAXBParser.loadEventsFromXMLByFilename(calendarFileName);
        } catch (Exception e) {
            System.out.println("Error loading the specified calendar file: " + e.getMessage());
            return;
        }

        List<Event> otherEvents = otherEventsWrapper.getEvents();
        List<Event> currentEvents = eventService.getEventsWrapper().getEvents();

        // List to collect new events and conflicting decisions
        List<Event> eventsToAdd = new ArrayList<>();

        for (Event otherEvent : otherEvents) {
            boolean conflictFound = false;

            for (Event currentEvent : currentEvents) {
                if (eventsConflict(currentEvent, otherEvent)) {
                    conflictFound = true;

                    System.out.println("Conflict detected:");
                    System.out.println("Current Event: " + currentEvent);
                    System.out.println("Other Event: " + otherEvent);

                    // Ask user to resolve conflict
                    int choice = 0;
                    while (choice < 1 || choice > 3) {
                        System.out.println("Choose which event to keep:");
                        System.out.println("1. Current Event");
                        System.out.println("2. Other Event");
                        System.out.println("3. Skip Both");

                        choice = InputUtils.readInt("Enter your choice (1-3): ");
                    }

                    switch (choice) {
                        case 1 -> {
                            // Keep the current event, skip adding other event
                            System.out.println("Kept the current event.");
                            // eventsToAdd.add(currentEvent);
                            // No need to add currentEvent again since it's already in currentEvents
                        }
                        case 2 -> {
                            // Replace current event with the other event
                            System.out.println("Replaced with the other event.");
                            // todo ?
                            currentEvents.remove(currentEvent);  // Optional: depending on whether you want to delete or overwrite
                            eventsToAdd.add(otherEvent); // Add the other event instead
                        }
                        case 3 -> {
                            // Skip both events
                            System.out.println("Skipped both events.");
                            // todo ?
                            currentEvents.remove(currentEvent);  // Optional: depending on whether you want to delete or overwrite
                        }
                        default -> System.out.println("Invalid choice. Skipping...");
                    }
                    break;
                }
            }

            if (!conflictFound) {
                eventsToAdd.add(otherEvent); // No conflict, simply add the event
            }
        }

        // Add all events that were decided upon
        currentEvents.addAll(eventsToAdd);

        System.out.println("Merge completed.");
    }

    private boolean eventsConflict(Event event1, Event event2) {
        return event1.getDate().equals(event2.getDate()) &&
                !(event1.getTimeEnd().isBefore(event2.getTimeStart()) ||
                        event2.getTimeEnd().isBefore(event1.getTimeStart()));
    }
}
