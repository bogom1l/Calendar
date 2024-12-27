package bg.tu_varna.sit.calendar.command;

import bg.tu_varna.sit.calendar.exception.EventException;

public interface Command {
    void execute() throws EventException;
}