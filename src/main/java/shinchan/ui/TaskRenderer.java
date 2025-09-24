package shinchan.ui;

import shinchan.tasks.Task;

import java.util.List;

public class TaskRenderer {
    private TaskRenderer() {}

    public static String numbered(List<Task> tasks) {
        StringBuilder list = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            list.append("\n").append(i + 1).append(". ").append(tasks.get(i));
        }
        return list.toString();
    }
}
