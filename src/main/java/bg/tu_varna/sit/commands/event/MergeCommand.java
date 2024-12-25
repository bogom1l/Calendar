package bg.tu_varna.sit.commands.event;

import bg.tu_varna.sit.commands.contracts.Command;
import bg.tu_varna.sit.model.Event;
import bg.tu_varna.sit.model.EventsWrapper;
import bg.tu_varna.sit.service.EventService;
import bg.tu_varna.sit.util.InputUtils;
import bg.tu_varna.sit.util.JAXBParser;

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

        for (Event otherEvent : otherEvents) {
            boolean conflictFound = false;

            for (Event currentEvent : currentEvents) {
                if (eventsConflict(currentEvent, otherEvent)) {
                    conflictFound = true;

                    System.out.println("Conflict detected:");
                    System.out.println("Current Event: " + currentEvent);
                    System.out.println("Other Event: " + otherEvent);

                    System.out.println("Choose which event to keep:");
                    System.out.println("1. Current Event");
                    System.out.println("2. Other Event");
                    System.out.println("3. Skip Both");

                    int choice = InputUtils.readInt("Enter your choice (1-3): ");
                    switch (choice) {
                        case 1 -> {
                            // Keep current event, skip adding other event
                            System.out.println("Kept the current event.");
                        }
                        case 2 -> {
                            // Replace current event with the other event
                            currentEvents.remove(currentEvent);
                            currentEvents.add(otherEvent);
                            System.out.println("Replaced with the other event.");
                        }
                        case 3 -> {
                            // Skip both events
                            System.out.println("Skipped both events.");
                        }
                        default -> System.out.println("Invalid choice. Skipping...");
                    }
                    break;
                }
            }

            if (!conflictFound) {
                currentEvents.add(otherEvent);
            }
        }

        System.out.println("Merge completed.");
    }

    private boolean eventsConflict(Event event1, Event event2) {
        return event1.getDate().equals(event2.getDate()) &&
                !(event1.getTimeEnd().isBefore(event2.getTimeStart()) ||
                        event2.getTimeEnd().isBefore(event1.getTimeStart()));
    }
}
