package bg.tu_varna.sit.commands.eventswrapper;

import bg.tu_varna.sit.commands.contracts.Command;
import bg.tu_varna.sit.service.EventService;

import java.io.File;
import java.util.Scanner;

public class SaveAsCommand implements Command {
    private EventService eventService;
    private Scanner scanner;

    public SaveAsCommand(EventService eventService, Scanner scanner) {
        this.eventService = eventService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Enter the new calendar file name to save as (e.g., 'new_calendar.xml'): ");
        String newFileName = scanner.nextLine();

        String currentDirectory = System.getProperty("user.dir");
        File newFile = new File(currentDirectory, newFileName);

        if (eventService.saveAs(newFile)) {
            System.out.println("Calendar saved as " + newFile.getAbsolutePath());
        } else {
            System.out.println("Failed to save the calendar.");
        }
    }
}