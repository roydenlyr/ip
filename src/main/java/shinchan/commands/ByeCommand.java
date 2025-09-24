package shinchan.commands;

import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

/**
 * Represents a command that terminates the program.
 * <p>
 * The {@code ByeCommand} displays a farewell message
 * and signals that the program should exit.
 */
public class ByeCommand implements Command {

    /**
     * Executes the bye operation.
     * <p>
     * Displays a farewell message to the user.
     *
     * @param taskList the task list (unused in this command)
     * @param persona  the persona responsible for displaying messages
     */
    public void execute(TaskList taskList, Persona persona) {
        persona.bye();
    }

    /**
     * Indicates that this command is an exit command.
     *
     * @return always {@code true}
     */
    public boolean isExit() {
        return true;
    }
}
