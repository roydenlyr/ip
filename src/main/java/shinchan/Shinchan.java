package shinchan;

import shinchan.commands.Command;
import shinchan.commands.Commands;
import shinchan.commands.Parser;
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
                        persona.bye();
                        break;
                    }
//                    Commands command = Parser.parse(line);
//                    switch (command) {
//                    case LIST:
//                        taskList.showList();
//                        break;
//                    case MARK:
//                        taskList.updateTaskStatus(line, true);
//                        break;
//                    case UNMARK:
//                        taskList.updateTaskStatus(line, false);
//                        break;
//                    case TODO:
//                        taskList.addTodo(line);
//                        break;
//                    case DEADLINE:
//                        taskList.addDeadline(line);
//                        break;
//                    case EVENT:
//                        taskList.addEvent(line);
//                        break;
//                    case EMPTY:
//                        Persona.printMessage("Please enter something");
//                        break;
//                    case BYE:
//                        persona.bye();
//                        return;
//                    case DELETE:
//                        taskList.deleteTask(line);
//                        break;
//                    case FIND:
//                        taskList.findWord(line);
//                        break;
//                    case DATE:
//                        taskList.findDate(line);
//                        break;
//                    default:
//                        Persona.printMessage("Invalid command");
//                        break;
//                    }
                } catch (ShinChanException | IOException e) {
                    Persona.printMessage(e.getMessage());
                }
            }
    }
}
