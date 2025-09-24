package shinchan.commands;

import shinchan.data.DataManager;
import shinchan.exceptions.InvalidDateException;
import shinchan.exceptions.TaskMissingDescriptionException;
import shinchan.tasks.Event;
import shinchan.tasks.Task;
import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Represents a command that adds a new event task to the task list.
 * <p>
 * The {@code AddEventCommand} creates an {@link Event} with the given
 * description, start time, and end time, then adds it to the task list,
 * displays a confirmation message, and appends it to storage.
 */
public class AddEventCommand implements Command {
    private final String description;
    private final LocalDateTime to;
    private final LocalDateTime from;

    /**
     * Creates a new {@code AddEventCommand}.
     *
     * @param description the description of the event
     * @param from        the start date and time of the event
     * @param to          the end date and time of the event
     */
    public AddEventCommand(String description, LocalDateTime from,  LocalDateTime to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    /**
     * Executes the add-event operation.
     * <p>
     * If the description is blank, a {@link TaskMissingDescriptionException} is thrown.
     * If the {@code from} date is after the {@code to} date,
     * an {@link InvalidDateException} is thrown.
     * Otherwise, a new {@link Event} task is created, added to the task list,
     * displayed via the persona, and persisted to file.
     *
     * @param taskList the task list to add the new event to
     * @param persona  the persona responsible for displaying messages
     * @throws TaskMissingDescriptionException if the description is blank
     * @throws InvalidDateException if the start date is after the end date
     * @throws IOException if writing the new event to file fails
     */
    public void execute(TaskList taskList, Persona persona)
            throws TaskMissingDescriptionException, IOException, InvalidDateException {
        if (description.isBlank()) {
            throw new TaskMissingDescriptionException("The description of Deadline task cannot be empty!");
        }
        if (from.isAfter(to)) {
            throw new InvalidDateException("Date of 'from' cannot be later than 'to'!");
        }
        Task event = new Event(description, from, to);
        taskList.add(event);
        persona.addTask(taskList.asUnmodifiableList());
        DataManager.appendToFile(event);
    }
}
