package shinchan.commands;

import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

/**
 * Represents a command that handles empty user input.
 * <p>
 * The {@code EmptyCommand} displays a standardized message
 * prompting the user to enter something.
 */
public class EmptyCommand implements Command {

    /**
     * Executes the empty-input operation.
     * <p>
     * Always displays a "Please enter something." message.
     *
     * @param taskList the task list (unused in this command)
     * @param persona  the persona responsible for displaying messages
     */
    public void execute(TaskList taskList, Persona persona) {
        Persona.printMessage("Please enter something.");
    }
}
