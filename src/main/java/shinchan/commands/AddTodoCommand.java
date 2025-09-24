package shinchan.commands;

import shinchan.data.DataManager;
import shinchan.exceptions.TaskMissingDescriptionException;
import shinchan.tasks.Task;
import shinchan.tasks.TaskList;
import shinchan.tasks.Todo;
import shinchan.ui.Persona;

import java.io.IOException;

/**
 * Represents a command that adds a new to-do task to the task list.
 * <p>
 * The {@code AddTodoCommand} creates a {@link Todo} with the given description,
 * adds it to the task list, displays a confirmation message,
 * and appends it to storage.
 */
public class AddTodoCommand implements Command {
    private final String description;

    /**
     * Creates a new {@code AddTodoCommand}.
     *
     * @param description the description of the to-do task
     */
    public AddTodoCommand(String description) {
        this.description = description;
    }


    /**
     * Executes the add-to-do operation.
     * <p>
     * If the description is blank, a {@link TaskMissingDescriptionException} is thrown.
     * Otherwise, a new {@link Todo} task is created, added to the task list,
     * displayed via the persona, and persisted to file.
     *
     * @param taskList the task list to add the new task to
     * @param persona  the persona responsible for displaying messages
     * @throws TaskMissingDescriptionException if the description is blank
     * @throws IOException if writing the new task to file fails
     */
    public void execute(TaskList taskList, Persona persona) throws TaskMissingDescriptionException, IOException {
        if (description.isBlank()) {
            throw new TaskMissingDescriptionException("The description of Todo task cannot be empty!");
        }

        Task todo = new Todo(description);
        taskList.add(todo);
        persona.addTask(taskList.asUnmodifiableList());
        DataManager.appendToFile(todo);
    }
}
