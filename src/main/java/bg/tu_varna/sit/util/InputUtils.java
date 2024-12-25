package bg.tu_varna.sit.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static LocalDate readLocalDate(String prompt) {
        LocalDate date = null;
        while (date == null) {
            System.out.print(prompt);
            try {
                date = LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter in YYYY-MM-DD format.");
            }
        }
        return date;
    }

    public static LocalTime readLocalTime(String prompt) {
        LocalTime time = null;
        while (time == null) {
            System.out.print(prompt);
            try {
                time = LocalTime.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid time format. Please enter in HH:MM format.");
            }
        }
        return time;
    }

    public static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isValidTime(String time) {
        try {
            LocalTime.parse(time);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }
}
