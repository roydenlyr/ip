package shinchan.commands;

import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

public class ErrorCommand implements Command {
    public void execute(TaskList taskList, Persona persona) {
        Persona.printMessage("Invalid Command.");
    }
}
