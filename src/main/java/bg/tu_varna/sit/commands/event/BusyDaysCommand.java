package bg.tu_varna.sit.commands.event;

import bg.tu_varna.sit.commands.contracts.Command;
import bg.tu_varna.sit.service.EventService;
import bg.tu_varna.sit.util.InputUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class BusyDaysCommand implements Command {
    private final EventService eventService;

    public BusyDaysCommand(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute() {
        LocalDate from = InputUtils.readLocalDate("Enter the start date (yyyy-mm-dd): ");
        LocalDate to = InputUtils.readLocalDate("Enter the end date (yyyy-mm-dd): ");

        List<Map.Entry<LocalDate, Long>> busyDays = eventService.getBusyDaysInRange(from, to);

        if (busyDays.isEmpty()) {
            System.out.println("No busy days in the specified range.");
        } else {
            System.out.println("Busy days sorted by booked minutes:");
            for (Map.Entry<LocalDate, Long> entry : busyDays) {
                System.out.println(entry.getKey() + ": " + entry.getValue() + " minutes booked");
            }

            

        }
    }
}