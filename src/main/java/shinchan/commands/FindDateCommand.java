package shinchan.commands;

import shinchan.tasks.Task;
import shinchan.tasks.TaskList;
import shinchan.ui.Persona;
import shinchan.ui.TaskRenderer;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a command that searches for tasks occurring on a specific date.
 * <p>
 * The {@code FindDateCommand} filters the task list to show only
 * tasks that return {@code true} for {@link Task#occursOn(LocalDateTime)}.
 */
public class FindDateCommand implements Command {
    private final LocalDateTime date;

    /**
     * Creates a new {@code FindDateCommand}.
     *
     * @param date the date to search for tasks
     */
    public FindDateCommand(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Executes the find-by-date operation.
     * <p>
     * If no tasks occur on the specified date, a "not found" message is displayed.
     * Otherwise, the matching tasks are rendered and displayed.
     *
     * @param taskList the task list containing tasks
     * @param persona  the persona responsible for displaying messages
     */
    public void execute(TaskList taskList, Persona persona) {
        List<Task> matches = taskList.find(task -> task.occursOn(date));
        if (matches.isEmpty()) {
            persona.dateNotFound();
            return;
        }
        persona.dateFound(TaskRenderer.numbered(matches));
    }
}
