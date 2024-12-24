package bg.tu_varna.sit.util;

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


}
