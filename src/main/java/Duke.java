import java.util.Scanner;
import java.util.Arrays;

public class Duke {
    public static void main(String[] args) {
        Task[] tasks = new Task[100];
        printMessage("Hello! I'm Duke.\n" +
                "What can I do for you?");

        while(true) {
            String line = new Scanner(System.in).nextLine();
            String sanitizedTask = sanitizeMessage(line);
            String message = "";
            int taskIndex = 0;
            switch(sanitizedTask) {
            case "bye":
                printMessage("Bye. Hope to see you again soon!");
                return;
            case "list":
                message = "Here are the tasks in your list: \n";
                for(int i = 0; i < Task.getNumOfTasks(); i++) {
                    message += (i + 1) + "." + tasks[i].getStatusIcon() + " " +
                            tasks[i].getDescription() +
                            (i == Task.getNumOfTasks() - 1 ? "" : "\n");
                }
                printMessage(message);
                break;
            case "mark":
                taskIndex = extractTrailingNumber(line) - 1;
                tasks[taskIndex].setDone(true);
                message = "Nice! I've marked this task as done:\n" +
                        tasks[taskIndex].getStatusIcon() + " " +
                        tasks[taskIndex].getDescription();
                printMessage(message);
                break;
            case "unmark":
                taskIndex = extractTrailingNumber(line) - 1;
                tasks[taskIndex].setDone(false);
                message = "Ok, I've marked this task as not done yet:\n" +
                        tasks[taskIndex].getStatusIcon() + " " +
                        tasks[taskIndex].getDescription();
                printMessage(message);
                break;
            default:
                tasks[Task.getNumOfTasks()] = new Task(line);
                printMessage("added: " + tasks[Task.getNumOfTasks() - 1].getDescription());
                break;
            }
        }
    }

    public static void printMessage (String message) {
        System.out.println("\n====================");
        System.out.println(message);
        System.out.println("====================\n");
    }

    public static String sanitizeMessage(String message) {
        message = message.trim().toLowerCase();
        if (message.contains("unmark")) {
            return "unmark";
        }
        if (message.contains("mark")) {
            return "mark";
        }
        return message;
    }

    public static int extractTrailingNumber(String message) {
        int lastIndex = message.length() - 1;
        while (lastIndex >= 0 && Character.isDigit(message.charAt(lastIndex))) {
            lastIndex--;
        }
        return Integer.parseInt(message.substring(lastIndex + 1));
    }
}
