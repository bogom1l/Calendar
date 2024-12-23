package bg.tu_varna.sit.commands.eventswrapper;

import bg.tu_varna.sit.commands.contracts.Command;
import bg.tu_varna.sit.service.EventService;
import java.io.File;
import java.util.Scanner;

public class OpenCommand implements Command {
    private EventService eventService;
    private Scanner scanner;

    public OpenCommand(EventService eventService, Scanner scanner) {
        this.eventService = eventService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Enter the calendar file name to open (e.g., 'calendar.xml'): ");
        String fileName = scanner.nextLine();

        String currentDirectory = System.getProperty("user.dir");
        File file = new File(currentDirectory, fileName);

        if (eventService.open(file)) {
            System.out.println("Calendar opened successfully.");
        } else {
            System.out.println("Failed to open calendar. Make sure the file exists.");
        }
    }

}
