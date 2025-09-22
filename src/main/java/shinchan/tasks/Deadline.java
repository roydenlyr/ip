package shinchan.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Todo{
    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime by){
        super(description);
        this.by = by;
    }

    public Deadline(String description) {
        super(description);
    }

    public LocalDateTime getBy() {
        return by;
    }

    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
        return "[D]" + getStatusIcon() + " " + getDescription() +
                " (by: " + by.format(formatter) + ")";
    }
}
