package bg.tu_varna.sit.calendar.command.impl;

import bg.tu_varna.sit.calendar.command.Command;
import bg.tu_varna.sit.calendar.model.Event;
import bg.tu_varna.sit.calendar.model.Holiday;
import bg.tu_varna.sit.calendar.model.HolidaysWrapper;
import bg.tu_varna.sit.calendar.service.EventService;
import bg.tu_varna.sit.calendar.util.InputUtils;
import bg.tu_varna.sit.calendar.util.JAXBParser;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class FindSlotCommand implements Command {
    private final EventService eventService;

    public FindSlotCommand(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute() {
        Set<LocalDate> holidays = loadHolidays("holidays.xml");
        if (holidays == null) {
            return;
        }

        LocalDate fromDate = InputUtils.readLocalDate("Enter the starting date (yyyy-mm-dd): ");

        int hoursToFind = InputUtils.readInt("Enter the number of hours to find (1-8): ");
        if(hoursToFind > 8) {
            System.out.println("The number of hours to find exceeds 8 hours!");
            return;
        }

        findAvailableSlot(fromDate, hoursToFind, holidays);
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

    private void findAvailableSlot(LocalDate fromDate, int hoursToFind, Set<LocalDate> holidays) {
        LocalDate currentDate = fromDate;

        while (true) {
            if (holidays.contains(currentDate)) {
                currentDate = currentDate.plusDays(1);
                continue;
            }

            List<Event> events = getSortedEventsForDate(currentDate);

            LocalTime availableStart = LocalTime.of(8, 0);
            LocalTime availableEnd = LocalTime.of(17, 0);

            if (findSlotInDay(events, currentDate, availableStart, availableEnd, hoursToFind)) {
                return;
            }

            currentDate = currentDate.plusDays(1);
        }
    }

    private List<Event> getSortedEventsForDate(LocalDate date) {
        List<Event> events = new ArrayList<>(eventService.getEventsByDate(date)); //todo why new arraylist?
        events.sort(Comparator.comparing(Event::getTimeStart));
        return events;
    }

    private boolean findSlotInDay(List<Event> events, LocalDate currentDate, LocalTime availableStart, LocalTime availableEnd, int hoursToFind) {
        for (Event event : events) {
            if (isSlotAvailable(availableStart, event.getTimeStart(), hoursToFind)) {
                printSlot(currentDate, availableStart);
                return true;
            }
            availableStart = event.getTimeEnd(); // Move to the end of the current event
        }

        // Check after the last event
        if (isSlotAvailable(availableStart, availableEnd, hoursToFind)) {
            printSlot(currentDate, availableStart);
            return true;
        }

        return false;
    }

    private boolean isSlotAvailable(LocalTime start, LocalTime end, int hoursToFind) {
        return Duration.between(start, end).toHours() >= hoursToFind;
    }

    private void printSlot(LocalDate date, LocalTime startTime) {
        System.out.println("Available slot found: " + date + " from " + startTime);
    }
}