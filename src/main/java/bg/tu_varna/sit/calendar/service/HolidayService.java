package bg.tu_varna.sit.calendar.service;

import bg.tu_varna.sit.calendar.model.EventsWrapper;
import bg.tu_varna.sit.calendar.model.Holiday;

import java.time.LocalDate;
import java.util.Set;

public interface HolidayService {
    Set<Holiday> getHolidays();

    boolean markDateAsHoliday(LocalDate date, EventsWrapper eventsWrapper);

    boolean isHoliday(LocalDate date);
}