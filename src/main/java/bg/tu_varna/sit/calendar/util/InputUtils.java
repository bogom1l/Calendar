package bg.tu_varna.sit.calendar.util;

import java.time.LocalDate;
import java.time.LocalTime;
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

    public static String readString(String prompt) {
        String input = null;

        while (input == null || input.isBlank()) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isBlank()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        }

        return input;
    }
}