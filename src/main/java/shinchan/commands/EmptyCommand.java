package shinchan.commands;

import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

public class EmptyCommand implements Command {
    public void execute(TaskList taskList, Persona persona) {
        Persona.printMessage("Please enter something.");
    }
}
