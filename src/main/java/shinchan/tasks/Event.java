package shinchan.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Deadline{
    protected LocalDateTime from;

    public Event(String description, LocalDateTime from, LocalDateTime by) {
        super(description, by);
        this.from = from;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public boolean occursOn(LocalDateTime date) {
        return !date.isBefore(from) && !date.isAfter(by);
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
        return "[E]" + getStatusIcon() + " " + getDescription() +
                " (from: " + from.format(formatter) + " to: " + by.format(formatter) + ")";
    }
}
