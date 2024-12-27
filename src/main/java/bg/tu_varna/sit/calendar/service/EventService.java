package bg.tu_varna.sit.calendar.service;

import bg.tu_varna.sit.calendar.model.Event;
import bg.tu_varna.sit.calendar.model.EventsWrapper;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EventService {
    EventsWrapper getEventsWrapper();

    boolean isCalendarOpen();

    boolean open(File xmlFile);

    void close();

    boolean save();

    boolean saveAs(File newFile);

    boolean bookEvent(Event event);

    void unbookEvent(Event event);

    Optional<Event> findEventByDateAndTimeStartAndTimeEnd(LocalDate date, LocalTime timeStart, LocalTime timeEnd);

    Optional<Event> findEventByDateAndTimeStart(LocalDate date, LocalTime timeStart);

    List<Event> getAgendaForDateSortedByTime(LocalDate date);

    List<Event> listAllEvents();

    List<Event> getAllEvents();

    List<Event> findEventsByTitleOrDescription(String searchTerm);

    List<Map.Entry<LocalDate, Map.Entry<Long, List<Event>>>> getBusyDaysWithEventsInRange(LocalDate from, LocalDate to);

    List<Event> getEventsByDate(LocalDate date);

    List<Event> getEventsByDate(LocalDate date, EventsWrapper wrapper);

    boolean updateEventDate(Event event, LocalDate newDate);

    boolean updateEventStartTime(Event event, LocalTime newTimeStart);

    boolean updateEventEndTime(Event event, LocalTime newTimeEnd);

    boolean updateEventTitle(Event event, String newTitle);

    boolean updateEventDescription(Event event, String newDescription);

    boolean isTimeRangeAvailableForDate(LocalDate date, LocalTime newTimeStart, LocalTime newTimeEnd);

    boolean isTimeStartAvailable(LocalDate date, LocalTime newTimeStart);

    boolean isTimeEndAvailable(LocalDate date, LocalTime newTimeEnd);
}
