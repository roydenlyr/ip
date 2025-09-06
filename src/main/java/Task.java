public class Task {
    protected String description;
    protected boolean isDone;
    private static int numOfTasks = 0;

    public Task(String description) {
        setDescription(description);
        setDone(false);
        numOfTasks++;
    }

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

    public static int getNumOfTasks() {
        return numOfTasks;
    }

    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]");
    }

    public String printTask() {
        return (getStatusIcon() + " " + getDescription());
    }
}
