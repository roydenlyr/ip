package shinchan.commands;

import shinchan.tasks.Task;
import shinchan.tasks.TaskList;
import shinchan.ui.Persona;
import shinchan.ui.TaskRenderer;

import java.time.LocalDateTime;
import java.util.List;

public class FindDateCommand implements Command {
    private final LocalDateTime date;

    public FindDateCommand(LocalDateTime date) {
        this.date = date;
    }

    public void execute(TaskList taskList, Persona persona) {
        List<Task> matches = taskList.find(task -> task.occursOn(date));
        if (matches.isEmpty()) {
            persona.dateNotFound();
            return;
        }
        persona.dateFound(TaskRenderer.numbered(matches));
    }
}
