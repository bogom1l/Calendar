package bg.tu_varna.sit.input;

public class AppState {
    private boolean calendarOpen;

    public boolean isCalendarOpen() {
        return calendarOpen;
    }

    public void setCalendarOpen(boolean calendarOpen) {
        this.calendarOpen = calendarOpen;
    }

    public void resetState() {
        this.calendarOpen = false;
    }
}