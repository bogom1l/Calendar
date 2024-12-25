package bg.tu_varna.sit.input;

import bg.tu_varna.sit.commands.contracts.Command;
import bg.tu_varna.sit.commands.event.*;
import bg.tu_varna.sit.commands.eventswrapper.CloseCommand;
import bg.tu_varna.sit.commands.eventswrapper.OpenCommand;
import bg.tu_varna.sit.commands.eventswrapper.SaveAsCommand;
import bg.tu_varna.sit.commands.eventswrapper.SaveCommand;
import bg.tu_varna.sit.service.EventService;
import bg.tu_varna.sit.service.HolidayService;

import java.util.Scanner;

public class UserInputHandler {
    private final Scanner scanner;
    private final EventService eventService;
    private final HolidayService holidayService;

    public UserInputHandler() {
        this.scanner = new Scanner(System.in);
        this.eventService = new EventService();
        this.holidayService = new HolidayService();
    }

    public void start() {
        Command openCommand = new OpenCommand(eventService, scanner);
        Command saveCommand = new SaveCommand(eventService);
        Command saveAsCommand = new SaveAsCommand(eventService, scanner);
        Command closeCommand = new CloseCommand(eventService);
        Command bookEventCommand = new BookCommand(eventService, scanner, holidayService);
        Command unbookEventCommand = new UnbookCommand(eventService, scanner);
        Command listAllEventsCommand = new ListAllCommand(eventService);
        Command agendaEventCommand = new AgendaCommand(eventService, scanner);
        Command changeEventCommand = new ChangeCommand(eventService, scanner);
        Command findEventCommand = new FindCommand(eventService, scanner);
        Command holidayCommand = new HolidayCommand(eventService, holidayService);

        while (true) {
            printMenu();

            String choice = scanner.nextLine();

            // Allow open and exit without check
            if (!eventService.isCalendarOpen() && !choice.equals("open") && !choice.equals("exit")) {
                System.out.println("No calendar file is currently open. Please open a file first.");
                continue;
            }

            switch (choice) {
                case "open":
                    openCommand.execute();
                    break;
                case "save":
                    saveCommand.execute();
                    break;
                case "saveas":
                    saveAsCommand.execute();
                    break;
                case "close":
                    closeCommand.execute();
                    break;
                case "book":
                    bookEventCommand.execute();
                    break;
                case "unbook":
                    unbookEventCommand.execute();
                    break;
                case "listall":
                    listAllEventsCommand.execute();
                    break;
                case "find":
                    findEventCommand.execute();
                    break;
                case "agenda":
                    agendaEventCommand.execute();
                    break;
                case "change":
                    changeEventCommand.execute();
                    break;
                case "holiday":
                    holidayCommand.execute();
                    break;
                case "exit":
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\nPlease select an operation:");
        if (!eventService.isCalendarOpen()) {
            System.out.println("open - Open calendar");
            System.out.println("exit - Exit");
        } else {
            System.out.println("save - Save calendar");
            System.out.println("saveas - Save calendar as");
            System.out.println("close - Close calendar");
            System.out.println("book - Book event");
            System.out.println("unbook - Unbook event");
            System.out.println("listall - List all events");
            System.out.println("find - Find event");
            System.out.println("agenda - List all events for a date");
            System.out.println("change - Change event");
            System.out.println("holiday - Mark date as holiday");
            System.out.println("exit - Exit");
        }
    }
}