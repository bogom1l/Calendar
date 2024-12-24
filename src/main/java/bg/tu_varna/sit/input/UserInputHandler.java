package bg.tu_varna.sit.input;

import bg.tu_varna.sit.commands.contracts.Command;
import bg.tu_varna.sit.commands.event.BookCommand;
import bg.tu_varna.sit.commands.event.FindCommand;
import bg.tu_varna.sit.commands.event.ListAllCommand;
import bg.tu_varna.sit.commands.event.UnbookCommand;
import bg.tu_varna.sit.commands.eventswrapper.CloseCommand;
import bg.tu_varna.sit.commands.eventswrapper.OpenCommand;
import bg.tu_varna.sit.commands.eventswrapper.SaveAsCommand;
import bg.tu_varna.sit.commands.eventswrapper.SaveCommand;
import bg.tu_varna.sit.service.EventService;

import java.util.Scanner;

public class UserInputHandler {
    private final Scanner scanner;
    private final EventService eventService;

    public UserInputHandler() {
        this.scanner = new Scanner(System.in);
        this.eventService = new EventService();
    }

    public void start() {
        Command openCommand = new OpenCommand(eventService, scanner);
        Command saveCommand = new SaveCommand(eventService);
        Command saveAsCommand = new SaveAsCommand(eventService, scanner);
        Command closeCommand = new CloseCommand(eventService);
        Command bookEventCommand = new BookCommand(eventService, scanner);
        Command unbookEventCommand = new UnbookCommand(eventService, scanner);
        Command listAllEventsCommand = new ListAllCommand(eventService);
        Command findEventCommand = new FindCommand(eventService, scanner);

        while (true) {
            printMenu();

            String choice = scanner.nextLine();

            // Allow open and exit without check
            if (!eventService.isCalendarOpen() && !choice.equals("1") && !choice.equals("9")) {
                System.out.println("No calendar file is currently open. Please open a file first.");
                continue;
            }

            switch (choice) {
                case "1":
                    openCommand.execute();
                    break;
                case "2":
                    saveCommand.execute();
                    break;
                case "3":
                    saveAsCommand.execute();
                    break;
                case "4":
                    closeCommand.execute();
                    break;
                case "5":
                    bookEventCommand.execute();
                    break;
                case "6":
                    unbookEventCommand.execute();
                    break;
                case "7":
                    listAllEventsCommand.execute();
                    break;
                case "8":
                    findEventCommand.execute();
                    break;
                case "9":
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
            System.out.println("1. Open calendar");
            System.out.println("9. Exit");
        } else {
            System.out.println("2. Save calendar");
            System.out.println("3. Save calendar as");
            System.out.println("4. Close calendar");
            System.out.println("5. Book event");
            System.out.println("6. Unbook event");
            System.out.println("7. List all events");
            System.out.println("8. Find event");
            System.out.println("9. Exit");
        }
    }
}