package bg.tu_varna.sit.service;

import bg.tu_varna.sit.model.EventsWrapper;
import bg.tu_varna.sit.model.Holiday;
import bg.tu_varna.sit.model.HolidaysWrapper;
import bg.tu_varna.sit.util.JAXBParser;
import jakarta.xml.bind.JAXBException;

import java.io.File;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class HolidayService {
    private final HolidaysWrapper holidaysWrapper = new HolidaysWrapper();
    private final String holidaysFile = "./holidays.xml";

    public HolidayService() {
        loadHolidays();
    }

    public boolean markDateAsHoliday(LocalDate date, EventsWrapper eventsWrapper) {
        if (date == null || isHoliday(date)) {
            return false; // Already a holiday or invalid input
        }

        // Ensure no events are scheduled on this date
        boolean hasEvents = eventsWrapper.getEvents().stream()
                .anyMatch(event -> event.getDate().equals(date));

        if (hasEvents) {
            System.out.println("Cannot mark this date as a holiday because events are already scheduled.");
            return false;
        }

        // Create a new Holiday object and add it to the holidaysWrapper
        holidaysWrapper.getHolidays().add(new Holiday(date));
        saveHolidays(); // Save the updated holidays list
        return true;
    }

    public boolean isHoliday(LocalDate date) {
        // Check if the date is already a holiday
        return holidaysWrapper.getHolidays().stream()
                .anyMatch(holiday -> holiday.getDate().equals(date));
    }

    public Set<Holiday> getHolidays() {
        return new HashSet<>(holidaysWrapper.getHolidays()); // Return a copy to ensure immutability
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
            return; // No holidays file, leave the holidaysWrapper empty
        }

        try {
            HolidaysWrapper loadedHolidaysWrapper = JAXBParser.loadHolidaysFromXML(file);
            holidaysWrapper.setHolidays(loadedHolidaysWrapper.getHolidays());
        } catch (JAXBException e) {
            System.err.println("Failed to load holidays: " + e.getMessage());
        }
    }
}
