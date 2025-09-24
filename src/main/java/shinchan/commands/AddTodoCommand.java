package shinchan.commands;

import shinchan.data.DataManager;
import shinchan.exceptions.TaskMissingDescriptionException;
import shinchan.tasks.Task;
import shinchan.tasks.TaskList;
import shinchan.tasks.Todo;
import shinchan.ui.Persona;

import java.io.IOException;

public class AddTodoCommand implements Command {
    private final String description;

    public AddTodoCommand(String description) {
        this.description = description;
    }

    public void execute(TaskList taskList, Persona persona) throws TaskMissingDescriptionException, IOException {
        if (description.isBlank()) {
            throw new TaskMissingDescriptionException("The description of Todo task cannot be empty!");
        }

        Task todo = new Todo(description);
        taskList.add(todo);
        persona.addTask(taskList.asUnmodifiableList());
        DataManager.appendToFile(todo);
    }
}
