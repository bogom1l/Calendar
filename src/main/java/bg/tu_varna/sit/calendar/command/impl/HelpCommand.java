package bg.tu_varna.sit.calendar.command.impl;

import bg.tu_varna.sit.calendar.command.Command;

import java.util.List;

public class HelpCommand implements Command {
    private final List<String> commandDescriptions;

    public HelpCommand() {
        this.commandDescriptions = List.of(
                "open <file> - Open a calendar file",
                "save - Save the currently open calendar",
                "saveas <file> - Save As the currently open calendar",
                "close - Close the currently open calendar",
                "book <date> <starttime> <endtime> <title> <description> - Book a new event",
                "unbook <date> <starttime> <endtime> - Unbook an existing event",
                "listall - List all events in the calendar",
                "find <string> - Find events by name or description",
                "agenda <date> - List all events for a specific date",
                "change <date> <starttime> <option> <newvalue> - Modify an existing event",
                "holiday <date> - Mark a date as a holiday",
                "busydays <from> <to> - Display days with events",
                "findslot <fromdate> <hours> - Find available time slots",
                "findslotwith <fromdate> <hours> <calendar> - Find available time slots in current and other calendar",
                "merge <calendar> - Merge another calendar file",
                "help - Show this help menu",
                "exit - Exit the application"
        );
    }

    @Override
    public void execute() {
        System.out.println("Available commands:");
        for (String description : commandDescriptions) {
            System.out.println(description);
        }
    }
}