package shinchan;

import shinchan.commands.Command;
import shinchan.data.Datamanager;
import shinchan.exceptions.EmptyTaskListException;
import shinchan.exceptions.MarkMissingItemNumberException;
import shinchan.exceptions.TaskMissingDateException;
import shinchan.exceptions.TaskMissingDescriptionException;
import shinchan.exceptions.TaskNumberOutOfBoundException;
import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

import java.io.IOException;
import java.util.Scanner;

public class Shinchan {
    public static void main(String[] args) {
        Persona persona = new Persona();
        persona.portrait();
        printMessage(persona.introduction());

        Datamanager datamanager = new Datamanager("./data/data.txt");
        TaskList taskList = new TaskList(datamanager.loadData());

        Scanner input = new Scanner(System.in);

            while(true) {
                try {
                    String line = input.nextLine();
                    Command command = extractCommand(line);
                    switch (command) {
                    case LIST:
                        taskList.showList();
                        break;
                    case MARK:
                        taskList.updateTaskStatus(line, true);
                        break;
                    case UNMARK:
                        taskList.updateTaskStatus(line, false);
                        break;
                    case TODO:
                        taskList.addTodo(line);
                        break;
                    case DEADLINE:
                        taskList.addDeadline(line);
                        break;
                    case EVENT:
                        taskList.addEvent(line);
                        break;
                    case EMPTY:
                        printMessage("Please enter something");
                        break;
                    case BYE:
                        printMessage(persona.bye());
                        return;
                    case DELETE:
                        taskList.deleteTask(line);
                        break;
                    default:
                        printMessage("Invalid command");
                        break;
                    }
                } catch (TaskNumberOutOfBoundException | MarkMissingItemNumberException | TaskMissingDateException
                         | TaskMissingDescriptionException | EmptyTaskListException | IOException e) {
                    printMessage(e.getMessage());
                }
            }
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

}
