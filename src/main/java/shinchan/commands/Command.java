package shinchan.commands;

import shinchan.exceptions.ShinChanException;
import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

public interface Command {
    void execute (TaskList taskList, Persona persona) throws ShinChanException;

    default boolean isExit() {
        return false;
    }
}
