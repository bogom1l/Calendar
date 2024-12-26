//package bg.tu_varna.sit.commands;
//
//import bg.tu_varna.sit.commands.contracts.Command;
//import bg.tu_varna.sit.commands.event.BookCommand;
//import bg.tu_varna.sit.commands.event.HelpCommand;
//import bg.tu_varna.sit.commands.event.MergeCommand;
//import bg.tu_varna.sit.commands.eventswrapper.CloseCommand;
//import bg.tu_varna.sit.commands.eventswrapper.OpenCommand;
//import bg.tu_varna.sit.commands.eventswrapper.SaveAsCommand;
//import bg.tu_varna.sit.commands.eventswrapper.SaveCommand;
//import bg.tu_varna.sit.service.EventService;
//import bg.tu_varna.sit.service.HolidayService;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//
//public class CommandRegistry {
//    private final Map<String, Command> commands = new HashMap<>();
//
//    public void registerCommand(String name, Command command) {
//        commands.put(name, command);
//    }
//
//    public Command getCommand(String name) {
//        return commands.get(name);
//    }
//
//    public void listCommands() {
//        System.out.println("Available commands:");
//        for (String name : commands.keySet()) {
//            System.out.println("- " + name);
//        }
//    }
//
//    private void initialize(EventService eventService, HolidayService holidayService, Scanner scanner) {
//
//        // Register commands
//        this.commands.put("open", new OpenCommand(eventService, scanner));
//        this.commands.put("close", new CloseCommand(eventService));
//        this.commands.put("save", new SaveCommand(eventService));
//        this.commands.put("saveas", new SaveAsCommand(eventService, scanner));
//        this.commands.put("help", new HelpCommand());
//        this.commands.put("exit", new ExitCommand());
//        this.commands.put("book", new BookCommand(eventService, scanner, holidayService));
//        this.commands.put("merge", new MergeCommand(eventService));
//        this.commands.put("help", new HelpCommand());
//        this.commands.put("help", new HelpCommand());
//        this.commands.put("help", new HelpCommand());
//        this.commands.put("help", new HelpCommand());
//        this.commands.put("help", new HelpCommand());
//        this.commands.put("help", new HelpCommand());
//        this.commands.put("help", new HelpCommand());
//        this.commands.put("help", new HelpCommand());
//
//
//    }
//}
