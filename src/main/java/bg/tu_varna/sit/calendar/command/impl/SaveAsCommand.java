package bg.tu_varna.sit.calendar.command.impl;

import bg.tu_varna.sit.calendar.command.Command;
import bg.tu_varna.sit.calendar.service.EventService;

import java.io.File;
import java.util.Scanner;

public class SaveAsCommand implements Command {
    private final EventService eventService;
    private final Scanner scanner;

    public SaveAsCommand(EventService eventService, Scanner scanner) {
        this.eventService = eventService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        String newFileName = promptForFileName();

        if (!isValidFileName(newFileName)) {
            System.out.println("Invalid file format. Please provide a valid .xml file.");
            return;
        }

        File newFile = getFileFromCurrentDirectory(newFileName);
        saveCalendar(newFile);
    }

    private String promptForFileName() {
        System.out.print("Enter the new calendar file name to save as (e.g., 'new_calendar.xml'): ");
        return scanner.nextLine();
    }

    private boolean isValidFileName(String fileName) {
        return fileName.endsWith(".xml");
    }

    private File getFileFromCurrentDirectory(String fileName) {
        String currentDirectory = System.getProperty("user.dir");
        return new File(currentDirectory, fileName);
    }

    private void saveCalendar(File file) {
        if (eventService.saveAs(file)) {
            System.out.println("Calendar saved as " + file.getAbsolutePath());
        } else {
            System.out.println("Failed to save the calendar.");
        }
    }

}