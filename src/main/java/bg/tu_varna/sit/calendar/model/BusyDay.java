package bg.tu_varna.sit.calendar.model;

import java.time.LocalDate;
import java.util.List;

public class BusyDay {
    private final LocalDate date;
    private final long totalMinutesBooked;
    private final List<Event> events;

    public BusyDay(LocalDate date, long totalMinutesBooked, List<Event> events) {
        this.date = date;
        this.totalMinutesBooked = totalMinutesBooked;
        this.events = events;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getTotalMinutesBooked() {
        return totalMinutesBooked;
    }

    public List<Event> getEvents() {
        return events;
    }
}