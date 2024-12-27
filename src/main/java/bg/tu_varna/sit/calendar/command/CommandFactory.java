package bg.tu_varna.sit.calendar.command;

import bg.tu_varna.sit.calendar.command.impl.*;
import bg.tu_varna.sit.calendar.service.EventService;
import bg.tu_varna.sit.calendar.service.HolidayService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandFactory {
    private final Map<CommandType, Command> commands = new HashMap<>();

    public CommandFactory(EventService eventService, HolidayService holidayService, Scanner scanner) {
        commands.put(CommandType.OPEN, new OpenCommand(eventService));
        commands.put(CommandType.CLOSE, new CloseCommand(eventService));
        commands.put(CommandType.SAVE, new SaveCommand(eventService));
        commands.put(CommandType.SAVEAS, new SaveAsCommand(eventService));
        commands.put(CommandType.HELP, new HelpCommand());
        commands.put(CommandType.EXIT, new ExitCommand(scanner));
        commands.put(CommandType.BOOK, new BookCommand(eventService, holidayService));
        commands.put(CommandType.UNBOOK, new UnbookCommand(eventService));
        commands.put(CommandType.LISTALL, new ListAllCommand(eventService));
        commands.put(CommandType.AGENDA, new AgendaCommand(eventService));
        commands.put(CommandType.CHANGE, new ChangeCommand(eventService));
        commands.put(CommandType.FIND, new FindCommand(eventService));
        commands.put(CommandType.HOLIDAY, new HolidayCommand(eventService, holidayService));
        commands.put(CommandType.BUSYDAYS, new BusyDaysCommand(eventService));
        commands.put(CommandType.FINDSLOT, new FindSlotCommand(eventService));
        commands.put(CommandType.FINDSLOTWITH, new FindSlotWithCommand(eventService));
        commands.put(CommandType.MERGE, new MergeCommand(eventService));
    }

    public Command getCommand(CommandType commandType) {
        return commands.getOrDefault(commandType, () -> System.out.println("Invalid Command"));
    }
}
