package bg.tu_varna.sit.calendar.model;

import bg.tu_varna.sit.calendar.util.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;

@XmlRootElement
public class Holiday {
    private LocalDate date;

    public Holiday() {
    }

    public Holiday(LocalDate date) {
        this.date = date;
    }

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}