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
        String fileName = promptForFileName();

        if (!isValidFileName(fileName)) {
            System.out.println("Invalid file format. Please provide a valid .xml file.");
            return;
        }

        File file = getFileFromCurrentDirectory(fileName);
        openCalendar(file);
    }

    private String promptForFileName() {
        System.out.print("Enter the calendar file name to open (e.g., 'calendar.xml'): ");
        return scanner.nextLine();
    }

    private boolean isValidFileName(String fileName){
        return fileName.endsWith(".xml");
    }

    private File getFileFromCurrentDirectory(String fileName) {
        String currentDirectory = System.getProperty("user.dir");
        return new File(currentDirectory, fileName);
    }

    private void openCalendar(File file) {
        if (eventService.open(file)) {
            System.out.println("Calendar opened successfully.");
        } else {
            System.out.println("Failed to open calendar. Make sure the file exists.");
        }
    }

}
