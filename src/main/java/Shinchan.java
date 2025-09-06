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
            Command command = sanitizeMessage(line);
            switch(command) {
            case BYE:
                printMessage(persona.bye());
                return;
            case LIST:
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
            case ADD:
                addTask(tasks, line, persona, command);
                break;
            case MARK:
                updateTaskStatus(tasks, line, true, persona);
                break;
            case UNMARK:
                updateTaskStatus(tasks, line, false, persona);
                break;
            case EMPTY:
                System.out.println("Please enter something");
                break;
            default:
                System.out.println("Invalid command");
            }
        }
    }

    public static void addTask(Task[] tasks, String line, Persona persona, Command command) {
        tasks[Task.getNumOfTasks()] = new Task(line);
        printMessage(persona.addTask() +
                "added: " + tasks[Task.getNumOfTasks() - 1].getDescription());
    }

    public static void updateTaskStatus(Task[] tasks, String line, boolean isDone, Persona persona) {
        if (Task.getNumOfTasks() == 0) {
            printMessage(persona.listEmpty());
            return;
        }
        int taskIndex = extractTrailingNumber(line) - 1;
        if (taskIndex < 0) return;
        if (taskIndex > Task.getNumOfTasks()) {
            printMessage("Hello? That task number is out of bounds!");
            return;
        }
        tasks[taskIndex].setDone(isDone);
        printMessage((isDone ? persona.markIntro() : persona.unmarkIntro()) + tasks[taskIndex].printTask());
    }

    public static void printMessage (String message) {
        System.out.println("\n====================");
        System.out.println(message);
        System.out.println("====================\n");
    }

    public static Command sanitizeMessage(String message) {
        try {
            if (message == null || message.isBlank()) {
                return Command.EMPTY;
            }

            message = message.trim().toUpperCase().split("\\s", 2)[0];
            return Command.valueOf(message);
        } catch (IllegalArgumentException e) {
            return Command.ADD;
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
            printMessage("Hello? Can include which index?");
            return -1;
        }
    }
}
