package bg.tu_varna.sit.calendar.service.impl;

import bg.tu_varna.sit.calendar.model.EventsWrapper;
import bg.tu_varna.sit.calendar.model.Holiday;
import bg.tu_varna.sit.calendar.model.HolidaysWrapper;
import bg.tu_varna.sit.calendar.service.HolidayService;
import bg.tu_varna.sit.calendar.util.JAXBParser;
import jakarta.xml.bind.JAXBException;

import java.io.File;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class HolidayServiceImpl implements HolidayService {
    private final HolidaysWrapper holidaysWrapper = new HolidaysWrapper();
    private final String holidaysFile = "./holidays.xml";

    public HolidayServiceImpl() {
        loadHolidays();
    }

    @Override
    public boolean markDateAsHoliday(LocalDate date, EventsWrapper eventsWrapper) {
        if (date == null || isHoliday(date)) {
            return false;
        }

        boolean hasEvents = eventsWrapper.getEvents().stream()
                .anyMatch(event -> event.getDate().equals(date));

        if (hasEvents) {
            System.out.println("Cannot mark this date as a holiday because events are already scheduled.");
            return false;
        }

        holidaysWrapper.getHolidays().add(new Holiday(date));
        saveHolidays();
        return true;
    }

    @Override
    public boolean isHoliday(LocalDate date) {
        return holidaysWrapper.getHolidays().stream()
                .anyMatch(holiday -> holiday.getDate().equals(date));
    }

    @Override
    public Set<Holiday> getHolidays() {
        return new HashSet<>(holidaysWrapper.getHolidays()); // Returns a copy to ensure immutability
    }

    private void saveHolidays() {
        try {
            JAXBParser.saveHolidaysToXML(holidaysWrapper, new File(holidaysFile));
        } catch (JAXBException e) {
            System.err.println("Failed to save holidays: " + e.getMessage());
        }
    }

    private void loadHolidays() {
        File file = new File(holidaysFile);
        if (!file.exists()) {
            return;
        }

        try {
            HolidaysWrapper loadedHolidaysWrapper = JAXBParser.loadHolidaysFromXML(file);
            holidaysWrapper.setHolidays(loadedHolidaysWrapper.getHolidays());
        } catch (JAXBException e) {
            System.err.println("Failed to load holidays: " + e.getMessage());
        }
    }
}
