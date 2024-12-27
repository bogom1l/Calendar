package bg.tu_varna.sit.calendar.command.impl;

import bg.tu_varna.sit.calendar.command.Command;
import bg.tu_varna.sit.calendar.service.EventService;
import bg.tu_varna.sit.calendar.util.InputUtils;

import java.io.File;

public class SaveAsCommand implements Command {
    private final EventService eventService;

    public SaveAsCommand(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute() {
        String newFileName = InputUtils.readString("Enter the new calendar file name to save as (e.g., 'new_calendar.xml'): ");

        if (!isValidFileName(newFileName)) {
            System.out.println("Invalid file format. Please provide a valid .xml file.");
            return;
        }

        File newFile = getFileFromCurrentDirectory(newFileName);
        saveCalendar(newFile);
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