package shinchan;

import shinchan.commands.Command;
import shinchan.parsers.Parser;
import shinchan.data.DataManager;
import shinchan.exceptions.ShinChanException;
import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

import java.io.IOException;
import java.util.Scanner;

/**
 * Entry point for the Shinchan application.
 * <p>
 * This class initializes the persona (UI), data manager,
 * and task list. It then enters the main loop where it
 * continuously accepts and executes user commands until
 * the exit command is given.
 */
public class Shinchan {
    /**
     * Main method of the Shinchan program.
     * <p>
     * Initializes required components (UI, data manager, task list),
     * and processes user commands in a loop. Exits when a terminating
     * command is entered.
     *
     * @param args Command-line arguments (not used).
     * @throws IOException If there is an error loading or saving data.
     */
    public static void main(String[] args) throws IOException {
        Persona persona = new Persona();
        DataManager dataManager = new DataManager("./data/data.txt");
        TaskList taskList = new TaskList(dataManager.load());
        Scanner input = new Scanner(System.in);

        persona.showIntroduction();
        while(true) {
            try {
                String line = input.nextLine();
                Command command = Parser.parse(line);
                command.execute(taskList, persona);
                if (command.isExit()) {
                    break;
                }
            } catch (ShinChanException | IOException e) {
                Persona.printMessage(e.getMessage());
            }
        }
    }
}
