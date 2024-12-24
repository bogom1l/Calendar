package bg.tu_varna.sit.model;

import bg.tu_varna.sit.util.LocalDateAdapter;
import bg.tu_varna.sit.util.LocalTimeAdapter;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;
import java.time.LocalTime;

@XmlRootElement
public class Event {
    private String title;

    //@XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate date;

    //@XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime timeStart;

    //@XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime timeEnd;

    private String description;

    // Default constructor required by JAXB
    public Event() {
    }

    public Event(String title, LocalDate date, LocalTime timeStart, LocalTime timeEnd, String description) {
        this.title = title;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.description = description;
    }

    @XmlElement
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @XmlElement
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    @XmlElement
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", timeStart='" + timeStart + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
