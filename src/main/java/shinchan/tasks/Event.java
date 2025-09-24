package shinchan.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task in the Shinchan application.
 * <p>
 * An event task has a description, a start time, and an end time.
 * It is considered to occur on all dates/times between {@code from} and {@code by}.
 */
public class Event extends Deadline{
    protected LocalDateTime from;

    /**
     * Creates a new event task with the given description, start time, and end time.
     *
     * @param description the description of the event
     * @param from        the start date and time of the event
     * @param by          the end date and time of the event
     */
    public Event(String description, LocalDateTime from, LocalDateTime by) {
        super(description, by);
        this.from = from;
    }

    /**
     * Returns the start date and time of this event.
     *
     * @return the start time as a {@link LocalDateTime}
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Checks whether this event occurs on the given date and time.
     * <p>
     * Returns true if the date falls between {@code from} and {@code by}, inclusive.
     *
     * @param date the date and time to check
     * @return true if the event occurs at the given date, false otherwise
     */
    public boolean occursOn(LocalDateTime date) {
        return !date.isBefore(from) && !date.isAfter(by);
    }

    /**
     * Returns a string representation of the event task.
     * <p>
     * The format is:
     * <pre>
     * [E][status] description (from: MMM dd yyyy HHmm to: MMM dd yyyy HHmm)
     * </pre>
     *
     * @return the formatted event string
     */
    public String toString() {

        return "[E]" + getStatusIcon() + " " + getDescription() +
                " (from: " + from.format(FORMATTER) + " to: " + by.format(FORMATTER) + ")";
    }
}
