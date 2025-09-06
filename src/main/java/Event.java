public class Event extends Deadline{
    protected String to, from;

    public Event(String description, String from, String to) {
        super(description);
        this.to = to;
        this.from = from;
    }

    public String toString() {
        return "[E]" + getStatusIcon() + " " + getDescription() +
                " (from: " + from + " to: " + to + ")";
    }
}
