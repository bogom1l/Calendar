package bg.tu_varna.sit.calendar.service.impl;

import bg.tu_varna.sit.calendar.model.AppState;
import bg.tu_varna.sit.calendar.model.BusyDay;
import bg.tu_varna.sit.calendar.model.Event;
import bg.tu_varna.sit.calendar.model.EventsWrapper;
import bg.tu_varna.sit.calendar.service.EventService;
import bg.tu_varna.sit.calendar.util.JAXBParser;
import jakarta.xml.bind.JAXBException;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class EventServiceImpl implements EventService {
    private final AppState appState;
    private EventsWrapper eventsWrapper;
    private File currentFile;

    public EventServiceImpl(AppState appState) {
        this.appState = appState;
        this.eventsWrapper = new EventsWrapper();
    }

    @Override
    public EventsWrapper getEventsWrapper() {
        return this.eventsWrapper;
    }

    @Override
    public boolean isCalendarOpen() {
        return appState.isCalendarOpen();
    }

    @Override
    public boolean open(File xmlFile) {
        if (xmlFile == null || !xmlFile.exists()) {
            return false;
        }

        try {
            this.eventsWrapper = JAXBParser.loadEventsFromXML(xmlFile);
            this.currentFile = xmlFile;
            appState.setCalendarState(true);
            return true;
        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void close() {
        this.eventsWrapper = new EventsWrapper();
        this.currentFile = null;
        appState.setCalendarState(false);
    }

    @Override
    public boolean save() {
        try {
            JAXBParser.saveEventsToXML(this.eventsWrapper, this.currentFile);
            return true;
        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveAs(File newFile) {
        try {
            JAXBParser.saveEventsToXML(this.eventsWrapper, newFile);
            this.currentFile = newFile;
            return true;
        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean bookEvent(Event event) {
        return this.eventsWrapper.getEvents().add(event);
    }

    @Override
    public Optional<Event> findEventByDateAndTimeStartAndTimeEnd(LocalDate date, LocalTime timeStart, LocalTime timeEnd) {
        return this.eventsWrapper.getEvents().stream()
                .filter(e -> e.getDate().equals(date) &&
                        e.getTimeStart().equals(timeStart) &&
                        e.getTimeEnd().equals(timeEnd))
                .findFirst();
    }

    @Override
    public Optional<Event> findEventByDateAndTimeStart(LocalDate date, LocalTime timeStart) {
        return this.eventsWrapper.getEvents().stream()
                .filter(e -> e.getDate().equals(date) &&
                        e.getTimeStart().equals(timeStart))
                .findFirst();
    }

    @Override
    public void unbookEvent(Event event) {
        this.eventsWrapper.getEvents().remove(event);
    }

    @Override
    public List<Event> getAgendaForDateSortedByTime(LocalDate date) {
        return eventsWrapper.getEvents().stream()
                .filter(event -> event.getDate().equals(date))
                .sorted(Comparator.comparing(Event::getTimeStart))
                .toList();
    }

    @Override
    public List<Event> listAllEvents() {
        List<Event> events = this.eventsWrapper.getEvents();

        events.sort(Comparator.comparing(Event::getDate)
                .thenComparing(Event::getTimeStart));

        return events;
    }

    @Override
    public List<Event> getAllEvents() {
        return this.eventsWrapper.getEvents();
    }

    @Override
    public List<Event> findEventsByTitleOrDescription(String searchTerm) {
        return this.eventsWrapper.getEvents().stream()
                .filter(event -> event.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        event.getDescription().toLowerCase().contains(searchTerm.toLowerCase()))
                .toList();
    }

    @Override
    public boolean updateEventDate(Event event, LocalDate newDate) {
        if (newDate != null) {
            event.setDate(newDate);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateEventStartTime(Event event, LocalTime newTimeStart) {
        if (newTimeStart != null) {
            event.setTimeStart(newTimeStart);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateEventEndTime(Event event, LocalTime newTimeEnd) {
        if (newTimeEnd != null) {
            event.setTimeEnd(newTimeEnd);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateEventTitle(Event event, String newTitle) {
        if (newTitle != null && !newTitle.trim().isEmpty()) {
            event.setTitle(newTitle);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateEventDescription(Event event, String newDescription) {
        if (newDescription != null && !newDescription.trim().isEmpty()) {
            event.setDescription(newDescription);
            return true;
        }
        return false;
    }

    @Override
    public boolean isTimeRangeAvailableForDate(LocalDate date, LocalTime newTimeStart, LocalTime newTimeEnd) {
        return this.eventsWrapper.getEvents().stream()
                .filter(event -> event.getDate().equals(date))
                .noneMatch(event ->
                        (newTimeStart.isBefore(event.getTimeEnd()) && newTimeStart.isAfter(event.getTimeStart())) ||
                                (newTimeEnd.isBefore(event.getTimeEnd()) && newTimeEnd.isAfter(event.getTimeStart())) ||
                                (newTimeStart.isBefore(event.getTimeStart()) && newTimeEnd.isAfter(event.getTimeEnd()))
                );
    }

    @Override
    public boolean isTimeStartAvailable(LocalDate date, LocalTime newTimeStart) {
        return this.eventsWrapper.getEvents().stream()
                .filter(event -> event.getDate().equals(date))
                .noneMatch(event -> newTimeStart.isBefore(event.getTimeEnd()) && newTimeStart.isAfter(event.getTimeStart()));
    }

    @Override
    public boolean isTimeEndAvailable(LocalDate date, LocalTime newTimeEnd) {
        return this.eventsWrapper.getEvents().stream()
                .filter(event -> event.getDate().equals(date))
                .noneMatch(event -> newTimeEnd.isBefore(event.getTimeEnd()) && newTimeEnd.isAfter(event.getTimeStart()));
    }

    @Override
    public List<BusyDay> getBusyDaysWithEventsInRange(LocalDate from, LocalDate to) {
        List<BusyDay> busyDays = new ArrayList<>();

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
                busyDays.add(new BusyDay(currentDate, bookedMinutes, eventsForDay));
            }
            currentDate = currentDate.plusDays(1);
        }

        // Sort by booked minutes in descending order
        busyDays.sort(Comparator.comparingLong(BusyDay::getTotalMinutesBooked).reversed());

        return busyDays;
    }

    @Override
    public List<Event> getEventsByDate(LocalDate date) {
        return eventsWrapper.getEvents()
                .stream()
                .filter(event -> event.getDate().equals(date))
                .toList();
    }

    @Override
    public List<Event> getEventsByDate(LocalDate date, EventsWrapper wrapper) {
        return wrapper.getEvents()
                .stream()
                .filter(event -> event.getDate().equals(date))
                .toList();
    }
}