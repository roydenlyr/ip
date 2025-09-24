package shinchan.commands;

import shinchan.exceptions.TaskMissingDescriptionException;
import shinchan.tasks.Task;
import shinchan.tasks.TaskList;
import shinchan.ui.Persona;
import shinchan.ui.TaskRenderer;

import java.util.List;

public class FindWordCommand implements Command {
    private final String word;

    public FindWordCommand(String word) {
        this.word = word;
    }

    public void execute(TaskList taskList, Persona persona) throws TaskMissingDescriptionException {
        if (word.isBlank()) {
            throw new TaskMissingDescriptionException("Please enter a word!");
        }
        List<Task> matches = taskList.find(task -> task.getDescription().contains(word));
        if (matches.isEmpty()) {
            persona.wordNotFound();
            return;
        }
        persona.wordFound(TaskRenderer.numbered(matches));
    }
}
