package shinchan.tasks;

/**
 * Represents a to-do task in the Shinchan application.
 * <p>
 * A to-do is a basic task containing only a description
 * without an associated date or time.
 */
public class Todo extends Task{
    /**
     * Creates a new to-do task with the specified description.
     *
     * @param description the description of the to-do task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the to-do task.
     * <p>
     * The format includes a type indicator {@code [T]}
     * followed by the task description and status.
     *
     * @return the string representation of the to-do
     */
    public String toString() {
        return "[T]" + super.toString();
    }
}
