package shinchan.tasks;

import shinchan.data.Datamanager;
import shinchan.exceptions.EmptyTaskListException;
import shinchan.exceptions.MarkMissingItemNumberException;
import shinchan.exceptions.TaskMissingDateException;
import shinchan.exceptions.TaskMissingDescriptionException;
import shinchan.exceptions.TaskNumberOutOfBoundException;
import shinchan.ui.Persona;

import java.io.IOException;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> taskList;
    private Persona persona;

    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
        persona = new Persona();
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
        Datamanager.writeToFile(taskList);
    }

    public void addEvent(String input)
            throws TaskMissingDateException, TaskMissingDescriptionException, IOException {
        String[] contents = extractContents(input);
        if (contents == null) {
            throw new TaskMissingDescriptionException("The description of Event task cannot be empty!");
        }
        if (contents.length != 3) {
            throw new TaskMissingDateException("Please enter a valid TO and FROM date!");
        }
        String description = contents[0].trim();
        String from = formateDate(contents[1]);
        String to = formateDate(contents[2]);

        Task event = new Event(description, from, to);
        taskList.add(event);
        persona.addTask(taskList);
        Datamanager.appendToFile(event);
    }

    public void addDeadline(String input)
            throws TaskMissingDateException, TaskMissingDescriptionException, IOException {
        String[] contents = extractContents(input);
        if (contents == null) {
            throw new TaskMissingDescriptionException("The description of Deadline task cannot be empty!");
        }
        if (contents.length != 2) {
            throw new TaskMissingDateException("Please enter a valid deadline date!");
        }
        String description = contents[0].trim();
        String deadline = formateDate(contents[1]);

        Task deadlineTask = new Deadline(description, deadline);
        taskList.add(deadlineTask);
        persona.addTask(taskList);
        Datamanager.appendToFile(deadlineTask);
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
        Datamanager.appendToFile(todo);
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
        Datamanager.writeToFile(taskList);
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

    private String formateDate(String date) {
        return date.split("\\s", 2)[1].trim();
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
