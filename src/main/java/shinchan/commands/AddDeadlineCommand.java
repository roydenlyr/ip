package shinchan.commands;

import shinchan.data.DataManager;
import shinchan.exceptions.TaskMissingDescriptionException;
import shinchan.tasks.Deadline;
import shinchan.tasks.Task;
import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

import java.io.IOException;
import java.time.LocalDateTime;

public class AddDeadlineCommand implements Command {
    private final String description;
    private final LocalDateTime by;

    public AddDeadlineCommand(String description, LocalDateTime by) {
        this.description = description;
        this.by = by;
    }

    public void execute(TaskList taskList, Persona persona) throws TaskMissingDescriptionException, IOException {
        if (description.isBlank()) {
            throw new TaskMissingDescriptionException("The description of Deadline task cannot be empty!");
        }
        Task deadline = new Deadline(description, by);
        taskList.add(deadline);
        persona.addTask(taskList.asUnmodifiableList());
        DataManager.appendToFile(deadline);
    }
}
