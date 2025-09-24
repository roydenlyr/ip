package shinchan.ui;

import shinchan.tasks.Task;

import java.util.List;

/**
 * Utility class for rendering tasks into user-friendly
 * string formats suitable for displaying in the UI.
 * <p>
 * This class cannot be instantiated and only provides
 * static rendering methods.
 */
public class TaskRenderer {
    private TaskRenderer() {
//        Prevent instantiation
    }

    /**
     * Renders a list of tasks as a numbered string.
     * <p>
     * Each task is prefixed with its 1-based index in the list.
     *
     * @param tasks the list of tasks to render
     * @return a string containing the tasks formatted as:
     *         <pre>
     *         1. Task A
     *         2. Task B
     *         3. Task C
     *         </pre>
     */
    public static String numbered(List<Task> tasks) {
        StringBuilder list = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            list.append("\n").append(i + 1).append(". ").append(tasks.get(i));
        }
        return list.toString();
    }
}
