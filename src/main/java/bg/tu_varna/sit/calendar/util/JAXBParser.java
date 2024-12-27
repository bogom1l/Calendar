package bg.tu_varna.sit.calendar.util;

import bg.tu_varna.sit.calendar.model.EventsWrapper;
import bg.tu_varna.sit.calendar.model.HolidaysWrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class JAXBParser {
    private static final String USER_DIR = System.getProperty("user.dir");

    private static JAXBContext createContext(Class<?> clazz) throws JAXBException {
        return JAXBContext.newInstance(clazz);
    }

    private static <T> T loadFromXML(Class<T> clazz, File xmlFile) throws JAXBException {
        JAXBContext context = createContext(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return clazz.cast(unmarshaller.unmarshal(xmlFile));
    }

    private static <T> void saveToXML(T object, File xmlFile, Class<T> clazz) throws JAXBException {
        JAXBContext context = createContext(clazz);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(object, xmlFile);
    }

    public static EventsWrapper loadEventsFromXML(File xmlFile) throws JAXBException {
        return loadFromXML(EventsWrapper.class, xmlFile);
    }

    public static void saveEventsToXML(EventsWrapper eventsWrapper, File xmlFile) throws JAXBException {
        saveToXML(eventsWrapper, xmlFile, EventsWrapper.class);
    }

    public static void saveHolidaysToXML(HolidaysWrapper holidaysWrapper, File file) throws JAXBException {
        saveToXML(holidaysWrapper, file, HolidaysWrapper.class);
    }

    public static HolidaysWrapper loadHolidaysFromXML(File file) throws JAXBException {
        return loadFromXML(HolidaysWrapper.class, file);
    }

    public static HolidaysWrapper loadHolidaysFromXMLByFilename(String fileName) throws JAXBException {
        File file = new File(USER_DIR, fileName);
        if (!file.exists()) {
            throw new JAXBException("File not found: " + fileName);
        }
        return loadHolidaysFromXML(file);
    }

    public static EventsWrapper loadEventsFromXMLByFilename(String fileName) throws JAXBException {
        File file = new File(USER_DIR, fileName);
        if (!file.exists()) {
            throw new JAXBException("File not found: " + fileName);
        }
        return loadEventsFromXML(file);
    }
}
