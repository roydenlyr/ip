package shinchan.commands;

import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

public class ByeCommand implements Command {
    public void execute(TaskList taskList, Persona persona) {
        persona.bye();

    }

    public boolean isExit() {
        return true;
    }
}
