package shinchan.commands;

import shinchan.exceptions.EmptyTaskListException;
import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

/**
 * Represents a command that lists all tasks in the task list.
 * <p>
 * The {@code ListCommand} displays the entire task list
 * in a numbered format. If the list is empty,
 * an {@link EmptyTaskListException} is thrown.
 */
public class ListCommand implements Command{

    /**
     * Executes the list operation.
     * <p>
     * If the task list is empty, an exception is thrown.
     * Otherwise, all tasks are displayed in a numbered list.
     *
     * @param taskList the task list containing tasks
     * @param persona  the persona responsible for generating messages
     * @throws EmptyTaskListException if the task list is empty
     */
    public void execute(TaskList taskList, Persona persona) throws EmptyTaskListException {
        if (taskList.size() == 0) {
            throw new EmptyTaskListException(persona.listEmpty());
        }
        StringBuilder message = new StringBuilder(persona.listIntro());
        for (int i = 0; i<taskList.size(); i++) {
            message.append("\n").append(i + 1).append(". ").append(taskList.get(i));
        }
        Persona.printMessage(message.toString());
    }
}
