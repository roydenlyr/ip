import java.util.Scanner;

public class Shinchan {
    public static void main(String[] args) {
        Persona persona = new Persona();
        persona.portrait();
        printMessage(persona.introduction());

        Task[] tasks = new Task[100];
        Scanner input = new Scanner(System.in);

        while(true) {
            String line = input.nextLine();
            String sanitizedTask = sanitizeMessage(line);
            switch(sanitizedTask) {
            case "bye":
                printMessage(persona.bye());
                return;
            case "list":
                int numOfTasks = Task.getNumOfTasks();
                StringBuilder msg = new StringBuilder();
                if (numOfTasks > 0) {
                    msg.append(persona.listIntro());
                    for (int i = 0; i < numOfTasks; i++) {
                        msg.append("\n").append(i + 1).append(". ").append(tasks[i].printTask());
                    }
                } else {
                    msg.append(persona.listEmpty());
                }
                printMessage(msg.toString());
                break;
            case "mark":
                updateTaskStatus(tasks, line, true, persona);
                break;
            case "unmark":
                updateTaskStatus(tasks, line, false, persona);
                break;
            default:
                addTask(tasks, line, persona, sanitizedTask);
                break;
            }
        }
    }

    public static void addTask(Task[] tasks, String line, Persona persona, String sanitizedTask) {
        if (sanitizedTask.isEmpty()) {
            printMessage("Please enter something useful");
            return;
        }
        tasks[Task.getNumOfTasks()] = new Task(line);
        printMessage(persona.addTask() +
                "added: " + tasks[Task.getNumOfTasks() - 1].getDescription());
    }

    public static void updateTaskStatus(Task[] tasks, String line, boolean isDone, Persona persona) {
        if (Task.getNumOfTasks() == 0) {
            persona.listIntro();
            return;
        }
        int taskIndex = extractTrailingNumber(line) - 1;
        if (taskIndex < 0) return;
        if (taskIndex > Task.getNumOfTasks()) {
            printMessage("Hello? That task number is out of bounds!");
            return;
        }
        tasks[taskIndex].setDone(isDone);
        printMessage(isDone ? persona.markIntro() : persona.unmarkIntro() + tasks[taskIndex].printTask());
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
        try {
            int lastIndex = message.length() - 1;
            while (lastIndex >= 0 && Character.isDigit(message.charAt(lastIndex))) {
                lastIndex--;
            }
            return Integer.parseInt(message.substring(lastIndex + 1));
        } catch (NumberFormatException e) {
            printMessage("Hello? Can include which index?");
            return -1;
        }
    }
}
