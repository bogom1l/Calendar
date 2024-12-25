package bg.tu_varna.sit.commands.event;

import bg.tu_varna.sit.commands.contracts.Command;
import bg.tu_varna.sit.model.Event;
import bg.tu_varna.sit.model.EventsWrapper;
import bg.tu_varna.sit.service.EventService;
import bg.tu_varna.sit.util.InputUtils;
import bg.tu_varna.sit.util.JAXBParser;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FindSlotWithCommand implements Command {
    private final EventService eventService;

    public FindSlotWithCommand(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute() {
        LocalDate fromDate = InputUtils.readLocalDate("Enter the starting date (yyyy-mm-dd): ");
        int hoursToFind = InputUtils.readInt("Enter the number of hours to find: ");
        String calendarFileName = InputUtils.readString("Enter the name of the other calendar (e.g., calendar.xml): ");

        EventsWrapper otherEventsWrapper = loadOtherCalendar(calendarFileName);

        LocalDate currentDate = fromDate;

        while (true) {
            List<Event> allEvents = new ArrayList<>();
            allEvents.addAll(eventService.getEventsByDate(currentDate));
            allEvents.addAll(eventService.getEventsByDate(currentDate, otherEventsWrapper));

            allEvents.sort(Comparator.comparing(Event::getTimeStart));

            LocalTime availableStart = LocalTime.of(8, 0);
            LocalTime availableEnd = LocalTime.of(17, 0);

            for (Event event : allEvents) {
                if (Duration.between(availableStart, event.getTimeStart()).toHours() >= hoursToFind) {
                    System.out.println("Available slot found: " + currentDate + " from " + availableStart);
                    return;
                }
                availableStart = event.getTimeEnd();
            }

            if (Duration.between(availableStart, availableEnd).toHours() >= hoursToFind) {
                System.out.println("Available slot found: " + currentDate + " from " + availableStart);
                return;
            }

            currentDate = currentDate.plusDays(1);
        }
    }

    private EventsWrapper loadOtherCalendar(String calendarFileName){
        try {
            return JAXBParser.loadEventsFromXMLByFilename(calendarFileName);
        } catch (Exception e) {
            System.out.println("Error loading the specified calendar file: " + e.getMessage());
            return null;
        }
    }

}
