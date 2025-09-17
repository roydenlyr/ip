package shinchan.tasks;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        setDescription(description);
        setDone(false);
    }

    public Task() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]");
    }

    public String toString() {
        return (getStatusIcon() + " " + getDescription());
    }
}
