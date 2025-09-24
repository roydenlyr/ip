package shinchan;

import shinchan.commands.Command;
import shinchan.parsers.Parser;
import shinchan.data.DataManager;
import shinchan.exceptions.ShinChanException;
import shinchan.tasks.TaskList;
import shinchan.ui.Persona;

import java.io.IOException;
import java.util.Scanner;

public class Shinchan {
    public static void main(String[] args) {
        Persona persona = new Persona();
        DataManager dataManager = new DataManager("./data/data.txt");
        TaskList taskList = new TaskList(dataManager.loadData());
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
