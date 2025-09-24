package shinchan.commands;

import shinchan.data.DataManager;
import shinchan.exceptions.InvalidDateException;
import shinchan.exceptions.TaskMissingDescriptionException;
import shinchan.tasks.Event;
import shinchan.tasks.Task;
import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

import java.io.IOException;
import java.time.LocalDateTime;

public class AddEventCommand implements Command {
    private final String description;
    private final LocalDateTime to;
    private final LocalDateTime from;

    public AddEventCommand(String description, LocalDateTime from,  LocalDateTime to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    public void execute(TaskList taskList, Persona persona)
            throws TaskMissingDescriptionException, IOException, InvalidDateException {
        if (description.isBlank()) {
            throw new TaskMissingDescriptionException("The description of Deadline task cannot be empty!");
        }
        if (from.isAfter(to)) {
            throw new InvalidDateException("Date of 'from' cannot be later than 'to'!");
        }
        Task event = new Event(description, from, to);
        taskList.add(event);
        persona.addTask(taskList.asUnmodifiableList());
        DataManager.appendToFile(event);
    }
}
