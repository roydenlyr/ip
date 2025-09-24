package shinchan.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task in the Shinchan application.
 * <p>
 * A deadline task has a description and must be completed
 * by a specific date and time.
 */
public class Deadline extends Todo{
    protected LocalDateTime by;
    protected final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");

    /**
     * Creates a new deadline task with the given description and due date.
     *
     * @param description the description of the deadline task
     * @param by          the date and time by which the task is due
     */
    public Deadline(String description, LocalDateTime by){
        super(description);
        this.by = by;
    }

    /**
     * Checks whether this deadline occurs on the given date and time.
     * <p>
     * This implementation compares equality with the stored deadline time.
     *
     * @param date the date and time to check
     * @return true if the deadline is exactly at the given date, false otherwise
     */
    public boolean occursOn(LocalDateTime date) {
        return by.isEqual(date);
    }

    /**
     * Returns the due date of this deadline task.
     *
     * @return the due date as a {@link LocalDateTime}
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Returns a string representation of the deadline task.
     * <p>
     * The format is:
     * <pre>
     * [D][status] description (by: MMM dd yyyy HHmm)
     * </pre>
     *
     * @return the formatted deadline string
     */
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
        return "[D]" + getStatusIcon() + " " + getDescription() +
                " (by: " + by.format(FORMATTER) + ")";
    }
}
