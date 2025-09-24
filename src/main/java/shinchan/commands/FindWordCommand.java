package shinchan.commands;

import shinchan.exceptions.TaskMissingDescriptionException;
import shinchan.tasks.Task;
import shinchan.tasks.TaskList;
import shinchan.ui.Persona;
import shinchan.ui.TaskRenderer;

import java.util.List;

/**
 * Represents a command that searches for tasks by keyword.
 * <p>
 * The {@code FindWordCommand} filters the task list to show only
 * tasks whose descriptions contain the given keyword.
 */
public class FindWordCommand implements Command {
    private final String word;

    /**
     * Creates a new {@code FindWordCommand}.
     *
     * @param word the keyword to search for in task descriptions
     */
    public FindWordCommand(String word) {
        this.word = word;
    }

    /**
     * Executes the find operation.
     * <p>
     * If the keyword is blank, a {@link TaskMissingDescriptionException} is thrown.
     * If no tasks match, a "not found" message is displayed.
     * Otherwise, the matching tasks are rendered and displayed.
     *
     * @param taskList the task list containing tasks
     * @param persona  the persona responsible for displaying messages
     * @throws TaskMissingDescriptionException if the keyword is blank
     */
    public void execute(TaskList taskList, Persona persona) throws TaskMissingDescriptionException {
        if (word.isBlank()) {
            throw new TaskMissingDescriptionException("Please enter a word!");
        }
        List<Task> matches = taskList.find(task -> task.getDescription().contains(word));
        if (matches.isEmpty()) {
            persona.wordNotFound();
            return;
        }
        persona.wordFound(TaskRenderer.numbered(matches));
    }
}
