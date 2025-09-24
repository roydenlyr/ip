package shinchan.commands;

import shinchan.exceptions.EmptyTaskListException;
import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

public class ListCommand implements Command{
    public void execute(TaskList taskList, Persona persona) throws EmptyTaskListException {
        if (taskList.size() == 0) {
            throw new EmptyTaskListException(persona.listEmpty());
        }
        StringBuilder message = new StringBuilder(persona.listIntro());
        for (int i = 0; i<taskList.size(); i++) {
            message.append("\n").append(i + 1).append(". ").append(taskList.get(i));
        }
        Persona.printMessage(message.toString());
    }
}
