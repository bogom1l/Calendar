package bg.tu_varna.sit;

import bg.tu_varna.sit.commands.CommandExecutor;
import bg.tu_varna.sit.commands.CommandFactory;
import bg.tu_varna.sit.input.AppState;
import bg.tu_varna.sit.input.ApplicationManager;
import bg.tu_varna.sit.input.UserInputHandler;
import bg.tu_varna.sit.service.EventService;
import bg.tu_varna.sit.service.HolidayService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        AppState appState = new AppState();

        EventService eventService = new EventService(appState);
        HolidayService holidayService = new HolidayService();

        Scanner scanner = new Scanner(System.in);

        UserInputHandler userInputHandler = new UserInputHandler(scanner);

        CommandFactory commandFactory = new CommandFactory(eventService, holidayService, scanner);

        CommandExecutor commandExecutor = new CommandExecutor(commandFactory);

        ApplicationManager applicationManager = new ApplicationManager(userInputHandler, commandExecutor, appState);
        applicationManager.start();
    }
}