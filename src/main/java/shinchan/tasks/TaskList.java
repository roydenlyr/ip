package shinchan.tasks;

import java.util.List;
import java.util.function.Predicate;

/**
 * Represents a collection of tasks in the Shinchan application.
 * <p>
 * Provides operations for adding, removing, updating,
 * and querying tasks.
 */
public class TaskList {
    private final List<Task> taskList;

    /**
     * Creates a new task list backed by the given list.
     *
     * @param taskList the initial list of tasks
     */
    public TaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the size of the task list
     */
    public int size() {
        return taskList.size();
    }

    /**
     * Retrieves the task at the specified index.
     *
     * @param index the position of the task (0-based)
     * @return the task at the given index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public Task get(int index) {
        return taskList.get(index);
    }

    /**
     * Adds a new task to the list.
     *
     * @param task the task to be added
     */
    public void add(Task task) {
        taskList.add(task);
    }

    /**
     * Removes the task at the specified index.
     *
     * @param index the position of the task to remove (0-based)
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public void removeAt(int index) {
        taskList.remove(index);
    }

    /**
     * Marks the task at the specified index as done or not done.
     *
     * @param index  the position of the task (0-based)
     * @param isDone true if marking as done, false if unmarking
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public void setDone(int index, boolean isDone) {
        taskList.get(index).setDone(isDone);
    }

    /**
     * Finds all tasks matching the given predicate.
     *
     * @param predicate the condition to match
     * @return a list of tasks that satisfy the predicate
     */
    public List<Task> find(Predicate<Task> predicate) {
        return taskList.stream().filter(predicate).toList();
    }

    /**
     * Returns an unmodifiable view of the task list.
     *
     * @return an unmodifiable list of tasks
     */
    public List<Task> asUnmodifiableList() {
        return List.copyOf(taskList);
    }
}
