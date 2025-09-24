package shinchan.commands;

import shinchan.data.DataManager;
import shinchan.exceptions.EmptyTaskListException;
import shinchan.exceptions.MarkMissingItemNumberException;
import shinchan.exceptions.ShinChanException;
import shinchan.exceptions.TaskNumberOutOfBoundException;
import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

import java.io.IOException;

public class DeleteCommand implements Command{
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    public void execute(TaskList taskList, Persona persona) throws ShinChanException, IOException {
        if (taskList.size() == 0) {
            throw new EmptyTaskListException(persona.listEmpty());
        }
        if (index < 0) {
            throw new MarkMissingItemNumberException("Please include a valid item number!");
        }
        if (index >= taskList.size()) {
            throw new TaskNumberOutOfBoundException("Task number out of bounds!");
        }
        persona.removeTask(taskList.get(index), taskList.size() - 1);
        taskList.removeAt(index);
        DataManager.writeToFile(taskList.asUnmodifiableList());
    }
}
