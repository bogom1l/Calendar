package bg.tu_varna.sit.calendar.command.impl.event;

import bg.tu_varna.sit.calendar.command.contract.Command;
import bg.tu_varna.sit.calendar.model.Event;
import bg.tu_varna.sit.calendar.service.EventService;
import bg.tu_varna.sit.calendar.util.InputUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class UnbookCommand implements Command {
    private final EventService eventService;

    public UnbookCommand(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute() {
        LocalDate date = InputUtils.readLocalDate("Date (YYYY-MM-DD): ");
        LocalTime timeStart = InputUtils.readLocalTime("Start Time (HH:MM): ");
        LocalTime timeEnd = InputUtils.readLocalTime("End Time (HH:MM): ");

        Optional<Event> eventToRemove = eventService.findEventByDateAndTimeStartAndTimeEnd(date, timeStart, timeEnd);

        if (eventToRemove.isPresent()) {
            eventService.unbookEvent(eventToRemove.get());
            System.out.println("Event unbooked successfully.");
        } else {
            System.out.println("Event not found.");
        }
    }
}