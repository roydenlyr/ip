package shinchan.commands;

import shinchan.data.DataManager;
import shinchan.exceptions.EmptyTaskListException;
import shinchan.exceptions.MissingItemNumberException;
import shinchan.exceptions.ShinChanException;
import shinchan.exceptions.TaskNumberOutOfBoundException;
import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

import java.io.IOException;

/**
 * Represents a command that deletes a task from the task list.
 * <p>
 * The {@code DeleteCommand} removes a task at the specified index,
 * updates the task list, shows a confirmation message,
 * and writes the updated list to storage.
 */
public class DeleteCommand implements Command{
    private final int index;

    /**
     * Creates a new {@code DeleteCommand}.
     *
     * @param index the zero-based index of the task to delete
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the delete operation.
     * <p>
     * If the task list is empty, an {@link EmptyTaskListException} is thrown.
     * If the index is negative, a {@link MissingItemNumberException} is thrown.
     * If the index is greater than or equal to the size of the list,
     * a {@link TaskNumberOutOfBoundException} is thrown.
     * Otherwise, the task at the specified index is removed,
     * a confirmation message is displayed, and the updated list is written to file.
     *
     * @param taskList the task list containing tasks
     * @param persona  the persona responsible for displaying messages
     * @throws ShinChanException if the task list is empty or the index is invalid
     * @throws IOException       if saving the task list to file fails
     */
    public void execute(TaskList taskList, Persona persona) throws ShinChanException, IOException {
        if (taskList.size() == 0) {
            throw new EmptyTaskListException(persona.listEmpty());
        }
        if (index < 0) {
            throw new MissingItemNumberException("Please include a valid item number!");
        }
        if (index >= taskList.size()) {
            throw new TaskNumberOutOfBoundException("Task number out of bounds!");
        }
        persona.removeTask(taskList.get(index), taskList.size() - 1);
        taskList.removeAt(index);
        DataManager.writeToFile(taskList.asUnmodifiableList());
    }
}
