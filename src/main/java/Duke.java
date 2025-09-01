import java.util.Scanner;
import java.util.Arrays;

public class Duke {
    public static void main(String[] args) {
        String[] tasks = new String[100];
        int taskCounter = 0;
        printMessage("Hello! I'm Duke.\n" +
                "What can I do for you?");

        while(true) {
            String line = new Scanner(System.in).nextLine();
            String sanitizedTask = sanitizeMessage(line);
            switch(sanitizedTask) {
            case "bye":
                printMessage("Bye. Hope to see you again soon!");
                return;
            case "list":
                String list = "";
                for (int i = 0; i < taskCounter; i++) {
                    list += (i + 1) + ": " + tasks[i] + (i == taskCounter - 1 ? "" : "\n");
                }
                printMessage(list);
                break;
            default:
                tasks[taskCounter] = line;
                printMessage("added: " + tasks[taskCounter]);
                taskCounter++;
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
        return message.trim().toLowerCase();
    }
}
