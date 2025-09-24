package shinchan.tasks;

import shinchan.data.DataManager;
import shinchan.exceptions.EmptyTaskListException;
import shinchan.exceptions.IllegalDateFormatException;
import shinchan.exceptions.MarkMissingItemNumberException;
import shinchan.exceptions.MissingDateException;
import shinchan.exceptions.MissingWordException;
import shinchan.exceptions.TaskMissingDateException;
import shinchan.exceptions.TaskMissingDescriptionException;
import shinchan.exceptions.TaskNumberOutOfBoundException;
import shinchan.ui.Persona;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TaskList {
    private final ArrayList<Task> taskList;
    private Persona persona;

    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
        persona = new Persona();
    }

    public int size() {
        return taskList.size();
    }

    public Task get(int index) {
        return taskList.get(index);
    }

    public void add(Task task) {
        taskList.add(task);
    }

    public void removeAt(int index) {
        taskList.remove(index);
    }

    public void setDone(int index, boolean isDone) {
        taskList.get(index).setDone(isDone);
    }

    public ArrayList<Task> find(Predicate<Task> predicate) {
        return (ArrayList<Task>) taskList.stream().filter(predicate).toList();
    }

    public ArrayList<Task> asUnmodifiableList() {
        return (ArrayList<Task>) List.copyOf(taskList);
    }

    public void findDate (String input) throws MissingDateException, IllegalDateFormatException {
        String[] contents = extractContents(input);
        if (contents == null) {
            throw new MissingDateException("Please include the date to search!");
        }

        LocalDateTime date = formatDate(input);
        StringBuilder msg = new StringBuilder();
        int index = 1;
        for (Task task : taskList) {
            if (task instanceof Deadline deadline) {
                if (deadline.getBy().isEqual(date)) {
                    msg.append("\n").append(index).append(". ").append(task);
                    index++;
                }
            }
            if (task instanceof Event event) {
                if (event.getBy().isEqual(date) || event.getFrom().isEqual(date)) {
                    msg.append("\n").append(index).append(". ").append(task);
                    index++;
                }
            }
        }
        if (msg.isEmpty()) {
            persona.dateNotFound();
            return;
        }
        persona.dateFound(msg.toString());
    }

    public void findWord (String input) throws MissingWordException {
        String[] contents = extractContents(input);
        if (contents == null) {
            throw new MissingWordException("Please include the word to search!");
        }
        String word = contents[0].trim();
        StringBuilder msg = new StringBuilder();
        int index = 1;
        for (Task task : taskList) {
            if (task.getDescription().contains(word)){
                msg.append("\n").append(index).append(". ").append(task);
                index++;
            }
        }
        if (msg.isEmpty()) {
            persona.wordNotFound();
            return;
        }
        persona.wordFound(msg.toString());
    }

    public void deleteTask(String input)
            throws EmptyTaskListException, TaskNumberOutOfBoundException, MarkMissingItemNumberException, IOException {
        if (taskList.isEmpty()) {
            throw new EmptyTaskListException(persona.listEmpty());
        }
        int taskIndex = extractTrailingNumber(input) - 1;
        if (taskIndex < 0) {
            throw new MarkMissingItemNumberException("Please include a valid item number!");
        }
        if (taskIndex >= taskList.size()) {
            throw new TaskNumberOutOfBoundException("Task number is out of bounds!");
        }

        persona.removeTask(taskList.get(taskIndex), taskList.size() - 1);
        taskList.remove(taskIndex);
        DataManager.writeToFile(taskList);
    }

    public void addEvent(String input)
            throws TaskMissingDateException, TaskMissingDescriptionException, IOException, IllegalDateFormatException {
        String[] contents = extractContents(input);
        if (contents == null) {
            throw new TaskMissingDescriptionException("The description of Event task cannot be empty!");
        }
        if (contents.length != 3) {
            throw new TaskMissingDateException("Please enter a valid TO and FROM date!");
        }
        String description = contents[0].trim();
        LocalDateTime from = formatDate(contents[1]);
        LocalDateTime to = formatDate(contents[2]);

        Task event = new Event(description, from, to);
        taskList.add(event);
        persona.addTask(taskList);
        DataManager.appendToFile(event);
    }

    public void addDeadline(String input)
            throws TaskMissingDateException, TaskMissingDescriptionException, IOException, IllegalDateFormatException {
        String[] contents = extractContents(input);
        if (contents == null) {
            throw new TaskMissingDescriptionException("The description of Deadline task cannot be empty!");
        }
        if (contents.length != 2) {
            throw new TaskMissingDateException("Please enter a valid deadline date!");
        }
        String description = contents[0].trim();
        LocalDateTime deadline = formatDate(contents[1]);

        Task deadlineTask = new Deadline(description, deadline);
        taskList.add(deadlineTask);
        persona.addTask(taskList);
        DataManager.appendToFile(deadlineTask);
    }

    public void addTodo(String input)
            throws TaskMissingDescriptionException, IOException {
        String[] contents = extractContents(input);
        if (contents == null) {
            throw new TaskMissingDescriptionException("The description of Todo task cannot be empty!");
        }
        String description = contents[0].trim();

        Task todo = new Todo(description);
        taskList.add(todo);
        persona.addTask(taskList);
        DataManager.appendToFile(todo);
    }

    public void updateTaskStatus(String input, boolean isDone)
            throws TaskNumberOutOfBoundException, MarkMissingItemNumberException, IOException {
        if (taskList.isEmpty()) {
            Persona.printMessage(persona.listEmpty());
            return;
        }
        int taskIndex = extractTrailingNumber(input) - 1;
        if (taskIndex < 0) {
            throw new MarkMissingItemNumberException("Please include a valid item number!");
        }
        if (taskIndex >= taskList.size()) {
            throw new TaskNumberOutOfBoundException("Task number is out of bounds!");
        }
        taskList.get(taskIndex).setDone(isDone);
        Persona.printMessage((isDone ? persona.markIntro() : persona.unmarkIntro()) + taskList.get(taskIndex));
        DataManager.writeToFile(taskList);
    }

    public void showList() {
        int numOfTasks = taskList.size();
        StringBuilder msg = new StringBuilder();
        if (numOfTasks > 0) {
            msg.append(persona.listIntro());
            for (int i = 0; i < numOfTasks; i++) {
                msg.append("\n").append(i + 1).append(". ").append(taskList.get(i));
            }
        } else {
            msg.append(persona.listEmpty());
        }
        Persona.printMessage(msg.toString());
    }

    private LocalDateTime formatDate(String date) throws IllegalDateFormatException {
        try {
            date = date.split("\\s", 2)[1].trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            return LocalDateTime.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalDateFormatException("Invalid date format! Expected yyyy-MM-dd HHmm, e.g, 2025-09-23 2359.");
        }
    }

    private String[] extractContents(String line) {
        String[] contents = line.split("\\s", 2);
        if (contents.length != 2) {
            return null;
        }
        String content = contents[1].trim();
        return content.split("/");
    }

    private int extractTrailingNumber(String message) {
        try {
            int lastIndex = message.length() - 1;
            while (lastIndex >= 0 && Character.isDigit(message.charAt(lastIndex))) {
                lastIndex--;
            }
            return Integer.parseInt(message.substring(lastIndex + 1));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
