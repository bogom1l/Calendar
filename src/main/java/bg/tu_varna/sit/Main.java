package bg.tu_varna.sit;

import bg.tu_varna.sit.calendar.command.CommandExecutor;
import bg.tu_varna.sit.calendar.command.CommandFactory;
import bg.tu_varna.sit.calendar.model.AppState;
import bg.tu_varna.sit.calendar.ui.ApplicationManager;
import bg.tu_varna.sit.calendar.ui.UserInputHandler;
import bg.tu_varna.sit.calendar.service.impl.EventServiceImpl;
import bg.tu_varna.sit.calendar.service.impl.HolidayServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        AppState appState = new AppState();

        EventServiceImpl eventService = new EventServiceImpl(appState);
        HolidayServiceImpl holidayService = new HolidayServiceImpl();

        Scanner scanner = new Scanner(System.in);

        UserInputHandler userInputHandler = new UserInputHandler(scanner);

        CommandFactory commandFactory = new CommandFactory(eventService, holidayService, scanner);

        CommandExecutor commandExecutor = new CommandExecutor(commandFactory);

        ApplicationManager applicationManager = new ApplicationManager(userInputHandler, commandExecutor, appState);
        applicationManager.start();
    }
}