package bg.tu_varna.sit.calendar.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "holidays")
public class HolidaysWrapper {
    private Set<Holiday> holidays = new HashSet<>();

    @XmlElement(name = "holiday")
    public Set<Holiday> getHolidays() {
        return holidays;
    }

    public void setHolidays(Set<Holiday> holidays) {
        this.holidays = holidays;
    }
}