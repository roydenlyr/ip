package shinchan.commands;

import shinchan.data.DataManager;
import shinchan.exceptions.TaskMissingDescriptionException;
import shinchan.tasks.Deadline;
import shinchan.tasks.Task;
import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Represents a command that adds a new deadline task to the task list.
 * <p>
 * The {@code AddDeadlineCommand} creates a {@link Deadline} with the given
 * description and due date, adds it to the task list, displays a confirmation
 * message, and appends it to storage.
 */
public class AddDeadlineCommand implements Command {
    private final String description;
    private final LocalDateTime by;

    /**
     * Creates a new {@code AddDeadlineCommand}.
     *
     * @param description the description of the deadline task
     * @param by          the due date and time of the deadline
     */
    public AddDeadlineCommand(String description, LocalDateTime by) {
        this.description = description;
        this.by = by;
    }

    /**
     * Executes the add-deadline operation.
     * <p>
     * If the description is blank, a {@link TaskMissingDescriptionException} is thrown.
     * Otherwise, a new {@link Deadline} task is created, added to the task list,
     * displayed via the persona, and persisted to file.
     *
     * @param taskList the task list to add the new deadline to
     * @param persona  the persona responsible for displaying messages
     * @throws TaskMissingDescriptionException if the description is blank
     * @throws IOException if writing the new deadline to file fails
     */
    public void execute(TaskList taskList, Persona persona) throws TaskMissingDescriptionException, IOException {
        if (description.isBlank()) {
            throw new TaskMissingDescriptionException("The description of Deadline task cannot be empty!");
        }
        Task deadline = new Deadline(description, by);
        taskList.add(deadline);
        persona.addTask(taskList.asUnmodifiableList());
        DataManager.appendToFile(deadline);
    }
}
