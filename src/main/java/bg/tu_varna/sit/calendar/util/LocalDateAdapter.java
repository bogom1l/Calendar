package bg.tu_varna.sit.calendar.util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v, DateTimeFormatter.ISO_DATE);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v != null ? v.toString() : null;
    }
}