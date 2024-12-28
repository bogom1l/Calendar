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
        LocalDate fromDate = InputUtils.readLocalDate("Enter the starting date (yyyy-mm-dd): ");
        int hoursToFind = InputUtils.readInt("Enter the number of hours to find: ");
        String calendarFileName = InputUtils.readString("Enter the name of the other calendar (e.g., calendar.xml): ");

        Set<LocalDate> holidays = loadHolidays("holidays.xml");
        if (holidays == null) return;

        EventsWrapper otherEventsWrapper = loadEventsWrapper(calendarFileName);
        if (otherEventsWrapper == null) return;

        findAvailableSlot(fromDate, hoursToFind, holidays, otherEventsWrapper, calendarFileName);
    }

    private Set<LocalDate> loadHolidays(String fileName) {
        try {
            HolidaysWrapper holidaysWrapper = JAXBParser.loadHolidaysFromXMLByFilename(fileName);
            Set<LocalDate> holidays = new HashSet<>();
            for (Holiday holiday : holidaysWrapper.getHolidays()) {
                holidays.add(holiday.getDate());
            }
            return holidays;
        } catch (Exception e) {
            System.out.println("Error loading " + fileName + ": " + e.getMessage());
            return null;
        }
    }

    private EventsWrapper loadEventsWrapper(String filename) {
        try {
            return JAXBParser.loadEventsFromXMLByFilename(filename);
        } catch (Exception e) {
            System.out.println("Error loading the specified calendar file: " + e.getMessage());
            return null;
        }
    }

    private void findAvailableSlot(LocalDate fromDate, int hoursToFind, Set<LocalDate> holidays, EventsWrapper otherEventsWrapper, String calendarFileName) {
        LocalDate currentDate = fromDate;

        while (true) {
            if (holidays.contains(currentDate)) {
                currentDate = currentDate.plusDays(1);
                continue;
            }

            List<Event> allEvents = gatherAllEventsForDate(currentDate, otherEventsWrapper);
            Map<Event, String> eventSources = mapEventSources(allEvents, currentDate, calendarFileName);

            allEvents.sort(Comparator.comparing(Event::getTimeStart));

            if (findSlotInDay(allEvents, eventSources, currentDate, hoursToFind)) {
                return;
            }

            currentDate = currentDate.plusDays(1);
        }
    }

    private List<Event> gatherAllEventsForDate(LocalDate date, EventsWrapper otherEventsWrapper) {
        List<Event> allEvents = new ArrayList<>(eventService.getEventsByDate(date));
        allEvents.addAll(eventService.getEventsByDate(date, otherEventsWrapper));
        return allEvents;
    }

    private Map<Event, String> mapEventSources(List<Event> allEvents, LocalDate date, String otherCalendarName) {
        Map<Event, String> eventSources = new HashMap<>();
        List<Event> currentCalendarEvents = eventService.getEventsByDate(date);

        for (Event event : allEvents) {
            if (currentCalendarEvents.contains(event)) {
                eventSources.put(event, "Current Calendar");
            } else {
                eventSources.put(event, otherCalendarName);
            }
        }
        return eventSources;
    }

    private boolean findSlotInDay(List<Event> allEvents, Map<Event, String> eventSources, LocalDate currentDate, int hoursToFind) {
        LocalTime availableStart = LocalTime.of(8, 0);
        LocalTime availableEnd = LocalTime.of(17, 0);

        for (Event event : allEvents) {
            if (isSlotAvailable(availableStart, event.getTimeStart(), hoursToFind)) {
                printSlot(currentDate, availableStart, eventSources.get(event));
                return true;
            }
            availableStart = event.getTimeEnd();
        }

        if (isSlotAvailable(availableStart, availableEnd, hoursToFind)) {
            printSlot(currentDate, availableStart, "Current Calendar");
            return true;
        }

        return false;
    }

    private boolean isSlotAvailable(LocalTime start, LocalTime end, int hoursToFind) {
        return Duration.between(start, end).toHours() >= hoursToFind;
    }

    private void printSlot(LocalDate date, LocalTime startTime, String calendarName) {
        System.out.println("Available slot found: " + date + " from " + startTime);
        System.out.println("Calendar: " + calendarName);
    }
}