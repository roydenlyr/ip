package shinchan;

import shinchan.commands.Command;
import shinchan.exceptions.MarkMissingItemNumberException;
import shinchan.exceptions.TaskMissingDateException;
import shinchan.exceptions.TaskMissingDescriptionException;
import shinchan.exceptions.TaskNumberOutOfBoundException;
import shinchan.tasks.Deadline;
import shinchan.tasks.Event;
import shinchan.tasks.Task;
import shinchan.tasks.Todo;
import shinchan.ui.Persona;

import java.util.Scanner;
import java.util.ArrayList;

public class Shinchan {
    public static void main(String[] args) {
        Persona persona = new Persona();
        persona.portrait();
        printMessage(persona.introduction());

        ArrayList<Task> taskList = new ArrayList<>();
        Scanner input = new Scanner(System.in);

            while(true) {
                try {
                    String line = input.nextLine();
                    Command command = extractCommand(line);
                    switch (command) {
                    case LIST:
                        showList(persona, taskList);
                        break;
                    case MARK:
                        updateTaskStatus(taskList, line, true, persona);
                        break;
                    case UNMARK:
                        updateTaskStatus(taskList, line, false, persona);
                        break;
                    case TODO:
                        addTodo(line, taskList, persona);
                        break;
                    case DEADLINE:
                        addDeadline(line, taskList, persona);
                        break;
                    case EVENT:
                        addEvent(line, taskList, persona);
                        break;
                    case EMPTY:
                        printMessage("Please enter something");
                        break;
                    case BYE:
                        printMessage(persona.bye());
                        return;
                    default:
                        printMessage("Invalid command");
                        break;
                    }
                } catch (TaskNumberOutOfBoundException | MarkMissingItemNumberException | TaskMissingDateException
                         | TaskMissingDescriptionException e) {
                    printMessage(e.getMessage());
                }
            }
    }

    private static void addEvent(String line, ArrayList<Task> taskList, Persona persona)
            throws TaskMissingDateException, TaskMissingDescriptionException {
        String[] contents = extractContents(line);
        if (contents == null) {
            throw new TaskMissingDescriptionException("The description of Event task cannot be empty!");
        }
        if (contents.length != 3) {
            throw new TaskMissingDateException("Please enter a valid TO and FROM date!");
        }
        String description = contents[0].trim();
        String from = formatDate(contents[1]);
        String to = formatDate(contents[2]);

        taskList.add(new Event(description, from, to));
        printMessage(persona.addTask(taskList));
    }

    private static void addDeadline(String line, ArrayList<Task> taskList, Persona persona)
            throws TaskMissingDateException, TaskMissingDescriptionException {
        String[] contents = extractContents(line);
        if (contents == null) {
            throw new TaskMissingDescriptionException("The description of Deadline task cannot be empty!");
        }
        if (contents.length != 2) {
            throw new TaskMissingDateException("Please enter a valid deadline date!");
        }
        String description = contents[0].trim();
        String deadline = formatDate(contents[1]);

        taskList.add(new Deadline(description, deadline));
        printMessage(persona.addTask(taskList));
    }

    private static void addTodo(String line, ArrayList<Task> taskList, Persona persona) throws TaskMissingDescriptionException {
        String[] contents = extractContents(line);
        if (contents == null) {
            throw new TaskMissingDescriptionException("The description of Todo task cannot be empty!");
        }
        String description = contents[0].trim();

        taskList.add(new Todo(description));
        printMessage(persona.addTask(taskList));
    }

    private static String[] extractContents(String line) {
        String[] contents = line.split("\\s", 2);
        if (contents.length != 2) {
            return null;
        }
        String content = contents[1].trim();
        return content.split("/");
    }

    private static String formatDate(String date) {
        return date.split("\\s", 2)[1].trim();
    }

    private static void showList(Persona persona, ArrayList<Task> taskList) {
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
        printMessage(msg.toString());
    }

    public static void updateTaskStatus(ArrayList<Task> taskList, String line, boolean isDone, Persona persona)
            throws TaskNumberOutOfBoundException, MarkMissingItemNumberException {
        if (taskList.isEmpty()) {
            printMessage(persona.listEmpty());
            return;
        }
        int taskIndex = extractTrailingNumber(line) - 1;
        if (taskIndex < 0) {
            throw new MarkMissingItemNumberException("Please include a valid item number!");
        }
        if (taskIndex >= taskList.size()) {
            throw new TaskNumberOutOfBoundException("Task number is out of bounds!");
        }
        taskList.get(taskIndex).setDone(isDone);
        printMessage((isDone ? persona.markIntro() : persona.unmarkIntro()) + taskList.get(taskIndex));
    }

    public static void printMessage (String message) {
        System.out.println("\n====================");
        System.out.println(message);
        System.out.println("====================\n");
    }

    public static Command extractCommand(String message) {
        try {
            if (message == null || message.isBlank()) {
                return Command.EMPTY;
            }

            message = message.trim().toUpperCase().split("\\s", 2)[0];
            return Command.valueOf(message);
        } catch (IllegalArgumentException e) {
            return Command.ERROR;
        }
    }

    public static int extractTrailingNumber(String message) {
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
