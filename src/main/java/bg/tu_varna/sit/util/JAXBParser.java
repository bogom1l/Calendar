package bg.tu_varna.sit.util;

import bg.tu_varna.sit.model.EventsWrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class JAXBParser {

    // Load EventsWrapper (calendar) from XML
    public static EventsWrapper loadEventsFromXML(File xmlFile) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(EventsWrapper.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (EventsWrapper) unmarshaller.unmarshal(xmlFile);
    }

    // Save EventsWrapper (calendar) to XML
    public static void saveEventsToXML(EventsWrapper eventsWrapper, File xmlFile) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(EventsWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(eventsWrapper, xmlFile);
    }
}
