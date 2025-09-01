import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        printMessage("Hello! I'm Duke\n" +
                "What can I do for you?");

        while(true) {
            String line = new Scanner(System.in).nextLine();
            if(sanitizeMessage(line).equals("bye")){
                printMessage("Bye. Hope to see you again soon!");
                return;
            }
            printMessage(line);
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
