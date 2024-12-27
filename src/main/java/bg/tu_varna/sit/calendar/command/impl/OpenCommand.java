package bg.tu_varna.sit.calendar.command.impl;

import bg.tu_varna.sit.calendar.command.Command;
import bg.tu_varna.sit.calendar.service.EventService;
import bg.tu_varna.sit.calendar.util.FileUtil;
import bg.tu_varna.sit.calendar.util.InputUtils;

import java.io.File;

public class OpenCommand implements Command {
    private final EventService eventService;

    public OpenCommand(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void execute() {
        if (isCalendarAlreadyOpened()) {
            System.out.println("A calendar is already open. Please close the current one before opening a new one.");
            return;
        }

        FileUtil.printAllXmlFiles();
        String fileName = InputUtils.readString("Enter the calendar file name to open (e.g., 'calendar.xml'): ");

        if (!isValidFileName(fileName)) {
            System.out.println("Invalid file format. Please provide a valid .xml file.");
            return;
        }

        File file = getFileFromCurrentDirectory(fileName);
        openCalendar(file);
    }

    private boolean isCalendarAlreadyOpened() {
        return eventService.isCalendarOpen();
    }

    private boolean isValidFileName(String fileName) {
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
