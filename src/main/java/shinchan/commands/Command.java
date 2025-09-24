package shinchan.commands;

import shinchan.exceptions.ShinChanException;
import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

import java.io.IOException;

public interface Command {
    void execute (TaskList taskList, Persona persona) throws ShinChanException, IOException;

    default boolean isExit() {
        return false;
    }
}
