package shinchan.commands;

import shinchan.data.DataManager;
import shinchan.exceptions.EmptyTaskListException;
import shinchan.exceptions.ShinChanException;
import shinchan.exceptions.TaskNumberOutOfBoundException;
import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

import java.io.IOException;

public class MarkCommand implements Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    public void execute(TaskList taskList, Persona persona) throws ShinChanException, IOException {
        if (taskList.size() == 0) {
            throw new EmptyTaskListException(persona.toString());
        }
        if (index < 0 || index >= taskList.size()) {
            throw new TaskNumberOutOfBoundException("Task number is out of bounds!");
        }
        taskList.setDone(index, true);
        Persona.printMessage(persona.markIntro() + taskList.get(index));
        DataManager.writeToFile(taskList.asUnmodifiableList());
    }
}
