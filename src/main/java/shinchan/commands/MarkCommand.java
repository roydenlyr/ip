package shinchan.commands;

import shinchan.data.DataManager;
import shinchan.exceptions.EmptyTaskListException;
import shinchan.exceptions.ShinChanException;
import shinchan.exceptions.TaskNumberOutOfBoundException;
import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

import java.io.IOException;

/**
 * Represents a command that marks a task as done.
 * <p>
 * The {@code MarkCommand} sets the {@code isDone} status of the specified task
 * to {@code true}, updates the task list, and saves the changes to storage.
 */
public class MarkCommand implements Command {
    private final int index;

    /**
     * Creates a new {@code MarkCommand}.
     *
     * @param index the zero-based index of the task to mark as done
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the mark operation.
     * <p>
     * If the task list is empty, an {@link EmptyTaskListException} is thrown.
     * If the index is invalid, a {@link TaskNumberOutOfBoundException} is thrown.
     * Otherwise, the task at the specified index is marked as done,
     * a confirmation message is shown, and the updated task list is written to file.
     *
     * @param taskList the task list containing tasks
     * @param persona  the persona responsible for displaying messages
     * @throws ShinChanException if the task list is empty or the index is invalid
     * @throws IOException       if saving the task list to file fails
     */
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
