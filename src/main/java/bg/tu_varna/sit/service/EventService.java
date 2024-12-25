package bg.tu_varna.sit.service;

import bg.tu_varna.sit.model.Event;
import bg.tu_varna.sit.model.EventsWrapper;
import bg.tu_varna.sit.util.JAXBParser;
import jakarta.xml.bind.JAXBException;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class EventService {
    private EventsWrapper eventsWrapper;
    private File currentFile;

    public EventService() {
        this.eventsWrapper = new EventsWrapper();
    }

    public boolean isCalendarOpen() {
        return currentFile != null;
    }

    public boolean open(File xmlFile) {
        if (xmlFile == null || !xmlFile.exists()) {
            return false;
        }

        try {
            this.eventsWrapper = JAXBParser.loadEventsFromXML(xmlFile);
            this.currentFile = xmlFile;
            return true;
        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        this.eventsWrapper = new EventsWrapper();
        this.currentFile = null;
    }

    public boolean save() {
        try {
            JAXBParser.saveEventsToXML(this.eventsWrapper, this.currentFile);
            return true;
        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveAs(File newFile) {
        try {
            JAXBParser.saveEventsToXML(this.eventsWrapper, newFile);
            this.currentFile = newFile; // Update the current file to the new one
            return true;
        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean bookEvent(Event event) {
        return this.eventsWrapper.getEvents().add(event);
    }

    public Optional<Event> findEventByDateAndTimeStartAndTimeEnd(LocalDate date, LocalTime timeStart, LocalTime timeEnd) {
        return this.eventsWrapper.getEvents().stream()
                .filter(e -> e.getDate().equals(date) &&
                        e.getTimeStart().equals(timeStart) &&
                        e.getTimeEnd().equals(timeEnd))
                .findFirst();
    }

    public Optional<Event> findEventByDateAndTimeStart(LocalDate date, LocalTime timeStart) {
        return this.eventsWrapper.getEvents().stream()
                .filter(e -> e.getDate().equals(date) &&
                        e.getTimeStart().equals(timeStart))
                .findFirst();
    }

    public void unbookEvent(Event event) {
        this.eventsWrapper.getEvents().remove(event);
    }

    public List<Event> getAgendaForDateSortedByTime(LocalDate date) {
        return eventsWrapper.getEvents().stream()
                .filter(event -> event.getDate().equals(date))
                .sorted(Comparator.comparing(Event::getTimeStart))
                .toList();
    }

    //todo: sort by date then by hour
    public List<Event> listAllEvents() {
        return this.eventsWrapper.getEvents();
    }

    public List<Event> findEventsByTitleOrDescription(String searchTerm) {
        return this.eventsWrapper.getEvents().stream()
                .filter(event -> event.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        event.getDescription().toLowerCase().contains(searchTerm.toLowerCase()))
                .toList();
    }

    public boolean updateEventDate(Event event, LocalDate newDate) {
        if (newDate != null) {
            event.setDate(newDate);
            return true;
        }
        return false;
    }

    // Method to update the event's start time
    public boolean updateEventStartTime(Event event, LocalTime newTimeStart) {
        if (newTimeStart != null) {
            event.setTimeStart(newTimeStart);
            return true;
        }
        return false;
    }

    // Method to update the event's end time
    public boolean updateEventEndTime(Event event, LocalTime newTimeEnd) {
        if (newTimeEnd != null) {
            event.setTimeEnd(newTimeEnd);
            return true;
        }
        return false;
    }

    // Method to update the event's title
    public boolean updateEventTitle(Event event, String newTitle) {
        if (newTitle != null && !newTitle.trim().isEmpty()) {
            event.setTitle(newTitle);
            return true;
        }
        return false;
    }

    // Method to update the event's description
    public boolean updateEventDescription(Event event, String newDescription) {
        if (newDescription != null && !newDescription.trim().isEmpty()) {
            event.setDescription(newDescription);
            return true;
        }
        return false;
    }

    public boolean isTimeRangeAvailableForDate(LocalDate date, LocalTime newTimeStart, LocalTime newTimeEnd) {
        return this.eventsWrapper.getEvents().stream()
                .filter(event -> event.getDate().equals(date))  // Only check events for the same date
                .noneMatch(event ->
                        (newTimeStart.isBefore(event.getTimeEnd()) && newTimeStart.isAfter(event.getTimeStart())) ||  // New start time overlaps with an event
                                (newTimeEnd.isBefore(event.getTimeEnd()) && newTimeEnd.isAfter(event.getTimeStart())) ||    // New end time overlaps with an event
                                (newTimeStart.isBefore(event.getTimeStart()) && newTimeEnd.isAfter(event.getTimeEnd()))    // New range fully overlaps with an event
                );
    }

    public boolean isTimeStartAvailable(LocalDate date, LocalTime newTimeStart) {
        return this.eventsWrapper.getEvents().stream()
                .filter(event -> event.getDate().equals(date))  // Only check events for the same date
                .noneMatch(event -> newTimeStart.isBefore(event.getTimeEnd()) && newTimeStart.isAfter(event.getTimeStart()));
    }

    public boolean isTimeEndAvailable(LocalDate date, LocalTime newTimeEnd) {
        return this.eventsWrapper.getEvents().stream()
                .filter(event -> event.getDate().equals(date))  // Only check events for the same date
                .noneMatch(event -> newTimeEnd.isBefore(event.getTimeEnd()) && newTimeEnd.isAfter(event.getTimeStart()));
    }

    public EventsWrapper getEventsWrapper() {
        return this.eventsWrapper;
    }

    private long getBookedMinutes(LocalDate date) {
        return eventsWrapper.getEvents().stream()
                .filter(event -> event.getDate().equals(date)) // Filter events by the date
                .mapToLong(event -> Duration.between(event.getTimeStart(), event.getTimeEnd()).toMinutes()) // Calculate the minutes for each event
                .sum();
    }

    //todo make this method more clean.
    // Method to get all the busy days within a range, including event details
    public List<Map.Entry<LocalDate, Map.Entry<Long, List<Event>>>> getBusyDaysWithEventsInRange(LocalDate from, LocalDate to) {
        Map<LocalDate, Map.Entry<Long, List<Event>>> bookedMinutesMap = new HashMap<>();

        // Iterate through all dates in the range and calculate booked minutes and events
        LocalDate currentDate = from;
        while (!currentDate.isAfter(to)) {
            LocalDate finalCurrentDate = currentDate;
            List<Event> eventsForDay = eventsWrapper.getEvents().stream()
                    .filter(event -> event.getDate().equals(finalCurrentDate)) // Filter events for this date
                    .toList();

            long bookedMinutes = eventsForDay.stream()
                    .mapToLong(event -> Duration.between(event.getTimeStart(), event.getTimeEnd()).toMinutes()) // Calculate the total minutes
                    .sum();

            if (bookedMinutes > 0) {
                bookedMinutesMap.put(currentDate, new AbstractMap.SimpleEntry<>(bookedMinutes, eventsForDay));
            }
            currentDate = currentDate.plusDays(1);
        }

        // Sort by booked minutes in descending order
        return bookedMinutesMap.entrySet().stream()
                .sorted((entry1, entry2) -> Long.compare(entry2.getValue().getKey(), entry1.getValue().getKey()))
                .toList();
    }

    public List<Event> getEventsByDate(LocalDate date) {
        return eventsWrapper.getEvents()
                .stream()
                .filter(event -> event.getDate().equals(date))
                .toList();
    }

    public List<Event> getEventsByDate(LocalDate date, EventsWrapper wrapper) {
        return wrapper.getEvents()
                .stream()
                .filter(event -> event.getDate().equals(date))
                .toList();
    }



}
