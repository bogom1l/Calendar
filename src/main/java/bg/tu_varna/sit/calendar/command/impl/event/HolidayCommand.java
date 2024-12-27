package bg.tu_varna.sit.calendar.command.impl.event;

import bg.tu_varna.sit.calendar.command.contract.Command;
import bg.tu_varna.sit.calendar.service.impl.EventServiceImpl;
import bg.tu_varna.sit.calendar.service.impl.HolidayServiceImpl;
import bg.tu_varna.sit.calendar.util.InputUtils;

import java.time.LocalDate;

public class HolidayCommand implements Command {
    private final EventServiceImpl eventService;
    private final HolidayServiceImpl holidayService;

    public HolidayCommand(EventServiceImpl eventService, HolidayServiceImpl holidayService) {
        this.eventService = eventService;
        this.holidayService = holidayService;
    }

    @Override
    public void execute() {
        LocalDate holidayDate = InputUtils.readLocalDate("Enter the date to mark as a holiday (yyyy-mm-dd): ");

        boolean success = holidayService.markDateAsHoliday(holidayDate, eventService.getEventsWrapper());

        if (success) {
            System.out.println("Date " + holidayDate + " has been successfully marked as a holiday.");
        } else {
            System.out.println("Failed to mark the date as a holiday. It might already be a holiday or invalid.");
        }
    }
}
