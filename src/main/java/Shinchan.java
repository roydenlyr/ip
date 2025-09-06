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
            Command command = extractCommand(line);
            switch(command) {
            case LIST:
                showList(persona, tasks);
                break;
            case ADD:
                addTask(tasks, line, persona);
                break;
            case MARK:
                updateTaskStatus(tasks, line, true, persona);
                break;
            case UNMARK:
                updateTaskStatus(tasks, line, false, persona);
                break;
            case TODO:
                addTodo(line, tasks);
                break;
            case DEADLINE:
                addDeadline(line, tasks);
                break;
            case EVENT:
                addEvent(line, tasks);
                break;
            case EMPTY:
                System.out.println("Please enter something");
                break;
            case BYE:
                printMessage(persona.bye());
                return;
            default:
                System.out.println("Invalid command");
                break;
            }
        }
    }

    private static void addEvent(String line, Task[] tasks) {
        String[] details = extractContent(line).split("/");

        String description = details[0].trim();
        String from = details[1].split("\\s", 2)[1].trim();
        String to = details[2].split("\\s", 2)[1].trim();

        tasks[Task.getNumOfTasks()] = new Event(description, from, to);
        Persona persona = new Persona();
        printMessage(persona.displayAddConfirmation(tasks[Task.getNumOfTasks() - 1]));
    }

    private static void addDeadline(String line, Task[] tasks) {
        String[] details = extractContent(line).split("/");
        String description = details[0].trim();
        String deadline = details[1].split("\\s", 2)[1].trim();
        tasks[Task.getNumOfTasks()] = new Deadline(description, deadline);
        Persona persona = new Persona();
        printMessage(persona.displayAddConfirmation(tasks[Task.getNumOfTasks() - 1]));
    }

    private static void addTodo(String line, Task[] tasks) {
        String description = extractContent(line);
        tasks[Task.getNumOfTasks()] = new Todo(description);
        Persona persona = new Persona();
        printMessage(persona.displayAddConfirmation(tasks[Task.getNumOfTasks() - 1]));
    }

    private static String extractContent(String line) {
        return line.split("\\s", 2)[1].trim();
    }

    private static void showList(Persona persona, Task[] tasks) {
        int numOfTasks = Task.getNumOfTasks();
        StringBuilder msg = new StringBuilder();
        if (numOfTasks > 0) {
            msg.append(persona.listIntro());
            for (int i = 0; i < numOfTasks; i++) {
                msg.append("\n").append(i + 1).append(". ").append(tasks[i]);
            }
        } else {
            msg.append(persona.listEmpty());
        }
        printMessage(msg.toString());
    }

    public static void addTask(Task[] tasks, String line, Persona persona) {
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
        printMessage((isDone ? persona.markIntro() : persona.unmarkIntro()) + tasks[taskIndex]);
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
