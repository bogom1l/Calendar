package bg.tu_varna.sit.calendar.command.impl;

import bg.tu_varna.sit.calendar.command.Command;
import bg.tu_varna.sit.calendar.model.Holiday;
import bg.tu_varna.sit.calendar.service.EventService;
import bg.tu_varna.sit.calendar.service.HolidayService;
import bg.tu_varna.sit.calendar.util.InputUtils;

import java.time.LocalDate;
import java.util.Set;

public class HolidayCommand implements Command {
    private final EventService eventService;
    private final HolidayService holidayService;

    public HolidayCommand(EventService eventService, HolidayService holidayService) {
        this.eventService = eventService;
        this.holidayService = holidayService;
    }

    @Override
    public void execute() {
        displayCurrentHolidays();

        LocalDate holidayDate = InputUtils.readLocalDate("Enter the date to mark as a holiday (yyyy-mm-dd): ");

        boolean success = holidayService.markDateAsHoliday(holidayDate, eventService.getEventsWrapper());

        if (!success) {
            System.out.println("Failed to mark the date as a holiday. It might already be a holiday or invalid.");
            return;
        }

        System.out.println("Date " + holidayDate + " has been successfully marked as a holiday.");
    }

    private void displayCurrentHolidays() {
        Set<Holiday> holidays = holidayService.getHolidays();
        if (holidays.isEmpty()) {
            System.out.println("There are no holidays currently set.");
            return;
        }

        System.out.println("Current holidays:");
        for (Holiday holiday : holidays) {
            System.out.println("- " + holiday.getDate());
        }
    }
}
