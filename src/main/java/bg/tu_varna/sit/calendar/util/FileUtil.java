package bg.tu_varna.sit.calendar.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileUtil {

    private static final String PROJECT_DIRECTORY = System.getProperty("user.dir");

    public static String getProjectDirectory() {
        return PROJECT_DIRECTORY;
    }

    public static List<File> getAllXmlFiles() {
        return getAllXmlFiles(PROJECT_DIRECTORY);
    }

    public static List<File> getAllXmlFiles(String directoryPath) {
        File dir = new File(directoryPath);
        List<File> xmlFiles = new ArrayList<>();

        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".xml"));
            if (files != null) {
                Collections.addAll(xmlFiles, files);
            }
        }
        return xmlFiles;
    }

    public static void printAllXmlFiles() {
        List<File> xmlFiles = getAllXmlFiles();

        if (xmlFiles.isEmpty()) {
            System.out.println("No XML files found in the main project directory.");
            return;
        }

        System.out.println("XML files in the main project directory:");
        for (File file : xmlFiles) {
            System.out.println("- " + file.getName());
        }
    }

}
