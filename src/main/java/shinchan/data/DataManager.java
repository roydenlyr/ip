package shinchan.data;

import shinchan.tasks.Deadline;
import shinchan.tasks.Event;
import shinchan.tasks.Task;
import shinchan.tasks.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.nio.charset.Charset;
import java.io.FileWriter;
import java.util.List;

public class DataManager {
    private File dataFile;

    public DataManager(String fileName) {
        dataFile = new File(fileName);
        createFile();
    }

    public File getDataFile() {
        return dataFile;
    }

    public void createFile() {
        try {
            if (dataFile.exists()) {
                System.out.println("File " + dataFile.getName() + " already exists.");
                return;
            }
            if (!dataFile.getParentFile().exists()) {
                dataFile.getParentFile().mkdirs();
            }
            dataFile.createNewFile();
        } catch (IOException e) {
            System.out.println("Cannot create file: " + e.getMessage());
        }
    }

    private ArrayList<String> readFile() throws IOException {
        if (!dataFile.exists()) {
            throw new FileNotFoundException();
        }
        if (dataFile.length() == 0) {
            System.out.println("empty file");
            return new ArrayList<>();
        }
        return (ArrayList<String>) Files.readAllLines(dataFile.toPath(), Charset.defaultCharset());
    }

    public ArrayList<Task> loadData() {
        ArrayList<Task> taskList = new ArrayList<>();
        try {
            ArrayList<String> dataItems = readFile();
            taskList = parse(dataItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taskList;
    }

    private ArrayList<Task> parse(ArrayList<String> dataItems) {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (String line : dataItems) {
            String taskDescription = getTaskDescription(line);
            String taskType = getTaskType(line);
            LocalDateTime[] date;
            switch (taskType) {
            case "T":
                Todo todo = new Todo(taskDescription);
                todo.setDone(isTaskDone(line));
                allTasks.add(todo);
                break;
            case "D":
                date = getTaskDate(line);
                Deadline deadline = new Deadline(taskDescription, date[0]);
                deadline.setDone(isTaskDone(line));
                allTasks.add(deadline);
                break;
            case "E":
                date = getTaskDate(line);
                Event event = new Event(taskDescription, date[0], date[1]);
                event.setDone(isTaskDone(line));
                allTasks.add(event);
                break;
            default:
                System.out.println("Unknown task encountered. Skipping");
                break;
            }
        }
        return allTasks;
    }

    private static String getTaskType(String line) {
        return Character.toString(line.charAt(1));
    }

    private static boolean isTaskDone(String line) {
        return line.charAt(4) == 'X';
    }

    private static String getTaskDescription(String line) {
        int start = 7;
        int end = line.indexOf("(");

        if (end == -1) {
            return line.substring(start).trim();
        }
        return line.substring(start, end).trim();
    }

    private static LocalDateTime[] getTaskDate(String line) {
        String type = getTaskType(line);
        String date = line.substring(line.indexOf('(') + 1, line.indexOf(')'));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
        if (type.equals("D")) {
            return new LocalDateTime[]{ LocalDateTime.parse(date.substring(date.indexOf(":") + 1).trim(), formatter) };
        }
        String from = date.substring(date.indexOf("from:") + 5, date.indexOf("to:")).trim();
        String to = date.substring(date.indexOf("to:") + 3).trim();

        return new LocalDateTime[]{ LocalDateTime.parse(from, formatter), LocalDateTime.parse(to, formatter) };
    }

    public static void writeToFile(List<Task> taskList) throws IOException {
        FileWriter fw = new FileWriter("./data/data.txt");
        for (Task task : taskList) {
            fw.write(task.toString() + "\n");
        }
        fw.close();
    }

    public static void appendToFile(Task task) throws IOException {
        FileWriter fw = new FileWriter("./data/data.txt", true);
        fw.write(task.toString() + "\n");
        fw.close();
    }
}
