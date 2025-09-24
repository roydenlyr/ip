package shinchan.tasks;

import java.time.LocalDateTime;

/**
 * Represents a generic task in the Shinchan application.
 * <p>
 * A task has a description and a completion status.
 * Subclasses such as {@code Todo}, {@code Deadline}, and {@code Event}
 * may add additional fields like dates or times.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new task with the given description.
     * The task is initially marked as not done.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        setDescription(description);
        setDone(false);
    }

    /**
     * Creates a task with no description.
     * Used primarily for subclass initialization.
     */
    public Task() {}

    /**
     * Returns the description of this task.
     *
     * @return the task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the description of this task.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Checks whether this task is marked as done.
     *
     * @return true if the task is done, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Sets the completion status of this task.
     *
     * @param done true if marking as done, false if marking as not done
     */
    public void setDone(boolean done) {
        isDone = done;
    }

    /**
     * Returns a status icon representing the completion state.
     * <p>
     * {@code [X]} for done, {@code [ ]} for not done.
     *
     * @return the status icon as a string
     */
    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]");
    }

    /**
     * Checks if this task occurs on the given date.
     * <p>
     * The base implementation always returns {@code false};
     * subclasses like {@code Deadline} and {@code Event}
     * should override this to provide date-specific checks.
     *
     * @param date the date to check
     * @return true if the task occurs on the given date, false otherwise
     */
    public boolean occursOn(LocalDateTime date) {
        return false;
    }

    /**
     * Returns a string representation of the task,
     * including its status icon and description.
     *
     * @return the formatted task string
     */
    public String toString() {
        return (getStatusIcon() + " " + getDescription());
    }
}
