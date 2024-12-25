package bg.tu_varna.sit.commands.event;

import bg.tu_varna.sit.commands.contracts.Command;
import bg.tu_varna.sit.service.EventService;
import bg.tu_varna.sit.service.HolidayService;
import bg.tu_varna.sit.util.InputUtils;

import java.time.LocalDate;

public class HolidayCommand implements Command {
    private final EventService eventService;
    private final HolidayService holidayService;

    public HolidayCommand(EventService eventService, HolidayService holidayService) {
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
