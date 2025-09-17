package shinchan.tasks;

public class Event extends Deadline{
    protected String from;

    public Event(String description, String from, String by) {
        super(description, by);
        this.from = from;
    }

    public String toString() {
        return "[E]" + getStatusIcon() + " " + getDescription() +
                " (from: " + from + " to: " + by + ")";
    }
}
