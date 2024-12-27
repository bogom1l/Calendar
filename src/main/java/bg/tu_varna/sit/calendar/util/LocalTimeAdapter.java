package bg.tu_varna.sit.calendar.util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {

    @Override
    public LocalTime unmarshal(String v) throws Exception {
        return LocalTime.parse(v, DateTimeFormatter.ISO_TIME);
    }

    @Override
    public String marshal(LocalTime v) throws Exception {
        return v != null ? v.toString() : null;
    }
}
