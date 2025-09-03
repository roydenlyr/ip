import java.util.Scanner;

public class Shinchan {
    public static void main(String[] args) {
        Persona persona = new Persona();
        Task[] tasks = new Task[100];
        persona.portrait();
        printMessage(persona.introduction());

        while(true) {
            String line = new Scanner(System.in).nextLine();
            String sanitizedTask = sanitizeMessage(line);
            String message;
            int taskIndex;
            switch(sanitizedTask) {
            case "bye":
                printMessage(persona.bye());
                return;
            case "list":
                int numOfTasks = Task.getNumOfTasks();
                if (numOfTasks > 0) {
                    message = persona.listIntro();
                    for (int i = 0; i < Task.getNumOfTasks(); i++) {
                        message += "\n" + (i + 1) + "." + tasks[i].getStatusIcon() + " " +
                                tasks[i].getDescription();
                    }
                } else {
                    message = persona.listEmpty();
                }
                printMessage(message);
                break;
            case "mark":
                taskIndex = extractTrailingNumber(line) - 1;
                tasks[taskIndex].setDone(true);
                message = persona.markIntro() +
                        tasks[taskIndex].getStatusIcon() + " " +
                        tasks[taskIndex].getDescription();
                printMessage(message);
                break;
            case "unmark":
                taskIndex = extractTrailingNumber(line) - 1;
                tasks[taskIndex].setDone(false);
                message = persona.unmarkIntro() +
                        tasks[taskIndex].getStatusIcon() + " " +
                        tasks[taskIndex].getDescription();
                printMessage(message);
                break;
            default:
                tasks[Task.getNumOfTasks()] = new Task(line);
                printMessage(persona.addTask() +
                        "added: " + tasks[Task.getNumOfTasks() - 1].getDescription());
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
