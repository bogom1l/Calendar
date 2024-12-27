package bg.tu_varna.sit.calendar.model;

public class AppState {
    private boolean state;

    public boolean isCalendarOpen() {
        return state;
    }

    public void setCalendarState(boolean state) {
        this.state = state;
    }
}