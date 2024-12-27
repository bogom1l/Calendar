package bg.tu_varna.sit.calendar.command.impl;

import bg.tu_varna.sit.calendar.command.Command;
import bg.tu_varna.sit.calendar.model.Event;
import bg.tu_varna.sit.calendar.model.EventsWrapper;
import bg.tu_varna.sit.calendar.model.Holiday;
import bg.tu_varna.sit.calendar.model.HolidaysWrapper;
import bg.tu_varna.sit.calendar.service.EventService;
import bg.tu_varna.sit.calendar.util.InputUtils;
import bg.tu_varna.sit.calendar.util.JAXBParser;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class FindSlotWithCommand implements Command {
    private final EventService eventService;

    public FindSlotWithCommand(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute() {

        //todo make this cleaner

        LocalDate fromDate = InputUtils.readLocalDate("Enter the starting date (yyyy-mm-dd): ");
        int hoursToFind = InputUtils.readInt("Enter the number of hours to find: ");
        String calendarFileName = InputUtils.readString("Enter the name of the other calendar (e.g., calendar.xml): ");

        Set<LocalDate> holidays = new HashSet<>();
        try {
            HolidaysWrapper holidaysWrapper = JAXBParser.loadHolidaysFromXMLByFilename("holidays.xml");
            for (Holiday holiday : holidaysWrapper.getHolidays()) {
                holidays.add(holiday.getDate());
            }
        } catch (Exception e) {
            System.out.println("Error loading holidays.xml: " + e.getMessage());
            return;
        }

        EventsWrapper otherEventsWrapper;
        try {
            // Use the helper method to load the additional calendar
            otherEventsWrapper = JAXBParser.loadEventsFromXMLByFilename(calendarFileName);
        } catch (Exception e) {
            System.out.println("Error loading the specified calendar file: " + e.getMessage());
            return;
        }

        LocalDate currentDate = fromDate;

        while (true) {
            // Skip holiday dates
            if (holidays.contains(currentDate)) {
                currentDate = currentDate.plusDays(1);
                continue;
            }

            List<Event> allEvents = new ArrayList<>();
            allEvents.addAll(eventService.getEventsByDate(currentDate));
            allEvents.addAll(eventService.getEventsByDate(currentDate, otherEventsWrapper));

            // Map events to their calendar sources
            List<String> eventSources = new ArrayList<>();
            LocalDate finalCurrentDate = currentDate;
            allEvents.forEach(event -> {
                if (eventService.getEventsByDate(finalCurrentDate).contains(event)) {
                    eventSources.add("Current Calendar");
                } else {
                    eventSources.add(calendarFileName);
                }
            });

            allEvents.sort(Comparator.comparing(Event::getTimeStart));

            LocalTime availableStart = LocalTime.of(8, 0);
            LocalTime availableEnd = LocalTime.of(17, 0);

            for (int i = 0; i < allEvents.size(); i++) {
                Event event = allEvents.get(i);
                String source = eventSources.get(i);

                if (Duration.between(availableStart, event.getTimeStart()).toHours() >= hoursToFind) {
                    System.out.println("Available slot found: " + currentDate + " from " + availableStart);
                    System.out.println("Calendar: " + source);
                    return;
                }
                availableStart = event.getTimeEnd();
            }

            if (Duration.between(availableStart, availableEnd).toHours() >= hoursToFind) {
                System.out.println("Available slot found: " + currentDate + " from " + availableStart);
                System.out.println("Calendar: Current Calendar");
                return;
            }

            currentDate = currentDate.plusDays(1);
        }
    }
}
