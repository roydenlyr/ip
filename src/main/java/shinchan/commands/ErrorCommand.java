package shinchan.commands;

import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

/**
 * Represents a command that handles invalid or unrecognized user input.
 * <p>
 * The {@code ErrorCommand} displays a standardized error message
 * when the user enters a command that cannot be parsed.
 */
public class ErrorCommand implements Command {

    /**
     * Executes the error operation.
     * <p>
     * Always displays an "Invalid Command" message.
     *
     * @param taskList the task list (unused in this command)
     * @param persona  the persona responsible for displaying messages
     */
    public void execute(TaskList taskList, Persona persona) {
        Persona.printMessage("Invalid Command.");
    }
}
