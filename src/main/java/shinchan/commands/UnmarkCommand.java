package shinchan.commands;

import shinchan.data.DataManager;
import shinchan.exceptions.EmptyTaskListException;
import shinchan.exceptions.ShinChanException;
import shinchan.exceptions.TaskNumberOutOfBoundException;
import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

import java.io.IOException;

public class UnmarkCommand implements Command {
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    public void execute(TaskList taskList, Persona persona) throws ShinChanException, IOException {
        if (taskList.size() == 0) {
            throw new EmptyTaskListException(persona.listEmpty());
        }
        if (index < 0 || index >= taskList.size()) {
            throw new TaskNumberOutOfBoundException("Task number out of bounds!");
        }
        taskList.setDone(index, false);
        Persona.printMessage(persona.unmarkIntro() + taskList.get(index));
        DataManager.writeToFile(taskList.asUnmodifiableList());
    }
}
