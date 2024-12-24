package bg.tu_varna.sit.service;

import bg.tu_varna.sit.model.Event;
import bg.tu_varna.sit.model.EventsWrapper;
import bg.tu_varna.sit.util.JAXBParser;
import jakarta.xml.bind.JAXBException;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

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

    public Optional<Event> findEventByDateAndTimeStartAndTimeEnd(String date, String timeStart, String timeEnd) {
        return this.eventsWrapper.getEvents().stream()
                .filter(e -> e.getDate().equals(date) &&
                        e.getTimeStart().equals(timeStart) &&
                        e.getTimeEnd().equals(timeEnd))
                .findFirst();
    }

    public Optional<Event> findEventByDateAndTimeStart(String date, String timeStart) {
        return this.eventsWrapper.getEvents().stream()
                .filter(e -> e.getDate().equals(date) &&
                        e.getTimeStart().equals(timeStart))
                .findFirst();
    }

    public void unbookEvent(Event event) {
        this.eventsWrapper.getEvents().remove(event);
    }

    public List<Event> getAgendaForDateSortedByTime(String date) {
        return eventsWrapper.getEvents().stream()
                .filter(event -> event.getDate().equals(date))
                .sorted(Comparator.comparing(Event::getTimeStart))
                .toList();
    }

    public List<Event> listAllEvents() {
        return this.eventsWrapper.getEvents();
    }

    public List<Event> findEvent(String searchTerm) {
        return this.eventsWrapper.getEvents().stream()
                .filter(e -> e.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        e.getDescription().contains(searchTerm))
                .collect(toList());
    }

    //todo ?
    public boolean isConflict(LocalDate date, LocalTime timeStart, LocalTime timeEnd) {
        // Loop through all events to find conflicts on the same date
        for (Event event : this.eventsWrapper.getEvents()) {
            // Check if the event is on the same date
            if (event.getDate().equals(date)) {
                LocalTime existingTimeStart = LocalTime.parse(event.getTimeStart());
                LocalTime existingTimeEnd = LocalTime.parse(event.getTimeEnd());

                // Check if the new event time range overlaps with an existing event
                if (timeStart.isBefore(existingTimeEnd) && timeEnd.isAfter(existingTimeStart)) {
                    return true; // Conflict found
                }
            }
        }
        return false; // No conflict
    }


}
